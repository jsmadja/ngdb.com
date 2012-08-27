package com.ngdb.web.services.infrastructure;

import static com.google.common.collect.Collections2.transform;
import static java.math.RoundingMode.HALF_DOWN;
import static org.joda.money.CurrencyUnit.EUR;
import static org.joda.money.CurrencyUnit.USD;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import com.google.common.base.Function;

public class CurrencyService {

    private ConversionRateService conversionRateService = new ConversionRateService();

    public double fromEurosToDollars(double euros) {
        return fromToRate(euros, EUR, USD, conversionRateService.getEurosToDollarRate());
    }

    public double fromDollarsToEuros(double dollars) {
        return fromToRate(dollars, USD, EUR, conversionRateService.getDollarToEurosRate());
    }

    private double fromToRate(Double amount, CurrencyUnit fromCurrency, CurrencyUnit toCurrency, double rateConversion) {
        amount = new BigDecimal(amount).setScale(2, HALF_DOWN).doubleValue();
        Money money = Money.of(fromCurrency, amount);
        BigDecimal conversionRate = new BigDecimal(rateConversion);
        Money dollar = money.convertedTo(toCurrency, conversionRate, HALF_DOWN);
        return dollar.getAmount().doubleValue();
    }

    public Collection<String> allCurrenciesWithout(String... currenciesToRemove) {
        Collection<String> currencies = allCurrencies();
        for (String removeCurrency : currenciesToRemove) {
            currencies.remove(removeCurrency);
        }
        return currencies;
    }

    public Collection<String> allCurrencies() {
        List<CurrencyUnit> registereCurrencies = CurrencyUnit.registeredCurrencies();
        return transform(registereCurrencies, new Function<CurrencyUnit, String>() {
            @Override
            @Nullable
            public String apply(@Nullable CurrencyUnit input) {
                return input.getCurrencyCode();
            }
        });
    }

}
