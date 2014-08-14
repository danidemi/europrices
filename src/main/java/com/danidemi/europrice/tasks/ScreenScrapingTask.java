package com.danidemi.europrice.tasks;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.danidemi.europrice.db.ProductItem;
import com.danidemi.europrice.db.ProductItemRepository;
import com.danidemi.europrice.db.Shop;
import com.danidemi.europrice.db.ShopRepository;
import com.danidemi.europrice.poc.pricegrabber.Callback;
import com.danidemi.europrice.poc.pricegrabber.ShopItem;
import com.danidemi.europrice.poc.pricegrabber.Request;
import com.danidemi.europrice.screenscraping.ScrapeContext;
import com.danidemi.europrice.screenscraping.ScrapeContextFactory;
import com.danidemi.europrice.screenscraping.Search;
import com.danidemi.europrice.tasks.scrapers.ProductItemScraper;

public class ScreenScrapingTask implements Runnable {
	
	private Logger log = LoggerFactory.getLogger(ScreenScrapingTask.class);
	
	private ShopRepository shopRepository;
	private ProductItemRepository productItemRepository;
	private Shop currentShop;
	private List<ProductItemScraper> scrapers;
	private List<Request> searches;
	private ScrapeContextFactory ctxFactory;
	private int itemCount;
	private JpaTransactionManager txManager;
	private TransactionStatus transaction;
	private int itemsPerTransactions;
		
	@Override @Transactional
	public void run() {
		
		Callback callback = new Callback() {
			
			@Override
			public void onStartScraping() {
				ScreenScrapingTask.this.onStartScraping();
			}
			
			@Override
			public void onNewShopItem(ShopItem item) {
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
	
	private void onNewShopItem(ShopItem item) {
		
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
		
		itemCount++;
		
		ProductItem productItem = currentShop.newProductItem();
		productItem.setKeywords( item.getDescription() );
		productItem.setShop(currentShop);
		productItem.setPriceInCent( item.getPriceInCent() );
		productItem.setDetailsURL(item.getUrlDetail());
		
		try{
			productItemRepository.save(productItem);			
		}catch(Exception e){
			log.error("Item " + productItem + " could not be saved, ignoring", e);
		}
		
		if(itemCount == itemsPerTransactions){
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
		txManager.commit(transaction);
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
	
	public void setTxManager(JpaTransactionManager txManager) {
		this.txManager = txManager;
	}
	
}
