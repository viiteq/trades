package com.tradevalidator.validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

import com.tradevalidator.rest.entity.Trade;

public class AmericanOptionStyleValidatorTest {
	
	AmericanOptionStyleValidator validator = new AmericanOptionStyleValidator();

	@Test
	public void shouldReturnErrorWhenExcerciseDateOutOfRange() {
		LocalDate tradeDate = LocalDate.of(2016, 10, 4);
		LocalDate expiryDate = LocalDate.of(2016, 10, 8);
		assertTrue(validator.validate(of("AMERICAN", tradeDate, expiryDate, LocalDate.of(2016, 10, 3))).isPresent());
		assertTrue(validator.validate(of("AMERICAN", tradeDate, expiryDate, LocalDate.of(2016, 10, 9))).isPresent());
		assertTrue(validator.validate(of("AMERICAN", tradeDate, expiryDate, LocalDate.of(2016, 10, 4))).isPresent());
		assertTrue(validator.validate(of("AMERICAN", tradeDate, expiryDate, LocalDate.of(2016, 10, 8))).isPresent());
	}
	
	@Test
	public void shouldNotReturnErrorWhenExcerciseDateInfRange() {
		LocalDate tradeDate = LocalDate.of(2016, 10, 4);
		LocalDate expiryDate = LocalDate.of(2016, 10, 8);
		assertFalse(validator.validate(of("AMERICAN", tradeDate, expiryDate, LocalDate.of(2016, 10, 6))).isPresent());
	}
	
	@Test
	public void shouldIgnoreNonAmericanStyle() {
		LocalDate tradeDate = LocalDate.of(2016, 10, 4);
		LocalDate expiryDate = LocalDate.of(2016, 10, 8);
		assertFalse(validator.validate(of("EUROPEAN", tradeDate, expiryDate, LocalDate.of(2016, 10, 10))).isPresent());
	}
	
	private Trade of(String style, LocalDate tradeDate, LocalDate expiryDate, LocalDate exerciseDate) {
		Trade trade = new Trade();
		trade.setStyle(style);
		trade.setExcerciseStartDate(exerciseDate);
		trade.setTradeDate(tradeDate);
		trade.setExpiryDate(expiryDate);
		return trade;
	}
}
