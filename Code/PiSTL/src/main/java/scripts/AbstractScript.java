package main.java.scripts;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AbstractScript implements Serializable {
	private String	servername;
	private int		serverport;
	
	
	public AbstractScript(String servername, int serverport) {
		if(servername != null && !servername.trim().equals(""))
			this.servername = servername.trim();
		
		if(serverport > 0)
			this.serverport = serverport;
	}
	
	
	
	public String getServername() {
		return servername;
	}
	
	public void setServername(String servername) {
		if(servername != null && !servername.trim().equals(""))
			this.servername = servername.trim();
	}
	
	
	public int getServerport() {
		return serverport;
	}
	
	public void setServerport(int serverport) {
		if(serverport > 0)
			this.serverport = serverport;
	}
}
