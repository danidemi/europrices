package com.danidemi.europrice.web.controller.api;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.danidemi.europrice.db.ProductItem;
import com.danidemi.europrice.db.ProductItemRepository;
import com.danidemi.europrice.db.ShopRepository;

@Controller
@RequestMapping(value="/api/0.0.1", produces="application/json")
public class Api_0_0_1 {
	
	@Autowired ShopRepository shopRepo;
	@Autowired ProductItemRepository productItemRep;
	
	@Transactional
	@RequestMapping(value="/keyword/{keyword}", method=RequestMethod.GET)
	public List<ProductItem> productByKeyword(@PathVariable String keyword){	
		List<ProductItem> findProductItemsByKeyword = productItemRep.findProductItemsByKeyword(keyword);
		return findProductItemsByKeyword;
	}
	
	
}
