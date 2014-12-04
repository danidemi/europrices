package com.danidemi.jlubricant.org.springframework.social.security;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;

import com.danidemi.jlubricant.org.springframework.security.provisioning.JdbcUserDetailsManager;

public class SocialUserDetailsService implements org.springframework.social.security.SocialUserDetailsService {

	public SocialUserDetailsService() {
		super();
	}
	
	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {

		//SELECT USERID,PROVIDERID,PROVIDERUSERID,RANK,DISPLAYNAME,PROFILEURL,IMAGEURL,ACCESSTOKEN,SECRET,REFRESHTOKEN,EXPIRETIME FROM "PUBLIC"."USERCONNECTION" where USERID='qwe'

		SocialUser socialUser = new SocialUser(null, null, null);
		return null;
	}
	
}
