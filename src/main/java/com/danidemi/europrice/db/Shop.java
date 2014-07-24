package com.danidemi.europrice.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/** A shop */
@Entity
public class Shop {

    private Long id;

	@Id @GeneratedValue
	public Long getId(){
		return id;
	}
    
    public void setId(Long id) {
		this.id = id;
	}
	
}
