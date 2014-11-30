package com.danidemi.europrice.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/account")
public class AccountController {

	@RequestMapping(value="/signin", method=RequestMethod.GET)
	public String singIn(){
		return "account-signin";
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String signUp(){
		return "account-signup";
	}
	
	@RequestMapping(value="/signout", method=RequestMethod.GET)
	public String signOut(){
		return "index";
	}	
	
}
