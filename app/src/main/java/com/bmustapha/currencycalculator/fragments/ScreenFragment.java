package com.bmustapha.currencycalculator.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bmustapha.currencycalculator.R;


public class ScreenFragment extends Fragment {

    private TextView bottomScreen;

    public ScreenFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_screen, container, false);

        bottomScreen = (TextView) view.findViewById(R.id.bottom_screen);
        bottomScreen.setText("0");

        return view;
    }

    public void displayNumber(String currentValue) {
        bottomScreen.setText(currentValue);
    }
}
