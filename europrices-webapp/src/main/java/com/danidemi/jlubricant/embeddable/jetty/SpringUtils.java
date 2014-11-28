package com.danidemi.jlubricant.embeddable.jetty;

public class SpringUtils {

	public static String fakeEmptyContext(){
        String fakeEmptyContext = 
        		"classpath:" + 
        				SpringUtils.class.getName().replace(".", "/") +
        		"." + "emptycontext" 
        		+ ".xml";
        return fakeEmptyContext;
	}
	
}
