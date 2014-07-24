package com.danidemi.europrice.db;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


public class ProductItemRepositoryImpl implements ProductItemRepositoryCustom {
	
    private EntityManager emf;

    @PersistenceContext
    public void setEntityManager(EntityManager emf) {
        this.emf = emf;
    }

	@Override
	public List<ProductItem> findProductItemsByKeywords(String[] keywords) {
		Set<ProductItem> result = null;
		for (String keyword : keywords) {
			Query query = emf.createQuery("from ProductItem as p where p.keywordsBundle like :keywords");
			query.setParameter("keywords", "|%" + keyword + "%|");
			List<ProductItem> resultList = query.getResultList();
			if(resultList!=null){
				if(result == null){
					result = new HashSet<>();
				}
				result.addAll(resultList);
			}
		}
        return new ArrayList<ProductItem>(result);
	}

	@Override
	public List<ProductItem> findProductItemsByKeyword(String keyword) {
		return findProductItemsByKeywords( new String[]{keyword} );
	}

}
