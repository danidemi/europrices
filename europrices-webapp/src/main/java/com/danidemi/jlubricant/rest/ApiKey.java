package com.danidemi.jlubricant.rest;

import static java.lang.String.format;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;



public class ApiKey {

	private String key;

	public ApiKey(String string) {
		if( StringUtils.isBlank(string) ) throw new IllegalArgumentException( format("Illegal api key value '%s'.", string) );
		this.key = string;
	}
	
	@Override
	public boolean equals(Object obj) {
			
		if (obj == null) { return false; }
		if (obj == this) { return true; }
		if (obj.getClass() != getClass()) {return false;}
		ApiKey rhs = (ApiKey) obj;
		return new EqualsBuilder()
			.append(key, rhs.key)
			.isEquals();
		
	}

	public String asString() {
		return key;
	}	

}
