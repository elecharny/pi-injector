package jppf;

import java.util.ArrayList;
import java.util.List;

import org.jppf.node.protocol.AbstractTask;

import scripts.AbstractScript;

public class InjectionTask extends AbstractTask<Void> {
	
	private static final long 				serialVersionUID = 1L;
	
	// Envoie toutes les 100 exécutions du scénario 
	// une notification contenant les résultats récupérés
	private int								notificationInterval; 
	private final AbstractScript 			script;
	private List<GenericResult>		 		resultsList;
	private int 							nbIteration;
	
	
	public InjectionTask(AbstractScript script, int nbIteration, int notificationInterval) {
		this.script = script;
		this.nbIteration = nbIteration;
		this.notificationInterval = notificationInterval;
		resultsList = new ArrayList<>();
	}

	private void pushResults() {
		System.out.println("Sending notification...");
		fireNotification(resultsList, true);
		resultsList = new ArrayList<>();
	}
	
	
	
	@Override
	public void run() {
		
		System.out.println("Requests injection started...");
		GenericResult result = null;
		
		script.beforeRun();
		
		// Nombre d'itérations du scénario
		for (int scriptCounter = 1; scriptCounter <= nbIteration; scriptCounter++) {
			
			result = new GenericResult(script.getUUID());
			result.setStartTime(System.currentTimeMillis());
			
			long startScriptTimeNano = System.nanoTime();
			
			// Nombre de requêtes dans le scénario
			for (int requestCounter = 0; 
					requestCounter < script.getRequestCount(); 
					requestCounter++) {
				
				long startRequestTimeNano = System.nanoTime();
				script.run();
				result.addRequestExecutionTime(System.nanoTime() - startRequestTimeNano);
			}
			
			result.setTotalScriptExecutionTime(
					System.nanoTime() - startScriptTimeNano);
			
			resultsList.add(result);
			
			// On s'assure que le prochain appel à script.run()
			// recommence bien le scénario tout entier
			script.restartScriptExecution();
			
			// envoi régulier des résultats grâce au système de notification
			if (scriptCounter % notificationInterval == 0) {
				System.out.println("scriptCounter : " + scriptCounter);
				pushResults();
			}
		}
		close("End of execution... Stopping the node...");
	}
	
	@Override
	public void onCancel() {
		close("Node cancel... Stopping the node...");
	}
	
	@Override
	public void onTimeout() {
		close("Node timeout... Stopping the node...");
	}
	
	private void close(String message) {
		System.out.println(message);
		pushResults();
		
		script.afterRun();
	}
}
