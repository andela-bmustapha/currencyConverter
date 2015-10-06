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
    private TextView historyScreen;
    private TextView targetCurrencyScreen;

    public ScreenFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screen, container, false);

        historyScreen = (TextView) view.findViewById(R.id.top_screen);
        targetCurrencyScreen = (TextView) view.findViewById(R.id.target_currency_screen);
        currentComputationScreen = (TextView) view.findViewById(R.id.bottom_screen);

        return view;
    }

    public void displayNumber(String currentValue) {
        currentComputationScreen.setText(currentValue);
    }

    public void setTargetCurrencyScreen(String currency) {
        targetCurrencyScreen.setText(currency);
    }

    public void setHistory(String entry) {
        String currentHistory = historyScreen.getText().toString();
        historyScreen.setText(currentHistory + " " + entry);
    }
}
