package com.danidemi.europrice.jetty.poc;

import java.io.IOException;
import java.util.Arrays;
import java.util.EventListener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.core.io.ClassPathResource;

/**
 * http://www.eclipse.org/jetty/documentation/current/embedding-jetty.html
 * @author danidemi
 */
public class PocStandardWebApp {

	public static void main(String[] args) {
		try {
			new PocStandardWebApp().run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void run() throws Exception {
		
		Server server = new Server();
		
		// shared http config
		HttpConfiguration http_config = new HttpConfiguration();
		http_config.setSecureScheme("https");
		http_config.setSecurePort(8443);
		http_config.setOutputBufferSize(32768);
		
		
        // HTTP connector #1       
        ServerConnector http = new ServerConnector(server,new HttpConnectionFactory(http_config));        
        http.setPort(8080);
        http.setIdleTimeout(30000);

        
        // HTTP connector #2       
        ServerConnector http2 = new ServerConnector(server,new HttpConnectionFactory(http_config));        
        http2.setPort(9090);
        http2.setIdleTimeout(30000);
		
        
        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/oh");
        /* Disable directory listings if no index.html is found. */
        webapp.setWar(new ClassPathResource("webapp").getURI().toString());
        webapp.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed", "true");        
        webapp.addEventListener(new ServletContextListener() {
			
			@Override
			public void contextInitialized(final ServletContextEvent sce) {
				PocStandardWebApp.this.onContextInitialized(sce);
			}
			
			@Override
			public void contextDestroyed(final ServletContextEvent sce) {
				PocStandardWebApp.this.onContextDestroyed(sce);
			}
		});
        
        server.setHandler( webapp );
        server.setConnectors( new Connector[]{http, http2} );
 
        
		
        server.start();
        server.dumpStdErr();
        server.join();
	}
	
	protected void onContextDestroyed(ServletContextEvent sce) {
		System.out.println("context destroyed");
		
	}

	protected void onContextInitialized(ServletContextEvent sce) {
		System.out.println("context initialized");
		
	}

	public static class ServletA extends HttpServlet {
		
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse response)
				throws ServletException, IOException {
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("<h1>SERVLET A</h1>");
			
		}
		
	}
	
	public static class ServletB extends HttpServlet {
		
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse response)
				throws ServletException, IOException {
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("<h1>SERVLET B</h1>");
			
		}
		
	}	
	
}
