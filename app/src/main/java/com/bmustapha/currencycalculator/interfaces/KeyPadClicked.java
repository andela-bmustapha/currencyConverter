package com.bmustapha.currencycalculator.interfaces;

/**
 * Created by tunde on 9/30/15.
 */
public interface KeyPadClicked {
    void sendValue(String value);
    void clearInput();
    void removeLast();
    void performCalculation();
    void setOperator(String operator);
}
