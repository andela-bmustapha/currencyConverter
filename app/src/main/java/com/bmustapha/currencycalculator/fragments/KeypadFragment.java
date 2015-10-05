package com.bmustapha.currencycalculator.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bmustapha.currencycalculator.R;
import com.bmustapha.currencycalculator.interfaces.KeyPadClicked;

/**
 * Created by tunde on 9/28/15.
 */
public class KeypadFragment extends Fragment {

    KeyPadClicked callBack;
    private View view;

    public KeypadFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_keypad, container, false);

        // put all buttons in an array
        int[] numberButtons = {
                R.id.number_1, R.id.number_2, R.id.number_3, R.id.number_4,
                R.id.number_5, R.id.number_6, R.id.number_7, R.id.number_8,
                R.id.number_9, R.id.number_0, R.id.decimal_point_button
        };

        int[] specialButtons = {
                R.id.addition_button, R.id.minus_button, R.id.divide_button,
                R.id.times_button, R.id.equal_to_button, R.id.clear_button, R.id.delete_button
        };

        // set click listeners for numeric buttons
        setNumericButtonsClickListener(numberButtons);
        // set click listeners for special buttons
        setSpecialButtonsClickListener(specialButtons);


        return view;
    }


    private void setNumericButtonsClickListener(int[] buttonArray) {
        for (final int button : buttonArray) {
            view.findViewById(button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBack.sendValue((String) ((Button) view).getText());
                }
            });
        }
    }

    private void setSpecialButtonsClickListener(int[] specialButtons) {
        for (final int button : specialButtons) {
            view.findViewById(button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (button) {
                        case R.id.clear_button:
                            callBack.clearInput();
                            break;
                        case R.id.delete_button:
                            callBack.removeLast();
                            break;
                        case R.id.equal_to_button:
                            callBack.performCalculation();
                            break;
                        default:
                            callBack.setOperator(String.valueOf(((Button) view).getText()));
                            break;
                    }
                }
            });
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            callBack = (KeyPadClicked) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}
