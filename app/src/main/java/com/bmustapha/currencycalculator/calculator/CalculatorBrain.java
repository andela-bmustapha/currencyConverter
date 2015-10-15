package com.bmustapha.currencycalculator.calculator;

import com.bmustapha.currencycalculator.config.Constants;

import java.util.ArrayList;

/**
 * Created by tunde on 9/28/15.
 */
public class CalculatorBrain {

    private final String DECIMAL_POINT = ".";
    private ArrayList<Double> operands;


    private ArrayList<String> operators;
    private ArrayList<String> currencies;
    private String enteredValue;

    private String choiceCurrency;
    private String targetCurrency;
    private Double answer;

    private boolean flag = false;


    public CalculatorBrain() {
        resetLists();
    }

    public void setEnteredValue(String value) {
        enteredValue = getFormattedValue(enteredValue, value);
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
            enteredValue = (enteredValue.substring(0, enteredValue.length() - 1).equals("")) ? "0" : enteredValue.substring(0, enteredValue.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCurrentValue() {
        return enteredValue;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.choiceCurrency = baseCurrency;
    }

    public void setOperator(String operator) {
        try {
            if (Double.parseDouble(enteredValue) != 0) {
                operands.add(Double.parseDouble(enteredValue));
                operators.add(operator);
                String evaluatedOperator = (flag) ? targetCurrency : choiceCurrency;
                currencies.add(evaluatedOperator);
                enteredValue = null;
            }
            flag = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getHistory() {
        String history = "";
        // loop through the currencies arrayList to get build history string
        for (int x = 0; x < currencies.size(); x++) {
            history += (history.equals("")) ? currencies.get(x) + Constants.SCREEN_FORMATTER.format(operands.get(x)) : " " + currencies.get(x) + Constants.SCREEN_FORMATTER.format(operands.get(x));
            try {
                history += (operators.get(x) != null) ? " " + operators.get(x) : "";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return history;
    }

    public void reset() {
        enteredValue = null;
        answer = null;
        resetLists();
        flag = false;
    }

    public void resetLists() {
        operands = new ArrayList<>();
        operators = new ArrayList<>();
        currencies = new ArrayList<>();
    }


    public Double performCalculation() {
        if (enteredValue != null) {
            operands.add(Double.parseDouble(enteredValue));
            String evaluatedOperator = (flag) ? targetCurrency : choiceCurrency;
            currencies.add(evaluatedOperator);
        }

        if (operands.size() >= 2) {
            answer = new ExpressionProcessor(currencies, operands, operators, targetCurrency).calculate();
        }
        enteredValue = String.valueOf(answer);
        flag = true;
        return answer;
    }


    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }
}
