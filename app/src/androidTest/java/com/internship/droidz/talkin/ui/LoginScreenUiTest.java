package com.internship.droidz.talkin.ui;

import android.os.SystemClock;
import android.support.test.rule.ActivityTestRule;

import com.internship.droidz.talkin.R;
import com.internship.droidz.talkin.ui.activity.login.LoginActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Koroqe on 09-Mar-17.
 */

public class LoginScreenUITest {

    public static final String EMAIL_TO_BE_TYPED = "test222test@test.com";
    public static final String PASSWORD_TO_BE_TYPED = "222222JJii";

    public static final String INVALID_EMAIL_TO_BE_TYPED = "test.com";
    public static final String INVALID_PASSWORD_TO_BE_TYPED = "222";


    public void performValidSignIn() {

        onView(withId(R.id.emailEditText)).perform(click(), replaceText(EMAIL_TO_BE_TYPED));
        onView(withId(R.id.passwordEditText)).perform(click(), replaceText(PASSWORD_TO_BE_TYPED));
        onView(withId(R.id.signInButton)).perform(click());
    }

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);

    @Test
    public void checkAuthorisation() {

        performValidSignIn();

        SystemClock.sleep(5000);
    }

    @Test
    public void checkAuthorisationWithInvalidPasswordAndEmail() {

        onView(withId(R.id.emailEditText)).perform(click(), replaceText(INVALID_EMAIL_TO_BE_TYPED));
        onView(withId(R.id.passwordEditText)).perform(click(), replaceText(INVALID_PASSWORD_TO_BE_TYPED));
        onView(withId(R.id.signInButton)).perform(click());

        SystemClock.sleep(3000);
    }


    @Test
    public void progressBarShouldBeShownDuringWebRequest() {

        performValidSignIn();

        onView(withId(R.id.progress_bar_login)).check(matches(isDisplayed()));
    }

    @Test
    public void signInButtonShouldBeEnabledIfEmailAndPasswordTyped() {

        onView(withId(R.id.emailEditText)).perform(click(), replaceText(EMAIL_TO_BE_TYPED));
        onView(withId(R.id.passwordEditText)).perform(click(), replaceText(PASSWORD_TO_BE_TYPED));

        onView(withId(R.id.signInButton)).check(matches(isClickable()));
    }
}
