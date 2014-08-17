package com.danidemi.europrice.db;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductItemRepository extends PagingAndSortingRepository<ProductItem, Long>, ProductItemRepositoryCustom {



	

}
