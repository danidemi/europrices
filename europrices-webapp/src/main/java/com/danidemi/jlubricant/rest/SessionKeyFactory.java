package com.danidemi.jlubricant.rest;

public interface SessionKeyFactory {

	SessionKey newSessionKey(ApiKey apiKey) throws AppKeyNotFoundException;

	boolean verify(ApiKey apiKey, SessionKey sessionKey);

}
