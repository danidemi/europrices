package com.danidemi.jlubricant.rest;

import java.util.ArrayList;
import java.util.List;

public class ListApiKeyRepository implements ApiKeyRepository {
	
	private List<String> keys;
	
	public ListApiKeyRepository(List<String> keys) {
		this.keys = new ArrayList<String>(keys);
	}

	@Override
	public boolean hasKey(ApiKey apiKey) {
		return keys.contains(apiKey.asString());
	}

}
