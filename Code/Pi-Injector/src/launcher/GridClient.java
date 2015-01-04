package launcher;

import java.util.List;

import javax.management.Notification;
import javax.management.NotificationListener;

import injectionTasks.InjectionTask;

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

import scripts.AbstractScript;
import scripts.LDAPScript;


public class GridClient {
	
	private int 								nodesCount = 0;
	private JPPFClient 							jppfClient = null;
	private AbstractJPPFClientConnection 		jppfClientConn = null;
	
	
	
	public GridClient() {
		// Connect to the JPPF Grid
		establishGridConnection();
		// Create a listener to receive notifications from JPPF nodes
		createJMXNodeListener();
		// Get the number of connected JPPF nodes
		refreshNodesCount();
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
	}
	
	
	private void createJMXNodeListener() {
		
		if (jppfClientConn != null) {
			JMXDriverConnectionWrapper driverJmx = 
					jppfClientConn.getConnectionPool().getJmxConnection();
			
			
			// Select all nodes
			NodeSelector selector = new NodeSelector.AllNodesSelector();
			
			// Create a listener
			NotificationListener listener = new NotificationListener() {
				@Override
				public void handleNotification(
						Notification notification, Object handback) {
					
					JPPFNodeForwardingNotification wrapping = 
							(JPPFNodeForwardingNotification) notification;
					
					System.out.println(
							"Received notification from nodeUuid : " 
									+ wrapping.getNodeUuid()
									+ ", mBeanName : " + wrapping.getMBeanName());
					
					TaskExecutionNotification notif = 
							(TaskExecutionNotification) wrapping.getNotification();
					
					System.out.println(
							"Data de la notification : " 
									+ (String)notif.getUserData());
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
	
	public int refreshNodesCount() {
		
		if (jppfClientConn != null) {
			JMXDriverConnectionWrapper driverJmx = 
					jppfClientConn.getConnectionPool().getJmxConnection();
			
			try {
				nodesCount = (Integer)driverJmx.invoke(
						JPPFDriverAdminMBean.MBEAN_NAME, "nbNodes");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return nodesCount;
	}
	
	
	
	
	public void launchScriptList(List<AbstractScript> scripts) {
		
		ApplicationRunner runner = new ApplicationRunner();
		
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
			} catch (JPPFException e) {
				e.printStackTrace();
			}
		}
		
		
		try {
			runner.executeBlockingJob(jppfClient, jppfJob);
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
		
		jppfJob.setBlocking(false);
		jppfClient.submitJob(jppfJob);
		
		// Do something here...
		try {
			Thread.sleep(15000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Test du job cancel pour arrêter les noeuds
		jppfJob.cancel();
		jppfJob.awaitResults();
	}
	
	
	
	
	
	public static void main(String[] args) {
		
		System.out.println("Starting...");
		
		LDAPScript ldapScript = new LDAPScript("192.168.1.28", 10389);
		ldapScript.addBindRequest("uid=admin,ou=system", "secret");
		
		GridClient client = new GridClient();
		client.launchBroadcastScript(ldapScript);
		
		System.out.println("Finishing...");
	}

}
