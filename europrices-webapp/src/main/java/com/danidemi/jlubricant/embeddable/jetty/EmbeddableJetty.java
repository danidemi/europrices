package com.danidemi.jlubricant.embeddable.jetty;

import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Arrays;
import java.util.EventListener;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

import com.asual.lesscss.LessOptions;
import com.asual.lesscss.LessServlet;
import com.danidemi.jlubricant.embeddable.EmbeddableServer;
import com.danidemi.jlubricant.embeddable.ServerStartException;
import com.danidemi.jlubricant.embeddable.ServerStopException;

/**
 * http://www.eclipse.org/jetty/documentation/current/embedding-jetty.html
 * 
 * @author danidemi
 */
public class EmbeddableJetty implements ApplicationContextAware, EmbeddableServer {
	
	private static final Logger log = LoggerFactory.getLogger(EmbeddableJetty.class);

	private Server server;
	private ApplicationContext mainSpringContext;
	GenericWebApplicationContext webApplicationContext;
	private int httpPort = 8080;
	private int idleTimeout = 30000;
	private boolean dirAllowed = true;
	private String[] virtualHosts = null;
	private String webappContextPath = "/";
	private String dispatcherServletSubPath = "/";

	private Thread jettyThread;

	private String host;
	
	
	/**
	 * The paths that will be taken in charge by Spring's DispatcherServlet.
	 * Values as {@code /appa/*} or {@code /} are ok. 
	 * @param dispatcherServletSubPath
	 */
	public void setDispatcherServletSubPath(String dispatcherServletSubPath) {
		this.dispatcherServletSubPath = dispatcherServletSubPath;
	}
	
	public String getDispatcherServletSubPath() {
		return dispatcherServletSubPath;
	}
	
	/**
	 * The context path the default web app will be published under.
	 * Values as "/" or "/app" are accepted.
	 */
	public void setWebappContextPath(String webappContextPath) {
		this.webappContextPath = webappContextPath;
	}
	
	public void setVirtualHosts(List<String> virtualHosts) {
		this.virtualHosts = virtualHosts.toArray( new String[virtualHosts.size()] );
	}
	
	public void setVirtualHost(String virtualHost) {
		this.setVirtualHosts( Arrays.asList( new String[]{virtualHost} ) );
	}
	
	public void setHost(String host) {
		this.host = host;
	}
		
	public boolean isDirAllowed() {
		return dirAllowed;
	}
	
	public void setDirAllowed(boolean dirAllowed) {
		this.dirAllowed = dirAllowed;
	}
	
	public void setIdleTimeout(int idleTimeout) {
		this.idleTimeout = idleTimeout;
	}

	public void setHttpPort(int httpPort) {
		this.httpPort = httpPort;
	}
	
	@Override
	public void start() throws ServerStartException {
		
		// where in the classpath can be found a web app structure
		String springResourcePathForWebApp = "webapp";
		String resourceURI;
		try {
			resourceURI = new ClassPathResource(springResourcePathForWebApp).getURI().toString();
		} catch (IOException e) {
			throw new ServerStartException("Unable to find web app files at resource '" + springResourcePathForWebApp + "'", e);
		}
		
		server = new Server();
		
		// shared http config
		HttpConfiguration http_config = new HttpConfiguration();
		http_config.setSecureScheme("https");
		http_config.setSecurePort(8443);
		http_config.setOutputBufferSize(32768);
		
		// HTTP connector #1
		ServerConnector http = new ServerConnector(server,
				new HttpConnectionFactory(http_config));
		http.setPort(httpPort);
		http.setIdleTimeout(idleTimeout);
		if(host != null){
			http.setHost(host);			
		}
		
		// HTTP connector #2
//		ServerConnector http2 = new ServerConnector(server,
//				new HttpConnectionFactory(http_config));
//		http2.setPort(9090);
//		http2.setIdleTimeout(30000);
		
		WebAppContext webapp = new WebAppContext();
		
		if(virtualHosts!=null){
			webapp.setVirtualHosts(virtualHosts);		
		}
		
		webapp.setContextPath(webappContextPath);
		
		webapp.setWar(resourceURI);
		
		// Disable directory listings if no index.html is found.
		webapp.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed",
				String.valueOf(dirAllowed));
		
		
		webapp.addEventListener(new InitializerListener(this));
		webapp.addEventListener(new RegisterLessServlet());
		webapp.addEventListener(new SecurityEnable(this));
		
		// Create the root web application context and set it as a servlet
		// attribute so the dispatcher servlet can find it.
		webApplicationContext = new GenericWebApplicationContext();
		webApplicationContext.setParent(mainSpringContext);
		webApplicationContext.refresh();
		webapp.setAttribute(
				WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
				webApplicationContext);
		
		server.setHandler(webapp);
		server.setConnectors(new Connector[] { http });
		
		try {
			log.info("Starting Jetty...");
			
			jettyThread = new Thread( new Runnable() {

				@Override
				public void run() {
					try {
						server.start();
						StringBuffer stringBuffer = new StringBuffer();
						server.dump(stringBuffer);
						log.debug( stringBuffer.toString() );
						try{
							server.join();							
						}catch(InterruptedException ie){
							log.info("Stopping Jetty...");
						}
						
						log.info("Jetty stopped...");
						
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
					
				}
				
			});
			jettyThread.setUncaughtExceptionHandler( new UncaughtExceptionHandler() {
				
				@Override
				public void uncaughtException(Thread t, Throwable e) {
					EmbeddableJetty.this.uncaughtExceptionOnJettyThread(t, e);
				}
			} );
			jettyThread.start();
			

			log.info("Jetty Started.");
		} catch (Exception e) {
			throw new ServerStartException("An error occurred starting Jetty.", e);
		}
	}

	protected void uncaughtExceptionOnJettyThread(Thread t, Throwable e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() throws ServerStopException {
		try {
			log.info("Stopping Jetty...");
			server.stop();
			if(jettyThread!=null){
				jettyThread.interrupt();
			}
		} catch (Exception e) {
			throw new ServerStopException("An error occurred stopping Jetty.", e);
		}
	}


	protected void onContextInitialized(ServletContextEvent sce) {
		log.warn("jaxax.servlet context initialized");
	}
	
	protected void onContextDestroyed(ServletContextEvent sce) {
		log.warn("jaxax.servlet context destroyed");
	}


	@Override
	public final void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.mainSpringContext = applicationContext;
	}
	
	/**
	 * A {@link org.springframework.web.WebApplicationInitializer} suited to be used with Jetty.
	 *
	 * <p>{@link #createServletApplicationContext()} returns the one specified in the server.</p> 
	 * <p>{@link #getServletMappings()} return the ones specified in the server.</p>
	 * 
	 * @see AbstractDispatcherServletInitializer
	 */
	private static class InitializerListener extends AbstractDispatcherServletInitializer implements ServletContextListener {

		private EmbeddableJetty jetty;

		public InitializerListener(EmbeddableJetty jetty) {
			this.jetty = jetty;
		}

		@Override
		protected WebApplicationContext createServletApplicationContext() {
			if(jetty.webApplicationContext == null){
				throw new IllegalStateException("Web app context not yet ready!");
			}
			return jetty.webApplicationContext;
		}
		
		@Override
		protected String[] getServletMappings() {
			return new String[]{jetty.getDispatcherServletSubPath()};
		}
		
	    /**
	     * Receives notification that the web application initialization process is starting.
	     */
		@Override
		public void contextInitialized(ServletContextEvent event) {
	        try {
	            onStartup(event.getServletContext());
	        } catch (ServletException e) {
	            logger.error("Failed to initialize web application", e);
	            System.exit(0);
	        }
		}

		@Override
		public void contextDestroyed(ServletContextEvent sce) {
			// nothing special
		}


		/**
		 * No roots contexts are needed, so return null.
		 */
		@Override
		protected WebApplicationContext createRootApplicationContext() {
			return null;
		}
		
	}
	
	private class RegisterLessServlet implements ServletContextListener {

		@Override
		public void contextInitialized(ServletContextEvent sce) {
			LessServlet lessServlet = new LessServlet();
			
			String servletName = "less";
			ServletContext servletContext = sce.getServletContext();
			
			
			
			ServletRegistration.Dynamic registration = servletContext.addServlet(servletName, lessServlet);
			registration.setLoadOnStartup(1);
			registration.setInitParameter("compress", Boolean.FALSE.toString());
			registration.setInitParameter("lineNumbers", Boolean.FALSE.toString());
			registration.setInitParameter("cache", Boolean.FALSE.toString());
			
			registration.addMapping("*.css");
			
		}

		@Override
		public void contextDestroyed(ServletContextEvent sce) {
			// nothing to do
			
		}
		
	}

}
