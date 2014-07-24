package com.danidemi.europrice.poc.pricegrabber;

import java.net.URL;

/**
 *
 * @author danidemi
 */
public class Item {

    private Long priceInCent;
    private String description;
    private URL url;

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
    
    
}
