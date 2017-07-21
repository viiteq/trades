package com.tradevalidator.rest.entity;

public enum ProductType {
	SPOT("Spot"),
	FORWARD("Forward"),
	OPTION("VanillaOption")
	;
	
	private String name;

	private ProductType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static ProductType byName(String name) {
		for (ProductType pType : ProductType.values()) {
			if (pType.getName().equals(name)) {
				return pType;
			}
		}
		throw new IllegalArgumentException("Unknown product name: " + name);
	}
}
