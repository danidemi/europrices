package com.danidemi.europrice.tasks;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.danidemi.europrice.poc.pricegrabber.ScrapedShopItem;

public class TransactionCallback extends DelegateCallback {

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
			transaction = txManager.getTransaction( new DefaultTransactionDefinition() );
			countdownToCommit = commitInterval;
		}
		
		super.onNewShopItem(item);
		
		countdownToCommit--;
		if(countdownToCommit == 0){
			countdownToCommit = commitInterval;
			txManager.commit(transaction);
			transaction = null;
		}
		
		
	}

	@Override
	public void onEndScraping() {
		
		super.onEndScraping();
		
		if(transaction != null){
			txManager.commit(transaction);					
		}
	}

	public void setCommitInterval(int commitInterval) {
		this.commitInterval = commitInterval;
	}
	
	public void setTxManager(PlatformTransactionManager txManager) {
		this.txManager = txManager;
	}

}
