package com.danidemi.europrice.web.controller.api;

import com.danidemi.europrice.db.model.Shop;

public class ResourceShop {

	private Shop dbShop;

	public ResourceShop(Shop dbShop) {
		this.dbShop = dbShop;
	}

	public String getName() {
		return dbShop.getName();
	}
	
}
