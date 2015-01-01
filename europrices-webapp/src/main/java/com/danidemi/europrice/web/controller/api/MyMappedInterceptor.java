package com.danidemi.europrice.web.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.danidemi.jlubricant.rest.ApiKey;
import com.danidemi.jlubricant.rest.SessionKey;
import com.danidemi.jlubricant.rest.SessionKeyFactory;

public class MyMappedInterceptor implements HandlerInterceptor {
	
	public static final String EUROPRICES_SESSION_KEY = "Europrices-Session-Key";
	public static final String EUROPRICES_API_KEY = "Europrices-Api-Key";
	private SessionKeyFactory skf;
	
	public void setSessionKeyFactory(SessionKeyFactory skf){
		this.skf = skf;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String api = request.getHeader(EUROPRICES_API_KEY);
		String session = request.getHeader(EUROPRICES_SESSION_KEY);
		if(skf.verify( new ApiKey(api) , new SessionKey(session))){
			return true;
		}else{
			response.sendError(HttpStatus.SC_UNAUTHORIZED, "Header unauthorized");
			throw new IllegalArgumentException("Invalid ");
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// nothing to do
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// nothing to do
	}

}
