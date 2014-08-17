package com.danidemi.europrice.db;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
	public List<ProductItem> findProductItemsByKeywords(List<String> terms) {
		return findProductItemsByKeywords( terms.toArray(new String[terms.size()]) );
	}    
	
	@Override
	public List<ProductItem> findProductItemsByKeywords(String[] keywords) {
		
		TreeSet<ProductItem> result = null;
		for (String keyword : keywords) {
			Query query = emf.createQuery("from ProductItem as p where p.keywordsBundle like :keywords");
			query.setParameter("keywords", "|%" + keyword + "%|");
			List<ProductItem> resultList = query.getResultList();
			if(resultList!=null){
				if(result == null){
					result = new TreeSet<>(new Comparator<ProductItem>() {

						@Override
						public int compare(ProductItem p1, ProductItem p2) {
							int compareTo = p1.getPriceInCent().compareTo(p2.getPriceInCent());
							if(compareTo != 0) return compareTo;
							compareTo = p1.getKeywordsBundle().compareTo(p2.getKeywordsBundle());
							if(compareTo != 0) return compareTo;
							compareTo = p1.getShop().getName().compareTo(p2.getShop().getName());
							return compareTo;
						}
					});
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
