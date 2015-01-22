package jppf;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class GridClientStarter implements ServletContextListener {
	
	GridClient gridclient;

	@Override
	public void contextInitialized(ServletContextEvent ctxtEvent) {
		ServletContext ctxt = ctxtEvent.getServletContext();
		
		System.out.println("Starting the grid client ...");
		gridclient = new GridClient();
		ctxt.setAttribute("gridClient", gridclient);
		System.out.println("Grid client started.");
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent ctxtEvent) {
		System.out.println("Stopping the grid client ...");
		gridclient.shutdown();
		System.out.println("Grid client stopped.");
	}
}
