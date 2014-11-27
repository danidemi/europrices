package com.danidemi.europrice;

import org.openqa.jetty.jetty.servlet.WebApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class EuroPricesWebApp {

	public static void main(String[] args) {
		//XmlWebApplicationContext ctx = new XmlWebApplicationContext();
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext();
		ctx.setBeanName("context-main");
		ctx.getEnvironment().setActiveProfiles("prod");
		ctx.setConfigLocation("ctx.xml");
		ctx.registerShutdownHook();
		ctx.refresh();
		ctx.start();
	}
	
}
