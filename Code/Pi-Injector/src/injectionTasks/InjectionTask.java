package injectionTasks;

import java.util.ArrayList;
import java.util.List;

import org.jppf.node.protocol.AbstractTask;

import resultsData.GenericResult;
import scripts.AbstractScript;

public class InjectionTask extends AbstractTask<Void> {
	
	private static final long 				serialVersionUID = 1L;
	
	// Envoie toutes les 100 exécutions du scénario 
	// une notification contenant les résultats récupérés
	private final int						notificationInterval = 100; 
	private final AbstractScript 			script;
	private List<GenericResult>		 		resultsList;
	
	
	public InjectionTask(AbstractScript script) {
		this.script = script;
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
		int scriptCounter = 0;
		GenericResult result = null;
		
		script.beforeRun();
		
		while (true) {
			
			long startScriptTimeNano = 0L;
			
			// si début du scénario
			if (!script.isStarted()) {
				scriptCounter++;
				result = new GenericResult(script.getUUID());
				result.setStartTime(System.currentTimeMillis());
				startScriptTimeNano = System.nanoTime();
			}
			
			long startRequestTimeNano = System.nanoTime();
			script.run();
			result.addRequestExecutionTime(System.nanoTime() - startRequestTimeNano);
			
			// si fin du scénario
			if (!script.isStarted()) {
				result.setTotalScriptExecutionTime(
						System.nanoTime() - startScriptTimeNano);
				resultsList.add(result);
			}
			
			// envoi régulier des résultats grâce au système de notification
			if (scriptCounter % notificationInterval == 0) {
				System.out.println("scriptCounter : " + scriptCounter);
				pushResults();
			}
		}
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
