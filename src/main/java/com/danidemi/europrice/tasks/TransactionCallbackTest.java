package com.danidemi.europrice.tasks;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import com.danidemi.europrice.poc.pricegrabber.Callback;
import com.danidemi.europrice.poc.pricegrabber.ScrapedShopItem;

@RunWith(MockitoJUnitRunner.class)
public class TransactionCallbackTest {

	@Mock PlatformTransactionManager txManager;
	@Mock Callback delegateCallback;
	TransactionStatus transaction;	
	ScrapedShopItem item = new ScrapedShopItem();
	
	@InjectMocks TransactionCallback tested = new TransactionCallback();
	
	
	@Before
	public void setUp(){
		transaction = mock(TransactionStatus.class);
		when( txManager.getTransaction(any(TransactionDefinition.class)) ).thenReturn( transaction );
	}
	
	@Test public void shouldOpenAndCommittransactionsAccordingToCommitInterval() {
		
		// given
		tested.setCommitInterval(2);
		
		// when
		tested.onStartScraping();
		tested.onNewShopItem(item);
		tested.onNewShopItem(item);
		tested.onNewShopItem(item);
		tested.onNewShopItem(item);
		tested.onEndScraping();
		
		// then
		verify(txManager, times(2)).getTransaction( any(TransactionDefinition.class) );
		verify(txManager, times(2)).commit( any(TransactionStatus.class ) );
		
	}
	
	@Test public void shouldManageOnlyOneTransactionIfItemsLessThanCommitInterval() {
		
		// given
		tested.setCommitInterval(100);
		
		// when
		tested.onStartScraping();
		tested.onNewShopItem(item);
		tested.onNewShopItem(item);
		tested.onNewShopItem(item);
		tested.onEndScraping();
		
		// then
		verify(txManager, times(1)).getTransaction( any(TransactionDefinition.class) );
		verify(txManager, times(1)).commit( any(TransactionStatus.class ) );
		
	}

	@Test public void shouldOpenAndCommitATransactionIfOneItemIsWritten() {
		
		// when
		tested.onStartScraping();
		tested.onNewShopItem(item);
		tested.onEndScraping();
		
		// then
		InOrder inOrder = inOrder(txManager);
		inOrder.verify(txManager).getTransaction( any(TransactionDefinition.class) );
		inOrder.verify(txManager).commit( any(TransactionStatus.class ) );
		
	}
	
	@Test public void shouldNotOpenATransactionIfNoItemsAreWritter() {
		
		// when
		tested.onStartScraping();
		tested.onEndScraping();
		
		// then
		verifyZeroInteractions( txManager );
		
	}
	
}
