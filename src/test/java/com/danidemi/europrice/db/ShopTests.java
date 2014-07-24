package com.danidemi.europrice.db;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;


//@ContextConfiguration(locations="/context.xml")
//public class ShopTests {
//	
//	@Autowired
//	ApplicationContext ctx;
//
//	@Test
//	public void shouldRunATest() {
//		
//	}
//	
//}

public class ShopTests {
	
	@Test
	public void shouldRunATest() {
	
		ApplicationContext ctx = new ClassPathXmlApplicationContext("/ctx.xml");
		
		
	}
	
}
