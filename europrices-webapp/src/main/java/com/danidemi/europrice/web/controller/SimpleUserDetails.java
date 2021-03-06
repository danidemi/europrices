package com.danidemi.europrice.web.controller;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.SerializationUtils;
import org.eclipse.jetty.util.security.Password;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SimpleUserDetails implements UserDetails, Cloneable, Serializable {
	
	private static final long serialVersionUID = 8032672037374790746L;
	
	private boolean enabled = false;
	private boolean locked = false;
	private boolean expired = false;
	private String username = null;
	private String password = null;
	private String passwordConfirmation = null;
	private Set<GrantedAuthority> authorities = new HashSet<>();
	
	private static class SimpleGrantedAuthority implements GrantedAuthority {

		private static final long serialVersionUID = 3106943139127602609L;
		private String grantedAuthority;
		
		public SimpleGrantedAuthority(String grantedAuthority) {
			super();
			this.grantedAuthority = grantedAuthority;
		}

		@Override
		public String getAuthority() {
			return grantedAuthority;
		}
		
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.unmodifiableCollection( authorities );
	}
	
	

	@Override
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !expired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}
	
	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void addAuthority(GrantedAuthority authority) {
		if(authority==null) return;
		this.authorities.add(authority);
	}
	
	public static GrantedAuthority createSimpleGrantedAuthority(String authority){
		return new SimpleGrantedAuthority(authority);
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return SerializationUtils.clone(this);
	}
	
	public SimpleUserDetails applyPasswordEncoding(SaltSource saltSource, PasswordEncoder passwordEncoder){
		SimpleUserDetails result;
		try {
			result = (SimpleUserDetails) this.clone();
		} catch (CloneNotSupportedException e) {
			// should really not happen
			throw new RuntimeException(e);
		}
		result.setPassword( passwordEncoder.encode(this.getPassword()) );
		return this;
	}

}
