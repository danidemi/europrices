package com.danidemi.europrice.db.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import com.danidemi.europrice.db.model.Favourite;

public interface FavouriteRepository extends CrudRepository<Favourite, Long> {

	public List<Favourite> findByFavouriteIdAndUserId(String favouriteId, String userId);

}
