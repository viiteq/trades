package com.tradevalidator.rest.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class represents trade that shall be validated.
 * Note: In case there are more product types consider switching to hierarchy which has Trade as base class with common fields extracted.
 *
 */
public class Trade {
	@JsonProperty(required = true)
	private String customer;
	private LocalDate valueDate;
	@JsonProperty(required = true)
	private LocalDate tradeDate;
	private LocalDate deliveryDate;
	@JsonProperty(required = true)
	private String ccyPair;
	@JsonProperty(required = true)
	private String type;
	private String style;
	private LocalDate excerciseStartDate;
	private LocalDate expiryDate;
	private LocalDate premiumDate;
	
	public String getCustomer() {
		return customer;
	}
	
	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getCcyPair() {
		return ccyPair;
	}

	public void setCcyPair(String ccyPair) {
		this.ccyPair = ccyPair;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public ProductType getProductType() {
		return ProductType.byName(type);
	}

	public LocalDate getValueDate() {
		return valueDate;
	}

	public void setValueDate(LocalDate valueDate) {
		this.valueDate = valueDate;
	}

	public LocalDate getTradeDate() {
		return tradeDate;
	}

	public void setTradeDate(LocalDate tradeDate) {
		this.tradeDate = tradeDate;
	}

	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public LocalDate getExcerciseStartDate() {
		return excerciseStartDate;
	}

	public void setExcerciseStartDate(LocalDate excerciseStartDate) {
		this.excerciseStartDate = excerciseStartDate;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public LocalDate getPremiumDate() {
		return premiumDate;
	}

	public void setPremiumDate(LocalDate premiumDate) {
		this.premiumDate = premiumDate;
	}
}
