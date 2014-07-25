package com.danidemi.europrice;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext();
		ctx.setConfigLocation("ctx.xml");
		ctx.registerShutdownHook();
		ctx.refresh();
		ctx.start();
	}
	
}
