package com.bmustapha.currencycalculator.helpers;

import android.os.AsyncTask;
import android.util.Log;

import com.bmustapha.currencycalculator.interfaces.RatesFetchValidator;
import com.bmustapha.currencycalculator.models.CurrencyRate;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tunde on 9/28/15.
 */
public class ExchangeRateHelper {

    private static final String URL = "https://openexchangerates.org/api/latest.json?app_id=48b5ac7dd61f4794b76d8d9a0cdfbff9";
    public static JSONObject ratesObject;
    public static List<CurrencyRate> topCurrenciesRates = new ArrayList<>();
    public static ArrayList<String> list;

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

    public static void getExchangeRates(RatesFetchValidator ratesFetchValidator) {
        Log.e("Testing: ", "Reached!");
        new ExchangeRateFetcher().execute(ratesFetchValidator);
    }

    private static class ExchangeRateFetcher extends AsyncTask<RatesFetchValidator, Void, RatesFetchValidator> {

        String finalString;

        @Override
        protected RatesFetchValidator doInBackground(RatesFetchValidator... ratesFetchValidators) {
            Log.e("Testing: ", "Do in background...!");
            finalString = getJsonString(URL);
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

    private static String getJsonString(String stringUrl) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {

            URL url = new URL(stringUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return String.valueOf(stringBuilder);
    }

    public static double getDollarEquivalent(double number, String currency) {
        double dollarEquivalent = 0;
        try {
            dollarEquivalent = number / ratesObject.getDouble(currency);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dollarEquivalent;
    }

    public static double getTargetCurrencyEquivalent(double dollar, String currency) {
        double finalAmount = 0;
        try {
            finalAmount = dollar * ratesObject.getDouble(currency);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return finalAmount;
    }
}
