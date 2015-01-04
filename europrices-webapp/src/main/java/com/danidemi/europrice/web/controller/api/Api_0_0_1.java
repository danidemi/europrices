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
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.danidemi.europrice.EuroPricesWebApp;
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
import com.danidemi.jlubricant.rest.ApiKey;
import com.danidemi.jlubricant.rest.AppKeyNotFoundException;
import com.danidemi.jlubricant.rest.SessionKey;
import com.danidemi.jlubricant.rest.SessionKeyFactory;
import com.google.common.collect.ImmutableMap;

@Controller
@RequestMapping(value="/api/", produces="application/json")
public class Api_0_0_1 {
	
	@Autowired ShopRepository shopRepo;
	@Autowired ProductItemRepository productItemRep;
	@Autowired SearchRepository searchRepository;
	@Autowired FavouriteRepository favouriteRepository;
	@Autowired SearchResultProductItemRepositoryImpl searchResultProductItemRepository;
	@Autowired SocialUserDetailsService userDetailsRepository;
	@Autowired SessionKeyFactory apiSessionKeyFactory;
	
	private SearchTermSplitter splitter = new SearchTermSplitter();
	
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason="ApiKey not found.")
	public static class UnauthorizeApiException extends Exception {

		private static final long serialVersionUID = -5509256896948759883L;

		@Override
		public synchronized Throwable fillInStackTrace() {
			return null;
		}
	}
	
	@RequestMapping(value="/getSessionKey", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Object getSessionKey(@RequestParam(value=MyMappedInterceptor.EUROPRICES_API_KEY, required=false) String apiKeyAsString) throws UnauthorizeApiException {
		
		try {
						
			ApiKey apiKey = new ApiKey(apiKeyAsString);
			SessionKey newSessionKey = apiSessionKeyFactory.newSessionKey( apiKey );
			return new ImmutableMap.Builder<String, Object>()
					.put(MyMappedInterceptor.EUROPRICES_API_KEY, apiKey.asString())
					.put(MyMappedInterceptor.EUROPRICES_SESSION_KEY, newSessionKey.asString())
					.build();
			
		} catch (Exception e) {
			
			throw new UnauthorizeApiException();
			
		}
		
	}
		
	@RequestMapping(value="/version", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public String version() {
		return "v0.0.001";
	}
	
	@Transactional
	@RequestMapping(value="/toggleFavourite", method=RequestMethod.POST)
	@ResponseBody
	public boolean toggleFavourite( @RequestParam("favouriteId") String favouriteId, @RequestParam("userId") String userId ) {
		
				
		boolean newStatus;
		List<Favourite> findByFavouriteIdAndUserId = favouriteRepository.findByFavouriteIdAndUserId(favouriteId, userId);
		if( org.apache.commons.collections4.CollectionUtils.isEmpty( findByFavouriteIdAndUserId )){
			
			try{
				userDetailsRepository.loadUserByUserId(userId);				
			}catch(UsernameNotFoundException unfe){
				return false;
			}
			
			
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
	public List<ResourceProductItemWithRelevancy> search(@RequestParam("searchTerms")String searchTerms, @RequestParam(required=false, value="userId") String userId) {
				
		List<String> terms = splitter.split(searchTerms);
		RelevancyScorer scorer = new RelevancyScorer(terms);
		
		List<? extends IProductItem> findProductItemsByKeywords;
		if(userId!=null){
			findProductItemsByKeywords = searchResultProductItemRepository.findProductItemsByKeywords(terms, userId);
		}else{
			findProductItemsByKeywords = productItemRep.findProductItemsByKeywords(terms);
		}
		
		
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
		
		// save search 
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
