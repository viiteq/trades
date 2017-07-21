package com.tradevalidator.validators;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
public class ValueDateAgainstProductTypeValidator implements Validator {
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private Set<String> tPlusOneCcys = new HashSet<>(Arrays.asList( "USDCAD", "USDTRY", "USDPHP", "USDRUB", "USDKZT", "USDPKR"));
	
	@Autowired
	private ExchangeService exchangeService;
	
	@Override
	public Set<ProductType> getSupportedProducts() {
		return new HashSet<>(Arrays.asList(ProductType.FORWARD, ProductType.SPOT));
	}

	@Override
	public Optional<String> validate(Trade trade) {
		if (trade.getProductType() == ProductType.SPOT) { // Forward is validated in ValueDateNotBeforeTradeDateValidator
			return validateSpot(trade);
		}
		return Optional.empty();
	}

	private Optional<String> validateSpot(Trade trade) {
		
		if (!CcyPairValidator.validateCcyPair(trade.getCcyPair())) {
			return Optional.empty(); // We cannot proceed since ccyPair is invalid. Such cases shall be validated elsewhere.
		}
		
		LocalDate incrementedDate = trade.getTradeDate().plusDays(tPlusOneCcys.contains(trade.getCcyPair()) ? 1 : 2); // in case of spot some pairs are for T+1 and the rest for T+2
		LocalDate expectedValueDate = exchangeService.getTradingDate(trade.getCcyPair(), incrementedDate);
		if (!expectedValueDate.equals(trade.getValueDate())) {
			return Optional.of("Expected value date is: " + expectedValueDate.format(formatter));
		}
		return Optional.empty();
	}
	
}
