package com.danidemi.europrice.db;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import org.apache.commons.lang.StringUtils;


import org.hibernate.annotations.Cascade;

/** A product from an online shop. */
@Entity
public class ProductItem implements Serializable {
	
	private static final long serialVersionUID = 4502929914718657671L;
	private Shop shop;
	private String detailsUrl;
	private Long id;
	private String keywordsBundle;
	private Long priceInCent;
	
    @Id  @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId(){
		return id;
	}
    
    public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne(optional=false, cascade=CascadeType.PERSIST)
	public Shop getShop(){
		return shop;
	}
	
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
	@Basic(optional=true)
	public String getDetailsURL() {
		return detailsUrl;
	}
	
	public void setDetailsURL(String shopURLString) {
		
		try {
			new URL(shopURLString);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException(e);
		}
		
		this.detailsUrl = shopURLString;
	}
	
	public void setPriceInCent(Long priceInCent) {
		this.priceInCent = priceInCent;
	}
	
	@Column(nullable=false, scale=12, precision=0)
	public Long getPriceInCent() {
		return priceInCent;
	}
	
	@Column(nullable=false, length=1024)
	public String getKeywordsBundle() {
		return keywordsBundle;
	}
	
	public void setKeywordsBundle(String keywordsBundle) {
		
		if(keywordsBundle!=null){
			String replaceAll = keywordsBundle.trim().toLowerCase().replaceAll("\\s{2,}", " ");
			keywordsBundle = replaceAll.replaceAll("\\|", "\\|\\|");
			
			
			String[] split = keywordsBundle.split(" ");
			keywordsBundle = "|" +  StringUtils.join(split, "|") + "|";
		}
		
		this.keywordsBundle = keywordsBundle;
	}
	
	
	
	@Transient
	URL getDetailsURLAsURL(){
		URL url = null;
		try {
			url = new URL(getDetailsURL());
		} catch (MalformedURLException e) {
			
		}
		return url;
	}

}
