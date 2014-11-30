package com.danidemi.europrice.web.controller;

import java.security.Principal;

import javax.servlet.ServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

    @RequestMapping(value="/index", method=RequestMethod.GET)
    public String root(Model model, Principal principalFromUnannotatedArgument, Authentication authenticationFromArgument, @AuthenticationPrincipal Principal principalFromAnnotatedArgument) {
    	SecurityContext context = SecurityContextHolder.getContext();
    	Authentication authenticationFromContext = context.getAuthentication();
    	Object principalFromContext = authenticationFromContext.getPrincipal();
    	System.out.println("principalFromUnannotatedArgument:" + principalFromUnannotatedArgument);
    	System.out.println("principalFromAnnotatedArgument:" + principalFromAnnotatedArgument);    	
    	System.out.println("authenticationFromArgument:" + authenticationFromArgument);
    	System.out.println("authenticationFromContext:" + authenticationFromContext);
    	System.out.println("principalFromContext:" + principalFromContext);
    	System.out.println("principalFromContext.getClass():" + principalFromContext.getClass());
        return "index";
    }
	
}
