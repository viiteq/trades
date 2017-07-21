package com.tradevalidator.validators;

import java.util.Arrays;
import java.util.Currency;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.tradevalidator.rest.entity.ProductType;
import com.tradevalidator.rest.entity.Trade;

@Component
public class CcyPairValidator implements Validator {

	@Override
	public Set<ProductType> getSupportedProducts() {
		return new HashSet<>(Arrays.asList(ProductType.FORWARD, ProductType.OPTION, ProductType.SPOT));
	}

	@Override
	public Optional<String> validate(Trade trade) {
		if (!validateCcyPair(trade.getCcyPair())) {
			return Optional.of("Invalid currency code.");
		}
		return Optional.empty();
	}

	public static boolean validateCcyPair(String ccyPair) {
		if (ccyPair == null || ccyPair.length() != 6) {
			return false;
		}
		
		try {
			Currency.getInstance(ccyPair.substring(0, 3));
			Currency.getInstance(ccyPair.substring(3));
		} catch (IllegalArgumentException ex) {
			return false;
		}
		
		return true;
	}
}
