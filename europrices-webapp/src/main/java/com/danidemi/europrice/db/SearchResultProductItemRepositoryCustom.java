package com.danidemi.europrice.db;

import java.util.List;

public interface SearchResultProductItemRepositoryCustom {

	List<SearchResultProductItem> findProductItemsByKeyword(String string, String userId);

	List<SearchResultProductItem> findProductItemsByKeywords(String[] keywords, String userId);
	
	List<SearchResultProductItem> findProductItemsByKeywords(List<String> terms, String userId);	
		
}
