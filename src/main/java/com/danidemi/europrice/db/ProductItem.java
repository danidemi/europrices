package com.danidemi.europrice.db;

import java.net.MalformedURLException;
import java.net.URL;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/** A product from an online shop. */
@Entity
public class ProductItem {
	
	private Shop shop;
	private String shopURLString;
	private Long id;
	
    @Id @GeneratedValue
    @NotNull
	public Long getId(){
		return id;
	}
    
    public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne(optional=false)
	public Shop getShop(){
		return shop;
	}
	
	@Basic(optional=true)
	public String getShopURLString() {
		return shopURLString;
	}
	
	public void setShopURLString(String shopURLString) {
		this.shopURLString = shopURLString;
	}
	
	@Transient
	URL getShopURL(){
		URL url = null;
		try {
			url = new URL(getShopURLString());
		} catch (MalformedURLException e) {
			
		}
		return url;
	}


}
