package com.tradevalidator.validators;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.tradevalidator.rest.entity.ProductType;
import com.tradevalidator.rest.entity.Trade;

@Component
public class CustomerValidator implements Validator {
	private Set<String> supportedCustomers = new HashSet<>(Arrays.asList("PLUTO1", "PLUTO2"));
	
	@Override
	public Set<ProductType> getSupportedProducts() {
		return new HashSet<>(Arrays.asList(ProductType.FORWARD, ProductType.OPTION, ProductType.SPOT));
	}

	@Override
	public Optional<String> validate(Trade trade) {
		if (!supportedCustomers.contains(trade.getCustomer())) {
			return Optional.of("Unsupported counterparty.");
		}
		return Optional.empty();
	}

}
