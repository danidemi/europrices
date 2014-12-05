package com.danidemi.jlubricant.org.springframework.social.security;

import static java.lang.String.format;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;

import com.danidemi.jlubricant.org.springframework.security.provisioning.JdbcUserDetailsManager;

/**
 * This is a default implementation of {@link org.springframework.social.security.SocialUserDetailsService}
 * that uses the default SQL table specified in the Spring Social documentation.
 * In this very peculiar scenario, the user id is equal to the username, so, it is enough to delegate.
 */
public class SocialUserDetailsService extends JdbcUserDetailsManager implements org.springframework.social.security.SocialUserDetailsService {

	public SocialUserDetailsService() {
		super();
	}
	
	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
		
//		SELECT u.USERNAME,
//	       u.PASSWORD,
//	       u.ENABLED
//				 c.USERID,
//	       c.PROVIDERID,
//	       c.PROVIDERUSERID,
//	       c.RANK,
//	       c.DISPLAYNAME,
//	       c.PROFILEURL,
//	       c.IMAGEURL,
//	       c.ACCESSTOKEN,
//	       c.SECRET,
//	       c.REFRESHTOKEN,
//	       c.EXPIRETIME
//	FROM USERCONNECTION c JOIN USERS u ON c.USERID = u.USERNAME
//	;
		UserDetails securityUserDetails = super.loadUserByUsername(userId);
		if(securityUserDetails==null) throw new UsernameNotFoundException( format("user with id '%s' not found.", userId ));
		return new SocialUser(
				securityUserDetails.getUsername(), 
				securityUserDetails.getPassword(), 
				securityUserDetails.getAuthorities());
	}

		
		
	
}
