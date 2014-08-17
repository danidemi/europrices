package com.danidemi.europrice.web.controller.api;

import com.danidemi.europrice.db.ProductItem;

public class ResourceProductItemWithRelevancy extends ResourceProductItem {

	private Double relevancy;

	public ResourceProductItemWithRelevancy(ProductItem dbProductItem, RelevancyScorer scorer) {
		super(dbProductItem);
		relevancy = scorer.score(this);
	}
	
	public Double getRelevancy() {
		return relevancy;
	}

}
