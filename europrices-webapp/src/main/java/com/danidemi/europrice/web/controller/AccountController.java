package com.danidemi.europrice.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/account")
public class AccountController {
	
	private JdbcUserDetailsManager userDao;
	
	@RequestMapping(value="/signin", method=RequestMethod.GET)
	public String singIn(){
		return "account-signin";
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String signUp(){
		return "account-signup";
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String signUp(SimpleUserDetails userDetails, BindingResult result){
		
		if(result.hasErrors()){
			return "account-signup";
		}
		
		if(userDao.userExists(userDetails.getUsername())){
			return "account-signup";
		}
		
		userDetails.addAuthority( SimpleUserDetails.createSimpleGrantedAuthority("ROLE_USER") );
		userDetails.setEnabled(true);
		userDetails.setLocked(false);
		userDao.createUser( userDetails );			
		return "account-signin";
	}	
	
	@RequestMapping(value="/signout", method=RequestMethod.GET)
	public String signOut(){
		return "index";
	}	
	
	@Autowired
	public void setUserDao(JdbcUserDetailsManager userDao) {
		this.userDao = userDao;
	}
	
}
