package com.tradevalidator.validators;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.tradevalidator.rest.entity.ProductType;
import com.tradevalidator.rest.entity.Trade;

@Component
public class AmericanOptionStyleValidator implements Validator {
	
	@Override
	public Set<ProductType> getSupportedProducts() {
		return new HashSet<>(Arrays.asList(ProductType.OPTION));
	}

	@Override
	public Optional<String> validate(Trade trade) {
		if (trade.getStyle().equals("AMERICAN") && (
				!trade.getExcerciseStartDate().isAfter(trade.getTradeDate()) || !trade.getExcerciseStartDate().isBefore(trade.getExpiryDate()))) {
			return Optional.of("American style option exercise date shall be between trade date and expiry date.");
		}
		return Optional.empty();
	}

}
