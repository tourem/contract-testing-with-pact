package com.larbotech.cdc;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Inventory {
	
	
	private String productName;
	
	private String locationName;
	
	private int quantity;

	public Inventory( String productName, String locationName, int quantity) {
		super();
		
		this.productName = productName;
		this.locationName = locationName;
		this.quantity = quantity;
	}

	public Inventory() {
		super();
		
	}

	@Override
	public String toString() {
		return "Inventory [productName=" + productName + ", locationName="
				+ locationName + ", quantity=" + quantity + "]";
	}
	
	

}
