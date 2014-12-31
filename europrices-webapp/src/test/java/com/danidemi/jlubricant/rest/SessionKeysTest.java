package com.danidemi.jlubricant.rest;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SessionKeysTest {
	
	private static final ApiKey WRONG_API_KEY = new ApiKey( "api-key-xxx" );
	private static final ApiKey API_KEY = new ApiKey("api-key");
	@Mock ApiKeyRepository fullApiKeyRepo;
	@Mock ApiKeyRepository emptyApiKeyRepo;
	
	@Before
	public void setUp() {
		when(fullApiKeyRepo.hasKey(any(ApiKey.class))).thenReturn(true);
		when(emptyApiKeyRepo.hasKey(any(ApiKey.class))).thenReturn(false);
	}
	
	@Test
	public void shouldDiscardOldKeys() throws AppKeyNotFoundException, InterruptedException{
		
		// given
		FakeSessionKeyFactory tested = new FakeSessionKeyFactory( fullApiKeyRepo, 0L );
		
		// when
		SessionKey immediatelyExpiredSessionKey = tested.newSessionKey(API_KEY);
		Thread.sleep(100L); // just to be sure the key will expire
		boolean outcome = tested.verify(API_KEY, immediatelyExpiredSessionKey);
		
		// then
		assertThat( outcome, is(false));
	}
	
	@Test
	public void shouldNotMixApiKeys() throws AppKeyNotFoundException {
		
		// given
		FakeSessionKeyFactory tested = new FakeSessionKeyFactory( fullApiKeyRepo );
		
		ApiKey apiKey1 = new ApiKey("api-key-1");
		ApiKey apiKey2 = new ApiKey("api-key-2");
		
		// when
		SessionKey sessionKey1 = tested.newSessionKey(apiKey1);
		SessionKey sessionKey2 = tested.newSessionKey(apiKey2);
		
		// then
		assertThat(tested.verify(apiKey1, sessionKey1), is(true));
		assertThat(tested.verify(apiKey2, sessionKey2), is(true));
		assertThat(tested.verify(apiKey1, sessionKey2), is(false));
		assertThat(tested.verify(apiKey2, sessionKey1), is(false));
		
	}
	
	@Test
	public void sessionKeyShouldNotContainApiKey() throws AppKeyNotFoundException {
		
		// given
		FakeSessionKeyFactory tested = new FakeSessionKeyFactory( fullApiKeyRepo );
		
		// when
		SessionKey sessionKey1 = tested.newSessionKey(new ApiKey("api-key-1"));
		
		// then
		assertThat( sessionKey1.asString(), not( containsString("api-key-1") ) );
		
	}
	
	@Test
	public void shouldAskTheKeyRepoIfTheAppKeyIsThereWhenCreatingASessionKey() {
		
		// given
		FakeSessionKeyFactory sessionKeyFactory = new FakeSessionKeyFactory( emptyApiKeyRepo );
		
		// when
		try{
			sessionKeyFactory.newSessionKey( API_KEY );
			fail();
		}catch(AppKeyNotFoundException aknfe){
			// ok;
		}
		
		// then
		verify(emptyApiKeyRepo).hasKey( API_KEY );
		
	}
	
	@Test
	public void shouldDenyAMismatchingApiKey() throws AppKeyNotFoundException {
		
		// given
		ApiKey apiKey = API_KEY;
		FakeSessionKeyFactory sessionKeyFactory = new FakeSessionKeyFactory( fullApiKeyRepo );
		SessionKey sessionKey = sessionKeyFactory.newSessionKey( apiKey );
		
		// when
		// ...the key is maliciously modified
		// ...and a verification is performed
		boolean outcome = sessionKeyFactory.verify( WRONG_API_KEY, sessionKey );
		
		// then
		assertThat( outcome , is(false));
		
	}

	@Test
	public void shouldDenyAMismatchedSessionKey() throws AppKeyNotFoundException {
		
		// given
		FakeSessionKeyFactory sessionKeyFactory = new FakeSessionKeyFactory( fullApiKeyRepo );
		SessionKey sessionKey = sessionKeyFactory.newSessionKey( API_KEY );
		
		// when
		// ...the key is maliciously modified
		SessionKey maliciousSessionKey = new SessionKey( sessionKey.toString() + "xxx" );
		
		// ...and a verification is performed
		boolean outcome = sessionKeyFactory.verify( API_KEY, maliciousSessionKey );
		
		// then
		assertThat( outcome , is(false));
		
	}
	
	@Test
	public void shouldConfirmAVerbatimSessionKey() throws AppKeyNotFoundException {
		
		// given
		FakeSessionKeyFactory sessionKeyFactory = new FakeSessionKeyFactory( fullApiKeyRepo );
		SessionKey sessionKey = sessionKeyFactory.newSessionKey( API_KEY );
		
		// when
		SessionKey verbatimKey = new SessionKey( sessionKey.asString() );
		
		// ...and a verification is performed
		boolean outcome = sessionKeyFactory.verify( API_KEY, verbatimKey );
		
		// then
		assertThat( outcome , is(true));
		
	}	
	
}
