package com.danidemi.europrice.web.controller.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cglib.core.CollectionUtils;

public class SearchTermSplitter {

	public List<String> split(String searchTerms) {
		String[] split = searchTerms.split("\\s{1,}");
		
		ArrayList<String> result = new ArrayList<String>(1);
		for (String string : split) {
			String trimmed = string.trim();
			if(!trimmed.isEmpty()){
				result.add(trimmed);
			}
		}
		return result;
		
		// TODO: back to j1.8
		//return (List<String>)Arrays.asList(split).stream().filter(s -> !s.trim().isEmpty()).collect(Collectors.toList());
		
	}

}
