package com.danidemi.europrice.web.controller.api;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SearchTermSplitter {

	public List<String> split(String searchTerms) {
		String[] split = searchTerms.split("\\s{1,}");
		
		return (List<String>)Arrays.asList(split).stream().filter(s -> !s.trim().isEmpty()).collect(Collectors.toList());
		
	}

}
