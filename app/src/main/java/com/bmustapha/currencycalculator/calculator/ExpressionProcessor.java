package com.bmustapha.currencycalculator.calculator;

import com.bmustapha.currencycalculator.helpers.ExchangeRateHelper;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by andela on 10/15/15.
 */
public class ExpressionProcessor {

    private ArrayList<Double> operands;
    private ArrayList<String> operators;
    private ArrayList<String> currencies;
    private ArrayList<Double> convertedValues;
    private String targetCurrency;

    public ExpressionProcessor(ArrayList<String> currencies, ArrayList<Double> operands, ArrayList<String> operators, String targetCurrency) {
        this.currencies = currencies;
        this.operands = operands;
        this.operators = operators;
        this.targetCurrency = targetCurrency;
        this.convertedValues = new ArrayList<>();
    }

    public Double calculate() {
        convertValues();
        return performCalculations();
    }

    private Double performCalculations() {
        Double leftOperand = null;
        Double rightOperand = null;

        for (int x = 0; x < convertedValues.size(); x++) {
            if (leftOperand == null) {
                leftOperand = convertedValues.get(x);
                continue;
            }
            rightOperand = convertedValues.get(x);
            leftOperand = doCalculation(leftOperand, rightOperand, operators.get(x - 1));
        }
        return leftOperand;
    }

    private Double doCalculation(Double leftOperand, Double rightOperand, String operator) {
        Double result = null;
        switch (operator) {
            case "+":
                result = add(leftOperand, rightOperand);
                break;
            case "-":
                result = subtract(leftOperand, rightOperand);
                break;
            case "\u00D7":
                result = multiply(leftOperand, rightOperand);
                break;
            case "\u00F7":
                result = divide(leftOperand, rightOperand);
                break;
        }
        return result;
    }

    private Double divide(Double leftOperand, Double rightOperand) {
        return leftOperand / rightOperand;
    }

    private Double multiply(Double leftOperand, Double rightOperand) {
        return leftOperand * rightOperand;
    }

    private Double subtract(Double leftOperand, Double rightOperand) {
        return leftOperand - rightOperand;
    }

    private Double add(Double leftOperand, Double rightOperand) {
        return leftOperand + rightOperand;
    }

    private void convertValues() {
        // loop through the currencies arrayList
        for (int x = 0; x < currencies.size(); x++) {
            convertedValues.add(getConvertedValue(operands.get(x), currencies.get(x), targetCurrency));
        }
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
}
