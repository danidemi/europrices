package com.danidemi.europrice.web.controller.api;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class SearchTermSplitterTest {

	@Test
	public void shouldSplitCorrectly() {
		
		// when
		List<String> split = new SearchTermSplitter().split("    hello world ");
		
		// then
		assertThat(split, contains("hello", "world"));
		
	}
	
}
