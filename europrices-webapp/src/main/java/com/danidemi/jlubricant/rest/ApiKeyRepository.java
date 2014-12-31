package com.danidemi.jlubricant.rest;

/**
 * An object that is able to manage a set of {@link ApiKey}.
 */
public interface ApiKeyRepository {

	boolean hasKey(ApiKey apiKey);

}
