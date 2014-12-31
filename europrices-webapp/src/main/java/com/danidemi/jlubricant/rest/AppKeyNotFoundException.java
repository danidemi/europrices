package com.danidemi.jlubricant.rest;

import static java.lang.String.format;

public class AppKeyNotFoundException extends Exception {

	private static final long serialVersionUID = 8591391562546465281L;

	public AppKeyNotFoundException(ApiKey apiKey) {
		super(format("app key %s not found", apiKey));
	}

}
