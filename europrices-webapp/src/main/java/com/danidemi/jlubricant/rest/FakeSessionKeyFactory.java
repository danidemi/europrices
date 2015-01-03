package com.danidemi.jlubricant.rest;

import static java.lang.String.format;

import java.text.ParseException;

import com.danidemi.jlubricant.org.springframework.security.crypto.encrypt.TinyAESTextEncryptor;
import com.danidemi.jlubricant.rest.at.horz.hash.XXHash;

public class FakeSessionKeyFactory implements SessionKeyFactory {

	private static final int NEVER_EXPIRE = -1;
	private ApiKeyRepository apiKeyRepository;
	private long sessKeyMaxAge;
	private TinyAESTextEncryptor tate;
	

	public FakeSessionKeyFactory(ApiKeyRepository apiKeyRepository) {
		this(apiKeyRepository, NEVER_EXPIRE);
	}

	public FakeSessionKeyFactory(ApiKeyRepository fullApiKeyRepo, long sessKeyMaxAge) {
		this.apiKeyRepository = fullApiKeyRepo;
		this.sessKeyMaxAge = sessKeyMaxAge;
		this.tate = new TinyAESTextEncryptor("wonrklsoprns3o+f");
	}

	@Override
	public SessionKey newSessionKey(ApiKey apiKeyObj) throws AppKeyNotFoundException {
		
		if(!apiKeyRepository.hasKey(apiKeyObj)){
			throw new AppKeyNotFoundException( apiKeyObj );
		}
		
		// let's start with "API-KEY"
		String apiKey = apiKeyObj.asString();
		
		// then there's an expiration timestamp "99887766"
		long utcExpiration = sessKeyMaxAge == NEVER_EXPIRE ? Long.MAX_VALUE : System.currentTimeMillis() + sessKeyMaxAge;

		// here we have the Api Key hashed "HHAPI-KEYHH"
		int hashedApiKey = XXHash.digestSmall(apiKey.getBytes(), apiKey.hashCode(), true);
		
		// let's combine the two: HHAPI-KEYHH#99887766
		String sessionDescriptor = format("%s#%s", hashedApiKey, utcExpiration);
		
		// let's calculate a CRC for the two and combine them: HHAPI-KEYHH#99887766#CRC 
		int sessionCrc = XXHash.digestSmall(sessionDescriptor.getBytes(), apiKey.hashCode(), true);
		String sessionKey = format("%s#%s", sessionDescriptor, sessionCrc);
		
		// let's return a encrypted version: encrypted(HHAPI-KEYHH#99887766#CRC)
		return new SessionKey( tate.encrypt( sessionKey ) );
	}
	
	@Override
	public void check(ApiKey apiKeyObj, SessionKey sessionKeyObj) throws KeysMismatchException {
		
		String apiKey = apiKeyObj.asString();
		
		// let's obtain decrypted(encrypted(HHAPI-KEYHH#99887766#CRC)) = HHAPI-KEYHH#99887766#CRC 
		String sessionKey = tate.decrypt(sessionKeyObj.asString());
		
		// let's split it with "#". We require three tokens or there are problems.
		String[] split = sessionKey.split("#");
		if(split.length != 3) throw new KeysMismatchException( format("Expected %d tokens, got %d.", 3, split.length) );

		// here we combine HHAPI-KEYHH#99887766
		String sessionDescriptor = split[0] + "#" + split[1];
		
		// and here the string representing the timestamp back to a number
		long utcExpiration;
		try{
			utcExpiration = Long.parseLong( split[1] );			
		}catch(NumberFormatException nfe){
			throw new KeysMismatchException( "Invalid timestamp" );
		}
		
		// here the crc from the string: "CRC"
		int actualSessionCrc = Integer.parseInt( split[2] );
		
		// we rebuild the CRC to discover if it is ok
		int expectedSessionCrc = XXHash.digestSmall(sessionDescriptor.getBytes(), apiKey.hashCode(), true);
		
		if(actualSessionCrc != expectedSessionCrc) throw new KeysMismatchException("CRC mismatch.");
		if(System.currentTimeMillis() > utcExpiration) throw new KeysMismatchException("Token Expired.");
		
		int expectedHashedApiKey = XXHash.digestSmall(apiKey.getBytes(), apiKey.hashCode(), true);
		int actualHashedApiKey = Integer.parseInt(split[0]);
		if( expectedHashedApiKey != actualHashedApiKey) throw new KeysMismatchException("Api Key not found");
	}
	
	@Override
	public boolean verify(ApiKey apiKeyObj, SessionKey sessionKeyObj) {
				
		try{
			
			check(apiKeyObj, sessionKeyObj);
			
			return true;
			
		}catch(Exception e){
			
			return false;
			
		}
		
		
	}

}
