package com.lee.core.business.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;

import org.hibernate.annotations.GenericGenerator;

/**
 * the base class of entity storey
 * @author Jason Lee
 *
 */
public abstract class Entity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "ID",nullable = false)
	@GenericGenerator(name="systemUUID",strategy="uuid")
	@GeneratedValue(generator="systemUUID")
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj instanceof Entity){
			return ((Entity)obj).getId().equals(this.id);
		}
		return false;
	}
	
	
}
