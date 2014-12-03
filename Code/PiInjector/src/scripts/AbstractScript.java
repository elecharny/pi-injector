package scripts;

import java.io.Serializable;

public class AbstractScript implements Serializable {

	private static final long serialVersionUID = 1L;

	private String	servername;
	private int		serverport;
	
	
	public AbstractScript(String servername, int serverport) {
		
		if (servername != null && !servername.trim().equals(""))
			this.servername = servername.trim();
		
		if (serverport > 0)
			this.serverport = serverport;
	}
	
	
	
	public String getServername() {
		return servername;
	}
	
	public void setServername(String servername) {
		if (servername != null && !servername.trim().equals(""))
			this.servername = servername.trim();
	}
	
	
	
	public int getServerport() {
		return serverport;
	}
	
	public void setServerport(int serverport) {
		if (serverport > 0)
			this.serverport = serverport;
	}
}
