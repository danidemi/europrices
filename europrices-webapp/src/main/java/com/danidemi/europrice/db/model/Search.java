package com.danidemi.europrice.db.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Search implements Serializable {

	private Long id;
	private String search;
	private Timestamp lastTime;
	private int times;
	private Integer resultCount;
	
	public Search() {
		lastTime = new Timestamp( System.currentTimeMillis() );
		times = 0;
	}
	
	public Search(String searchTerms) {
		this();
		this.search = searchTerms;
	}

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId(){
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(nullable=false, length=1024, unique=true)
	public String getSearch() {
		return search;
	}
	
	public void setSearch(String search) {
		this.search = search;
	}
	
	@Column(nullable=false)
	public Date getLastTime() {
		return lastTime;
	}
	
	public void setLastTime(Timestamp lastTime) {
		this.lastTime = lastTime;
	}
	
	@Column(nullable=false)
	public int getTimes() {
		return times;
	}
	
	public void setTimes(int times) {
		this.times = times;
	}

	public void incTimes() {
		times++;
	}

	@Column(nullable = false)
	public Integer getResultCount() {
		return resultCount;
	}
	
	public void setResultCount(Integer resultCount) {
		this.resultCount = resultCount;
	}
	
}
