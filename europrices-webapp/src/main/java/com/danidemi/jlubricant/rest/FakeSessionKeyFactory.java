package com.danidemi.jlubricant.rest;

import static java.lang.String.format;

import org.apache.commons.codec.binary.Base64;

import com.danidemi.jlubricant.org.springframework.security.crypto.encrypt.TinyAES;
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
		
		String apiKey = apiKeyObj.asString();
		long utcExpiration = sessKeyMaxAge == NEVER_EXPIRE ? Long.MAX_VALUE : System.currentTimeMillis() + sessKeyMaxAge;
		
		int hashedApiKey = XXHash.digestSmall(apiKey.getBytes(), apiKey.hashCode(), true);
		String sessionDescriptor = format("%s#%s", hashedApiKey, utcExpiration);
		int sessionCrc = XXHash.digestSmall(sessionDescriptor.getBytes(), apiKey.hashCode(), true);
		String sessionKey = format("%s#%s", sessionDescriptor, sessionCrc);
		
		return new SessionKey( tate.encrypt( sessionKey ) );
	}
	
	/** 
	 * Verify whether the given session key is valid for the given api key.
	 * @return true if ok.
	 */
	public boolean verify(ApiKey apiKeyObj, SessionKey sessionKeyObj) {
		
		//return apiKey.equals(new ApiKey("api-key")) && sessionKey.equals( new SessionKey("kkk") );
		
		//return apiKey.asString().equals(sessionKey.asString());
		
//		String key = apiKey.asString();
//		int digestSmall = XXHash.digestSmall(key.getBytes(), key.hashCode(), true);
//		return sessionKey.asString().equals( String.valueOf(digestSmall) );
		
		try{
			
			String apiKey = apiKeyObj.asString();
			String sessionKey = tate.decrypt(sessionKeyObj.asString());
			
			String[] split = sessionKey.split("#");
			String sessionDescriptor = split[0] + "#" + split[1];
			long utcExpiration = Long.parseLong( split[1] );
			
			int actualSessionCrc = Integer.parseInt( split[2] );
			int expectedSessionCrc = XXHash.digestSmall(sessionDescriptor.getBytes(), apiKey.hashCode(), true);
			
			if(actualSessionCrc != expectedSessionCrc) throw new IllegalArgumentException("Hash mismatch");
			if(System.currentTimeMillis() > utcExpiration) throw new IllegalArgumentException("Expired");
			
			int expectedHashedApiKey = XXHash.digestSmall(apiKey.getBytes(), apiKey.hashCode(), true);
			int actualHashedApiKey = Integer.parseInt(split[0]);
			if( expectedHashedApiKey != actualHashedApiKey) throw new IllegalArgumentException("Mismatch");
			
			return true;
			
		}catch(Exception e){
			
			return false;
			
		}
		
		
	}

}
