package com.danidemi.jlubricant.rest;

public interface SessionKeyFactory {

	SessionKey newSessionKey(ApiKey apiKey) throws AppKeyNotFoundException;

	/** 
	 * Verify whether the given session key is valid for the given api key.
	 * @return true if ok, false otherwise.
	 */
	boolean verify(ApiKey apiKey, SessionKey sessionKey);

	/** 
	 * Verify whether the given session key is valid for the given api key.
	 * Terminates without errors if ok.
	 * @throw {@link KeysMismatchException} if not ok.
	 */	
	void check(ApiKey apiKeyObj, SessionKey sessionKeyObj) throws KeysMismatchException;

}
