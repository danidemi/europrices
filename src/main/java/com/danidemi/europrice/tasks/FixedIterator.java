package com.danidemi.europrice.tasks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.danidemi.europrice.poc.pricegrabber.Request;
import com.danidemi.europrice.tasks.scrapers.ProductItemScraper;

/**
 * {@link FixedIterator} provide configure a set of searches on a a given set of shop.
 */
public class FixedIterator implements Iterable< Tupla2<Request,ProductItemScraper> >{

	private List<ProductItemScraper> scrapers;
	private List<Request> searches;
	
	@Override
	public Iterator<Tupla2<Request, ProductItemScraper>> iterator() {
		
		ArrayList<Tupla2<Request, ProductItemScraper>> result = new ArrayList<>();
		
		for (Request request : searches) {
			for (ProductItemScraper scraper : scrapers) {
				result.add( new Tupla2<Request, ProductItemScraper>(request, scraper) );
			}
		}
		
		return result.iterator();
	}
	
	/**
	 * The list of {@link ProductItemScraper} that will be run as part of this task.
	 */
	public void setScrapers(List<ProductItemScraper> scrapers) {
		this.scrapers = scrapers;
	}
	
	/**
	 * The list of request that will be issued as part of this task.
	 */
	public void setRequests(List<Request> searches){
		this.searches = searches;
	}
	
	

}
