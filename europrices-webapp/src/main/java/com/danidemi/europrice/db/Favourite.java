package com.danidemi.europrice.db;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Favourite implements Serializable {

	private static final long serialVersionUID = 206506912799277818L;
	
	private String favouriteId;
	private String userId;
	private Long id;

	public Favourite(String favouriteId, String userId) {
		super();
		this.favouriteId = favouriteId;
		this.userId = userId;
	}
	
	public Favourite(Long favouriteId, String userId) {
		this(favouriteId.toString(), userId);
	}
	
	public Favourite() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}	
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFavouriteId() {
		return favouriteId;
	}
	
	public void setFavouriteId(String favouriteId) {
		this.favouriteId = favouriteId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public String getUserId() {
		return userId;
	}
	
}
