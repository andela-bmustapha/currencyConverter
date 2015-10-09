package com.bmustapha.currencycalculator.helpers;

import android.os.AsyncTask;
import android.util.Log;

import com.bmustapha.currencycalculator.config.Constants;
import com.bmustapha.currencycalculator.interfaces.RatesFetchValidator;
import com.bmustapha.currencycalculator.models.CurrencyRate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tunde on 9/28/15.
 */
public class ExchangeRateHelper {

    private static JSONObject ratesObject;
    private static List<CurrencyRate> topCurrenciesRates = new ArrayList<>();

    public static void createTopCurrenciesRate() {
        Iterator<String> iterator = ExchangeRateHelper.ratesObject.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            try {
                CurrencyRate currencyRate = new CurrencyRate(key, ratesObject.getDouble(key));
                topCurrenciesRates.add(currencyRate);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // sort the items in the list
        Collections.sort(topCurrenciesRates);
    }

    public static List<CurrencyRate> getTopCurrenciesRates() {
        return topCurrenciesRates;
    }

    public static JSONObject getRates() {
        return ratesObject;
    }

    public static void getExchangeRates(RatesFetchValidator ratesFetchValidator) {
        new ExchangeRateFetcher().execute(ratesFetchValidator);
    }

    public static boolean isRateSet() {
        return ratesObject != null;
    }

    private static class ExchangeRateFetcher extends AsyncTask<RatesFetchValidator, Void, RatesFetchValidator> {

        String finalString;

        @Override
        protected RatesFetchValidator doInBackground(RatesFetchValidator... ratesFetchValidators) {
            Log.e("Testing: ", "Do in background...!");
            finalString = RatesFetcher.getJsonString(Constants.URL);
            return ratesFetchValidators[0];
        }

        @Override
        protected void onPostExecute(RatesFetchValidator ratesFetchValidator) {
            // create a JSON object from the string
            try {
                Log.e("Testing: ", "Post Execute...");
                JSONObject exchangeRateJsonObject = new JSONObject(finalString);
                ratesObject = exchangeRateJsonObject.getJSONObject("rates");
                ratesFetchValidator.onFinish();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
