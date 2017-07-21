package com.tradevalidator.validators;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tradevalidator.rest.entity.ProductType;
import com.tradevalidator.rest.entity.Trade;
import com.tradevalidator.service.ExchangeService;

@Component
public class ValueDateOnTradingDayValidator implements Validator {
	
	@Autowired
	private ExchangeService exchgService;

	@Override
	public Set<ProductType> getSupportedProducts() {
		return new HashSet<>(Arrays.asList(ProductType.FORWARD, ProductType.OPTION, ProductType.SPOT));
	}

	@Override
	public Optional<String> validate(Trade trade) {
		if (!CcyPairValidator.validateCcyPair(trade.getCcyPair())) {
			return Optional.empty(); // We cannot proceed since ccyPair is invalid. Such cases should be validated elsewhere.
		}
		
		LocalDate testedDate = trade.getProductType() == ProductType.OPTION ? trade.getDeliveryDate() : trade.getValueDate();
		if (!exchgService.isTradingDate(trade.getCcyPair(), testedDate)) {
			return Optional.of("Value date cannot be on non trading day.");
		}
		return Optional.empty();
	}

}
