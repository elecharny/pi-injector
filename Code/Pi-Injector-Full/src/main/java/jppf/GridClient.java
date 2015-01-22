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
					
					if(mapGenericResult.containsKey(wrapping.getNodeUuid()))
						mapGenericResult.get(wrapping.getNodeUuid()).addAll((List<GenericResult>)notif.getUserData());
					else
						mapGenericResult.put(wrapping.getNodeUuid(), (List<GenericResult>)notif.getUserData());
					
					
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
						JPPFDriverAdminMBean.MBEAN_NAME, "nbIdlesNodes");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return nodesCount;
	}
	
	
	
	
	public void launchScriptList(List<AbstractScript> scripts) {
		
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
			
			long min = 0;
			int nbRequestSec = 0;
			int nbSec = 0;
			int nbReqParSec = 0;
			
			// premierPassage va nous servir a savoir la différence entre le premier noeud lancer et les autres
			// on pourra donc calculer une différence de temps enter les deux
			Boolean premierPassage = true;
			
			
			for(GenericResult result : entry.getValue()){
				
				if(premierPassage){
					
					premierPassage = false;
					if(minCurrentTime < result.getStartTime()){
						nbSec = (int) ((result.getStartTime() - minCurrentTime)/1000L);
					}
					min = result.getStartTime()/1000000000L;
				}
					
				for(Long duration : result.getRequestsExecutionTimes()){
					nbReqParSec++;
						
					if(duration/1000000000L <= min){
						nbRequestSec++;
					}else{
						if(agregation.containsKey(nbSec)){
							agregation.get(nbSec).add(new DataByInjector(entry.getKey(), nbRequestSec));
						}else{
							ArrayList<DataByInjector> list = new ArrayList<DataByInjector>();
							list.add(new DataByInjector(entry.getKey(), nbRequestSec));
							agregation.put(nbSec, list);
						}
						nbRequestSec = 1;
						nbSec += duration/1000000000L - min;
						min = duration/1000000000L;
					}
				}
			}
		}
		
		// maintenant on crée le fichier total
		PrintWriter writer;
		
		try{
			writer = new PrintWriter("test-" + nodesCount + ".csv", "UTF-8");
			int agreg;
			for(Entry<Integer, List<DataByInjector>> entry : agregation.entrySet()) {
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
		
		GridClient client = new GridClient();
		//client.launchBroadcastScript(ldapScript);
		
		List<AbstractScript> scripts = new ArrayList<>();
		scripts.add(ldapScript);
		client.launchScriptList(scripts);
		
		System.out.println("Finishing...");
	}

}
