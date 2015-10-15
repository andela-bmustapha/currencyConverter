package com.bmustapha.currencycalculator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bmustapha.currencycalculator.R;
import com.bmustapha.currencycalculator.calculator.CalculatorBrain;
import com.bmustapha.currencycalculator.fragments.ScreenFragment;
import com.bmustapha.currencycalculator.helpers.ExchangeRateHelper;
import com.bmustapha.currencycalculator.helpers.InternetChecker;
import com.bmustapha.currencycalculator.interfaces.KeyPadClicked;
import com.bmustapha.currencycalculator.interfaces.SpinnerItemSelected;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity implements KeyPadClicked, SpinnerItemSelected {


    private ScreenFragment screenFragment;
    private CalculatorBrain calculatorBrain;
    NumberFormat formatter = new DecimalFormat("#0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check for internet connect and exit if no internet connection is found
        if (!InternetChecker.internetConnectionAvailable(this)) {
            // change activity to no internet activity
            Intent noInternetIntent = new Intent(this, NoInternetActivity.class);
            startActivity(noInternetIntent);
            finish();
        }

        // get instance of the screen fragment
        screenFragment = (ScreenFragment) getSupportFragmentManager().findFragmentById(R.id.top_screen_fragment);
        calculatorBrain = new CalculatorBrain();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.action_top_conversion:
                showTopConversionRates();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showTopConversionRates() {
        if (ExchangeRateHelper.isRateSet()) {
            Intent topRatesIntent = new Intent(this, TopConversionActivity.class);
            startActivity(topRatesIntent);
        } else {
            Toast.makeText(this, "Rates unavailable!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void sendValue(String value) {
        calculatorBrain.setEnteredValue(value);
        screenFragment.displayNumber(calculatorBrain.getCurrentValue());
    }

    @Override
    public void clearInput() {
        calculatorBrain.reset();
        screenFragment.reset();
    }

    @Override
    public void removeLast() {
        calculatorBrain.removeLast();
        screenFragment.displayNumber(calculatorBrain.getCurrentValue());
    }

    @Override
    public void performCalculation() {
        calculate();
        screenFragment.setHistory(calculatorBrain.getHistory());
    }

    private void calculate() {
        try {
            screenFragment.displayNumber(formatter.format(calculatorBrain.performCalculation()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setOperator(String operator) {
        // check if both operands are set and perform calculation if true
        calculatorBrain.setOperator(operator);
        screenFragment.setHistory(calculatorBrain.getHistory());
    }

    @Override
    public void setBaseCurrency(String baseCurrency) {
        calculatorBrain.setBaseCurrency(baseCurrency);
    }

    @Override
    public void setTargetCurrency(String targetCurrency) {
        calculatorBrain.setTargetCurrency(targetCurrency);
        screenFragment.setTargetCurrencyScreen(targetCurrency);
    }
}
