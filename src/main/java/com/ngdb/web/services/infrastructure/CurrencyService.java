package com.ngdb.web.services.infrastructure;

import com.google.common.base.Function;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Collections2.transform;
import static java.math.RoundingMode.HALF_DOWN;

public class CurrencyService {

    private ConversionRateService conversionRateService = new ConversionRateService();

    public Collection<String> allCurrencies() {
        List<CurrencyUnit> registeredCurrencies = CurrencyUnit.registeredCurrencies();
        return transform(registeredCurrencies, new Function<CurrencyUnit, String>() {
            @Override
            @Nullable
            public String apply(@Nullable CurrencyUnit input) {
                return input.getCurrencyCode();
            }
        });
    }

    public Double fromToRate(Double price, String from, String to) {
        price = new BigDecimal(price).setScale(2, HALF_DOWN).doubleValue();
        Money money = Money.of(CurrencyUnit.of(from), price);
        BigDecimal conversionRate = new BigDecimal(conversionRateService.getRate(from, to));
        Money dollar = money.convertedTo(CurrencyUnit.of(to), conversionRate, HALF_DOWN);
        return dollar.getAmount().doubleValue();
    }
}
