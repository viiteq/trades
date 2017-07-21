package com.tradevalidator.validators;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.tradevalidator.rest.entity.ProductType;
import com.tradevalidator.rest.entity.Trade;

@Component
public class PremiumExpiryDateValidator implements Validator {
	
	@Override
	public Set<ProductType> getSupportedProducts() {
		return new HashSet<>(Arrays.asList(ProductType.OPTION));
	}

	@Override
	public Optional<String> validate(Trade trade) {
		if (!trade.getPremiumDate().isBefore(trade.getDeliveryDate()) || !trade.getExpiryDate().isBefore(trade.getDeliveryDate())) {
			return Optional.of("Expiry date and premium date shall be before delivery date.");
		}
		return Optional.empty();
	}

}
