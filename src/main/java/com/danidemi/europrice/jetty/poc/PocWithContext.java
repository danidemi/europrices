package com.danidemi.europrice.jetty.poc;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 * http://www.eclipse.org/jetty/documentation/current/embedding-jetty.html
 * @author danidemi
 */
public class PocWithContext {

	public static void main(String[] args) {
		try {
			new PocWithContext().run();
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
		
        
        ServletContextHandler context1 = new ServletContextHandler();
        context1.setContextPath("/ctx1");
        context1.addServlet(ServletA.class, "/a");
        context1.addServlet(ServletB.class, "/b");
        
        ServletContextHandler context2 = new ServletContextHandler();
        context2.setContextPath("/ctx2");
        context2.addServlet(ServletA.class, "/a");
        context2.addServlet(ServletB.class, "/b");
        
        ContextHandlerCollection coll = new ContextHandlerCollection();
        coll.setHandlers(new Handler[]{ context1, context2 });
		

        
        server.setConnectors(new ServerConnector[]{ http, http2 });
		server.setHandler(coll);
        
		
        server.start();
        server.dumpStdErr();
        server.join();
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
