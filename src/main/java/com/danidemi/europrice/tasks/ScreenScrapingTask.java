package com.danidemi.europrice.tasks;

import com.danidemi.europrice.db.ProductItem;
import com.danidemi.europrice.db.ProductItemRepository;
import com.danidemi.europrice.db.Shop;
import com.danidemi.europrice.db.ShopRepository;
import com.danidemi.europrice.poc.pricegrabber.Callback;
import com.danidemi.europrice.poc.pricegrabber.Item;

public class ScreenScrapingTask implements Runnable {
	
	private ShopRepository shopRepository;
	private ProductItemRepository productItemRepository;
	
	private Shop currentShop;

	@Override
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
		
	}
	
	protected void onStart() {
		currentShop = new Shop();
	}
	
	protected void onNewItem(Item item) {
		ProductItem productItem = currentShop.newProductItem();
		productItem.setKeywordsBundle( item.getDescription() );
		productItem.setShop(currentShop);
		productItem.setPriceInCent( item.getPriceInCent() );
		productItemRepository.save(productItem);
	}

	protected void onEnd() {
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
