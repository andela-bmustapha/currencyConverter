package com.bmustapha.currencycalculator.calculator;

import android.content.Context;

import com.bmustapha.currencycalculator.helpers.ExchangeRateHelper;

/**
 * Created by tunde on 9/28/15.
 */
public class CalculatorBrain {

    private final Context context;
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


    public CalculatorBrain(Context context) {
        this.context = context;
    }


    public void setValue(String value) {
        if (operator == null) {
            isFirst = true;
            // first value is not set yet
            if (value.equals("0") && firstValue.equals("0")) {
                firstValue = value;
            } else if (value.equals(".")) {
                if (!firstValue.contains(".") && firstValue.equals("0")) {
                    firstValue = "0" + value;
                } else if (!firstValue.contains(".") && !firstValue.equals("0")){
                    firstValue += value;
                }
            } else {
                if (firstValue.equals("0")) {
                    firstValue = value;
                } else {
                    firstValue += value;
                }
            }
        } else {
            isFirst = false;
            // set second value
            if (value.equals("0") && secondValue.equals("0")) {
                secondValue = value;
            } else if (value.equals(".")) {
                if (!secondValue.contains(".") && secondValue.equals("0")) {
                    secondValue = "0" + value;
                } else if (!secondValue.contains(".") && !secondValue.equals("0")){
                    secondValue += value;
                }
            } else {
                if (secondValue.equals("0")) {
                    secondValue = value;
                } else {
                    secondValue += value;
                }
            }
        }
    }

    public void removeLast() {
        if (isFirst) {
            if (!firstValue.equals("")) {
                firstValue = firstValue.substring(0, firstValue.length() - 1);
                if (firstValue.equals("")) {
                    firstValue = "0";
                }
            }
        } else {
            if (!secondValue.equals("")) {
                secondValue = secondValue.substring(0, secondValue.length() - 1);
                if (secondValue.equals("")) {
                    secondValue = "0";
                }
            }
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

    public String getTargetCurrency() {
        return targetCurrency;
    }
}
