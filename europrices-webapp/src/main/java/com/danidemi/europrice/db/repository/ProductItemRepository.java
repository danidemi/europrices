package com.danidemi.europrice.db.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.danidemi.europrice.db.model.ProductItem;

public interface ProductItemRepository extends PagingAndSortingRepository<ProductItem, Long>, ProductItemRepositoryCustom {

	List<ProductItem> findByDetailsURL(String urlDetail);
		
}
