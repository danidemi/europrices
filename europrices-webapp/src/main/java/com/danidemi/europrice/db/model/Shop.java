package com.danidemi.europrice.db.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Shop implements Serializable {

	private static final long serialVersionUID = -7648653195433910441L;
	private Long id;
	private String name;

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId(){
		return id;
	}
    
    public void setId(Long id) {
		this.id = id;
	}

	public ProductItem newProductItem() {
		ProductItem productItem = new ProductItem();
		productItem.setShop(this);
		return productItem;
	}
	
	@Column(nullable=false, length=128, unique=true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
