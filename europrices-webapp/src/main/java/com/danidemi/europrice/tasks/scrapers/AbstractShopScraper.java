package com.danidemi.europrice.tasks.scrapers;

import java.util.Locale;
import java.util.Objects;

import net.sf.cglib.core.Local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.danidemi.europrice.poc.pricegrabber.Callback;
import com.danidemi.europrice.poc.pricegrabber.MyAction;
import com.danidemi.europrice.poc.pricegrabber.Request;
import com.danidemi.europrice.screenscraping.ScrapeAction;
import com.danidemi.europrice.screenscraping.ScrapeContext;
import com.danidemi.europrice.screenscraping.ScrapeException;
import com.danidemi.europrice.utils.Utils;
import com.danidemi.europrice.utils.Utils.Language;

/**
 * Base class that allows just to implement {@link AbstractShopScraper#buildScrapeAction(ScrapeContext, Request, Callback)} to define a {@link ProductItemScraper}.
 */
public abstract class AbstractShopScraper implements ProductItemScraper {
	
	private static Logger log = LoggerFactory.getLogger(AbstractShopScraper.class);

	protected final MyAction action;
	protected ScrapeAction root;
	protected final String shopName;
	
	public AbstractShopScraper(String shopName, Utils.Language language) {
		action = new MyAction();
		
		Objects.requireNonNull(language, "language cannot be null");
		Objects.requireNonNull(shopName, "shopName cannot be null");
		
		action.setLanguage(language);
		this.shopName = shopName;
	}
	
	public String getShopName() {
		return shopName;
	}

	protected abstract ScrapeAction buildScrapeAction(ScrapeContext ctx, Request request, Callback callback) throws Exception;

	@Override
	public final void scrape(ScrapeContext ctx, Request request, Callback callback) {
		
		action.setCallback(callback);
		try {
			root = buildScrapeAction(ctx, request, callback);
		} catch (Exception e) {
			throw new RuntimeException("An error occurred while createing shop scrape action", e);
		}
		
		
		boolean isStarted = false;
		try {
			root.startScraping();
			isStarted = true;
			
			root.scrape(ctx);
			
			root.endScraping();
			isStarted = false;
		} catch (ScrapeException e) {
			log.error("An error occurred while scraping '" + this + "'.", e);
		} finally {
			if(isStarted){
				try{
					root.endScraping();
				}catch(Exception e){
					log.error("An error occurred while ending scraping on '" + this + "', giving up!", e);
				}
			}
		}
	}
	
}
