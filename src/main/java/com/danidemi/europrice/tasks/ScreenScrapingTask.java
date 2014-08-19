package com.danidemi.europrice.tasks;

import java.net.URL;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.danidemi.europrice.db.ProductItem;
import com.danidemi.europrice.db.ProductItemRepository;
import com.danidemi.europrice.db.Shop;
import com.danidemi.europrice.db.ShopRepository;
import com.danidemi.europrice.poc.pricegrabber.Callback;
import com.danidemi.europrice.poc.pricegrabber.Request;
import com.danidemi.europrice.poc.pricegrabber.ScrapedShopItem;
import com.danidemi.europrice.screenscraping.ScrapeContext;
import com.danidemi.europrice.screenscraping.ScrapeContextFactory;
import com.danidemi.europrice.tasks.scrapers.ProductItemScraper;
import com.danidemi.europrice.utils.Utils;

@Deprecated
public class ScreenScrapingTask implements Runnable {
	
	private Logger log = LoggerFactory.getLogger(ScreenScrapingTask.class);

	// dependencies
	private ShopRepository shopRepository;
	private ProductItemRepository productItemRepository;
	private List<ProductItemScraper> scrapers;
	private List<Request> searches;
	private ScrapeContextFactory ctxFactory;
	private PlatformTransactionManager txManager;
	
	private Shop currentShop;	
	private int itemCount;
	private TransactionStatus transaction;
	private int itemsPerTransactions;
	
	private int newItems = 0;
	private int updatedItems = 0;
	private int errorItems = 0;
		
	@Override
	public void run() {
		
		Callback callback = new Callback() {
			
			@Override
			public void onStartScraping() {
				ScreenScrapingTask.this.onStartScraping();
			}
			
			@Override
			public void onNewShopItem(ScrapedShopItem item) {
				ScreenScrapingTask.this.onNewShopItem(item);
			}
			
			@Override
			public void onEndScraping() {
				ScreenScrapingTask.this.onEndScraping();
			}
		};
	
		ScrapeContext scrapeContext = ctxFactory.getScrapeContext();
		for (Request request : searches) {
			for (ProductItemScraper scraper : scrapers) {
				
				log.info("Scraping for '{}' on '{}'.", request, scraper);
				
				scraper.scrape(scrapeContext, request, callback);
			}
		}
		
	}
	
	private void onStartScraping() {
		currentShop = null;
		itemCount = 0;
		
		transaction = txManager.getTransaction( new DefaultTransactionDefinition() );
	}
	

	
	private void onNewShopItem(ScrapedShopItem item) {
		
		if(currentShop == null){
			String shopName = item.getShopName();			
			currentShop = firstShop(shopName); 
			if(currentShop == null){
				currentShop = new Shop();
				currentShop.setName(item.getShopName());
				shopRepository.save(currentShop);
			}
		}else{
			if(!currentShop.getName().equals(item.getShopName())){
				String shopName = item.getShopName();			
				currentShop = firstShop(shopName); 
				if(currentShop == null){
					currentShop = new Shop();
					currentShop.setName(item.getShopName());
					shopRepository.save(currentShop);
				}
			}
		}
		
		
		
		ProductItem productItem = null;
		boolean isNew = false;
		try{
			
			
			URL urlDetail = item.getUrlDetail();
			productItem = Utils.firstIfExists( productItemRepository.findByDetailsURL(urlDetail.toString()) );
			if(productItem == null){
				log.debug("New product '{}' from {}.", StringUtils.abbreviate(item.getDescription(), 25), currentShop);
				productItem = currentShop.newProductItem();
				isNew = true;
			}else{
				log.debug("Updating existing product '{}'.", StringUtils.abbreviate(productItem.getShortDescription(), 25));
				isNew = false;
			}
			productItem.withKeywordsIn( item.getDescription() );
			productItem.setShop(currentShop);
			productItem.setPriceInCent( item.getPriceInCent() );
			productItem.setDetailsURL(urlDetail);
			productItem.setLanguage( item.getLanguage() );
			
			productItemRepository.save(productItem);			
			itemCount++;
			
			if(isNew) { newItems++;} else {updatedItems++;}
			
		}catch(Exception e){
			log.error("Item " + productItem + " could not be saved, ignoring", e);
			errorItems++;
		}
		
		if(itemCount == itemsPerTransactions){
			
			log.debug("Committing transaction for {} items.", itemCount);
			
			itemCount = 0;
			txManager.commit(transaction);
			transaction = txManager.getTransaction( new DefaultTransactionDefinition() );
		}
		
	}

	private Shop firstShop(String shopName) {
		List<Shop> findByName = shopRepository.findByName(shopName);
		Shop theShop = findByName.size() == 1 ? findByName.get(0) : null;
		return theShop;
	}

	private void onEndScraping() {
		currentShop = null;
		
		log.debug("Committing transaction for {} items.", itemCount);
		
		txManager.commit(transaction);
		
		log.info("{} new items, {} updated, {} with errors", newItems, updatedItems, errorItems);
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
	
	public void setProductItemRepository(
			ProductItemRepository productItemRepository) {
		this.productItemRepository = productItemRepository;
	}
	
	public void setShopRepository(ShopRepository shopRepository) {
		this.shopRepository = shopRepository;
	}
	
	public void setCtxFactory(ScrapeContextFactory ctxFactory) {
		this.ctxFactory = ctxFactory;
	}
	
	public void setTxManager(PlatformTransactionManager txManager) {
		this.txManager = txManager;
	}
	
}
