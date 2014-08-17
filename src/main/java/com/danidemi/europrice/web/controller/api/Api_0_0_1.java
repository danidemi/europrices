package com.danidemi.europrice.web.controller.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

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
	public List<ResourceProductItem> search(@RequestParam("searchTerms")String searchTerms) {
		List<String> terms = splitter.split(searchTerms);;
		List<ProductItem> findProductItemsByKeywords = productItemRep.findProductItemsByKeywords(terms);
		return map(findProductItemsByKeywords);
	}
	
	@Transactional
	@RequestMapping(value="/keyword/{keyword}", method=RequestMethod.GET)
	@ResponseBody
	public List<ResourceProductItem> productByKeyword(@PathVariable(value="keyword") String keyword){
		
		List<ProductItem> findProductItemsByKeyword2 = productItemRep.findProductItemsByKeyword(keyword);
		List<ResourceProductItem> collect = map(findProductItemsByKeyword2);
		
		return collect;
	}

	private List<ResourceProductItem> map(List<ProductItem> findProductItemsByKeyword2) {
		List<ResourceProductItem> collect = findProductItemsByKeyword2
			.stream()
			.map(p -> new ResourceProductItem(p))
			.collect(Collectors.toList());
		return collect;
	}
	
	
}
