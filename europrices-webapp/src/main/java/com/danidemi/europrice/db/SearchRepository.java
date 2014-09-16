package com.danidemi.europrice.db;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface SearchRepository extends PagingAndSortingRepository<Search, Long> {

	List<Search> findBySearch(String search);
	
}
