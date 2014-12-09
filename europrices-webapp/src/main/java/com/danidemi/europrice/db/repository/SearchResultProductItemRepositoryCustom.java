package com.danidemi.europrice.db.repository;

import java.util.List;

import com.danidemi.europrice.db.model.SearchResultProductItem;

public interface SearchResultProductItemRepositoryCustom {

	List<SearchResultProductItem> findProductItemsByKeyword(String string, String userId);

	List<SearchResultProductItem> findProductItemsByKeywords(String[] keywords, String userId);
	
	List<SearchResultProductItem> findProductItemsByKeywords(List<String> terms, String userId);	
		
}
