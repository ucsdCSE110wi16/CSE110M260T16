package com.example.stevenzheng.cse110tasklist;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class UnitTest {

    @Before
    public void setActivity() {}

    @Test
    public void testValidPassword() throws InterruptedException {
        String badPassword  = "short";
        String goodPassword = "thispasswordislong";

        assertThat(isBadPassword(badPassword), is(true));
        assertThat(isBadPassword(goodPassword), is(false));
    }

    @Test
    public void testValidEmail() throws InterruptedException {
        String badEmail  = "bad.e@mail";
        String goodEmail = "email@example.com";

        assertThat(isBadEmail(badEmail), is(true));
        assertThat(isBadEmail(goodEmail), is(false));
    }

    private boolean isBadPassword (String password) {
        return (password.length() < 6);
    }

    private boolean isBadEmail(String email) {
        if (!email.contains(".") || !email.contains("@"))
            return true;
        return (email.lastIndexOf(".") <= email.lastIndexOf("@"));
    }

}