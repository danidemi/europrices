package com.danidemi.jlubricant.screenscraping.action;

import java.net.MalformedURLException;

import org.openqa.selenium.By;

/**
 * Fluent access to {@link ScrapeAction ScrapeActions}
 * @author danidemi
 *
 */
public abstract class Actions {

	public static Sequence inSequence(ScrapeAction firstAction){
		Sequence actionList = new Sequence();
		actionList.add( firstAction );
		return actionList;
	}
	
	public static ScrapeAction goTo(String url) throws MalformedURLException{
		return inSequence( new GoToUrl(url) ).then( new Pause(3, 5) );
	}
	
	public static ScrapeAction search(String searchTerm, By searchField, By searchButton){
		Search search = new Search();
		search.setSearchField(searchField);
		search.setSearchText(searchTerm);
		search.setStartSearchButton(searchButton);
		
		return inSequence( search ).then( new Pause(3, 5) );
	}
	
	/** Return an action suited to scrape a site that shows results paginated through "prev" and "next" links. */
	public static ScrapeAction forEachPageWithNextLinkDo(By theNextSelector, ScrapeAction theAction) {
		ForEachPage forEachPage = new ForEachPage();
		forEachPage.setNextSelector(theNextSelector);
		forEachPage.setAction(theAction);
		return forEachPage;
	}
	
	public static ForEachItem forEachItem(By theItemSelector, ScrapeAction theAction){
		ForEachItem forEachItem = new ForEachItem();
		forEachItem.setItemSelector(theItemSelector);
		forEachItem.setAction(theAction);
		return forEachItem;
	}
	
	private Actions(){
		throw new UnsupportedOperationException("Not meant to be instantiated.");
	}
	
}
