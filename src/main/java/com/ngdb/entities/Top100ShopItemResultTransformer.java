package com.ngdb.entities;

import org.hibernate.transform.ResultTransformer;
import org.joda.money.CurrencyUnit;
import org.joda.time.DateMidnight;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import static java.text.MessageFormat.format;
import static org.joda.money.CurrencyUnit.of;

public class Top100ShopItemResultTransformer implements ResultTransformer {

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        long id = ((BigInteger) tuple[0]).longValue();
        String title = tuple[1].toString();
        String platform = tuple[2].toString();
        String currency = CurrencyUnit.of(tuple[3].toString()).getSymbol();
        Double amount = (Double)tuple[4];
        long shopItemId = ((BigInteger) tuple[5]).longValue();
        long sellerId = ((BigInteger) tuple[6]).longValue();
        String state = tuple[7].toString();
        Date saleDate = (Date)tuple[8];
        return new Top100ShopItem(id, title, platform, amount, currency, shopItemId, sellerId, state, saleDate);
    }

    @Override
    public List transformList(List collection) {
        return collection;
    }
}
