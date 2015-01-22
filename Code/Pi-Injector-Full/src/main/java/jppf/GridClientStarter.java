package jppf;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class GridClientStarter implements ServletContextListener {
	
	GridClient gridclient;

	@Override
	public void contextInitialized(ServletContextEvent ctxtEvent) {
		ServletContext ctxt = ctxtEvent.getServletContext();
		
		gridclient = new GridClient();
		ctxt.setAttribute("gridClient", gridclient);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent ctxtEvent) {
		gridclient.shutdown();
	}
}
