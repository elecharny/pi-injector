package scripts;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public abstract class AbstractScript implements Serializable {

	private static final long 		serialVersionUID 	= 1L;
	
	private final UUID 				scriptUUID;
	private List<MethodWithParams>	scriptMethods;
	private	int						methodCounter;
	
	
	public AbstractScript() {

		scriptUUID 	= UUID.randomUUID();
		scriptMethods = new ArrayList<>();
		methodCounter = 0;
	}
	
	public UUID getUUID() {
		return scriptUUID;
	}
	
	public boolean isStarted() {
		return methodCounter > 0;
	}
	
	
	/**
	 * Is executed one time before the first call to the run method. It permits
	 * to instantiate some things at the beginning of the script.
	 */
	public void beforeRun() {
		// Do nothing here
	}
	
	/**
	 * Main method of the different scripts which will be called by the 
	 * InjectionTask in a loop.
	 */
	public void run() {
		
		MethodWithParams mwp = scriptMethods.get(methodCounter);
		
		try {
			mwp.getMethod().invoke(this, mwp.getParams().toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (methodCounter >= scriptMethods.size() - 1)
			methodCounter = 0;
		else
			methodCounter++;
	}
	
	/**
	 * Is executed one time AFTER the last call to the run method. It permits to
	 * finish some things after the script was running.
	 */
	public void afterRun() {
		// Do nothing here
	}
	
	
	protected void addNewMethod(Method method, List<Object> params) {
		if (!isStarted()) {
			if (method != null && params != null) {
				scriptMethods.add(new MethodWithParams(method, params));
			}
		}
	}
	
	
	
	
	
	private class MethodWithParams {
		private Method 			method;
		private List<Object> 	params;
		
		public MethodWithParams(Method method, List<Object> params) {
			this.method = method;
			this.params = params;
		}
		
		public Method getMethod() {
			return method;
		}
		
		public List<Object> getParams() {
			return params;
		}
	}
}
