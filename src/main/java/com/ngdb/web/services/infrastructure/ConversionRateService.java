package com.ngdb.web.services.infrastructure;

import com.google.common.io.ByteStreams;
import org.joda.money.CurrencyUnit;
import org.joda.money.IllegalCurrencyException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.joda.money.CurrencyUnit.EUR;

public class ConversionRateService {

	private static final Logger LOG = LoggerFactory.getLogger(ConversionRateService.class);

	private static Map<String, Double> conversionRates = new HashMap<String, Double>();

	private Date nextUpdate = new Date();

    public double getRate(String from, String to) {
        if(from.equalsIgnoreCase(to)) {
            return 1;
        }
        reloadIfNeeded();
        if (conversionRates.isEmpty()) {
            throw new UnavailableRatingException("Unavailable Rate "+from+" -> "+to);
        }
        if("USD".equalsIgnoreCase(from)) {
            return conversionRates.get(to);
        }
        if("USD".equalsIgnoreCase(to)) {
            return 1/conversionRates.get(from);
        }
        return conversionRates.get(to) / conversionRates.get(from);
    }

	private void reloadIfNeeded() {
		if (new Date().after(nextUpdate)) {
			retrieveConversionRates();
			nextUpdate = new DateTime().plusDays(1).toDate();
		}
	}

	private void retrieveConversionRates() {
		try {
			String content = getLastRates();
			conversionRates.clear();
			Pattern conversionRatesPattern = Pattern.compile("\"([A-Z]{3})\": ([0-9]+\\.*[0-9]*),");
			Matcher matcher = conversionRatesPattern.matcher(content);
			while (matcher.find()) {
				insertConversionRate(matcher);
			}
		} catch (Exception e) {
			LOG.error("Cannot retrieve conversion rates, "+e.getMessage());
		}
	}

	private void insertConversionRate(Matcher matcher) {
		try {
			CurrencyUnit currency = CurrencyUnit.of(matcher.group(1));
			Double conversionRateToDollar = Double.valueOf(matcher.group(2));
			conversionRates.put(currency.getCode(), conversionRateToDollar);
		} catch (IllegalCurrencyException e) {
			System.err.println(e.getMessage());
		}
	}

	private String getLastRates() throws IOException {
        String openexchangeratesAppId = isProductionServer() ? "233ef39741bd4b09aaa16e660da2d5bf" : "";
		URL url = new URL("http://openexchangerates.org/latest.json?app_id="+openexchangeratesAppId);
		URLConnection openConnection = url.openConnection();
		return new String(ByteStreams.toByteArray(openConnection.getInputStream()));
	}

    private boolean isProductionServer() {
        return "true".equalsIgnoreCase(System.getProperty("production"));
    }

}
