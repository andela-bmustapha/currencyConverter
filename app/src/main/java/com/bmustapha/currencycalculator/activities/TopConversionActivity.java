package com.bmustapha.currencycalculator.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.bmustapha.currencycalculator.R;
import com.bmustapha.currencycalculator.adapters.TopConversionRatesAdapter;
import com.bmustapha.currencycalculator.helpers.ExchangeRateHelper;
import com.bmustapha.currencycalculator.models.CurrencyRate;

import java.util.ArrayList;

public class TopConversionActivity extends AppCompatActivity {

    private ArrayList<CurrencyRate> topList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_conversion);

        getTopTen();

        // get view objects
        ListView listView = (ListView) findViewById(R.id.top_conversion_rates_list);

        // create adapter
        TopConversionRatesAdapter topConversionRatesAdapter = new TopConversionRatesAdapter(this, topList);
        listView.setAdapter(topConversionRatesAdapter);
    }

    private void getTopTen() {
        int count = 0;
        topList = new ArrayList<>();
        while (count < 10) {
            topList.add(ExchangeRateHelper.topCurrenciesRates.get(count));
            count++;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_top_conversion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
