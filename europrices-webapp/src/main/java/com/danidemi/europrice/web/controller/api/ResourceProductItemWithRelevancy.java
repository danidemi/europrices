package com.danidemi.europrice.web.controller.api;

import com.danidemi.europrice.db.model.Favouritable;
import com.danidemi.europrice.db.model.IProductItem;

public class ResourceProductItemWithRelevancy extends ResourceProductItem {

	private Double relevancy;

	public ResourceProductItemWithRelevancy(IProductItem dbProductItem, RelevancyScorer scorer) {
		super(dbProductItem);
		relevancy = scorer.score(this);
	}
	
	public Double getRelevancy() {
		return relevancy;
	}
	
	public boolean isFavourite() {
		if(this.dbProductItem instanceof Favouritable){
			return ((Favouritable) this.dbProductItem).isFavourite(); 
		}else{
			return false;
		}
	}
	
}
