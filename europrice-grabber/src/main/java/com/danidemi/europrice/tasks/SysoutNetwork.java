package com.danidemi.europrice.tasks;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;

import com.danidemi.europrice.pricegrabber.screenscraping.action.ScrapedProduct;
import com.danidemi.jlubricant.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SysoutNetwork extends JsonNetwork {
	
	private static final Logger log = LoggerFactory.getLogger(SysoutNetwork.class);

	private String storeProductUri;
	
	public SysoutNetwork() {
	}
	
	public synchronized void storeNewProducts(List<ScrapedProduct> productsToStore) throws JsonGenerationException, JsonMappingException, IOException {
						
		String json = map(productsToStore);
		System.out.println("==========================");
		System.out.println(json);
		System.out.println("==========================");
				
	}
		
}
