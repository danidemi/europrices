package com.danidemi.europrice.web.controller;

import java.security.Principal;

import javax.servlet.ServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    @RequestMapping(value="/index", method=RequestMethod.GET)
    public String root(Model model, Principal principal, Authentication authentication) {
    	System.out.println(principal);
    	System.out.println(authentication);
    	SecurityContext context = SecurityContextHolder.getContext();
		Authentication otherAuthentication = context.getAuthentication();
    	System.out.println(otherAuthentication);
    	Object thePrincipal = otherAuthentication.getPrincipal();
    	System.out.println(thePrincipal);
    	System.out.println(thePrincipal.getClass());
        return "index";
    }
	
}
