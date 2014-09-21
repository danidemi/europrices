package com.danidemi.europrice.web.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.danidemi.europrice.db.ProductItemRepository;
import com.danidemi.europrice.db.SearchRepository;
import com.danidemi.europrice.db.ShopRepository;
import com.danidemi.europrice.pricegrabber.screenscraping.action.ScrapedProduct;
import com.danidemi.europrice.pricegrabber.screenscraping.action.ScrapedProductCallback;

@Controller
@RequestMapping(value="/adminapi/", produces="application/json")
public class AdminApi_0_0_1 {
	
	public enum Outcome { OK, FAILED }
	
	@Autowired ShopRepository shopRepo;
	@Autowired ProductItemRepository productItemRep;
	@Autowired SearchRepository searchRepository;
	@Autowired ScrapedProductCallback callback;
			
	@RequestMapping(value="/storeProducts", method=RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public Result storeProducts(@RequestBody List<ScrapedProduct> products){

		synchronized (callback) {
			callback.onStartScraping();
			for (ScrapedProduct scrapedProduct : products) {
				callback.onNewShopItem( scrapedProduct );
			}
			callback.onEndScraping();			
		}
		
		return new Result(Outcome.OK);
	}
	
	private static class Result {
		
		
		private Outcome outcome;
		
		public Result(Outcome outcome) {
			super();
			this.outcome = outcome;
		}

		public void setOutcome(Outcome outcome) {
			this.outcome = outcome;
		}
		
		public Outcome getOutcome() {
			return outcome;
		}
		
	}
	
}
