package com.tradevalidator.validators;

import java.util.Optional;
import java.util.Set;

import com.tradevalidator.rest.entity.ProductType;
import com.tradevalidator.rest.entity.Trade;

/**
 * Interface implemented by all validators. 
 *
 */
public interface Validator {
	
	/**
	 * Used to match relevant validators to given trade.
	 * 
	 * @return supported products
	 */
	Set<ProductType> getSupportedProducts();
	
	/**
	 * Validates single trade.
	 * @param trade
	 * @return error message or empty if no errors
	 */
	Optional<String> validate(Trade trade);
}
