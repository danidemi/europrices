package com.danidemi.europrice.utils;

import java.util.List;

public class Utils {

	public final static <T> T firstIfExists(List<T> items){
		if(items != null && !items.isEmpty()){
			return items.iterator().next();			
		}else{
			return null;
		}
	}
	
}
