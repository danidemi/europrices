package com.danidemi.europrice.web.controller.api;

import com.danidemi.europrice.db.model.IProductItem;

public class ResourceProductItem {
	
	protected IProductItem dbProductItem;

	public ResourceProductItem(IProductItem dbProductItem) {
		this.dbProductItem = dbProductItem;
	}
	
	public String getName() {
		return this.dbProductItem.getShortDescription();
	}
	
	public Long getPriceInEuroCent() {
		return this.dbProductItem.getPriceInCent();
	}
	
	public ResourceShop getShop() {
		return new ResourceShop(dbProductItem.getShop());
	}
	
	public String getDetailsUrl() {
		return dbProductItem.getDetailsURL();
	}
	
	public String getLanguageIsoCode() {
		return dbProductItem.getLanguage().name();
	}

	public Long getId() {
		return dbProductItem.getId();
	}
	
}
