package com.danidemi.jlubricant.embeddable.jetty;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class SpringFeature implements Feature, ApplicationContextAware {

	private XmlWebApplicationContext wac;

	@Override
	public void install(EmbeddableJetty embeddableJetty) {
		
		embeddableJetty.getHandler().addEventListener(new ContextLoaderListener(wac));
		
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {

		wac = new XmlWebApplicationContext();
		wac.setParent( applicationContext );
		wac.set
	}

}
