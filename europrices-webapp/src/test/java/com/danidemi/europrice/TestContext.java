package com.danidemi.europrice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(locations={"/ctx.xml"})
public class TestContext {
	
	@Autowired ConfigurableApplicationContext springCtx;
	
	@Test public void instantianteAllBeans() {
		
		String[] beanDefinitionNames = springCtx.getBeanFactory().getBeanDefinitionNames();
		for (String beanName : beanDefinitionNames) {
			springCtx.getBean(beanName);
		}
		
	}

}
