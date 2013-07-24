package com.ngdb.web.services.infrastructure;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import static java.math.RoundingMode.HALF_DOWN;

public class CurrencyService {

    private ConversionRateService conversionRateService = new ConversionRateService();

    public Collection<String> allCurrencies() {
        return Arrays.asList("EUR", "USD", "GBP", "JPY", "CHF");
    }

    public Double fromToRate(Double price, String from, String to) {
        price = new BigDecimal(price).setScale(2, HALF_DOWN).doubleValue();
        Money money = Money.of(CurrencyUnit.of(from), price);
        BigDecimal conversionRate = new BigDecimal(conversionRateService.getRate(from, to));
        Money dollar = money.convertedTo(CurrencyUnit.of(to), conversionRate, HALF_DOWN);
        return dollar.getAmount().doubleValue();
    }
}
