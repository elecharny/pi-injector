package main.java.resultsData;


public class DataContainer {
	private int nbRequest;
	private int nbSec;
	
	
	public DataContainer(int nbRequest, int nbSec) {
		super();
		this.nbRequest = nbRequest;
		this.nbSec = nbSec;
	}
	
	
	public int getNbRequest() {
		return nbRequest;
	}
	public void setNbRequest(int nbRequest) {
		this.nbRequest = nbRequest;
	}
	public int getNbSec() {
		return nbSec;
	}
	public void setNbSec(int nbSec) {
		this.nbSec = nbSec;
	}
}
