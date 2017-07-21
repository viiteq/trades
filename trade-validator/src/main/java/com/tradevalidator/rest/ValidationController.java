package com.tradevalidator.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.codahale.metrics.Timer.Context;
import com.tradevalidator.rest.entity.Trade;
import com.tradevalidator.rest.entity.TradeErrors;
import com.tradevalidator.service.ValidationService;

@RestController
public class ValidationController {
	private final MetricRegistry metrics = new MetricRegistry();
	private final Timer responses = metrics.timer("Validator responses");
	
	public ValidationController() {
		JmxReporter reporter = JmxReporter.forRegistry(metrics).build();
		reporter.start();
	}
	
	@Autowired
	private ValidationService validationService;

	@CrossOrigin(origins = "*")
	@RequestMapping(path = "/validate", method = RequestMethod.POST, produces = "application/json")
    public List<TradeErrors> validate(@RequestBody List<Trade> trades) {
		Context timer =  responses.time();
		try {
			return validationService.validate(trades);
		} finally {
			timer.stop();
		}
    }
}
