package com.bmustapha.currencycalculator.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bmustapha.currencycalculator.R;


public class ScreenFragment extends Fragment {

    private TextView currentComputationScreen;
    private TextView targetCurrencyScreen;
    private TextView historyScreen;

    public ScreenFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screen, container, false);


        targetCurrencyScreen = (TextView) view.findViewById(R.id.target_currency_screen);
        currentComputationScreen = (TextView) view.findViewById(R.id.bottom_screen);
        historyScreen = (TextView) view.findViewById(R.id.history_screen);

        return view;
    }

    public void displayNumber(String currentValue) {
        currentComputationScreen.setText(currentValue);
    }

    public void setTargetCurrencyScreen(String currency) {
        targetCurrencyScreen.setText(currency);
    }

    public void setHistory(String value) {
        if (!value.contains("null")) {
            if (containsOperator(value)) {
                // replace entire text
                historyScreen.setText(value);
            } else {
                historyScreen.append(value);
            }
        }
    }

    public void reset() {
        currentComputationScreen.setText("0");
        historyScreen.setText("");
    }

    private boolean containsOperator(String value) {
        return value.contains("+") || value.contains("-") || value.contains("\u00D7") || value.contains("\u00F7");
    }
}
