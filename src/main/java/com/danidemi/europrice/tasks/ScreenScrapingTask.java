package com.danidemi.europrice.tasks;

import java.util.List;

import javax.transaction.Transactional;

import com.danidemi.europrice.db.ProductItem;
import com.danidemi.europrice.db.ProductItemRepository;
import com.danidemi.europrice.db.Shop;
import com.danidemi.europrice.db.ShopRepository;
import com.danidemi.europrice.poc.pricegrabber.Callback;
import com.danidemi.europrice.poc.pricegrabber.Item;
import com.danidemi.europrice.poc.pricegrabber.Request;
import com.danidemi.europrice.screenscraping.ScrapeContext;
import com.danidemi.europrice.screenscraping.ScrapeContextFactory;
import com.danidemi.europrice.screenscraping.Search;
import com.danidemi.europrice.tasks.scrapers.ProductItemScraper;

public class ScreenScrapingTask implements Runnable {
	
	private ShopRepository shopRepository;
	private ProductItemRepository productItemRepository;
	private Shop currentShop;
	private List<ProductItemScraper> scrapers;
	private List<Request> searches;
	private ScrapeContextFactory ctxFactory;
	

	public void setCtxFactory(ScrapeContextFactory ctxFactory) {
		this.ctxFactory = ctxFactory;
	}
	
	@Override @Transactional
	public void run() {
		
		Callback callback = new Callback() {
			
			@Override
			public void onStart() {
				ScreenScrapingTask.this.onStart();
			}
			
			@Override
			public void onNewItem(Item item) {
				ScreenScrapingTask.this.onNewItem(item);
			}
			
			@Override
			public void onEnd() {
				ScreenScrapingTask.this.onEnd();
			}
		};
	
		ScrapeContext scrapeContext = ctxFactory.getScrapeContext();
		for (Request request : searches) {
			
			for (ProductItemScraper scraper : scrapers) {
			
				scraper.scrape(scrapeContext, request, callback);
			
			}
			
		}
		
	}
	
	public void setScrapers(List<ProductItemScraper> scrapers) {
		this.scrapers = scrapers;
	}
	
	void setRequests(List<Request> searches){
		this.searches = searches;
	}
	
	private void onStart() {
		currentShop = null;
	}
	
	private void onNewItem(Item item) {
		
		if(currentShop == null){
			String shopName = item.getShopName();			
			currentShop = firstShop(shopName); 
			if(currentShop == null){
				currentShop = new Shop();
				currentShop.setName(item.getShopName());
			}
		}else{
			if(!currentShop.getName().equals(item.getShopName())){
				String shopName = item.getShopName();			
				currentShop = firstShop(shopName); 
				if(currentShop == null){
					currentShop = new Shop();
					currentShop.setName(item.getShopName());
				}
			}
		}
		
		ProductItem productItem = currentShop.newProductItem();
		productItem.setKeywordsBundle( item.getDescription() );
		productItem.setShop(currentShop);
		productItem.setPriceInCent( item.getPriceInCent() );
		productItemRepository.save(productItem);
		
	}

	private Shop firstShop(String shopName) {
		List<Shop> findByName = shopRepository.findByName(shopName);
		Shop theShop = findByName.size() == 1 ? findByName.get(0) : null;
		return theShop;
	}

	private void onEnd() {
		currentShop = null;
	}
	
	public void setProductItemRepository(
			ProductItemRepository productItemRepository) {
		this.productItemRepository = productItemRepository;
	}
	
	public void setShopRepository(ShopRepository shopRepository) {
		this.shopRepository = shopRepository;
	}
	
}
