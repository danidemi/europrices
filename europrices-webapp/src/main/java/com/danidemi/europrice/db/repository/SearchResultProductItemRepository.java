package com.danidemi.europrice.db.repository;

import org.springframework.data.repository.Repository;

import com.danidemi.europrice.db.model.SearchResultProductItem;

public interface SearchResultProductItemRepository extends Repository<SearchResultProductItem, Long>, SearchResultProductItemRepositoryCustom {
		
}
