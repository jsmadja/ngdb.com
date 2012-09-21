package com.ngdb.web.pages.shop;

import org.apache.commons.lang.StringUtils;

public class PriceUtils {

    public static double stringPriceToDouble(String priceToConvert) {
        priceToConvert = priceToConvert.replace(',', '.');
        StringUtils.replaceChars(priceToConvert, ((char) 160) + "", "");
        return Double.valueOf(priceToConvert);
    }

}
