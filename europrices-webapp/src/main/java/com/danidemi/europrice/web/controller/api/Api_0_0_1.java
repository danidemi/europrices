package com.danidemi.europrice.web.controller.api;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.collections.ComparatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.cglib.core.Transformer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.danidemi.europrice.db.model.Favourite;
import com.danidemi.europrice.db.model.IProductItem;
import com.danidemi.europrice.db.model.ProductItem;
import com.danidemi.europrice.db.model.Search;
import com.danidemi.europrice.db.repository.FavouriteRepository;
import com.danidemi.europrice.db.repository.ProductItemRepository;
import com.danidemi.europrice.db.repository.SearchRepository;
import com.danidemi.europrice.db.repository.SearchResultProductItemRepositoryImpl;
import com.danidemi.europrice.db.repository.ShopRepository;
import com.danidemi.europrice.utils.Utils;

@Controller
@RequestMapping(value="/api/", produces="application/json")
public class Api_0_0_1 {
	
	@Autowired ShopRepository shopRepo;
	@Autowired ProductItemRepository productItemRep;
	@Autowired SearchRepository searchRepository;
	@Autowired FavouriteRepository favouriteRepository;
	@Autowired SearchResultProductItemRepositoryImpl searchResultProductItemRepository;
	
	private SearchTermSplitter splitter = new SearchTermSplitter();
		
	@RequestMapping(value="/version", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public String version() {
		return "v0.0.001";
	}
	
	@Transactional
	@RequestMapping(value="/toggleFavourite", method=RequestMethod.POST)
	@ResponseBody
	public boolean toggleFavourite( @RequestParam String favouriteId, @RequestParam String userId ) {
				
		boolean newStatus;
		List<Favourite> findByFavouriteIdAndUserId = favouriteRepository.findByFavouriteIdAndUserId(favouriteId, userId);
		if( org.apache.commons.collections4.CollectionUtils.isEmpty( findByFavouriteIdAndUserId )){
			Favourite newFavourite = new Favourite(favouriteId, userId);
			favouriteRepository.save( newFavourite );
			newStatus = true;
		}else{
			favouriteRepository.delete( findByFavouriteIdAndUserId );
			newStatus = false;
		}
		return newStatus;
	}
	
	@Transactional
	@RequestMapping(value="/search", method=RequestMethod.GET)
	@ResponseBody
	public List<ResourceProductItemWithRelevancy> search(@RequestParam("searchTerms")String searchTerms) {
				
		List<String> terms = splitter.split(searchTerms);
		
		RelevancyScorer scorer = new RelevancyScorer(terms);
		
		//List<? extends IProductItem> findProductItemsByKeywords = productItemRep.findProductItemsByKeywords(terms);
		List<? extends IProductItem> findProductItemsByKeywords = searchResultProductItemRepository.findProductItemsByKeywords(terms, null);
		
		
		List<ResourceProductItemWithRelevancy> map = map(findProductItemsByKeywords, scorer);
		
		// TODO: back to j1.8		
		// Comparator<ResourceProductItemWithRelevancy> c1 = (ResourceProductItemWithRelevancy p1, ResourceProductItemWithRelevancy p2) -> p1.getPriceInEuroCent().compareTo(p2.getPriceInEuroCent());
		// Comparator<ResourceProductItemWithRelevancy> c2 = (ResourceProductItemWithRelevancy p1, ResourceProductItemWithRelevancy p2) -> p2.getRelevancy().compareTo(p1.getRelevancy());
		
		Comparator<ResourceProductItemWithRelevancy> c1 = new Comparator<ResourceProductItemWithRelevancy>() {

			@Override
			public int compare(ResourceProductItemWithRelevancy o1, ResourceProductItemWithRelevancy o2) {
				return o1.getPriceInEuroCent().compareTo(o2.getPriceInEuroCent());
			}
		};
		
		Comparator<ResourceProductItemWithRelevancy> c2 = new Comparator<ResourceProductItemWithRelevancy>() {

			@Override
			public int compare(ResourceProductItemWithRelevancy o1, ResourceProductItemWithRelevancy o2) {
				return o2.getRelevancy().compareTo(o1.getRelevancy());
			}
		};		
		
		Collections.sort(map, ComparatorUtils.chainedComparator(c2, c1));
		
		Search search = Utils.firstIfExists( searchRepository.findBySearch(searchTerms) );
		if(search == null) {
			search = new Search(searchTerms);
		}
		search.setLastTime(new Timestamp(System.currentTimeMillis()));
		search.incTimes();
		search.setResultCount(map.size());
		searchRepository.save(search);
		
		return map;
	}
	
	private List<ResourceProductItemWithRelevancy> map(List<? extends IProductItem> findProductItemsByKeyword2, final RelevancyScorer scorer) {
		
		// TODO: back to j1.8
//		List<ResourceProductItemWithRelevancy> collect = findProductItemsByKeyword2
//			.stream()
//			.map(p -> new ResourceProductItemWithRelevancy(p, scorer))
//			.collect(Collectors.toList());
		
		List<ResourceProductItemWithRelevancy> collect = CollectionUtils.transform( findProductItemsByKeyword2, new Transformer() {
			
			@Override
			public Object transform(Object arg0) {
				return new ResourceProductItemWithRelevancy((IProductItem) arg0, scorer);
			}
		} );
		
		return collect;
	}
	
	
}
