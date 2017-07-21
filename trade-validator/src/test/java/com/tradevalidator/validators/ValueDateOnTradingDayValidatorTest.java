package com.tradevalidator.validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.tradevalidator.rest.entity.ProductType;
import com.tradevalidator.rest.entity.Trade;
import com.tradevalidator.service.ExchangeService;

@RunWith(MockitoJUnitRunner.class)
public class ValueDateOnTradingDayValidatorTest {
	
	@InjectMocks
	ValueDateOnTradingDayValidator validator;

	@Mock
	ExchangeService exchgService;
	
	@Test
	public void shouldReturnErrorWhenValueDateNotMatch() {
		LocalDate date = LocalDate.of(2016, 10, 1);
		when(exchgService.isTradingDate("EURUSD", date)).thenReturn(false);
		assertTrue(validator.validate(of(ProductType.SPOT, "EURUSD", null, date)).isPresent());
		assertTrue(validator.validate(of(ProductType.OPTION, "EURUSD", date, null)).isPresent());
	}
	
	@Test
	public void shouldNotReturnErrorWhenValueDateMatch() {
		LocalDate date = LocalDate.of(2016, 10, 1);
		when(exchgService.isTradingDate("EURUSD", date)).thenReturn(true);
		assertFalse(validator.validate(of(ProductType.SPOT, "EURUSD", null, date)).isPresent());
		assertFalse(validator.validate(of(ProductType.OPTION, "EURUSD", date, null)).isPresent());
	}
	
	private Trade of(ProductType type, String ccyPair, LocalDate deliveryDate, LocalDate valueDate) {
		Trade trade = new Trade();
		trade.setType(type.getName());
		trade.setCcyPair(ccyPair);
		trade.setDeliveryDate(deliveryDate);
		trade.setValueDate(valueDate);
		return trade;
	}
}
