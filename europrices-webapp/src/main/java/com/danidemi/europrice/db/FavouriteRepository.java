package com.danidemi.europrice.db;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

public interface FavouriteRepository extends CrudRepository<Favourite, Long> {

	public List<Favourite> findByFavouriteIdAndUserId(String favouriteId, String userId);

}
