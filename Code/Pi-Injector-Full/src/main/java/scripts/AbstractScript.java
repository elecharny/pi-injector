package scripts;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Classe abstraite permettant de factoriser le mécanisme d'enregistrement
 * des méthodes exécutant les différentes requêtes d'un protocole.
 * Une fois que cet objet est instancié et paramétré, il est donné à 
 * une InjectionTask qui se chargera d'exécuter les méthodes qui y sont
 * enregistrées.
 */
public abstract class AbstractScript implements Serializable {

	// Utile pour la sérialisation
	private static final long 		serialVersionUID 	= 1L;
	
	// UUID du script qui sera recopié dans les objets GenericResult
	// afin de permettre de savoir à quel script correspondent les temps
	// d'exécution calculés
	private final UUID 				scriptUUID;
	
	// Liste qui va nous permettre de stocker les méthodes (= requêtes) qui 
	// composent le scénario courant.
	private List<MethodWithParams>	scriptMethods;
	
	// Indicateur permettant de savoir à quelle méthode on en est dans
	// l'exécution du scénario (= dans la liste des méthodes)
	private	int						methodCounter;
	
	
	public AbstractScript() {

		scriptUUID 	= UUID.randomUUID();
		scriptMethods = new ArrayList<>();
		methodCounter = 0;
	}
	
	/**
	 * Permet de récupérer l'UUID correspondant à ce script.
	 * @return UUID du script courant.
	 */
	public UUID getUUID() {
		return scriptUUID;
	}
	
	/**
	 * Permet de connaître le nombre de méthodes (= requêtes) qui composent le 
	 * scénario courant.
	 * @return Le nombre de requêtes stockées dans le scénario.
	 */
	public int getRequestCount() {
		return scriptMethods.size();
	}
	
	/**
	 * Permet de redémarrer l'exécution d'un scénario depuis sa première requête.
	 */
	public void restartScriptExecution() {
		methodCounter = 0;
	}
	
	
	/**
	 * Méthode exécutée une fois avant le premier appel à la méthode run. Elle
	 * permets d'initialiser certaines choses au début du test de charge, avant 
	 * l'exécution du scénario.
	 */
	public void beforeRun() {
		// Do nothing here
	}
	
	/**
	 * Méthode principale des différents scénarios qui sera appelée en boucle par
	 * l'InjectionTask sur les différents "noeuds" du framework JPPF.
	 * Chaque appel à cette méthode exécute la requête suivante dans le scénario.
	 * Lorsque le scénario est fini, cette méthode recommence depuis le début en
	 * exécutant la première requête à nouveau.
	 */
	public void run() {
		
		MethodWithParams mwp = scriptMethods.get(methodCounter);
		
		try {
			Method method = 
					this.getClass().getDeclaredMethod(
							mwp.getMethodName(), mwp.getParamTypes());
			method.invoke(this, mwp.getParams().toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (methodCounter >= scriptMethods.size() - 1)
			methodCounter = 0;
		else
			methodCounter++;
	}
	
	/**
	 * Méthode exécutée une fois après le dernier appel à la méthode run. Elle
	 * permet de terminer certaines choses une fois que les exécutions du scénario
	 * sont finies.
	 */
	public void afterRun() {
		// Do nothing here
	}
	
	/**
	 * Méthode utilisée par les classes héritant de AbstractScript permettant 
	 * d'enregistrer des méthodes et leurs paramètres dans le scénario.
	 * @param methodName Nom de la méthode à enregistrer
	 * @param paramTypes Types des paramètres de la méthode à enregistrer
	 * @param params Paramètres à donner à la méthode lorsqu'elle sera invoquée
	 */
	protected void addNewMethod(
						String methodName, 
						Class<?>[] paramTypes, 
						List<Object> params) {
		if (methodCounter == 0) {
			if (methodName != null && paramTypes != null && params != null) {
				scriptMethods.add(
						new MethodWithParams(
								methodName, paramTypes, params));
			}
		}
	}
	
	
	
	
	/**
	 * Classe interne permettant de réunir dans un seul objet :
	 * 		- La signature d'une méthode (nom + type des paramètres)
	 * 		- Les paramètres à envoyer à cette méthode lorsqu'elle sera invoquée
	 */
	private class MethodWithParams implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		private String 			methodName;
		private Class<?>[]		paramTypes;
		private List<Object> 	params;
		
		public MethodWithParams(
					String methodName, 
					Class<?>[] paramTypes, 
					List<Object> params) {
			this.methodName = methodName;
			this.paramTypes = paramTypes;
			this.params = params;
		}
		
		/**
		 * Permet d'obtenir le nom de la méthode
		 * @return Nom de la méthode
		 */
		public String getMethodName() {
			return methodName;
		}
		
		/**
		 * Permet d'obtenir le tableau des types des paramètres de la méthode
		 * @return Types des paramètres de la méthode
		 */
		public Class<?>[] getParamTypes() {
			return paramTypes;
		}
		
		/**
		 * Permet d'obtenir la liste des paramètres à transmettre à la méthode
		 * lorsqu'elle sera appelée
		 * @return Liste des paramètres à transmettre à la méthode
		 */
		public List<Object> getParams() {
			return params;
		}
	}
}
