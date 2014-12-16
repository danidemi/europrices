package com.danidemi.europrice.db.model;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import javassist.compiler.NoFieldException;

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
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cascade;

import com.danidemi.europrice.utils.Utils.Language;

/** A product from an online shop. */
@Entity
public class ProductItem implements Serializable, IProductItem {

	private static final long serialVersionUID = 4502929914718657671L;
	private Shop shop;
	private String detailsUrl;
	private Long id;
	private String keywordsBundle;
	private Long priceInCent;
	private Language language;

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	@ManyToOne(optional = false, cascade = CascadeType.PERSIST)
	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	@Override
	@Basic(optional = false)
	@Column(unique=true, nullable=false)
	public String getDetailsURL() {
		return detailsUrl;
	}

	public void setDetailsURL(String shopURLString) {

		try {
			new URL(shopURLString);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("The provided URL '" + shopURLString + "' is malformed. " + e.getMessage(), e);
		}

		this.detailsUrl = shopURLString;
	}

	public void setDetailsURL(URL shopURL) {

		this.detailsUrl = shopURL.toString();
	}

	public void setPriceInCent(Long priceInCent) {
		this.priceInCent = priceInCent;
	}

	@Override
	@Column(nullable = false, scale = 12, precision = 0)
	public Long getPriceInCent() {
		return priceInCent;
	}

	@Override
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
	
	@Override
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
	
	@Override
	@Transient
	public String getShortDescription() {
		
		String trim = keywordsBundle.replaceAll("\\|", " ").trim();
		return trim;
	}

	@Override
	@Transient
	public
	URL getDetailsURLAsURL() {
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

}
