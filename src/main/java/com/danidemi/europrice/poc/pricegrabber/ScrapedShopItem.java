package com.danidemi.europrice.poc.pricegrabber;

import java.net.URL;

import com.danidemi.europrice.utils.Utils.Language;

/**
 *
 * @author danidemi
 */
public class ScrapedShopItem {

    private Long priceInCent;
    private String description;
    private URL url;
	private String shopName;
	private Language language;

    public Long getPriceInCent() {
        return priceInCent;
    }

    public String getDescription() {
        return description;
    }

    public void setPriceInCent(Long priceInCent) {
        this.priceInCent = priceInCent;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    void setUrlDetail(URL href) {
        this.url = href;
    }

    public URL getUrlDetail() {
        return url;
    }

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
    
	public String getShopName() {
		return shopName;
	}
	
	public void setLanguage(Language language) {
		this.language = language;
	}
	
	public Language getLanguage() {
		return language;
	}
	    
}
