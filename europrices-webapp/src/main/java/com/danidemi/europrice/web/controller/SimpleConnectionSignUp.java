/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.danidemi.europrice.web.controller;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;


public final class SimpleConnectionSignUp implements ConnectionSignUp {
	
	private UserDetailsManager manager;
	
	public SimpleConnectionSignUp(UserDetailsManager manager) {
		super();
		this.manager = manager;
	}

	public String execute(Connection<?> connection) {
		
		SimpleUserDetails ud = new SimpleUserDetails();
		ud.setEnabled(true);
		ud.setExpired(false);
		ud.setLocked(false);
		StringBuffer pwd = new StringBuffer();
		for(int i=0; i<10; i++){
			pwd.append( (char)RandomUtils.nextInt(65, 90) );
		}
		;
		ud.setPassword(pwd.toString());
		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
		ud.addAuthority(authority);
		String userID = connection.getKey().toString();
		ud.setUsername(userID);
		manager.createUser(ud);
		return userID;
		
	}
}