package com.danidemi.europrice.web.controller;

import java.io.Serializable;
import java.util.Collection;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.http.client.utils.CloneUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import com.google.inject.ImplementedBy;

public class MyJdbcUserDetailsManager extends JdbcUserDetailsManager {

	private PasswordEncoder passwordEncoder;
	
	@Override
	public void createUser(UserDetails user) {
		super.createUser( new PasswordEncodedUser( user ) );
	}
	
	@Override
	public void updateUser(UserDetails user) {
		super.createUser( new PasswordEncodedUser( user ) );
	}
	
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	private class PasswordEncodedUser implements UserDetails {
		
		private static final long serialVersionUID = -8103487155453483943L;
		private UserDetails delegate;
				
		private PasswordEncodedUser(UserDetails originalUser){
			this.delegate = originalUser;
		}

		public Collection<? extends GrantedAuthority> getAuthorities() {
			return delegate.getAuthorities();
		}

		public String getPassword() {
			String originalPassword = delegate.getPassword();
			String encodedPassword = MyJdbcUserDetailsManager.this.passwordEncoder.encode(originalPassword);
			return encodedPassword;
		}

		public String getUsername() {
			return delegate.getUsername();
		}

		public boolean isAccountNonExpired() {
			return delegate.isAccountNonExpired();
		}

		public boolean isAccountNonLocked() {
			return delegate.isAccountNonLocked();
		}

		public boolean isCredentialsNonExpired() {
			return delegate.isCredentialsNonExpired();
		}

		public boolean isEnabled() {
			return delegate.isEnabled();
		}

		
	}
	
}
