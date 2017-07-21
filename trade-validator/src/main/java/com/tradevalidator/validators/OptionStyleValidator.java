package com.tradevalidator.validators;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.tradevalidator.rest.entity.ProductType;
import com.tradevalidator.rest.entity.Trade;

@Component
public class OptionStyleValidator implements Validator {
	private Set<String> supportedStyles = new HashSet<>(Arrays.asList("AMERICAN", "EUROPEAN"));
	
	@Override
	public Set<ProductType> getSupportedProducts() {
		return new HashSet<>(Arrays.asList(ProductType.OPTION));
	}

	@Override
	public Optional<String> validate(Trade trade) {
		if (!supportedStyles.contains(trade.getStyle())) {
			return Optional.of("Unsupported option style.");
		}
		return Optional.empty();
	}

}
