package com.bmustapha.currencycalculator.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.bmustapha.currencycalculator.R;
import com.bmustapha.currencycalculator.helpers.ExchangeRateHelper;
import com.bmustapha.currencycalculator.interfaces.RatesFetchValidator;
import com.bmustapha.currencycalculator.interfaces.SpinnerItemSelected;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by tunde on 9/28/15.
 */
public class SpinnersFragment extends Fragment {

    SpinnerItemSelected spinnerInterface;
    ArrayAdapter<String> adapter;
    Spinner targetCurrencySpinner;
    Spinner baseCurrencySpinner;

    public SpinnersFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spinners, container, false);

        baseCurrencySpinner = (Spinner) view.findViewById(R.id.base_currency);
        targetCurrencySpinner = (Spinner) view.findViewById(R.id.target_currency);

        // baseCurrencySpinner.setDropDownViewResource(R.layout.spinner_dropdown_style);

        if (ExchangeRateHelper.list == null) {
            ExchangeRateHelper.list = new ArrayList<String>();
            getExchangeRates();
        } else {
            setAdapters();
        }

        return view;
    }

    public void getExchangeRates() {
        ExchangeRateHelper.getExchangeRates(new RatesFetchValidator() {
            @Override
            public void onFinish() {
                // loop through the json and populate the spinner list
                populateSpinners();
                // create list for to 10 currencies
                ExchangeRateHelper.createTopCurrenciesRate();
            }
        });
    }

    private void populateSpinners() {
        Iterator<String> iterator = ExchangeRateHelper.ratesObject.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            ExchangeRateHelper.list.add(key);
        }
        // sort the items in the list
        Collections.sort(ExchangeRateHelper.list);
        setAdapters();
    }

    private void setAdapters() {
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, ExchangeRateHelper.list);
        baseCurrencySpinner.setAdapter(adapter);
        targetCurrencySpinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        baseCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerInterface.setBaseCurrency(adapter.getItem(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        targetCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerInterface.setTargetCurrency(adapter.getItem(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            spinnerInterface = (SpinnerItemSelected) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}
