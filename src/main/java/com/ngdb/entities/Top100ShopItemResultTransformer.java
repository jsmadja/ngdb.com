package com.ngdb.entities;

import org.hibernate.transform.ResultTransformer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static java.text.MessageFormat.format;
import static org.joda.money.CurrencyUnit.of;

public class Top100ShopItemResultTransformer implements ResultTransformer {

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        long id = ((BigInteger) tuple[0]).longValue();
        String title = tuple[1].toString();
        String platform = tuple[2].toString();
        String currency = tuple[3].toString();
        Double amount = (Double)tuple[4];
        long shopItemId = ((BigInteger) tuple[5]).longValue();
        String price =  format("{0} {1}", amount, of(currency).getSymbol());
        return new Top100ShopItem(id, title, platform, price, shopItemId);
    }

    @Override
    public List transformList(List collection) {
        return collection;
    }
}
