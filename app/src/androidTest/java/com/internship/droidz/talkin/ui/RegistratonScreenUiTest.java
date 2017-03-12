package com.internship.droidz.talkin.ui;

import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;

import com.internship.droidz.talkin.R;
import com.internship.droidz.talkin.ui.activity.registration.RegistrationActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Koroqe on 09-Mar-17.
 */

public class RegistratonScreenUITest {

    public static final String EMAIL_TO_BE_TYPED = "test222test@test.com";
    public static final String PASSWORD_TO_BE_TYPED = "222222JJii";
    public static final String CONFIRM_PASSWORD_TO_BE_TYPED = "222222JJii";
    public static final String NAME_TO_BE_TYPED = "name";
    public static final String PHONE_TO_BE_TYPED = "222222222";
    public static final String SITE_TO_BE_TYPED = "www.test.com";

    @Rule
    public ActivityTestRule<RegistrationActivity> mActivityRule = new ActivityTestRule<>(
            RegistrationActivity.class);

    @Test
    public void checkRegistration() throws InterruptedException {

        onView(withId(R.id.emailEditTextReg)).perform(click(), replaceText(EMAIL_TO_BE_TYPED));
        onView(withId(R.id.passwordEditTextReg)).perform(click(), replaceText(PASSWORD_TO_BE_TYPED));
        onView(withId(R.id.confirmPasswordEditText)).perform(click(), replaceText(CONFIRM_PASSWORD_TO_BE_TYPED));
        onView(withId(R.id.nameEditText)).perform(click(), replaceText(NAME_TO_BE_TYPED), closeSoftKeyboard());
        onView(withId(R.id.phoneEditText)).perform(click(), typeText(PHONE_TO_BE_TYPED), closeSoftKeyboard());
        onView(withId(R.id.siteEditText)).perform(click(), replaceText(SITE_TO_BE_TYPED), closeSoftKeyboard());

        onView(withId(R.id.signUpButtonReg)).perform(scrollTo(), click());

        SystemClock.sleep(5000);
    }
}
