package com.danidemi.europrice.db;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ShopRepository extends PagingAndSortingRepository<Shop, Long> {

	List<Shop> findByName(String name);
	
}
