package com.danidemi.jlubricant.rest;

import static java.lang.String.format;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class SessionKey {

	private String key;

	public SessionKey(String string) {
		this.key = string;
	}
	
	@Override
	public boolean equals(Object obj) {
			
		if (obj == null) { return false; }
		if (obj == this) { return true; }
		if (obj.getClass() != getClass()) {return false;}
		SessionKey rhs = (SessionKey) obj;
		return new EqualsBuilder()
			.append(key, rhs.key)
			.isEquals();
		
	}

	public String asString() {
		return key;
	}
	
	@Override
	public String toString() {
		return format("session key:%s", key);
	}

}
