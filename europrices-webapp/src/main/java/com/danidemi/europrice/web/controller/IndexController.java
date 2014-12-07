package com.danidemi.europrice.web.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
	
	@Autowired private UsersConnectionRepository socialUsersRepo;

    @RequestMapping(value={"/index"}, method=RequestMethod.GET)
    public String root(Model model, Principal principalFromUnannotatedArgument, Authentication authenticationFromArgument, @AuthenticationPrincipal Principal principalFromAnnotatedArgument) {

    	SecurityContext context = SecurityContextHolder.getContext();
    	Authentication authenticationFromContext = null;
    	Object principalFromContext = null;
    	if(context !=null){
    		authenticationFromContext = context.getAuthentication();
    		if(authenticationFromContext!=null){
    			principalFromContext = authenticationFromContext.getPrincipal();    			
    		}
    	}
    	
    	if(authenticationFromArgument!=null && authenticationFromArgument.isAuthenticated()){
    		
    		List<Connection<?>> connections = new ArrayList<Connection<?>>( );
    		ConnectionRepository socialConnectionRepo = socialUsersRepo.createConnectionRepository( authenticationFromArgument.getName() );
    		String displayName = null;
    		if(socialConnectionRepo!=null){
    			
    			
    			Connection<?> socialConnection = null;
    			
    			MultiValueMap<String, Connection<?>> findAllConnections = socialConnectionRepo.findAllConnections();
    			List<List<Connection<?>>> values = new ArrayList<List<Connection<?>>>( findAllConnections.values() );
    			for (List<Connection<?>> list : values) {
					for (Connection<?> connection : list) {
						connections.add( connection );
					}
				}
    			
    			if(!connections.isEmpty()){
    				socialConnection = connections.iterator().next();
    				model.addAttribute("imageUrl", socialConnection.getImageUrl());    
    				displayName = socialConnection.getDisplayName();
    			}
    			
    		}
    		
    		displayName = StringUtils.isEmpty(displayName) ? authenticationFromArgument.getName() : displayName;
    		
    		model.addAttribute("socialDisplayName", displayName);
    	}
    	

    	
        return "index";
    }
	
}
