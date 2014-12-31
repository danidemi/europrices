package com.danidemi.jlubricant.rest;

public class WhateverApiKeyRepository implements ApiKeyRepository {

	@Override
	public boolean hasKey(ApiKey apiKey) {
		return true;
	}

}
