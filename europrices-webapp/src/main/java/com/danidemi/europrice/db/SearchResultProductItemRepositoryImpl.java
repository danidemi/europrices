package com.danidemi.europrice.db;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


public class SearchResultProductItemRepositoryImpl implements SearchResultProductItemRepositoryCustom {
	
    private EntityManager emf;

    @PersistenceContext
    public void setEntityManager(EntityManager emf) {
        this.emf = emf;
    }
    
	@Override
	public List<SearchResultProductItem> findProductItemsByKeywords(List<String> terms) {
		return findProductItemsByKeywords( terms.toArray(new String[terms.size()]) );
	}    
	
	@Override
	public List<SearchResultProductItem> findProductItemsByKeywords(String[] keywords) {
		
		TreeSet<SearchResultProductItem> result = null;
		for (String keyword : keywords) {
			Query query = emf.createQuery("from SearchResultProductItem as p where p.keywordsBundle like :keywords");
			query.setParameter("keywords", "|%" + keyword + "%|");
			List<SearchResultProductItem> resultList = query.getResultList();
			if(resultList!=null){
				if(result == null){
					result = new TreeSet<>(new Comparator<SearchResultProductItem>() {

						@Override
						public int compare(SearchResultProductItem p1, SearchResultProductItem p2) {
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
		
		
		
		
		
        return new ArrayList<SearchResultProductItem>(result);
	}

	@Override
	public List<SearchResultProductItem> findProductItemsByKeyword(String keyword) {
		return findProductItemsByKeywords( new String[]{keyword} );
	}



}
