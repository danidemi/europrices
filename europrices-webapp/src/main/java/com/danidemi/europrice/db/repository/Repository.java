package com.danidemi.europrice.db.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.danidemi.europrice.db.model.ProductItem;

public class Repository {

    private EntityManager emf;

    @PersistenceContext
    public void setEntityManager(EntityManager emf) {
        this.emf = emf;
    }

//    public void persist(Object object){
//    	emf.persist(object);
//    }

	public void persist(Object... objects) {
		for (Object object : objects) {
			emf.persist(object);
		}
		
	}

	public List<ProductItem> findProductItemsByKeywords(String[] keywords) {
		Set<ProductItem> result = null;
		for (String keyword : keywords) {
			Query query = emf.createQuery("from ProductItem as p where p.keywordsBundle like :keywords");
			query.setParameter("keywords", "|%" + keyword + "%|");
			List resultList = query.getResultList();
			if(resultList!=null){
				if(result == null){
					result = new HashSet<>();
				}
				result.addAll(resultList);
			}
		}
        return new ArrayList<ProductItem>(result);
	}

	public List<ProductItem> findProductItemsByKeyword(String keyword) {
		return findProductItemsByKeywords( new String[]{keyword} );
	}
    
}