package com.danidemi.europrice.db.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.danidemi.europrice.db.model.Shop;

public interface ShopRepository extends PagingAndSortingRepository<Shop, Long> {

	List<Shop> findByName(String name);
	
}
