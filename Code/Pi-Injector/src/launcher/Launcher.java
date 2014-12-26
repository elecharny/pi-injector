package launcher;

import javax.management.Notification;
import javax.management.NotificationListener;

import injectionTasks.LDAPInjectionTask;

import org.jppf.client.AbstractJPPFClientConnection;
import org.jppf.client.JPPFClient;
import org.jppf.client.JPPFJob;
import org.jppf.management.JMXDriverConnectionWrapper;
import org.jppf.management.JPPFDriverAdminMBean;
import org.jppf.management.JPPFNodeTaskMonitorMBean;
import org.jppf.management.NodeSelector;
import org.jppf.management.TaskExecutionNotification;
import org.jppf.management.forwarding.JPPFNodeForwardingNotification;

import scripts.LDAPScript;


public class Launcher {

	public static void main(String[] args) {
		
		System.out.println("Launching...");

		try (JPPFClient jppfClient = new JPPFClient()) {
			int nbNodes = 0;

			AbstractJPPFClientConnection conn = null;
			
			while (conn == null) {
				Thread.sleep(100L);
				conn = (AbstractJPPFClientConnection) jppfClient.getClientConnection();
			}
			
			JMXDriverConnectionWrapper driverJmx = 
					conn.getConnectionPool().getJmxConnection();
			
			
			nbNodes = (Integer)driverJmx.invoke(
					JPPFDriverAdminMBean.MBEAN_NAME, "nbNodes");
			System.out.println("NOMBRE DE NOEUDS : " + nbNodes);
			
			
			
			// Create a JMX listener 
			
			// Select all nodes
			NodeSelector selector = new NodeSelector.AllNodesSelector();
			
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
			
			driverJmx.registerForwardingNotificationListener(
							selector, JPPFNodeTaskMonitorMBean.MBEAN_NAME, 
							listener, null, null);
			
			
			
			
			ApplicationRunner runner = new ApplicationRunner();
			
			JPPFJob jppfJob = new JPPFJob();
			jppfJob.setName("LDAP Injection Job");
			
			LDAPScript ldapScript = new LDAPScript("192.168.1.28", 10389);
			ldapScript.addBindRequest("uid=admin,ou=system", "secret");
			
			for (int i = 0; i < nbNodes; i++) {
				LDAPInjectionTask jppfTask = new LDAPInjectionTask(ldapScript);
				jppfTask.setId(jppfJob.getName() + " - Task " + i);
				jppfJob.add(jppfTask, (Object[])null);
			}
			
			
			runner.executeBlockingJob(jppfClient, jppfJob);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
