package com.bmustapha.currencycalculator.calculator;

import com.bmustapha.currencycalculator.helpers.ExchangeRateHelper;

import org.json.JSONException;

/**
 * Created by tunde on 9/28/15.
 */
public class CalculatorBrain {

    private String firstValue;
    private String secondValue;
    private Double answer;
    private String operator;
    private String firstValueCurrency;
    private String secondValueCurrency;
    private String targetCurrency;
    private boolean isFirst = true;
    private String baseCurrency;

    private Double firstValueConverted;
    private Double secondValueConverted;
    private final String DECIMAL_POINT = ".";



    public CalculatorBrain() {

    }

    public void setValue(String value) {
        if (operator != null) {
            setSecondValue(value);
        } else {
            setFirstValue(value);
        }
    }

    private void setFirstValue(String value) {
        firstValue = getFormattedValue(firstValue, value);
    }

    private void setSecondValue(String value) {
        secondValue = getFormattedValue(secondValue, value);
    }

    private String getFormattedValue(String initialValue, String value) {
        String returnValue;
        if (initialValue == null || initialValue.equals("0")) {
            returnValue = isDecimal(value) ? "0." : value;
        } else {
            // check if incoming value is decimal and handle it
            returnValue = isDecimal(value) ? handleDecimal(initialValue) : initialValue + value;
        }
        return returnValue;
    }

    private boolean isDecimal(String value) {
        return value.equals(DECIMAL_POINT);
    }

    private String handleDecimal(String initialValue) {
        return (initialValue.contains(DECIMAL_POINT)) ? initialValue : initialValue + DECIMAL_POINT;
    }

    public void removeLast() {
        try {
            if (operator != null) {
                // remove last character of second value
                secondValue = (secondValue.substring(0, secondValue.length() - 1).equals("")) ? "0" : secondValue.substring(0, secondValue.length() - 1);
            } else {
                // remove last character of first value
                firstValue = (firstValue.substring(0, firstValue.length() - 1).equals("")) ? "0" : firstValue.substring(0, firstValue.length() - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void reset() {

        firstValue = null;
        secondValue = null;
        answer = null;
        operator = null;
        isFirst = true;
        firstValueConverted = null;
        secondValueConverted = null;
    }

    public String getCurrentValue() {
        if (isFirst) {
            return firstValue;
        }
        return secondValue;
    }

    public String getHistory() {
        if (secondValue == null || secondValue.equals("0")) {
            return firstValueCurrency + " " + firstValue + " " + operator;
        } else {
            return " " + secondValueCurrency + " " + secondValue;
        }
    }

    public void setOperator(String operator) {
        try {
            if (Double.parseDouble(firstValue) > 0) {
                this.firstValueCurrency = baseCurrency;
                this.operator = operator;
                isFirst = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void performCalculation() {
        if (operator != null) {
            this.secondValueCurrency = baseCurrency;
            calculate();
        }
    }

    public Double getAnswer() {
        return answer;
    }

    private void calculate() {
        convertNumbers();
        switch (operator) {
            case "+":
                add();
                break;
            case "-":
                subtract();
                break;
            case "\u00D7":
                multiply();
                break;
            case "\u00F7":
                divide();
                break;
        }
    }

    public void updateValues() {
        firstValue = String.valueOf(answer);
        secondValue = "0";
    }

    private void convertNumbers() {
        firstValueConverted = getConvertedValue(Double.parseDouble(firstValue), firstValueCurrency, targetCurrency);
        secondValueConverted = getConvertedValue(Double.parseDouble(secondValue), secondValueCurrency, targetCurrency);
    }

    private double getConvertedValue(double number, String baseCurrency, String targetCurrency) {
        double finalValue = 0;
        try {
            double baseCurrencyValue = ExchangeRateHelper.getRates().getDouble(baseCurrency);
            double targetCurrencyValue = ExchangeRateHelper.getRates().getDouble(targetCurrency);
            finalValue = (number * targetCurrencyValue) / baseCurrencyValue;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return finalValue;
    }

    public boolean operandsSet() {
        return firstValue != null && secondValue != null;
    }

    private void divide() {
        answer = firstValueConverted / secondValueConverted;
    }

    private void multiply() {
        answer = firstValueConverted * secondValueConverted;
    }

    private void subtract() {
        answer = firstValueConverted - secondValueConverted;
    }

    private void add() {
        answer = firstValueConverted + secondValueConverted;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }
}
