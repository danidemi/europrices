package com.danidemi.europrice.web.controller.api;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.danidemi.europrice.db.model.Shop;

@Controller
@RequestMapping("/pocapi")
public class PocApi {

	@RequestMapping(value="/version", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public String version() {
		return "v0.0.001";
	}
	
	@RequestMapping(value="/shops", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Shop> shops() {
		
		Shop shop1 = new Shop();
		shop1.setId(10L); shop1.setName("the shop");
		
		Shop shop2 = new Shop();
		shop2.setId(20L); shop2.setName("de zop");
		
		Shop shop3 = new Shop();
		shop3.setId(30L); shop3.setName("und zop");
		
		return Arrays.asList( shop1, shop2, shop3 );
		
	}	
	
}
