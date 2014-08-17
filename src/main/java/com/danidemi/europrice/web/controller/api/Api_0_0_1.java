package com.danidemi.europrice.web.controller.api;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.collections.ComparatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.danidemi.europrice.db.ProductItem;
import com.danidemi.europrice.db.ProductItemRepository;
import com.danidemi.europrice.db.ShopRepository;

@Controller
@RequestMapping(value="/api/", produces="application/json")
public class Api_0_0_1 {
	
	@Autowired ShopRepository shopRepo;
	@Autowired ProductItemRepository productItemRep;
	
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
		
		Comparator<ResourceProductItemWithRelevancy> c1 = (ResourceProductItemWithRelevancy p1, ResourceProductItemWithRelevancy p2) -> p1.getPriceInEuroCent().compareTo(p2.getPriceInEuroCent());
		Comparator<ResourceProductItemWithRelevancy> c2 = (ResourceProductItemWithRelevancy p1, ResourceProductItemWithRelevancy p2) -> p2.getRelevancy().compareTo(p1.getRelevancy());
		Collections.sort(map, ComparatorUtils.chainedComparator(c2, c1));
		
		return map;
	}
	
	private List<ResourceProductItemWithRelevancy> map(List<ProductItem> findProductItemsByKeyword2, RelevancyScorer scorer) {
		
		List<ResourceProductItemWithRelevancy> collect = findProductItemsByKeyword2
			.stream()
			.map(p -> new ResourceProductItemWithRelevancy(p, scorer))
			.collect(Collectors.toList());
		
		return collect;
	}
	
	
}
