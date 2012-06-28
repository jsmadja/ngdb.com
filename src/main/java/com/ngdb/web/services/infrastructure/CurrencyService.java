package com.ngdb.web.services.infrastructure;

import static java.math.RoundingMode.HALF_DOWN;
import static org.joda.money.CurrencyUnit.EUR;
import static org.joda.money.CurrencyUnit.USD;

import java.math.BigDecimal;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

public class CurrencyService {

	private ConversionRateService conversionRateService = new ConversionRateService();

	public double fromEurosToDollars(double euros) {
		return fromToRate(euros, EUR, USD, conversionRateService.getEurosToDollarRate());
	}

	public double fromDollarsToEuros(double dollars) {
		return fromToRate(dollars, USD, EUR, conversionRateService.getDollarToEurosRate());
	}

	private double fromToRate(double amount, CurrencyUnit fromCurrency, CurrencyUnit toCurrency, double rateConversion) {
		Money money = Money.of(fromCurrency, amount);
		BigDecimal conversionRate = new BigDecimal(rateConversion);
		Money dollar = money.convertedTo(toCurrency, conversionRate, HALF_DOWN);
		return dollar.getAmount().doubleValue();
	}

}
