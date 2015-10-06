package com.bmustapha.currencycalculator.calculator;

import com.bmustapha.currencycalculator.helpers.ExchangeRateHelper;

/**
 * Created by tunde on 9/28/15.
 */
public class CalculatorBrain {

    private String firstValue = "0";
    private String secondValue = "0";
    private Double answer;
    private String operator;
    private String firstValueCurrency;
    private String secondValueCurrency;
    private String targetCurrency;
    private boolean isFirst = true;
    private String baseCurrency;
    private double firstValueInDollar;
    private double secondValueInDollar;
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
        if (operator != null) {
            // remove last character of second value
            secondValue = (secondValue.substring(0, secondValue.length() - 1).equals("")) ? "0" : secondValue.substring(0, secondValue.length() - 1);
        } else {
            // remove last character of first value
            firstValue = (firstValue.substring(0, firstValue.length() - 1).equals("")) ? "0" : firstValue.substring(0, firstValue.length() - 1);
        }
    }

    public void reset() {
        firstValue = "0";
        secondValue = "0";
        operator = null;
        isFirst = true;
        firstValueInDollar = 0;
        secondValueInDollar = 0;
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
        if (Double.parseDouble(firstValue) > 0) {
            this.firstValueCurrency = baseCurrency;
            this.operator = operator;
            isFirst = false;
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
        firstValue = secondValue;
        secondValue = "0";
    }

    private void convertNumbers() {
       firstValueInDollar = ExchangeRateHelper.getDollarEquivalent(Double.parseDouble(firstValue), firstValueCurrency);
       secondValueInDollar = ExchangeRateHelper.getDollarEquivalent(Double.parseDouble(secondValue), secondValueCurrency);
    }

    private double getTargetAmount(double dollar) {
        return ExchangeRateHelper.getTargetCurrencyEquivalent(dollar, targetCurrency);
    }

    private void divide() {
        convertNumbers();
        double answerInDollar = firstValueInDollar / secondValueInDollar;
        answer = getTargetAmount(answerInDollar);
    }

    private void multiply() {
        convertNumbers();
        double answerInDollar = firstValueInDollar * secondValueInDollar;
        answer = getTargetAmount(answerInDollar);
    }

    private void subtract() {
        convertNumbers();
        double answerInDollar = firstValueInDollar - secondValueInDollar;
        answer = getTargetAmount(answerInDollar);
    }

    private void add() {
        convertNumbers();
        double answerInDollar = firstValueInDollar + secondValueInDollar;
        answer = getTargetAmount(answerInDollar);
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }
}
