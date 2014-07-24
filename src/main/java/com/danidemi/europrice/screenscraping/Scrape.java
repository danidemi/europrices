package com.danidemi.europrice.screenscraping;

import org.openqa.selenium.By;

public class Scrape {

	public static ActionList inSequence(ScrapeAction firstAction){
		ActionList actionList = new ActionList();
		actionList.add( firstAction );
		return actionList;
	}
	
	public static Search search(String searchTerm, By searchField, By searchButton){
		Search search = new Search();
		search.setSearchField(searchField);
		search.setSearchText(searchTerm);
		search.setStartSearchButton(searchButton);
		return search;
	}
	
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
	
	private Scrape(){
		throw new UnsupportedOperationException("Not meant to be instantiated.");
	}
	
}
