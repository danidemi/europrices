package com.danidemi.europrice;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanExpressionException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(locations = { "/ctx.xml" })
public class TestContext {

	@Autowired
	ConfigurableApplicationContext springCtx;

	@Test
	public void instantianteAllBeans() {

		ConfigurableListableBeanFactory beanFactory = springCtx
				.getBeanFactory();

		beanFactory.registerScope("request", new FakeScope());
		beanFactory.registerScope("session", new FakeScope());

		String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
		for (String beanName : beanDefinitionNames) {
			try{
				springCtx.getBean(beanName);				
			}catch(BeanExpressionException bee){

			}
		}

	}

	public static final class FakeScope implements Scope {
		private Map<String, Object> objectMap = Collections
				.synchronizedMap(new HashMap<String, Object>());

		/**
		 * (non-Javadoc)
		 *
		 * @see org.springframework.beans.factory.config.Scope#get(java.lang.String,
		 *      org.springframework.beans.factory.ObjectFactory)
		 */
		public Object get(String name, ObjectFactory<?> objectFactory) {
			if (!objectMap.containsKey(name)) {
				objectMap.put(name, objectFactory.getObject());
			}
			return objectMap.get(name);

		}

		/**
		 * (non-Javadoc)
		 *
		 * @see org.springframework.beans.factory.config.Scope#remove(java.lang.String)
		 */
		public Object remove(String name) {
			return objectMap.remove(name);
		}

		/**
		 * (non-Javadoc)
		 *
		 * @see org.springframework.beans.factory.config.Scope#registerDestructionCallback
		 *      (java.lang.String, java.lang.Runnable)
		 */
		public void registerDestructionCallback(String name, Runnable callback) {
			// do nothing
		}

		/**
		 * (non-Javadoc)
		 *
		 * @see org.springframework.beans.factory.config.Scope#resolveContextualObject(java.lang.String)
		 */
		public Object resolveContextualObject(String key) {
			return null;
		}

		/**
		 * (non-Javadoc)
		 *
		 * @see org.springframework.beans.factory.config.Scope#getConversationId()
		 */
		public String getConversationId() {
			return "MyScope";
		}

		/**
		 * clear the beans
		 */
		public void clearBean() {
			objectMap.clear();
		}
	}

}
