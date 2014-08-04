package com.danidemi.europrice.jetty;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;

public class JettyServer {

	private int jettyPort;
	private Handler jettyWebAppContext;
	private Server server;
	
	
	public JettyServer() {
		this.server = new Server();
	}
	

	public void start() throws Exception {
				
		/* Create a basic connector. */
		ServerConnector httpConnector = new ServerConnector(server);
		httpConnector.setPort(jettyPort);
		server.addConnector(httpConnector);
		
		server.setHandler(jettyWebAppContext);
		
		server.start();
				
	}
	
	public void setJettyWebAppContext(Handler jettyWebAppContext) {
		this.jettyWebAppContext = jettyWebAppContext;
	}
	
	
	
	
	
	public final void stop() throws Exception {
		server.stop();
	}


	public void setJettyPort(int jettyPort) {
		this.jettyPort = jettyPort;
	}
	
}
