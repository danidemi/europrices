package com.danidemi.europrice.db.repository;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.danidemi.europrice.db.model.ProductItem;
import com.danidemi.europrice.db.model.Shop;
import com.danidemi.europrice.utils.Utils.Language;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/ctx.xml")
@ActiveProfiles(profiles = "test")
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class RepositoryTest {

	@Autowired
	ProductItemRepository repository;

	@Autowired
	ShopRepository shopRepository;
	
	@Test
	public void shouldCreateAShop() {

		// given
		Shop shop = new Shop();
		shop.setName("the shop");
		
		// when
		Long id = shopRepository.save(shop).getId();
		
		// then
		Shop loadedShop = shopRepository.findOne(id);
		assertThat( loadedShop, equalTo(shop) );
		
	}

	@Test
	public void shouldCreateAProductItem() {
		
		// given
		Shop shop = new Shop();
		shop.setName("the shop");
		
		// when
		ProductItem pi = shop.newProductItem();
		pi.setPriceInCent(1000L);
		pi.withKeywords("good", "excellent");
		pi.setDetailsURL("http://url");
		pi.setLanguage(Language.ca);
		Long id = repository.save(pi).getId();

		// then
		assertThat(pi, equalTo(repository.findOne(id)));
	}

	@Test
	public void shouldSearchForProductByKeyword() {

		// given
		Shop shop = new Shop();
		shop.setName("the shop");
		shopRepository.save(shop);

		ProductItem p1 = shop.newProductItem();
		p1.setDetailsURL("http://url1" + System.currentTimeMillis());
		p1.setPriceInCent(102L);
		p1.setLanguage(Language.el);
		p1.withKeywordsIn("gl 120 Luna");
		repository.save(p1);

		ProductItem p3 = shop.newProductItem();
		p3.setPriceInCent(102L);
		p3.setDetailsURL("http://url2" + System.currentTimeMillis());
		p3.setLanguage(Language.el);
		p3.withKeywordsIn("NewtWearable omni");
		repository.save(p3);

		ProductItem p2 = shop.newProductItem();
		p2.setPriceInCent(102L);
		p2.setDetailsURL("http://url3" + System.currentTimeMillis());
		p2.setLanguage(Language.el);
		p2.withKeywordsIn("ZAMZUNG super plus 334 120Gb ");
		repository.save(p2);

		// when
		List<ProductItem> items = repository.findProductItemsByKeyword("120");

		// then
		assertThat(items, containsInAnyOrder(p1, p2));

	}
}
