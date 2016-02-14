package com.example.stevenzheng.cse110tasklist;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static java.lang.Thread.*;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
// Turn all animation scales to off before running tests
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SignUpTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    private MainActivity mActivity = null;

    @Before
    public void setActivity() {
        mActivity = mActivityRule.getActivity();
    }

    // Given that I don't have an account
    // When I enter my credentials and create an account
    // Then I should be a registered and should be able to successfully log in
    @Test
    public void testSignUp() throws InterruptedException {
        onView(withId(R.id.B_signUp)).perform(click());
        String fName = "John";
        String lName = "Smith";
        String testPass = "password123";
        // Note: Since accounts can only be created one with an email, to repeat
        // this test change the email to something random
        String testEmail = "test123@test.com";

        // enter name
        onView(withId(R.id.TextField_firstName)).perform(typeText(fName));
        onView(withId(R.id.TextField_lastName)).perform(typeText(lName));
        // sign in with test credentials
        onView(withId(R.id.TextField_email)).perform(typeText(testEmail));
        onView(withId(R.id.TextField_password)).perform(typeText(testPass));

        // click button to sign in
        onView(withId(R.id.B_signIn)).perform(click());
        // TODO: find non temporary solution to time delay
        sleep(2000);
        onView(withId(R.id.Text_name)).check(matches(withText(fName)));
    }

}