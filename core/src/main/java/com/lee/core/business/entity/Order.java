package com.lee.core.business.entity;

public class Order {

	private boolean isAsc;
	
	private String propertyName;
	
	private Order(boolean isAsc, String propertyName) {
		this.isAsc = isAsc;
		this.propertyName = propertyName;
	}
	
	public static Order asc(String propertyName) {
		return new Order(true, propertyName);
	}
	
	public static Order desc(String propertyName) {
		return new Order(false, propertyName);
	}

	public boolean isAsc() {
		return isAsc;
	}

	public String getPropertyName() {
		return propertyName;
	}
	
}
