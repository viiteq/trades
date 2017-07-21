package com.tradevalidator.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.tradevalidator.rest.entity.ProductType;
import com.tradevalidator.rest.entity.Trade;
import com.tradevalidator.rest.entity.TradeErrors;
import com.tradevalidator.validators.Validator;

/**
 * Validates trades against discovered validators.
 *
 */
@Service
public class ValidationService {
	
	@Autowired
	private ApplicationContext context;
	
	private Map<ProductType, List<Validator>> validators = new HashMap<>();
	
	@PostConstruct
	void init() {
		context.getBeansOfType(Validator.class).values().forEach(validator -> 
			validator.getSupportedProducts().forEach(product -> 
				validators.computeIfAbsent(product, p -> new ArrayList<Validator>()).add(validator)));
	}

	public List<TradeErrors> validate(List<Trade> trades) {
		List<TradeErrors> errors = new ArrayList<>();
		
		for (int i = 0; i < trades.size(); i++) {
			Trade trade = trades.get(i);
			List<String> tradeErrs = validators.getOrDefault(trade.getProductType(), Collections.emptyList()).stream()
				.map(validator -> validator.validate(trade))
				.filter(Optional::isPresent)
				.map(error -> error.get())
				.collect(Collectors.toList());
			
			if (!tradeErrs.isEmpty()) {
				errors.add(new TradeErrors(i, tradeErrs));
			}
		}
		
		return errors;
	}
}
