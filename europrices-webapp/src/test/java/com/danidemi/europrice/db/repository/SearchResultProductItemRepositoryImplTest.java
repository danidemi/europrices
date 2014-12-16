package com.danidemi.europrice.db.repository;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.security.SocialUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.danidemi.europrice.db.model.ProductItem;
import com.danidemi.europrice.db.model.SearchResultProductItem;
import com.danidemi.europrice.db.model.Shop;
import com.danidemi.europrice.utils.Utils.Language;
import com.danidemi.jlubricant.org.springframework.security.core.StringBasedGrantedAuthority;
import com.danidemi.jlubricant.org.springframework.social.security.SocialUserDetailsService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/ctx.xml")
@ActiveProfiles(profiles = "test")
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class SearchResultProductItemRepositoryImplTest {

	@Autowired SearchResultProductItemRepositoryImpl tested;
	@Autowired ProductItemRepository itemRepository;
	@Autowired ShopRepository shopRepository;
	@Autowired SocialUserDetailsService sop;
	
	@Test
	@Transactional
	public void shouldFindItemIfUserIsAnonymous() {
		
		// given
		Shop shop = new Shop();
		shop.setName("The Shop");
		
		ProductItem newProductItem = shop.newProductItem();
		newProductItem.setDetailsURL("http://detail.phone=12");
		newProductItem.setKeywordsBundle("mitsumi okko");
		newProductItem.setLanguage(Language.es);
		newProductItem.setPriceInCent(134_00L);
		
		itemRepository.save(newProductItem);
		
		// when
		List<SearchResultProductItem> mitsumiPhones = tested.findProductItemsByKeyword("mitsumi", null);
		
		// then
		assertThat(mitsumiPhones.size(), equalTo(1));
		
	}
	
	@Test
	public void shouldFindItemIfUserIsNotAnonymous() {
		
		// given
		Shop shop = new Shop();
		shop.setName("The Shop");
		
		ProductItem newProductItem = shop.newProductItem();
		newProductItem.setDetailsURL("http://detail.phone=12");
		newProductItem.setKeywordsBundle("mitsumi okko");
		newProductItem.setLanguage(Language.es);
		newProductItem.setPriceInCent(134_00L);
		
		sop.createUser(new SocialUser("a", "a", StringBasedGrantedAuthority.toAuthorities("ROLE")));
		
		itemRepository.save(newProductItem);
		
		// when
		List<SearchResultProductItem> mitsumiPhonesForUser = tested.findProductItemsByKeyword("mitsumi", "a");
		
		// then
		assertThat(mitsumiPhonesForUser.size(), equalTo(1));
		
	}
	
	@Test
	public void shouldReturnEmptyListWhenNoResults() {
		
		// when
		List<String> terms = Arrays.asList("match", "nothing");
		String userId = null;
		
		// then
		assertThat( tested.findProductItemsByKeywords( terms, userId).isEmpty(), is(true) );
		
	}
	
}
