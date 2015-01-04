package com.danidemi.europrice.web.controller.api;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.danidemi.europrice.db.model.Favourite;
import com.danidemi.europrice.db.model.ProductItem;
import com.danidemi.europrice.db.model.Shop;
import com.danidemi.europrice.db.repository.FavouriteRepository;
import com.danidemi.europrice.db.repository.ProductItemRepository;
import com.danidemi.europrice.db.repository.ShopRepository;
import com.danidemi.europrice.utils.Utils.Language;
import com.danidemi.jlubricant.org.springframework.social.security.SocialUserDetailsService;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/ctx.xml")
@ActiveProfiles("test")
public class Api_0_0_1Test {

    private static final String USER_MOBILE_HATER_ID = "mobile-hater";
	static final long MOBILE_MOTOR_ROLLA_ID = 120L;
	static final long MOBILE_MOTOR_BIKE_ID = 200L;
	static final String USER_MOTOR_FUN_ID = "motor-fun";
	@Autowired WebApplicationContext wac;
    @Autowired ShopRepository shopRepository;
    @Autowired ProductItemRepository itemRepository;
    @Autowired FavouriteRepository favouriteRepository;
    @Autowired SocialUserDetailsService userRepository;
    
    MockMvc mockMvc;
    
    @Before
    public void setup() {
    	if(mockMvc==null) this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
    
    @Test
    @Transactional
    public void shouldGetA401WhenAskingForSessionKeyWithInvalidApiKey() throws Exception {

    	// when
        ResultActions result = mockMvc.perform(
        		post("/api/getSessionKey")
        		.param("apiKey", "XXXXXXX")
        		.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
        	);
        
		// then
        result        	
        	.andDo(print())
            .andExpect(status().is(401));
    	
    }    
    
    @Test
    @Transactional
    public void shouldGetASessionKeyWhenApiKeyIsValid() throws Exception {

    	// when
        ResultActions result = mockMvc.perform(
        		post("/api/getSessionKey")
        		.param(MyMappedInterceptor.EUROPRICES_API_KEY, "weuhd923eu")
        		.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
        	);
        
		// then
        result        	
        	.andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.Europrices-Api-Key").value("weuhd923eu"))
            .andExpect(jsonPath("$.Europrices-Session-Key").value( notNullValue() ));
    	
    }

    @Test
    @Transactional
    public void shouldSearchForTerm() throws Exception {
    	
        String sessionKey = getSessionKey("weuhd923eu");
    	
    	// when
        ResultActions result = mockMvc.perform(
        		get("/api/search?searchTerms=compact")
        		.header(MyMappedInterceptor.EUROPRICES_API_KEY, "weuhd923eu")
        		.header(MyMappedInterceptor.EUROPRICES_SESSION_KEY, sessionKey)
        		.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
        	);
        	
        // then
        result        	
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            
            .andExpect(jsonPath("$.[0].name").value("zamzung submodel1 compact"))
            .andExpect(jsonPath("$.[0].priceInEuroCent").value(100_00))
            .andExpect(jsonPath("$.[0].favourite").value(false))
            
            .andExpect(jsonPath("$.[1].name").value("zamzung submodel3 compact"))
            .andExpect(jsonPath("$.[1].priceInEuroCent").value(110_00))
            .andExpect(jsonPath("$.[1].favourite").value(false))
            .andDo(print());
        
    }
    
    @Test
    @Transactional
    public void shouldSearchForTermByUser() throws Exception {
    	
        String sessionKey = getSessionKey("weuhd923eu");
    	
        ResultHandler rh = null;
		ResultActions result = this.mockMvc.perform(
        		get("/api/search?searchTerms=motor&user=" + USER_MOTOR_FUN_ID)
        		.header(MyMappedInterceptor.EUROPRICES_API_KEY, "weuhd923eu")
        		.header(MyMappedInterceptor.EUROPRICES_SESSION_KEY, sessionKey)        		
        		.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
        	).andDo(print());
        
        System.out.println( result.toString() );
        
        result
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").exists())
            .andExpect(jsonPath("$.[0].priceInEuroCent").value(77_00))
            .andExpect(jsonPath("$.[0].priceInEuroCent").value(77_00))
            .andExpect(jsonPath("$.[0].favourite").value(false));
        
    }    
    
    @Test
    @Transactional
    public void shouldToggleFavourite() throws Exception{
    	
    	String sessionKey = getSessionKey("weuhd923eu");
    	    	
    	this.mockMvc
    			.perform(
    					post("/api/toggleFavourite?favouriteId=" + MOBILE_MOTOR_ROLLA_ID + "&userId=" + USER_MOBILE_HATER_ID)
		        		.header(MyMappedInterceptor.EUROPRICES_API_KEY, "weuhd923eu")
		        		.header(MyMappedInterceptor.EUROPRICES_SESSION_KEY, sessionKey)     					
						.accept(MediaType.APPLICATION_JSON_VALUE)
				)
    			.andExpect( status().isOk() )
    			.andExpect(jsonPath("$").value(true));
    	
    	this.mockMvc
		.perform(
				post("/api/toggleFavourite?favouriteId=" + MOBILE_MOTOR_ROLLA_ID + "&userId=" + USER_MOBILE_HATER_ID)
        		.header(MyMappedInterceptor.EUROPRICES_API_KEY, "weuhd923eu")
        		.header(MyMappedInterceptor.EUROPRICES_SESSION_KEY, sessionKey)				
				.accept(MediaType.APPLICATION_JSON_VALUE)
		)
		.andExpect( status().isOk() )
		.andExpect(jsonPath("$").value(false));    	
    	
    }
    
    @Before
    @Transactional
	public void fixture() {
//    	select distinct item.*, (fav.FAVOURITEID IS NOT NULL) as IS_FAVOURITE from PRODUCTITEM item left join FAVOURITE fav on item.ID = fav.FAVOURITEID left join USERS u on fav.USERID = u.USERNAME
//    			where (u.USERNAME IS NULL) OR (u.USERNAME='facebook:10205411082539753')
//    			order by item.ID
    	//if(fixtureDone) return;
    	
    	//fixtureDone  = true;
    	
    	ProductItem motorBike = null;
    	ProductItem motorRolla = null;
		
		{
    	Shop shop = new Shop();
    	shop.setName("telefonini.it");
    	shopRepository.save(shop);
    	
    	ProductItem zamzungS1 = shop.newProductItem();
    	zamzungS1.withKeywordsIn("Zamzung SubModel1");
    	zamzungS1.setDetailsURL("http://url1");
    	zamzungS1.setPriceInCent(90_00L);
    	zamzungS1.setLanguage(Language.it);
    	zamzungS1.setId(10L);
    	
    	ProductItem zamzungS2 = shop.newProductItem();
    	zamzungS2.withKeywordsIn("Zamzung SubModel2");
    	zamzungS2.setDetailsURL("http://url2");
    	zamzungS2.setPriceInCent(91_00L);
    	zamzungS2.setLanguage(Language.it);
    	zamzungS2.setId(20L);
    	
    	ProductItem glPrevioux = shop.newProductItem();
    	glPrevioux.withKeywordsIn("GL Previoux");
    	glPrevioux.setDetailsURL("http://url3");
    	glPrevioux.setPriceInCent(92_00L);
    	glPrevioux.setLanguage(Language.it);
    	glPrevioux.setId(30L);
    	
    	itemRepository.save( Arrays.asList( zamzungS1, zamzungS2, glPrevioux ) );
    	}
    	
    	{
	    	Shop shop = new Shop();
	    	shop.setName("telefonini.fr");
	    	shopRepository.save(shop);
	    	
	    	ProductItem zamzungS1 = shop.newProductItem();
	    	zamzungS1.withKeywordsIn("Zamzung SubModel1 Compact");
	    	zamzungS1.setDetailsURL("http://url4");
	    	zamzungS1.setPriceInCent(100_00L);
	    	zamzungS1.setLanguage(Language.it);
	    	zamzungS1.setId(100L);
	    	
	    	ProductItem zamzungS3 = shop.newProductItem();
	    	zamzungS3.withKeywordsIn("Zamzung SubModel3 Compact");
	    	zamzungS3.setDetailsURL("http://url5");
	    	zamzungS3.setPriceInCent(110_00L);
	    	zamzungS3.setLanguage(Language.it);
	    	zamzungS3.setId(110L);
	    	
	    	motorRolla = shop.newProductItem();
	    	motorRolla.withKeywordsIn("Motor Rolla");
	    	motorRolla.setDetailsURL("http://url6");
	    	motorRolla.setPriceInCent(120_00L);
	    	motorRolla.setLanguage(Language.it);
	    	motorRolla.setId(MOBILE_MOTOR_ROLLA_ID);
			itemRepository.save(Arrays.asList(zamzungS1, zamzungS3,
					motorRolla));
    	}
    	
    	{
	    	Shop shop = new Shop();
	    	shop.setName("moviles.es");
	    	shopRepository.save(shop);
	    		    	
	    	motorBike = shop.newProductItem();
	    	motorBike.withKeywordsIn("Motor Bike");
	    	motorBike.setDetailsURL("http://url7");
	    	motorBike.setPriceInCent(77_00L);
	    	motorBike.setLanguage(Language.it);
	    	motorBike.setId(MOBILE_MOTOR_BIKE_ID);
	    	motorBike = itemRepository.save(motorBike);
    	}    
    	
    	{
    		userRepository.createUser( new SocialUser(USER_MOTOR_FUN_ID, USER_MOTOR_FUN_ID, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))) );
    		userRepository.createUser( new SocialUser(USER_MOBILE_HATER_ID, USER_MOBILE_HATER_ID, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))) );
    	}
    	
    	{
    		favouriteRepository.save( new Favourite(motorBike.getId(), USER_MOTOR_FUN_ID) );
    		favouriteRepository.save( new Favourite(motorRolla.getId(), USER_MOTOR_FUN_ID) );

    	}

	}
    
    public Map<String, Object> jsonToMap(String json) throws Exception{
    	JsonFactory factory = new JsonFactory();
    	ObjectMapper mapper = new ObjectMapper(factory);
    	TypeReference<HashMap<String,Object>> typeRef
    	              = new TypeReference<HashMap<String,Object>>() {};
    	HashMap<String,Object> o = mapper.readValue(json, typeRef);
    	return o;
    }
    
	private String getSessionKey(String apiKey) throws Exception,
			UnsupportedEncodingException {
		ResultActions _result = mockMvc.perform(post("/api/getSessionKey")
				.param(MyMappedInterceptor.EUROPRICES_API_KEY, apiKey)
				.accept(MediaType
						.parseMediaType("application/json;charset=UTF-8")));
		MvcResult andReturn = _result.andReturn();
		Map<String, Object> _appi = jsonToMap(andReturn.getResponse()
				.getContentAsString());
		String sessionKey = (String) _appi.get(MyMappedInterceptor.EUROPRICES_SESSION_KEY);
		return sessionKey;
	}    
	
}
