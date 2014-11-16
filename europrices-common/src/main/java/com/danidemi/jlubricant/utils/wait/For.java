package com.danidemi.jlubricant.utils.wait;

import java.util.concurrent.TimeUnit;

/**
 * Wait for a given amount of time, regardless of any interruption.
 * @author Daniele Demichelis
 */
public class For {

	private long amount;

	public For(long millis) {
		this.amount = millis;
	}
	
	public For(long amount, TimeUnit unit){
		new For( TimeUnit.MILLISECONDS.convert(amount, unit) ).pause();
	}
	
	public void pause(){
		long now = System.currentTimeMillis();
		do{
			try {
				Thread.sleep(amount);
			} catch (InterruptedException e) {
				
			}			
		}while(System.currentTimeMillis() - now < amount);
	}
	
}
