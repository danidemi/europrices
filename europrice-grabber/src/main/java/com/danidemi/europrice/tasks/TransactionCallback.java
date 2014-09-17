package com.danidemi.europrice.tasks;

import org.slf4j.Logger;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.danidemi.europrice.poc.pricegrabber.ScrapedShopItem;
import com.danidemi.jlubricant.slf4j.LoggerFactory;

/**
 * This callback sends "fake" events to its delegate corresponding on when a transaction is opened and closed.
 * This is because, using JPA, it would be nice if the delegate release any entity when a transaction is committed,
 * in order to avoid to have detached objects across two transactions.
 */
public class TransactionCallback extends DelegateCallback {

	private Logger log = LoggerFactory.getLogger(TransactionCallback.class);
	private PlatformTransactionManager txManager;
	private TransactionStatus transaction;
	private int commitInterval;
	private int countdownToCommit;
	
	@Override
	public void onStartScraping() {
		super.onStartScraping();
	}

	@Override
	public void onNewShopItem(ScrapedShopItem item) {
		if(transaction == null){
			
			super.onStartScraping();
			
			DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
			txDefinition.setName("Tx" + System.currentTimeMillis());
			transaction = txManager.getTransaction( txDefinition );
			log.info("Opened transaction {}.", transaction);
			countdownToCommit = commitInterval;
		}
		
		super.onNewShopItem(item);
		
		countdownToCommit--;
		if(countdownToCommit == 0){
			countdownToCommit = commitInterval;
			commit();
		}
		
		
	}


	@Override
	public void onEndScraping() {
		
		super.onEndScraping();
		
		if(transaction != null){
			commit();
		}
	}
	
	public void setCommitInterval(int commitInterval) {
		this.commitInterval = commitInterval;
	}
	
	public void setTxManager(PlatformTransactionManager txManager) {
		this.txManager = txManager;
	}
	
	private void commit() {
		txManager.commit(transaction);
		log.info("Committed transaction {}.", transaction);
		transaction = null;
		super.onEndScraping();
	}

}
