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
import com.danidemi.europrice.utils.Json;
import com.danidemi.jlubricant.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Network {
	
	private static final Logger log = LoggerFactory.getLogger(Network.class);

	private Json mapper = new Json();
	
	private String storeProductUri;
	
	public Network() {
	}
	
	public synchronized void storeNewProducts(List<ScrapedProduct> productsToStore) throws JsonGenerationException, JsonMappingException, IOException {
						
		CloseableHttpClient httpClient;
		httpClient = HttpClientBuilder.create().build();
		String json = mapper.toJson(productsToStore);
		
		HttpPost post = new HttpPost(storeProductUri);
		post.setHeader("Content-Type", "application/json");
		HttpEntity entity = new StringEntity(json);
		post.setEntity(entity);
		
		HttpContext ctx = new BasicHttpContext();
		CloseableHttpResponse response = httpClient.execute(post, ctx);
		
		if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
			log.error("Status [{}] has been reported while saving {}", response.getStatusLine().getStatusCode(), json);
		}
		
		response.close();
		
		
	}
	
	public void setStoreProductsUri(String uri) {
		this.storeProductUri = uri;
	}
		
}
