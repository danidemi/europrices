package com.danidemi.europrice.jetty.poc;

import java.io.IOException;
import java.util.Arrays;
import java.util.EventListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
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
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

/**
 * http://www.eclipse.org/jetty/documentation/current/embedding-jetty.html
 * 
 * @author danidemi
 */
public class PocStandardWebApp implements ApplicationContextAware,
		WebApplicationInitializer {

	public static void main(String[] args) {
		try {
			new PocStandardWebApp().run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Server server;
	private ApplicationContext mainSpringContext;
	private GenericWebApplicationContext webApplicationContext;

	public void start() throws Exception {
		this.run();
	}

	public void stop() throws Exception {
		server.stop();
	}

	private void run() throws Exception {

		server = new Server();

		// shared http config
		HttpConfiguration http_config = new HttpConfiguration();
		http_config.setSecureScheme("https");
		http_config.setSecurePort(8443);
		http_config.setOutputBufferSize(32768);

		// HTTP connector #1
		ServerConnector http = new ServerConnector(server,
				new HttpConnectionFactory(http_config));
		http.setPort(8080);
		http.setIdleTimeout(30000);

		// HTTP connector #2
		ServerConnector http2 = new ServerConnector(server,
				new HttpConnectionFactory(http_config));
		http2.setPort(9090);
		http2.setIdleTimeout(30000);

		WebAppContext webapp = new WebAppContext();
		webapp.setVirtualHosts(new String[] { "localhost" });
		webapp.setContextPath("/oh");
		/* Disable directory listings if no index.html is found. */
		webapp.setWar(new ClassPathResource("webapp").getURI().toString());
		webapp.setInitParameter("org.eclipse.jetty.servlet.Default.dirAllowed",
				"true");
//		webapp.addEventListener(new ServletContextListener() {
//
//			@Override
//			public void contextInitialized(final ServletContextEvent sce) {
//				PocStandardWebApp.this.onContextInitialized(sce);
//			}
//
//			@Override
//			public void contextDestroyed(final ServletContextEvent sce) {
//				PocStandardWebApp.this.onContextDestroyed(sce);
//			}
//		});
		
		webapp.addEventListener(new ZuppaEventListener(this));

		// Create the root web application context and set it as a servlet
		// attribute so the dispatcher servlet can find it.
		webApplicationContext = new GenericWebApplicationContext();
		webApplicationContext.setParent(mainSpringContext);
		webApplicationContext.refresh();
		webapp.setAttribute(
				WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE,
				webApplicationContext);

		server.setHandler(webapp);
		server.setConnectors(new Connector[] { http, http2 });

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

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.mainSpringContext = applicationContext;

	}

	@Override
	public void onStartup(ServletContext container) throws ServletException {
		
		DispatcherServlet dispatcherServlet = mainSpringContext.getBean(DispatcherServlet.class);
		
		if(dispatcherServlet == null) {
			throw new IllegalStateException("No dispatcher servlet");
		}
		if(webApplicationContext == null){
			throw new IllegalStateException("No webApplicationContext available yet");
		}
		
		dispatcherServlet.setApplicationContext(webApplicationContext);
		
		ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", dispatcherServlet);
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/spring");
		
//		XmlWebApplicationContext appContext = new XmlWebApplicationContext();
//		appContext.setConfigLocation("/WEB-INF/spring/dispatcher-config.xml");
//
//		ServletRegistration.Dynamic dispatcher = container.addServlet(
//				"dispatcher", new DispatcherServlet(appContext));
//		dispatcher.setLoadOnStartup(1);
//		dispatcher.addMapping("/");

	}
		
	private static class ZuppaEventListener extends AbstractDispatcherServletInitializer implements ServletContextListener {

		private PocStandardWebApp poc;

		public ZuppaEventListener(PocStandardWebApp pocStandardWebApp) {
			this.poc = pocStandardWebApp;
		}

		@Override
		public void contextInitialized(ServletContextEvent sce) {
	        try {
	            onStartup(sce.getServletContext());
	        } catch (ServletException e) {
	            logger.error("Failed to initialize web application", e);
	            System.exit(0);
	        }
		}

		@Override
		public void contextDestroyed(ServletContextEvent sce) {
			// nothing special
		}

		@Override
		protected WebApplicationContext createServletApplicationContext() {
			if(poc.webApplicationContext == null){
				throw new IllegalStateException("Web app context not yet ready!");
			}
			return poc.webApplicationContext;
		}

		@Override
		protected String[] getServletMappings() {
			return new String[]{"/appa/*"};
		}

		@Override
		protected WebApplicationContext createRootApplicationContext() {
			return null;
		}
		
	}

}
