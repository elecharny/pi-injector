package jppf;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.servlet.ServletContext;

import org.jppf.JPPFException;
import org.jppf.client.AbstractJPPFClientConnection;
import org.jppf.client.JPPFClient;
import org.jppf.client.JPPFJob;
import org.jppf.management.JMXDriverConnectionWrapper;
import org.jppf.management.JPPFDriverAdminMBean;
import org.jppf.management.JPPFNodeTaskMonitorMBean;
import org.jppf.management.NodeSelector;
import org.jppf.management.TaskExecutionNotification;
import org.jppf.management.forwarding.JPPFNodeForwardingNotification;

import dataExtraction.DataByInjector;
import scripts.AbstractScript;
import scripts.LDAPScript;


public class GridClient {
	
	/*********************************************** SINGLETON */
	
	private static GridClient						instance;
	private static Object							synchronizer = new Object();
	
	public static GridClient getInstance() {
		
		if (instance == null) {
			synchronized (synchronizer) {
				if (instance == null) {
					instance = new GridClient();
				}
			}
		}

		return instance;
	}
	
	/*********************************************** SINGLETON */
	
	
	
	
	private int 									nodesCount = 0;
	private JPPFClient 								jppfClient = null;
	private AbstractJPPFClientConnection 			jppfClientConn = null;
	private JMXDriverConnectionWrapper				driverJmx = null;
	
	private HashMap<String, List<GenericResult>>	mapGenericResult = null;
	
	
	public GridClient() {
		// Connect to the JPPF Grid
		establishGridConnection();
		// Create a listener to receive notifications from JPPF nodes
		createJMXNodeListener();
		// Get the number of connected JPPF nodes
		refreshNodesCount();
		
		mapGenericResult = new HashMap<String, List<GenericResult>>();
	}

	
	
	private void establishGridConnection() {
		
		jppfClient = new JPPFClient();
		
		// Waiting for the client connection
		while (jppfClientConn == null) {
			try {
				Thread.sleep(100L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			jppfClientConn = 
					(AbstractJPPFClientConnection) jppfClient.getClientConnection();
		}
		
		driverJmx = jppfClientConn.getConnectionPool().getJmxConnection();
	}
	
	
	private void createJMXNodeListener() {
		
		if (driverJmx != null) {			
			
			// Select all nodes
			NodeSelector selector = new NodeSelector.AllNodesSelector();
			
			// Create a listener
			NotificationListener listener = new NotificationListener() {
				@SuppressWarnings("unchecked")
				@Override
				public void handleNotification(
						Notification notification, Object handback) {
					
					JPPFNodeForwardingNotification wrapping = 
							(JPPFNodeForwardingNotification) notification;
					
					System.out.println(
							"Received notification from nodeUuid : " 
									+ wrapping.getNodeUuid()
									+ ", mBeanName : " + wrapping.getMBeanName());
					
					System.out.println("Before notif'");
					TaskExecutionNotification notif = 
							(TaskExecutionNotification) wrapping.getNotification();
					System.out.println("After notif'");
					
					synchronized(mapGenericResult){
						if(mapGenericResult.containsKey(wrapping.getNodeUuid())){
							mapGenericResult.get(wrapping.getNodeUuid()).addAll((List<GenericResult>)notif.getUserData());
							System.out.println("OK");
						}
						else{
							mapGenericResult.put(wrapping.getNodeUuid(), (List<GenericResult>)notif.getUserData());
							System.out.println("KO");
						}
					}
					
					
					System.out.println(
							"Notif content : " + ((List<GenericResult>)notif.getUserData()).get(0).getTotalScriptExecutionTime());
				}
			};
			
			// Register the listener
			try {
				driverJmx.registerForwardingNotificationListener(
						selector, JPPFNodeTaskMonitorMBean.MBEAN_NAME, 
						listener, null, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void createJMXNodeListener(NotificationListener notifListener) {
		
		if (driverJmx != null) {		
			
			// Select all nodes
			NodeSelector selector = new NodeSelector.AllNodesSelector();
			
			// Create a listener
			NotificationListener listener = notifListener;
			
			// Register the listener
			try {
				driverJmx.registerForwardingNotificationListener(
						selector, JPPFNodeTaskMonitorMBean.MBEAN_NAME, 
						listener, null, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public int refreshNodesCount() {
		
		if (driverJmx != null) {

			try {
				nodesCount = (Integer)driverJmx.invoke(
						JPPFDriverAdminMBean.MBEAN_NAME, "nbIdleNodes");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return nodesCount;
	}
	
	
	
	
	public void launchScriptList(List<AbstractScript> scripts,String name,
			int nbInjector,int nbIteration,ServletContext context) {
		
		JPPFJob jppfJob = new JPPFJob();
		jppfJob.setName("LDAP Injection Job");
		
		// TODO: Modifier ici pour utiliser TOUS les scripts fournis
		// et faire des vérifs pour savoir s'il n'y a pas plus de scripts fournis
		// que d'injecteurs disponibles
		
		for (int i = 0; i < nodesCount; i++) {
			InjectionTask jppfTask = new InjectionTask(scripts.get(0));
			jppfTask.setId(jppfJob.getName() + " - Task " + i);
			try {
				jppfJob.add(jppfTask, (Object[])null);
				System.out.println("Task " + jppfTask.getId() + " added to the job...");
			} catch (JPPFException e) {
				e.printStackTrace();
			}
		}
		
		try {
			System.out.println("Blocking job ready for execution...");
			
			jppfJob.setBlocking(true);
			jppfClient.submitJob(jppfJob);
			
			// Une fois que le job est terminé, on va traiter et agréger les données
			aggregationData(shortAndFindMinCurrentTime());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void launchBroadcastScript(AbstractScript script) {
		
		JPPFJob jppfJob = new JPPFJob();
		jppfJob.setName("LDAP Injection Job");
		jppfJob.getSLA().setBroadcastJob(true);
		
		InjectionTask jppfTask = new InjectionTask(script);
		jppfTask.setId(jppfJob.getName() + " - Task broadcasted");
		
		try {
			jppfJob.add(jppfTask, (Object[])null);
		} catch (JPPFException e1) {
			e1.printStackTrace();
		}
		
		jppfJob.setBlocking(false);
		jppfClient.submitJob(jppfJob);
		
		System.out.println("Job submitted to " + refreshNodesCount() + " nodes.");
		
		// Do something here...
		try {
			Thread.sleep(30000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Test du job cancel pour arrêter les noeuds
		jppfJob.cancel();
		jppfJob.awaitResults();
	}
	
	
	public long shortAndFindMinCurrentTime() {
		
		// On commence par trier toutes les listes pour chaque noeud
		for(List<GenericResult> list : mapGenericResult.values()){
			Collections.sort(list);
		}
		
		//une fois les listes triées, on va chercher la plus petite valeur 
		// pour le current time, ce qui va nous servir de référentiel pour agréger les données
		// (cette valeur sera donc notre temps 0) 
		long minCurrentTime = 0;
		for(List<GenericResult> list : mapGenericResult.values()){
			if(list.size() != 0){
				if(minCurrentTime != 0 && minCurrentTime > list.get(0).getStartTime())
					minCurrentTime = list.get(0).getStartTime();
				if(minCurrentTime == 0)
					minCurrentTime = list.get(0).getStartTime();
			}
		}
		return minCurrentTime;
	}
	
	public void aggregationData(Long minCurrentTime){

		TreeMap<Integer, List<DataByInjector>> agregation = new TreeMap<Integer, List<dataExtraction.DataByInjector>>();
		
		//on va stocker la liste de nom des tache
		List<String> listNameTask = new ArrayList<String>();
		
		//maintenant, pour chaque noeuds, on va calculer le nombre de requête par seconde
		for(Entry<String, List<GenericResult>> entry : mapGenericResult.entrySet()){
			listNameTask.add(entry.getKey());
			

			int nbRequestSec = 0;
			long nbSec = 0;
			
			// premierPassage va nous servir a savoir la différence entre le premier noeud lancer et les autres
			// on pourra donc calculer une différence de temps enter les deux
			Boolean premierPassage = true;
			
			
			for(GenericResult result : entry.getValue()){
				
				if(premierPassage){
					
					if(minCurrentTime < result.getStartTime()){
						nbSec = result.getStartTime() - minCurrentTime;
						System.out.println("NB DE SEC " + nbSec);
					}
				}
					
				for(Long duration : result.getRequestsExecutionTimes()){
					
					if(premierPassage){
						premierPassage = false;
					}
					if((nbSec + duration)%1000000000L  <= nbSec%1000000000L){
						nbRequestSec++;
						nbSec+=duration;
						System.out.println(nbSec);
					}else{
						Integer secSearch = new Integer((int) (nbSec/1000000000L));
						if(agregation.containsKey(secSearch)){
							//on vérifie que l'objet n'existe qu'en un seul exemplaire par seconde pour chaque noeud
							// si c'est le cas, on ajoute dans l'bjet existant
							int position = agregation.get(secSearch).indexOf(new DataByInjector(entry.getKey(), 0));
							if(position != -1){
								agregation.get(secSearch).get(position).setNbRequest(agregation.get(secSearch).get(position).getNbRequest() + nbRequestSec);
							}else{
								agregation.get(secSearch).add(new DataByInjector(entry.getKey(), nbRequestSec));
							}
						}else{
							ArrayList<DataByInjector> list = new ArrayList<DataByInjector>();
							list.add(new DataByInjector(entry.getKey(), nbRequestSec));
							agregation.put(secSearch, list);
						}
						nbSec+=duration;
						nbRequestSec = 1;
					}
				}
			}
		}
		
		// maintenant on crée le fichier total
		PrintWriter writer;
		
		try{
			writer = new PrintWriter("test1-" + nodesCount + ".csv", "UTF-8");
			int agreg;
			for(Entry<Integer, List<DataByInjector>> entry : agregation.entrySet()) {
				System.out.println(entry.getKey());
				StringBuilder str = new StringBuilder();
				str.append(entry.getKey()+ ";");
				agreg = 0;
				for(String name : listNameTask){
					Boolean isPresent = false;
					for(DataByInjector data : entry.getValue())
					{
						if(data.getTaskID().equals(name)){
							str.append(data.getNbRequest() +";");
							agreg += data.getNbRequest();
							isPresent = true;
						}
					}
					if(!isPresent){
						str.append("0;");
					}
				}
				str.append(agreg);
				writer.println(str);
			}
			writer.close();
			
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	
	public void shutdown(){
		jppfClient.close();
	}
	
	public static void main(String[] args) {
		
		System.out.println("Starting...");
		
		LDAPScript ldapScript = new LDAPScript("192.168.1.28", 10389);
		ldapScript.addBindRequest("uid=admin,ou=system", "secret");
		
		/*GridClient client = new GridClient();
		//client.launchBroadcastScript(ldapScript);
		
		List<AbstractScript> scripts = new ArrayList<>();
		scripts.add(ldapScript);
		client.launchScriptList(scripts);
		
		System.out.println("Finishing...");*/
	}

}
