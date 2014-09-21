package com.danidemi.europrice;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EuropricesGrabber {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext();
		ctx.getEnvironment().setActiveProfiles("prod");
		ctx.setConfigLocation("ctx.xml");
		ctx.registerShutdownHook();
		ctx.refresh();
		ctx.start();
	}
	
}
