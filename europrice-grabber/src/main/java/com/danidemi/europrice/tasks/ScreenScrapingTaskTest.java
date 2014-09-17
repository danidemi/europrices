package com.danidemi.europrice.tasks;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;

import javax.transaction.TransactionManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.danidemi.europrice.db.ProductItem;
import com.danidemi.europrice.db.ProductItemRepository;
import com.danidemi.europrice.db.ShopRepository;
import com.danidemi.europrice.poc.pricegrabber.Callback;
import com.danidemi.europrice.poc.pricegrabber.Request;
import com.danidemi.europrice.poc.pricegrabber.ScrapedShopItem;
import com.danidemi.europrice.screenscraping.ScrapeContext;
import com.danidemi.europrice.screenscraping.ScrapeContextFactory;
import com.danidemi.europrice.screenscraping.Scraper;
import com.danidemi.europrice.tasks.scrapers.ProductItemScraper;
import com.danidemi.europrice.utils.Utils.Language;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ScreenScrapingTaskTest {
	
	@Mock ProductItemRepository productItemRepository;
	@Mock ShopRepository shopRepository;
	@Mock ScrapeContextFactory ctxFactory;
	@Mock ProductItemScraper scraper1;
	@Mock ProductItemScraper scraper2;
	@Mock PlatformTransactionManager txManager;
	
	@Captor ArgumentCaptor< ScrapeContext > scrapeCtxArg;
	@Captor ArgumentCaptor< Request > requestArg;
	@Captor ArgumentCaptor< Callback > callbackArg;
	@Captor ArgumentCaptor< ProductItem > dbProductItemArg;
	
	@InjectMocks ScreenScrapingTask tested = new ScreenScrapingTask();
	
	@Test
	public void shouldStoreTheLanguage() throws MalformedURLException {
		
		// given
		ScrapedShopItem item = new ScrapedShopItem(
				100_00L, "a wonderful smartphone", new URL("http://www.smartphone.com"), "TheShop", Language.de
				);
		tested.setScrapers( Arrays.asList( scraper1 ) );
		tested.setRequests( Arrays.asList( new Request("mobiles") ) );
		
		// when
		tested.run();
		
		// then
		verify(scraper1).scrape(scrapeCtxArg.capture(), requestArg.capture(), callbackArg.capture());
		
		// when
		Callback callback = callbackArg.getValue();
		callback.onStartScraping();
		callback.onNewShopItem(item);
		callback.onEndScraping();
		
		// then
		verify( productItemRepository ).save( dbProductItemArg.capture() );
		ProductItem dbItem = dbProductItemArg.getValue();
		assertThat( dbItem.getLanguage(), equalTo(Language.de) );
		
	}
	
}
