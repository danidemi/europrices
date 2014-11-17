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

import com.danidemi.europrice.db.ProductItem;
import com.danidemi.europrice.db.ProductItemRepository;
import com.danidemi.europrice.db.Search;
import com.danidemi.europrice.db.SearchRepository;
import com.danidemi.europrice.db.ShopRepository;
import com.danidemi.europrice.utils.Utils;

@Controller
@RequestMapping(value="/api/", produces="application/json")
public class Api_0_0_1 {
	
	@Autowired ShopRepository shopRepo;
	@Autowired ProductItemRepository productItemRep;
	@Autowired SearchRepository searchRepository;
	
	private SearchTermSplitter splitter = new SearchTermSplitter();
		
	@RequestMapping(value="/version", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public String version() {
		return "v0.0.001";
	}
	
	@Transactional
	@RequestMapping(value="/search", method=RequestMethod.GET)
	@ResponseBody
	public List<ResourceProductItemWithRelevancy> search(@RequestParam("searchTerms")String searchTerms) {
				
		List<String> terms = splitter.split(searchTerms);
		
		RelevancyScorer scorer = new RelevancyScorer(terms);
		
		List<ProductItem> findProductItemsByKeywords = productItemRep.findProductItemsByKeywords(terms);
		
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
	
	private List<ResourceProductItemWithRelevancy> map(List<ProductItem> findProductItemsByKeyword2, final RelevancyScorer scorer) {
		
		// TODO: back to j1.8
//		List<ResourceProductItemWithRelevancy> collect = findProductItemsByKeyword2
//			.stream()
//			.map(p -> new ResourceProductItemWithRelevancy(p, scorer))
//			.collect(Collectors.toList());
		
		List<ResourceProductItemWithRelevancy> collect = CollectionUtils.transform( findProductItemsByKeyword2, new Transformer() {
			
			@Override
			public Object transform(Object arg0) {
				return new ResourceProductItemWithRelevancy((ProductItem) arg0, scorer);
			}
		} );
		
		return collect;
	}
	
	
}
