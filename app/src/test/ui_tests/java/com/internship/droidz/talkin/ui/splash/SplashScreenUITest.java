package com.internship.droidz.talkin.uitests.splash;

import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;

/**
 * Created by Koroqe on 23-Jan-17.
 */

public class SplashScreenUITest {

    @Rule
    public ActivityTestRule<SplashScreen> mActivityRule = new ActivityTestRule<>(
            SplashScreen.class);

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void onCreate() throws Exception {
        Espresso.onView(withId(R.id.activity_splash_screen)).check(doesNotExist);
    }

}