package com.danidemi.europrice.pricegrabber.screenscraping.shops;

import java.util.Locale;
import java.util.Objects;

import net.sf.cglib.core.Local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.danidemi.europrice.pricegrabber.screenscraping.action.ProductScrapeAction;
import com.danidemi.europrice.pricegrabber.screenscraping.action.ScrapedProductCallback;
import com.danidemi.europrice.utils.Utils;
import com.danidemi.europrice.utils.Utils.Language;
import com.danidemi.jlubricant.screenscraping.ScreenScrapingException;
import com.danidemi.jlubricant.screenscraping.action.ScrapeAction;
import com.danidemi.jlubricant.screenscraping.context.ScrapeContext;

/**
 * Base class that allows just to implement {@link AbstractShopScraper#buildScrapeAction(ScrapeContext, Request, ScrapedProductCallback)} to define a {@link ProductItemScraper}.
 */
public abstract class AbstractShopScraper implements ProductItemScraper {
	
	private static Logger log = LoggerFactory.getLogger(AbstractShopScraper.class);

	protected final ProductScrapeAction action;
	protected ScrapeAction root;
	protected final String shopName;
	
	public AbstractShopScraper(String shopName, Utils.Language language) {
		action = new ProductScrapeAction();
		
		Objects.requireNonNull(language, "language cannot be null");
		Objects.requireNonNull(shopName, "shopName cannot be null");
		
		action.setLanguage(language);
		this.shopName = shopName;
	}
	
	public String getShopName() {
		return shopName;
	}

	protected abstract ScrapeAction buildScrapeAction(ScrapeContext ctx, Request request, ScrapedProductCallback callback) throws Exception;

	@Override
	public final void scrape(ScrapeContext ctx, Request request, ScrapedProductCallback callback) {
		
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
		} catch (ScreenScrapingException e) {
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
