package com.danidemi.europrice.tasks;

import java.net.URL;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.danidemi.europrice.db.ProductItem;
import com.danidemi.europrice.db.ProductItemRepository;
import com.danidemi.europrice.db.Shop;
import com.danidemi.europrice.db.ShopRepository;
import com.danidemi.europrice.poc.pricegrabber.Callback;
import com.danidemi.europrice.poc.pricegrabber.ScrapedShopItem;
import com.danidemi.europrice.utils.Utils;

public class BusinessCallback implements Callback {
	
	private Logger log = LoggerFactory.getLogger(ScreenScrapingTask.class);
	
	private ShopRepository shopRepository;
	private ProductItemRepository productItemRepository;
	private Shop currentShop;	
	private int itemCount;	
	private int newItems = 0;
	private int updatedItems = 0;
	private int errorItems = 0;


	@Override
	public void onStartScraping() {
		currentShop = null;
	}

	@Override
	public void onNewShopItem(ScrapedShopItem item) {
		
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
				
	}

	@Override
	public void onEndScraping() {
		currentShop = null;
		
		
		log.info("{} new items, {} updated, {} with errors", newItems, updatedItems, errorItems);
		
	}
	
	private Shop firstShop(String shopName) {
		List<Shop> findByName = shopRepository.findByName(shopName);
		Shop theShop = findByName.size() == 1 ? findByName.get(0) : null;
		return theShop;
	}

}
