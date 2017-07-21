package com.tradevalidator.validators;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.tradevalidator.rest.entity.ProductType;
import com.tradevalidator.rest.entity.Trade;

@Component
public class ValueDateNotBeforeTradeDateValidator implements Validator {

	@Override
	public Set<ProductType> getSupportedProducts() {
		return new HashSet<>(Arrays.asList(ProductType.FORWARD, ProductType.OPTION, ProductType.SPOT));
	}

	@Override
	public Optional<String> validate(Trade trade) {
		if ((trade.getProductType() == ProductType.OPTION && trade.getDeliveryDate().isBefore(trade.getTradeDate())) ||
				(trade.getProductType() != ProductType.OPTION && trade.getValueDate().isBefore(trade.getTradeDate()))) {
			return Optional.of("Value date cannot be before trade date.");
		}
		return Optional.empty();
	}

}
