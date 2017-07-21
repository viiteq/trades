package com.tradevalidator.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import com.tradevalidator.rest.entity.ExchgRate;
import com.tradevalidator.service.ExchangeService;

@RunWith(MockitoJUnitRunner.class)
public class ExchangeServiceTest {

	@InjectMocks
	ExchangeService exchgService;
	
	@Mock
	RestTemplate restTemplate;
	
	@Test
	public void shouldPassValidUrlToRestTemplate() {
		when(restTemplate.getForObject(anyString(), any())).thenReturn(exchgRate(LocalDate.of(2016, 10, 1)));
		exchgService.isTradingDate("EURUSD", LocalDate.of(2016, 10, 1));
		verify(restTemplate).getForObject("http://api.fixer.io/2016-10-01?base=EUR&symbols=USD", ExchgRate.class);
	}
	
	@Test
	public void shouldReturnFalseOnIfNotTradingDate() {
		when(restTemplate.getForObject(anyString(), any())).thenReturn(exchgRate(LocalDate.of(2016, 10, 1)));
		boolean tradingDate = exchgService.isTradingDate("EURUSD", LocalDate.of(2016, 10, 2));
		assertFalse(tradingDate);
	}
	
	@Test
	public void shouldReturnTrueOnIfTradingDate() {
		when(restTemplate.getForObject(anyString(), any())).thenReturn(exchgRate(LocalDate.of(2016, 10, 1)));
		boolean tradingDate = exchgService.isTradingDate("EURUSD", LocalDate.of(2016, 10, 1));
		assertTrue(tradingDate);
	}
	
	@Test
	public void shouldIterateToCloseTradingDate() {
		when(restTemplate.getForObject(anyString(), any())).thenReturn(exchgRate(LocalDate.of(2016, 10, 1)));
		when(restTemplate.getForObject("http://api.fixer.io/2016-10-04?base=EUR&symbols=USD", ExchgRate.class)).thenReturn(exchgRate(LocalDate.of(2016, 10, 4)));
		LocalDate tradingDate = exchgService.getTradingDate("EURUSD", LocalDate.of(2016, 10, 2));
		assertEquals(LocalDate.of(2016, 10, 4), tradingDate);
	}
	
	private ExchgRate exchgRate(LocalDate date) {
		ExchgRate exchgRate = new ExchgRate();
		exchgRate.setDate(date);
		return exchgRate;
	}
}
