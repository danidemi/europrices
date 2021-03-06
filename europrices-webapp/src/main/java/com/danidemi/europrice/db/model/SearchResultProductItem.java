package com.danidemi.europrice.db.model;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.danidemi.europrice.utils.Utils.Language;

/** 
 * A product as a search result triggered by a user.
 * This entity is extracted from view "SEARCH_RESULT_PRODUCT_ITEM" where more details are available as for instance
 * an attribute that tell if a specific user added the item in the list of favourites.
 * This is to avoid the usual 1+N antipattern.
 */
@Entity
@Table(name="SEARCH_RESULT_PRODUCT_ITEM")
public class SearchResultProductItem implements Serializable, IProductItem, Favouritable {

	private static final long serialVersionUID = -5281431782994678243L;
	private Shop shop;
	private String detailsUrl;
	private Long id;
	private String keywordsBundle;
	private Long priceInCent;
	private Language language;	
	private boolean isFavourite;
	private String username;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
		
	@Basic(optional=false) 
	@Column(name="USERNAME")
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String id) {
		this.username = id;
	}	
	
	@ManyToOne(optional = false, cascade = CascadeType.PERSIST)
	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	@Basic(optional = false)
	@Column(unique=true, nullable=false)
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

	public void setDetailsURL(URL shopURL) {

		this.detailsUrl = shopURL.toString();
	}

	public void setPriceInCent(Long priceInCent) {
		this.priceInCent = priceInCent;
	}

	@Column(nullable = false, scale = 12, precision = 0)
	public Long getPriceInCent() {
		return priceInCent;
	}

	@Column(nullable = false, length = 1024)
	public String getKeywordsBundle() {
		return keywordsBundle;
	}

	public void setKeywordsBundle(String keywordsBundle) {

		this.keywordsBundle = keywordsBundle;
	}
	
	public void setLanguage(Language language) {
		this.language = language;
	}
	
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	public Language getLanguage() {
		return language;
	}
	
	/** Set this product keywords. */
	@Transient
	public void withKeywords(String... keywords) {
		
		StringBuilder sb = new StringBuilder();
		for (String string : keywords) {
			if(sb.length() > 0){
				sb.append("|");
			}
			sb.append( string.toLowerCase().trim().replaceAll("\\|", "\\|\\|") );
		}
		keywordsBundle = "|" + sb.toString() + "|";
		
	}
	
	/** Set this product keywords using all the words in the provided string. */
	public void withKeywordsIn(String stringWithKeywords) {
		
		if (stringWithKeywords != null) {
			String replaceAll = stringWithKeywords.trim().toLowerCase()
					.replaceAll("\\s{2,}", " ");
			stringWithKeywords = replaceAll.replaceAll("\\|", "\\|\\|");

			String[] split = stringWithKeywords.split(" ");
			stringWithKeywords = "|" + StringUtils.join(split, "|") + "|";
		}

		this.keywordsBundle = stringWithKeywords;
		
	}
	
	@Transient
	public String getShortDescription() {
		
		String trim = keywordsBundle.replaceAll("\\|", " ").trim();
		return trim;
	}

	@Transient
	public URL getDetailsURLAsURL() {
		URL url = null;
		try {
			url = new URL(getDetailsURL());
		} catch (MalformedURLException e) {

		}
		return url;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).toString();
	}
	
	@Override
	@Basic(optional = false)
	@Column(name="IS_FAVOURITE")
	public boolean isFavourite() {
		return isFavourite;
	}
	
	public void setFavourite(boolean isFavourite) {
		this.isFavourite = isFavourite;
	}

	@Override
	@Transient
	public boolean getFavourite() {
		return isFavourite;
	}

}
