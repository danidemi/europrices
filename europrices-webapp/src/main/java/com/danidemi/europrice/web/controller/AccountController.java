package com.danidemi.europrice.web.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@RequestMapping(value="/socialsignin", method=RequestMethod.GET)
	public String socialSignIn(HttpServletRequest req){
		
		System.out.println("================================");
		Enumeration<String> attributeNames = req.getAttributeNames();
		while(attributeNames.hasMoreElements()){
			String name = attributeNames.nextElement();
			System.out.println(name + "=" + req.getAttribute(name));
		}
		
		System.out.println("================================");
		System.out.println( req.getParameterMap() );
		
		System.out.println("================================");
    	SecurityContext context = SecurityContextHolder.getContext();
    	Authentication authenticationFromContext = context.getAuthentication();
    	Object principalFromContext = authenticationFromContext.getPrincipal();
    	System.out.println("authenticationFromContext:" + authenticationFromContext);
    	System.out.println("principalFromContext:" + principalFromContext);
    	System.out.println("principalFromContext.getClass():" + principalFromContext.getClass());		
		
		return "index";
	}
	
	@Autowired
	public void setUserDao(JdbcUserDetailsManager userDao) {
		this.userDao = userDao;
	}
		
}
