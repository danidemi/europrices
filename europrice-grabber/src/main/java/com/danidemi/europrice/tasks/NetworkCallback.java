package com.danidemi.europrice.tasks;

import java.io.IOException;
import java.util.ArrayList;

import com.danidemi.europrice.pricegrabber.screenscraping.action.ScrapedProduct;
import com.danidemi.europrice.pricegrabber.screenscraping.action.ScrapedProductCallback;

public class NetworkCallback implements ScrapedProductCallback {

	private ArrayList<ScrapedProduct> products;
	private JsonNetwork network;
	private int bufferSize;
	
	public NetworkCallback() {
		products = new ArrayList<ScrapedProduct>();
		bufferSize = 20;
	}
	
	@Override
	public void onStartScraping() {
		products.clear();
	}

	@Override
	public void onNewShopItem(ScrapedProduct item) {
		products.add( item );
		if(products.size() > bufferSize){
			flush();
		}
	}

	@Override
	public void onEndScraping() {
		flush();
	}

	private void flush() {
		
		try {
			network.storeNewProducts(products);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		products.clear();
		
	}
	
	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}
	
	public void setNetwork(JsonNetwork network) {
		this.network = network;
	}
}
