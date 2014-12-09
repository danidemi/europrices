package com.danidemi.europrice.db.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.danidemi.europrice.db.model.Search;

public interface SearchRepository extends PagingAndSortingRepository<Search, Long> {

	List<Search> findBySearch(String search);
	
}
