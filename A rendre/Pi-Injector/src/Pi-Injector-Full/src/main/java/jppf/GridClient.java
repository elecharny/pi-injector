package jppf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


/**
 * Classe permettant à l'interface graphique de s'abstraire complètement du fonctionnement
 * du framework JPPF. Cette classe utilise le pattern singleton
 */
public class GridClient {
	/*-------------------------------------------------------------- SINGLETON */
	private static GridClient						instance;
	private static Object							synchronizer = new Object();
	
	/**
	 * Permet la gestion d'un singleton
	 * @return Instance unique de GridClient
	 */
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
	/*-------------------------------------------------------------- SINGLETON */
	
	
	private int 									nodesCount = 0;
	private JPPFClient 								jppfClient = null;
	private AbstractJPPFClientConnection 			jppfClientConn = null;
	private JMXDriverConnectionWrapper				driverJmx = null;
	private HashMap<String, List<GenericResult>>	mapGenericResult = null;
	private static int								notificationInterval = 100;
	private int										nbIterationTotal = 0;
	private int										iterationEffectuees = 0;
	private Object									lockIterationEffectuees = new Object();
	private String									name = null;
	private Map<String, Double>						mapPourcentage = null;
	
	
	public GridClient() {
		// Connect to the JPPF Grid
		establishGridConnection();
		// Create a listener to receive notifications from JPPF nodes
		createJMXNodeListener();
		// Get the number of connected JPPF nodes
		refreshNodesCount();
		
		mapGenericResult = new HashMap<String, List<GenericResult>>();
	}

	/**
	 * Permet de récupérer le jmx connection
	 */
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
	
	/**
	 * Permet de créé des listener qui vont permettre de récupérer les données envoyées
	 * par les noeuds et de mettre à jour l'avancement de l'exécution dans le context servlet.
	 */
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
					TaskExecutionNotification notif = 
							(TaskExecutionNotification) wrapping.getNotification();
					
					// Si userData != null alors il s'agit d'une notification à nous 
					// (et pas une notif système)
					if (notif.getUserData() != null) {
						synchronized (lockIterationEffectuees) {
							System.out.println(
									"Received notification from nodeUuid : " 
											+ wrapping.getNodeUuid());
							try {
								synchronized(mapGenericResult) {
									if(mapGenericResult.containsKey(wrapping.getNodeUuid())) {
										mapGenericResult.get(wrapping.getNodeUuid()).addAll((List<GenericResult>)notif.getUserData());
									}
									else {
										mapGenericResult.put(wrapping.getNodeUuid(), (List<GenericResult>)notif.getUserData());
									}
								}
							}
							catch (Exception e) {
								System.out.println(e);
							}
							
							try {
								iterationEffectuees += ((List<GenericResult>)notif.getUserData()).size();
								
								System.out.println("iterationEffectuees : " + iterationEffectuees);
								System.out.println("nbIterationTotal : " + nbIterationTotal);
								System.out.println((iterationEffectuees / new Double(nbIterationTotal)) * 100 + " %");
								
								if (mapPourcentage != null) {
									synchronized (mapPourcentage) {
										// Ajout des données d'exécution reçues
										mapPourcentage.put(name, 
												iterationEffectuees / new Double(nbIterationTotal));
										
										// Une fois que le job est terminé, on va traiter et agréger les données
										if (iterationEffectuees >= nbIterationTotal) {
											// Aggrégation des données et écriture dans un fichier
											aggregationData(shortAndFindMinCurrentTime());
											
											// Suppression des données envoyées par les noeuds
											// pour ce test
											if (mapGenericResult != null) {
												synchronized (mapGenericResult) {
													mapGenericResult.clear();
												}
											}
											
											// Suppression du pourcentage d'avancement pour ce test
											mapPourcentage.remove(name);
										}
									}
								}
							} catch (Exception e) {
								System.out.println(e);
							}
						}
					}
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
	
	/**
	 * Permet d'abonner le listener mis en paramètre sur l'envoie des données
	 * @param notifListener
	 */
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
	
	/**
	 * Permet de rafraichir le nombre de noeuds connecté sur le réseau.
	 * @return nombre de noeuds présent.
	 */
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
	
	
	/**
	 * Permet d'envoyer le scénario sur un nombre de noeuds déterminé et d'attendre que tout les noeuds ont
	 * terminé d'effectuer leurs tasks.
	 * @param script script représentant
	 * @param name nom donnée via l'interface, va nous servir pour le nom du fichier créé lors de l'agregation
	 * @param nbInjector nombre d'injecteur utilisé pour le test
	 * @param nbIteration nombre d'itération de scénario effectué par les injecteurs
	 * @param context context servlet, permet de mettre à jour la donnée pourcentage (contenu dans une map)
	 */
	@SuppressWarnings("unchecked")
	public void launchScript(
			AbstractScript	script,
			String			name,
			int				nbInjector,
			int				nbIteration,
			ServletContext	context
	) {
		synchronized (lockIterationEffectuees) {
			iterationEffectuees = 0;
		}
		
		JPPFJob jppfJob = new JPPFJob();
		jppfJob.setName(name);
		this.name = name;
		
		if(nbInjector > nodesCount)
			nbInjector = nodesCount;
		
		this.nbIterationTotal = nbIteration * nbInjector;
		
		if(context.getAttribute("TestProgress") != null) {
			this.mapPourcentage = (HashMap<String, Double>) context.getAttribute("TestProgress");
			synchronized(mapPourcentage) {
				mapPourcentage.put(name, new Double(0));
			}
		}
		else {
			mapPourcentage = new HashMap<String, Double>();
			mapPourcentage.put(name, new Double(0));
			context.setAttribute("TestProgress", mapPourcentage);
		}		
		
		for (int i = 0; i < nbInjector; i++) {
			InjectionTask jppfTask = new InjectionTask(script, nbIteration, notificationInterval);
			jppfTask.setId(jppfJob.getName() + " - Task " + i);
			jppfTask.setResubmit(false);
			try {
				jppfJob.add(jppfTask, (Object[])null);
				System.out.println("Task " + jppfTask.getId() + " added to the job...");
			} catch (JPPFException e) {
				e.printStackTrace();
			}
		}
		
		try {
			System.out.println("Non-Blocking job ready for execution...");
			
			jppfJob.setBlocking(false);
			jppfClient.submitJob(jppfJob);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * @return Retourne La GenericResult contenu dans la liste mapGenericResult qui a le plus petit currenttime.
	 * Cela va nous servir à choisir un temps de référence pour comparer l'exécution de tout les noeuds
	 */
	public long shortAndFindMinCurrentTime() {
		// On commence par trier toutes les listes pour chaque noeud
		for(List<GenericResult> list : mapGenericResult.values()){
			Collections.sort(list);
		}
		
		// une fois les listes triées, on va chercher la plus petite valeur 
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
	
	
	/**Une fois que tout les noeuds on fini leurs exécutions, on va vouloir agréger les données.
	 * aggregationData permet donc de parcourir une liste de GenericResult et de calculer pour tout
	 * les noeuds qui on envoyer des notifications, le nombre de requête par injecteur puis d'en
	 * faire la somme. ce résultat sera enregistré dans un fichier de type .csv. 
	 * 
	 * @param minCurrentTime représente le plus petit currenttime dans les resultat (retour de la methode
	 * shortAndFindMinCurrentTime()).
	 */
	public void aggregationData(Long minCurrentTime) {
		Map<Integer, List<DataByInjector>> agregation = new TreeMap<Integer, List<dataExtraction.DataByInjector>>();
		
		//on va stocker la liste de nom des taches
		List<String> listNameTask = new ArrayList<String>();
		
		//maintenant, pour chaque noeuds, on va calculer le nombre de requête par seconde
		for(Entry<String, List<GenericResult>> entry : mapGenericResult.entrySet()){
			listNameTask.add(entry.getKey());

			int nbRequestSec = 1;
			long nbSec = 0;
			
			// premierPassage va nous servir a savoir la différence entre le premier noeud lancer et les autres
			// on pourra donc calculer une différence de temps enter les deux
			Boolean premierPassage = true;
			
			for(GenericResult result : entry.getValue()) {
				if(premierPassage) {
					if(minCurrentTime < result.getStartTime()) {
						nbSec = result.getStartTime() - minCurrentTime;
					}
				}
					
				for(Long duration : result.getRequestsExecutionTimes()) {
					if(premierPassage) {
						premierPassage = false;
					}
					if((nbSec + duration)%1000000000L <= nbSec%1000000000L) {
						nbRequestSec++;
						nbSec+=duration;
					}
					else {
						Integer secSearch = new Integer((int) (nbSec/1000000000L));
						if(agregation.containsKey(secSearch)) {
							//on vérifie que l'objet n'existe qu'en un seul exemplaire par seconde pour chaque noeud
							// si c'est le cas, on ajoute dans l'bjet existant
							int position = agregation.get(secSearch).indexOf(new DataByInjector(entry.getKey(), 0));
							if(position != -1) {
								agregation.get(secSearch).get(position).setNbRequest(agregation.get(secSearch).get(position).getNbRequest() + nbRequestSec);
							}
							else {
								agregation.get(secSearch).add(new DataByInjector(entry.getKey(), nbRequestSec));
							}
						}
						else {
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
		
		try {
			writer = new PrintWriter(".." + File.separator + "tests-results" + File.separator + name + ".csv", "UTF-8");
			int agreg;
			for(Entry<Integer, List<DataByInjector>> entry : agregation.entrySet()) {
				StringBuilder str = new StringBuilder();
				str.append(entry.getKey()+ ";");
				agreg = 0;
				for(String name : listNameTask){
					Boolean isPresent = false;
					for(DataByInjector data : entry.getValue())
					{
						if(data.getTaskID().equals(name)) {
							str.append(data.getNbRequest() +";");
							agreg += data.getNbRequest();
							isPresent = true;
						}
					}
					if(!isPresent) {
						str.append("0;");
					}
				}
				str.append(agreg);
				writer.println(str);
			}
			writer.close();
			
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	
	public void shutdown() {
		jppfClient.close();
	}
}
