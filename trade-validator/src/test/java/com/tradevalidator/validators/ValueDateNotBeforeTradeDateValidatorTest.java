package com.tradevalidator.validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

import com.tradevalidator.rest.entity.ProductType;
import com.tradevalidator.rest.entity.Trade;

public class ValueDateNotBeforeTradeDateValidatorTest {

	ValueDateNotBeforeTradeDateValidator validator = new ValueDateNotBeforeTradeDateValidator();
	
	@Test
	public void shouldReturnErrorWhenOptionDeliveryDateBeforeTradeDate() {
		LocalDate tradeDate = LocalDate.of(2016, 10, 4);
		LocalDate deliveryDate = LocalDate.of(2016, 10, 3);
		assertTrue(validator.validate(of(ProductType.OPTION, tradeDate, null, deliveryDate)).isPresent());
	}
	
	@Test
	public void shouldReturnErrorWhenNotOptionValueDateBeforeTradeDate() {
		LocalDate tradeDate = LocalDate.of(2016, 10, 4);
		LocalDate valueDate = LocalDate.of(2016, 10, 3);
		assertTrue(validator.validate(of(ProductType.SPOT, tradeDate, valueDate, null)).isPresent());
	}
	
	@Test
	public void shouldNotReturnErrorWhenOptionDeliveryDateCorrect() {
		LocalDate tradeDate = LocalDate.of(2016, 10, 4);
		LocalDate deliveryDate = LocalDate.of(2016, 10, 5);
		assertFalse(validator.validate(of(ProductType.OPTION, tradeDate, null, deliveryDate)).isPresent());
	}
	
	@Test
	public void shouldNotReturnErrorWhenNotOptionValueDateCorrect() {
		LocalDate tradeDate = LocalDate.of(2016, 10, 4);
		LocalDate valueDate = LocalDate.of(2016, 10, 4);
		assertFalse(validator.validate(of(ProductType.SPOT, tradeDate, valueDate, null)).isPresent());
	}
	
	private Trade of(ProductType type, LocalDate tradeDate, LocalDate valueDate, LocalDate deliveryDate) {
		Trade trade = new Trade();
		trade.setType(type.getName());
		trade.setTradeDate(tradeDate);
		trade.setValueDate(valueDate);
		trade.setDeliveryDate(deliveryDate);
		return trade;
	}
}
