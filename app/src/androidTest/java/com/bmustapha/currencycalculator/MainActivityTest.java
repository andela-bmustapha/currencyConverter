package com.bmustapha.currencycalculator;

import android.test.ActivityInstrumentationTestCase2;

import com.bmustapha.currencycalculator.activities.MainActivity;

/**
 * Created by andela on 10/6/15.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTest(){
        super(MainActivity.class);
    }


    //test to see if activity exists
    public void testActivityExists() {
        MainActivity activity = getActivity();
        assertNotNull(activity);
    }
}
