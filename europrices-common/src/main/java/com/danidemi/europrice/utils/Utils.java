package com.danidemi.europrice.utils;

import java.util.List;

public class Utils {
	
	public enum Language {
		/** Deutsch */
		de,
		
		/**le français */ 
		fr, 
		
		/** Nederlands */ 
		nl,
		
		/** ελληνικά */ 
		el,
		
		/** Estonia   : eesti keel */ 
		et,
		
		/** suomi */ 
		fi, 
		
		/** svenska */
		sv, 
 	
		/** English */ 
		en, 
		
		/** Gaeilge */
		ga, 

		/** Italiano */
		it,
		
		/** latviešu valoda */ 
		lv,
		
		/** Malti */
		mt,
		
		/** português */ 
		pt,
		
		/** Slovakia 	: slovenský jazyk */ 
		sk,
	
		/** Slovenia 	: slovenščina */ 
		sl,
		
		/** Spain 	 	: español */ 
		es, 
		
		/** Euskara */ 
		eu,
		
		/** català */
		ca, 
		/** galego */ 
		gl
	}

	public final static <T> T firstIfExists(List<T> items){
		if(items != null && !items.isEmpty()){
			return items.iterator().next();			
		}else{
			return null;
		}
	}
	
}
