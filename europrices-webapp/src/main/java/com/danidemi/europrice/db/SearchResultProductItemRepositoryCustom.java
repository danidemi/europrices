package com.danidemi.europrice.db;

import java.util.List;

public interface SearchResultProductItemRepositoryCustom {

	List<SearchResultProductItem> findProductItemsByKeyword(String string);

	List<SearchResultProductItem> findProductItemsByKeywords(String[] keywords);
	
	List<SearchResultProductItem> findProductItemsByKeywords(List<String> terms);	
		
}
