package com.tradevalidator.rest.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TradeErrors {
	
	public TradeErrors(int tradeRowNumber, List<String> errors) {
		this.tradeRowNumber = tradeRowNumber;
		this.errors = errors;
	}

	@JsonProperty(required = true)
	private int tradeRowNumber;
	private List<String> errors;
	public int getTradeRowNumber() {
		return tradeRowNumber;
	}
	public void setTradeRowNumber(int tradeRowNumber) {
		this.tradeRowNumber = tradeRowNumber;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
	
}
