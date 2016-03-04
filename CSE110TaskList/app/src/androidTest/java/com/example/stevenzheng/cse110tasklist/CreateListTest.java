package com.example.stevenzheng.cse110tasklist;

import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigInteger;
import java.security.SecureRandom;

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
public class CreateListTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    private MainActivity mActivity = null;

    public String getRandomID() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(60, random).toString(32);
    }

    @Before
    public void setActivity() {
        mActivity = mActivityRule.getActivity();
    }

    // Given that I am logged in
    // When I click Create Task List
    // Then I should be in a screen allowing me to create lists
   @Test
   public void testCreateList() throws InterruptedException {
       onView(withId(R.id.B_signInForm)).perform(click());
       String testEmail = "cse2@cse.com";
       String testPass = "cse110";

       // sign in with test credentials
       onView(withId(R.id.TextField_email)).perform(typeText(testEmail));
       onView(withId(R.id.TextField_password)).perform(typeText(testPass), ViewActions.closeSoftKeyboard());

       // click button to sign in
       onView(withId(R.id.B_signIn)).perform(click());

       // delay to let activity load and in case of slow internet
       sleep(4000);
       onView(withId(R.id.B_createNewList)).perform(click());

       // generate random task details
       String listName = getRandomID();
       String listPass = getRandomID();

       sleep(4000);
       // feed random data into task details
       onView(withId(R.id.TextField_taskListName)).perform(typeText(listName));
       onView(withId(R.id.TextField_password)).perform(typeText(listPass), ViewActions.closeSoftKeyboard());

       onView(withId(R.id.B_create)).perform(click());
    }

}