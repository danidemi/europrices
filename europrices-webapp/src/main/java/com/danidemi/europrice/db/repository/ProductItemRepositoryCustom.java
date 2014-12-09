package com.danidemi.europrice.db.repository;

import java.net.URL;
import java.util.List;

import com.danidemi.europrice.db.model.ProductItem;

public interface ProductItemRepositoryCustom {

	List<ProductItem> findProductItemsByKeyword(String string);

	List<ProductItem> findProductItemsByKeywords(String[] keywords);
	
	List<ProductItem> findProductItemsByKeywords(List<String> terms);	
		
}
