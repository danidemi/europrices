package com.danidemi.europrice.db;

import java.net.URL;
import java.util.List;

public interface ProductItemRepositoryCustom {

	List<ProductItem> findProductItemsByKeyword(String string);

	List<ProductItem> findProductItemsByKeywords(String[] keywords);
	
	List<ProductItem> findProductItemsByKeywords(List<String> terms);	
		
}
