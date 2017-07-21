package com.tradevalidator.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tradevalidator.rest.entity.ExchgRate;

/**
 * Service for checking trading days for given currency pairs.
 * Uses external web service: http://api.fixer.io
 */
@Service
public class ExchangeService { 
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static final String FIXER_URL = "http://api.fixer.io/%s?base=%s&symbols=%s";
	
	@Autowired
	private RestTemplate restTemplate;
	

	/**
	 * Checks if given ccyPair was traded on given date.
	 * @param ccyPair
	 * @param date
	 */
	public boolean isTradingDate(String ccyPair, LocalDate date) {
		ExchgRate exchg = getRate(ccyPair, date);
		return exchg.getDate().equals(date);
	}
	
	/**
	 * Finds trading date close to given date.
	 * Found date is never before given date.
	 * @param ccyPair
	 * @param date
	 */
	public LocalDate getTradingDate(String ccyPair, LocalDate date) {
		LocalDate nextDate = date;
		ExchgRate exchg;
		do {
			exchg = getRate(ccyPair, nextDate);
			nextDate = nextDate.plusDays(1);
		} while (exchg.getDate().isBefore(date));
		
		return exchg.getDate();
	}
	
	private ExchgRate getRate(String ccyPair, LocalDate date) {
		return restTemplate.getForObject(String.format(FIXER_URL, date.format(formatter), 
				ccyPair.substring(0, 3), ccyPair.substring(3)), ExchgRate.class);
	}
}
