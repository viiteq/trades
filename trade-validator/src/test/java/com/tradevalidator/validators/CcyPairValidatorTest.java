package com.tradevalidator.validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.tradevalidator.rest.entity.Trade;
import com.tradevalidator.validators.CcyPairValidator;

public class CcyPairValidatorTest {

	CcyPairValidator validator = new CcyPairValidator();
	
	@Test
	public void shouldReturnErrorWhenCcCodeInvalid() {
		assertTrue(validator.validate(of("EURXYZ")).isPresent());
		assertTrue(validator.validate(of("XYZEUR")).isPresent());
		assertTrue(validator.validate(of("EURUS")).isPresent());
		assertTrue(validator.validate(of("EUR")).isPresent());
		assertTrue(validator.validate(of("")).isPresent());
	}
	
	@Test
	public void shouldNotReturnErrorWhenCcCodeValid() {
		assertFalse(validator.validate(of("EURUSD")).isPresent());
	}
	
	private Trade of(String ccyPair) {
		Trade trade = new Trade();
		trade.setCcyPair(ccyPair);
		return trade;
	}
}
