package com.bmustapha.currencycalculator;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.TextView;

import com.bmustapha.currencycalculator.activities.MainActivity;
import com.bmustapha.currencycalculator.activities.NoInternetActivity;

/**
 * Created by andela on 10/6/15.
 */
public class NoInternetActivityTest extends ActivityInstrumentationTestCase2<NoInternetActivity> {


    public NoInternetActivityTest(){
        super(NoInternetActivity.class);
    }

    public void testActivityExists() {
        NoInternetActivity activity = getActivity();
        assertNotNull(activity);
    }

    /*
        As a user, I want to make some currency conversion using the app,
        but I have no internet, so I should see a page that tells me I have no internet
     */
    public void testNoInternetInfo() {
        NoInternetActivity activity = getActivity();
        TextView infoText = (TextView) activity.findViewById(R.id.no_internet_text_view);
        assertNotNull(infoText);
        String presumedValue = "No internet connection. Connect to the internet and click the refresh button";
        String actualValue = infoText.getText().toString();
        assertEquals(presumedValue, actualValue);
    }

    /*
        After seeing the no internet error, I want to be able to click on the refresh button,
        and the app should try to reconnect to the internet and show the calculator interface
     */
    public void testRefreshButton() {
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(MainActivity.class.getName(), null, false);
        final NoInternetActivity activity = getActivity();

        final Button refresh = (Button) activity.findViewById(R.id.refresh_internet_button);
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                refresh.performClick();
            }
        });
        MainActivity mainActivity = (MainActivity) getInstrumentation().waitForMonitor(activityMonitor);
        assertNotNull(mainActivity);
        assertEquals("Monitor for Converter has not been called", 1, activityMonitor.getHits());
        assertEquals("Activity is of wrong type", MainActivity.class, mainActivity.getClass());
        getInstrumentation().removeMonitor(activityMonitor);
        mainActivity.finish();
    }
}
