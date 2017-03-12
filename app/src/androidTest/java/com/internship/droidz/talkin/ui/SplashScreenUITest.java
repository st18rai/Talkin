package com.internship.droidz.talkin.ui;

import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;

import com.internship.droidz.talkin.ui.activity.splash.SplashActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Koroqe on 23-Jan-17.
 */

public class SplashScreenUITest {

    @Rule
    public ActivityTestRule<SplashActivity> mActivityRule = new ActivityTestRule<>(
            SplashActivity.class);

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void finishActivityShouldBeCalledAfter3000ms() {

        SystemClock.sleep(3100);
        assertTrue(mActivityRule.getActivity().isFinishing());
    }

}