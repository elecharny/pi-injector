package dataExtraction;

public class DataByInjector {
	private String taskID;
	private int nbRequest;


	public DataByInjector(String taskID, int nbRequest) {
		super();
		this.taskID = taskID;
		this.nbRequest = nbRequest;
	}
	
	public String getTaskID() {
		return taskID;
	}


	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}


	public int getNbRequest() {
		return nbRequest;
	}


	public void setNbRequest(int nbRequest) {
		this.nbRequest = nbRequest;
	}
	
	@Override
	public boolean equals(Object other){
		if (other == null) return false;
		if (other == this) return true;
		if (!(other instanceof DataByInjector))return false;
		DataByInjector otherObject = (DataByInjector)other;
		if(otherObject.getTaskID().equals(getTaskID()))
			return true;
		return false;
	}
}