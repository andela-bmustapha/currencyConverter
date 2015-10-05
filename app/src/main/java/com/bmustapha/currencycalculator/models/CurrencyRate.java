package com.bmustapha.currencycalculator.models;

import android.support.annotation.NonNull;

/**
 * Created by andela on 10/2/15.
 */
public class CurrencyRate implements Comparable<CurrencyRate> {

    private String currencyName;
    private double currencyRate;

    public CurrencyRate(String currencyName, double currencyRate) {
        this.currencyName = currencyName;
        this.currencyRate = currencyRate;
    }

    public double getCurrencyRate() {
        return currencyRate;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    @Override
    public int compareTo(@NonNull CurrencyRate currencyRate) {
        if (this.getCurrencyRate() > currencyRate.getCurrencyRate()) {
            return 1;
        } else if(currencyRate.getCurrencyRate() > this.getCurrencyRate()) {
            return -1;
        }
        return 0;
    }
}
