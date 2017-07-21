package com.tradevalidator.validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

import com.tradevalidator.rest.entity.Trade;

public class PremiumExpiryDateValidatorTest {
	
	PremiumExpiryDateValidator validator = new PremiumExpiryDateValidator();

	@Test
	public void shouldReturnErrorWhenDatesOutOfRange() {
		LocalDate deliveryDate = LocalDate.of(2016, 10, 2);
		assertTrue(validator.validate(of(LocalDate.of(2016, 10, 4), LocalDate.of(2016, 10, 5), deliveryDate)).isPresent());
		assertTrue(validator.validate(of(LocalDate.of(2016, 10, 1), LocalDate.of(2016, 10, 5), deliveryDate)).isPresent());
		assertTrue(validator.validate(of(LocalDate.of(2016, 10, 5), LocalDate.of(2016, 10, 1), deliveryDate)).isPresent());
	}
	
	@Test
	public void shouldNotReturnErrorWhenDatesInRange() {
		LocalDate deliveryDate = LocalDate.of(2016, 10, 2);
		assertFalse(validator.validate(of(LocalDate.of(2016, 10, 1), LocalDate.of(2016, 10, 1), deliveryDate)).isPresent());
	}
	
	private Trade of(LocalDate premiumDate, LocalDate expiryDate, LocalDate deliveryDate) {
		Trade trade = new Trade();
		trade.setPremiumDate(premiumDate);
		trade.setDeliveryDate(deliveryDate);
		trade.setExpiryDate(expiryDate);
		return trade;
	}
}
