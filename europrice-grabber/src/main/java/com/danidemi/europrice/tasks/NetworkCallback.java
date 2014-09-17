package com.danidemi.europrice.tasks;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.danidemi.europrice.db.ProductItem;
import com.danidemi.europrice.db.ProductItemRepository;
import com.danidemi.europrice.db.Shop;
import com.danidemi.europrice.db.ShopRepository;
import com.danidemi.europrice.network.Client;
import com.danidemi.europrice.poc.pricegrabber.Callback;
import com.danidemi.europrice.poc.pricegrabber.ScrapedShopItem;
import com.danidemi.europrice.utils.Utils;

public class NetworkCallback implements Callback {
	
	private Logger log = LoggerFactory.getLogger(NetworkCallback.class);
	
	private List<ScrapedShopItem> items = new ArrayList<>();
	private int newItems = 0;
	private int updatedItems = 0;
	private int errorItems = 0;
	
	private Client client;

	public void setClient(Client client) {
		this.client = client;
	}
	
	@Override
	public void onStartScraping() {		
		items.clear();
	}

	@Override
	public void onNewShopItem(ScrapedShopItem item) {
		
		items.add(item);
						
	}

	@Override
	public void onEndScraping() {
		
		client.sentItems(items);
		
	}
	
	private Shop firstShop(String shopName) {

	}

}
