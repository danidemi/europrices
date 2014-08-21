package com.danidemi.europrice.web.controller.api;

public class ResourceProductItem {
	
	private com.danidemi.europrice.db.ProductItem dbProductItem;

	public ResourceProductItem(com.danidemi.europrice.db.ProductItem dbProductItem) {
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
	
}
