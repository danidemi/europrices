package com.danidemi.europrice.jetty;

import java.io.IOException;

import javax.servlet.Servlet;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.BaseHolder.Source;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletMapping;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.servlet.DispatcherServlet;

public class EmbeddedJetty implements BeanFactoryAware, ApplicationContextAware {

    private static final String CONTEXT_PATH = "/";
    private static final String CONFIG_LOCATION = "eu.kielczewski.example.config";
    private static final String MAPPING_URL = "/*";
    private static final String DEFAULT_PROFILE = "dev";

	private BeanFactory beanFactory;
	private ApplicationContext appcontext;

    private void start() throws Exception {
    	
        Server server = new Server(8080);
        
        HandlerCollection handlerCollection = new HandlerCollection();
        


        
		handlerCollection.addHandler(getServletContextHandler());
		
        server.setHandler(handlerCollection);        
        server.start();
        server.join();
    }

	private DispatcherServlet getDispatcherServlet() {
		DispatcherServlet dispatcherServlet = beanFactory.getBean(DispatcherServlet.class);
		dispatcherServlet.setApplicationContext(appcontext);
		return dispatcherServlet;
	}

    private ServletContextHandler getServletContextHandler() throws IOException {
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setErrorHandler(null);
        contextHandler.setContextPath(CONTEXT_PATH);
        DispatcherServlet dispatcherServlet = getDispatcherServlet();
        ServletHandler dispatcherServletHandler = new ServletHandler();
		dispatcherServletHandler.setServlets(new org.eclipse.jetty.servlet.ServletHolder[]{
				new org.eclipse.jetty.servlet.ServletHolder(dispatcherServlet)
		});
		
		ServletHolder holder = new ServletHolder();
		holder.setServlet( dispatcherServlet );
		
        ServletMapping sm = new ServletMapping();
        sm.setServletName("dispatcher");
		ServletMapping[] servletMappings = new ServletMapping[]{ sm };
		dispatcherServletHandler.setServletMappings(servletMappings);
		
        contextHandler.addServlet( holder, MAPPING_URL);
        //contextHandler.addEventListener(new ContextLoaderListener(context));
        contextHandler.setResourceBase(new ClassPathResource("webapp").getURI().toString());
        return contextHandler;
    }

//    private static WebApplicationContext getContext() {
//        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
//        context.setConfigLocation(CONFIG_LOCATION);
//        context.getEnvironment().setDefaultProfiles(DEFAULT_PROFILE);
//        return context;
//    }

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.appcontext = applicationContext;
		
	}

}
