package com.danidemi.europrice.web.controller.api;

import java.util.List;

/** 
 * Gives a search score to a {@link ResourceProductItem}.
 * */
public class RelevancyScorer {
	
	private List<String> terms;

	public RelevancyScorer(List<String> terms) {
		this.terms = terms;
	}

	double score(ResourceProductItem item){
		
		double score = 0;
		for (String term : terms) {
			if( item.getName().contains(term)) {
				score = score + 1;
			}
		}
		return score;
		
	}
	
}
