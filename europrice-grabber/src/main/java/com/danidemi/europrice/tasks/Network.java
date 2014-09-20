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

import com.danidemi.europrice.pricegrabber.screenscraping.action.ScrapedProduct;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Network {

	private ObjectMapper mapper = new ObjectMapper();
	private StringWriter writer = new StringWriter();
	
	private CloseableHttpClient httpClient;
	private String storeProductUri;
	
	public Network() {
		httpClient = HttpClientBuilder.create().build();
	}
	
	public synchronized void storeNewProducts(List<ScrapedProduct> productsToStore) throws JsonGenerationException, JsonMappingException, IOException {
				
		mapper.writeValue(writer, productsToStore);
		String json = writer.toString();
		
		storeProductUri = "http://httpbin.org/post";
		HttpPost post = new HttpPost(storeProductUri);
		post.setHeader("content", "application/json");
		HttpEntity entity = new StringEntity(json);
		post.setEntity(entity);
		
		HttpContext ctx = new BasicHttpContext();
		CloseableHttpResponse response = httpClient.execute(post, ctx);
		
		if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
			throw new RuntimeException("Server returned an error:" + response.getStatusLine());
		}
		
	}
	
	public void setStoreProductsUri(String uri) {
		this.storeProductUri = uri;
	}
		
}
