package com.tradevalidator.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

import com.tradevalidator.rest.entity.ProductType;
import com.tradevalidator.rest.entity.Trade;
import com.tradevalidator.rest.entity.TradeErrors;
import com.tradevalidator.service.ValidationService;
import com.tradevalidator.validators.Validator;

@RunWith(MockitoJUnitRunner.class)
public class ValidationServiceTest {

	@InjectMocks
	ValidationService validationService;
	
	@Mock
	ApplicationContext applicationContext;
	
	@Test
	public void shouldApplyRelevantValidators() {
		when(applicationContext.getBeansOfType(Validator.class)).thenReturn(new HashMap<String, Validator>(){
			{
				put("1", of(new HashSet<>(Arrays.asList(ProductType.FORWARD, ProductType.OPTION)),  Optional.of("errorMsg1")));
				put("2", of(new HashSet<>(Arrays.asList(ProductType.FORWARD)),  Optional.of("errorMsg2")));
				put("3", of(new HashSet<>(Arrays.asList(ProductType.SPOT)),  Optional.of("errorMsg3")));
			}
		});
		validationService.init();
		List<TradeErrors> errors = validationService.validate(Arrays.asList(of(ProductType.FORWARD)));
		assertTrue(errors.size() == 1);
		assertTrue(errors.get(0).getErrors().size() == 2);
		assertEquals("errorMsg1", errors.get(0).getErrors().get(0));
		assertEquals("errorMsg2", errors.get(0).getErrors().get(1));
	}
	
	@Test
	public void shouldReturnEmptyListWhenTradeListEmpty() {
		when(applicationContext.getBeansOfType(Validator.class)).thenReturn(new HashMap<String, Validator>(){
			{
				put("1", of(new HashSet<>(Arrays.asList(ProductType.FORWARD, ProductType.OPTION)),  Optional.of("errorMsg1")));
				put("2", of(new HashSet<>(Arrays.asList(ProductType.FORWARD)),  Optional.of("errorMsg2")));
				put("3", of(new HashSet<>(Arrays.asList(ProductType.SPOT)),  Optional.of("errorMsg3")));
			}
		});
		validationService.init();
		List<TradeErrors> errors = validationService.validate(Collections.emptyList());
		assertTrue(errors.isEmpty());
	}
	
	@Test
	public void shouldReturnEmptyListWhenNoErrors() {
		when(applicationContext.getBeansOfType(Validator.class)).thenReturn(new HashMap<String, Validator>(){
			{
				put("1", of(new HashSet<>(Arrays.asList(ProductType.FORWARD, ProductType.OPTION)),  Optional.empty()));
				put("2", of(new HashSet<>(Arrays.asList(ProductType.FORWARD)),  Optional.empty()));
				put("3", of(new HashSet<>(Arrays.asList(ProductType.SPOT)),  Optional.empty()));
			}
		});
		validationService.init();
		List<TradeErrors> errors = validationService.validate(Arrays.asList(of(ProductType.FORWARD)));
		assertTrue(errors.isEmpty());
	}
	
	@Test
	public void shouldReturnEmptyListWhenNoValidators() {
		when(applicationContext.getBeansOfType(Validator.class)).thenReturn(new HashMap<String, Validator>());
		validationService.init();
		List<TradeErrors> errors = validationService.validate(Arrays.asList(of(ProductType.FORWARD)));
		assertTrue(errors.isEmpty());
	}
	
	@Test
	public void shouldReturnEmptyListWhenNoMatchingValidators() {
		when(applicationContext.getBeansOfType(Validator.class)).thenReturn(new HashMap<String, Validator>(){
			{
				put("1", of(new HashSet<>(Arrays.asList(ProductType.OPTION)),  Optional.empty()));
				put("2", of(new HashSet<>(Arrays.asList(ProductType.OPTION)),  Optional.empty()));
				put("3", of(new HashSet<>(Arrays.asList(ProductType.SPOT)),  Optional.empty()));
			}
		});
		validationService.init();
		List<TradeErrors> errors = validationService.validate(Arrays.asList(of(ProductType.FORWARD)));
		assertTrue(errors.isEmpty());
	}
	
	@Test
	public void shouldReturnMultipleErrorsWhenMultipleTradesAreInvalid() {
		when(applicationContext.getBeansOfType(Validator.class)).thenReturn(new HashMap<String, Validator>(){
			{
				put("1", of(new HashSet<>(Arrays.asList(ProductType.FORWARD, ProductType.OPTION)),  Optional.of("errorMsg1")));
				put("2", of(new HashSet<>(Arrays.asList(ProductType.FORWARD)),  Optional.of("errorMsg2")));
				put("3", of(new HashSet<>(Arrays.asList(ProductType.SPOT)),  Optional.of("errorMsg3")));
			}
		});
		validationService.init();
		List<TradeErrors> errors = validationService.validate(Arrays.asList(of(ProductType.FORWARD), of(ProductType.OPTION)));
		assertTrue(errors.size() == 2);
	}
	
	private Trade of(ProductType productType) {
		Trade trade = new Trade();
		trade.setType(productType.getName());
		return trade;
	}
	
	private Validator of(Set<ProductType> supportedProducts, Optional<String> errorMsg) {
		return new Validator() {
			
			@Override
			public Optional<String> validate(Trade trade) {
				return errorMsg;
			}
			
			@Override
			public Set<ProductType> getSupportedProducts() {
				return supportedProducts;
			}
		};
	}
}
