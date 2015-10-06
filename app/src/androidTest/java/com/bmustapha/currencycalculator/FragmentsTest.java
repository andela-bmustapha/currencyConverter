package com.bmustapha.currencycalculator;

import android.test.ActivityInstrumentationTestCase2;

import com.bmustapha.currencycalculator.activities.MainActivity;
import com.bmustapha.currencycalculator.fragments.KeypadFragment;
import com.bmustapha.currencycalculator.fragments.ScreenFragment;
import com.bmustapha.currencycalculator.fragments.SpinnersFragment;

/**
 * Created by andela on 10/6/15.
 */
public class FragmentsTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private KeypadFragment keypadFragment = new KeypadFragment();
    private ScreenFragment screenFragment = new ScreenFragment();
    private SpinnersFragment spinnersFragment = new SpinnersFragment();

    public FragmentsTest() {
        super(MainActivity.class);
    }

    // test if keypad fragment is null or created successfully
    public void testKeypadFragmentNotNull() {
        assertNotNull(keypadFragment);
    }

    // test if screen fragment is null or created successfully
    public void testScreenFragmentNotNull() {
        assertNotNull(screenFragment);
    }

    // test if spinner fragment is null or created successfully
    public void testSpinnerFragmentNotNull() {
        assertNotNull(spinnersFragment);
    }
}
