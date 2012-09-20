package com.ngdb.web.services.infrastructure;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CurrencyServiceTest {

	private CurrencyService currencyService = new CurrencyService();

	@Test
	@Ignore
	public void should_convert_from_euro_to_dollar() {
		double dollar = currencyService.fromEurosToDollars(1);
		assertEquals(1.24, dollar, 0);
	}

	@Test
	@Ignore
	public void should_convert_from_dollar_to_euro() {
		assertEquals(0.80, currencyService.fromDollarsToEuros(1), 0);
	}

	@Test
	public void should_not_fail() {
		currencyService.fromDollarsToEuros(9.877);
		currencyService.fromEurosToDollars(9.877);
	}

}
