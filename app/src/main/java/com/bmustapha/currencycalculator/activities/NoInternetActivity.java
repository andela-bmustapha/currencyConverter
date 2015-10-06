package com.bmustapha.currencycalculator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bmustapha.currencycalculator.R;
import com.bmustapha.currencycalculator.helpers.InternetChecker;

public class NoInternetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

        Button refreshButton = (Button) findViewById(R.id.refresh_internet_button);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InternetChecker.internetConnectionAvailable(getBaseContext())) {
                    // change to the main activity
                    Intent mainActivityIntent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(mainActivityIntent);
                    finish();
                } else {
                    Snackbar.make(v, "No internet...", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                }
            }
        });
    }

}
