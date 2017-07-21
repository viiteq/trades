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
public class ValueDateAgainstProductTypeValidatorTest {
	
	@InjectMocks
	ValueDateAgainstProductTypeValidator validator;

	@Mock
	ExchangeService exchgService;
	
	@Test
	public void shouldReturnErrorWhenValueDateNotMatch() {
		LocalDate date = LocalDate.of(2016, 10, 1);
		LocalDate t2 = date.plusDays(2);
		LocalDate t1 = date.plusDays(1);
		when(exchgService.getTradingDate("EURUSD", t2)).thenReturn(LocalDate.of(2016, 10, 6));
		when(exchgService.getTradingDate("USDCAD", t1)).thenReturn(LocalDate.of(2016, 10, 6));
		assertTrue(validator.validate(of(ProductType.SPOT, "EURUSD", date, t2)).isPresent());
		assertTrue(validator.validate(of(ProductType.SPOT, "USDCAD", date, t1)).isPresent());
	}
	
	@Test
	public void shouldNotReturnErrorWhenValueDateMatch() {
		LocalDate date = LocalDate.of(2016, 10, 1);
		LocalDate t2 = date.plusDays(2);
		LocalDate t1 = date.plusDays(1);
		when(exchgService.getTradingDate("EURUSD", t2)).thenReturn(LocalDate.of(2016, 10, 6));
		when(exchgService.getTradingDate("USDCAD", t1)).thenReturn(LocalDate.of(2016, 10, 6));
		assertFalse(validator.validate(of(ProductType.FORWARD, "EURUSD", date, t2)).isPresent());
		assertFalse(validator.validate(of(ProductType.FORWARD, "USDCAD", date, t1)).isPresent());
	}
	
	@Test
	public void shouldIgnoreForward() {
		LocalDate date = LocalDate.of(2016, 10, 1);
		LocalDate t2 = date.plusDays(2);
		LocalDate t1 = date.plusDays(1);
		when(exchgService.getTradingDate("EURUSD", t2)).thenReturn(t2);
		when(exchgService.getTradingDate("USDCAD", t1)).thenReturn(t1);
		assertFalse(validator.validate(of(ProductType.SPOT, "EURUSD", date, t2)).isPresent());
		assertFalse(validator.validate(of(ProductType.SPOT, "USDCAD", date, t1)).isPresent());
	}
	
	private Trade of(ProductType type, String ccyPair, LocalDate tradeDate, LocalDate valueDate) {
		Trade trade = new Trade();
		trade.setType(type.getName());
		trade.setCcyPair(ccyPair);
		trade.setTradeDate(tradeDate);
		trade.setValueDate(valueDate);
		return trade;
	}
}
