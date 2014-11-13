package com.danidemi.jlubricant.embeddable.jetty;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;

import com.asual.lesscss.LessServlet;

/** 
 * This {@link ServletContextListener} enable Less.
 * {@linkplain http://lesscss.org/} using Rostislav Hristov's lesscss-servlet 
 * {@linkplain https://github.com/asual/lesscss-servlet}
 */
class RegisterLessServlet implements ServletContextListener {

	/**
	 * 
	 */
	private EmbeddableJetty enableLessCssServletContextListener;

	/**
	 * @param embeddableJetty
	 */
	RegisterLessServlet(EmbeddableJetty embeddableJetty) {
		enableLessCssServletContextListener = embeddableJetty;
	}

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