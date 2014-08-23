package com.danidemi.europrice.tasks;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.Iterator;

import org.apache.commons.collections.IteratorUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.danidemi.europrice.poc.pricegrabber.Request;
import com.danidemi.europrice.screenscraping.Search;
import com.danidemi.europrice.tasks.scrapers.ProductItemScraper;

@RunWith(MockitoJUnitRunner.class)
public class FixedIteratorTest {

	@Test
	public void should(){
		
		FixedIterator fixedIterator = new FixedIterator();
		fixedIterator.setRequests( Arrays.asList( new Request("r1") ) );
		fixedIterator.setScrapers( Arrays.asList( mock( ProductItemScraper.class ) ) );
		
		Iterable<Tupla2<Request,ProductItemScraper>> tested = fixedIterator;
		
		Iterator<Tupla2<Request, ProductItemScraper>> iterator = tested.iterator();
		
		int consumeIteratorCountingItems = consumeIteratorCountingItems(iterator);
		
		assertThat( consumeIteratorCountingItems, equalTo(1) );
	}
	
	@Test
	public void should2(){
		
		FixedIterator fixedIterator = new FixedIterator();
		Request request = new Request("r1");
		fixedIterator.setRequests( Arrays.asList( request ) );
		ProductItemScraper mock = mock( ProductItemScraper.class );
		fixedIterator.setScrapers( Arrays.asList( mock ) );
		
		Iterable<Tupla2<Request,ProductItemScraper>> tested = fixedIterator;
		
		Iterator<Tupla2<Request, ProductItemScraper>> iterator = tested.iterator();
		
		for (Tupla2<Request, ProductItemScraper> tupla2 : tested) {
			assertThat( tupla2.getOne(), equalTo(request)  );
			assertThat( tupla2.getTwo(), equalTo(mock)  );
		}
	}	

	private int consumeIteratorCountingItems(
			Iterator<Tupla2<Request, ProductItemScraper>> iterator) {
		int count = 0;
		while(iterator.hasNext()){
			iterator.next();
			count++;
		}
		return count;
	}
	
}
