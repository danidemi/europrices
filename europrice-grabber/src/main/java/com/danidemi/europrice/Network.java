package com.danidemi.europrice;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.danidemi.europrice.pricegrabber.screenscraping.action.ScrapedProduct;
import com.danidemi.europrice.utils.Utils.Language;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Network {

	public void run() throws JsonGenerationException, JsonMappingException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		StringWriter writer = new StringWriter();
		
		ScrapedProduct scrapedProduct = new ScrapedProduct();
		scrapedProduct.setDescription("description");
		scrapedProduct.setLanguage(Language.ca);
		scrapedProduct.setPriceInCent(12345L);
		scrapedProduct.setShopName("shopName");
		scrapedProduct.setUrlDetail(new URL("http://cacca"));
		
		mapper.writeValue(writer, scrapedProduct);
		String json = writer.toString();
		
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		
		HttpPost post = new HttpPost("http://httpbin.org/post");
		post.setHeader("content", "application/json");
		HttpEntity entity = new StringEntity(json);
		post.setEntity(entity);
		
		HttpContext ctx = new BasicHttpContext();
		CloseableHttpResponse response = httpClient.execute(post, ctx);
		
		List<String> readLines = IOUtils.readLines( response.getEntity().getContent() );
		
		System.out.println( readLines );
	}
	
	public static void main(String[] args) {
		try {
			new Network().run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
