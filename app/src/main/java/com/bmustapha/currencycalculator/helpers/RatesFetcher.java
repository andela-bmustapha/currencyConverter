package com.bmustapha.currencycalculator.helpers;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by andela on 10/9/15.
 */
public class RatesFetcher {

    public static String getJsonString(String stringUrl) {
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
}
