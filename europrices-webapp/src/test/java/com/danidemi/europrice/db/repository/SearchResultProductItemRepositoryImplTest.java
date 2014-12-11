package com.danidemi.europrice.db.repository;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/ctx.xml")
@ActiveProfiles(profiles = "test")
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class SearchResultProductItemRepositoryImplTest {

	@Autowired SearchResultProductItemRepositoryImpl tested;
	
	@Test
	public void shouldReturnEmptyListWhenNoResults() {
		
		assertThat( tested.findProductItemsByKeywords( Arrays.asList("terms"), null).isEmpty(), is(true) );
		
	}
	
}
