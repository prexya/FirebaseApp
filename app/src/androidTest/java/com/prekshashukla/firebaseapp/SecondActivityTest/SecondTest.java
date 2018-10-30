package com.prekshashukla.firebaseapp.SecondActivityTest;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;
import com.prekshashukla.firebaseapp.activities.MainActivity;
import com.prekshashukla.firebaseapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * test class for first activity and assert that button click navigates to proper destination
 *
 * Created by preksha-pc on 6/29/2017.
 */
@RunWith(AndroidJUnit4.class)
public class SecondTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void validateSecondActivity() {
        String email = "preksha@gmail.com";
        String password = "qwerty12";

        //find the email edit text and type in the first name
        onView(ViewMatchers.withId(R.id.activity_main_username)).perform(typeText(email), closeSoftKeyboard());

        //find the password edit text and type in the last name
        onView(withId(R.id.activity_main_password)).perform(typeText(password), closeSoftKeyboard());

        //click the signin button
        onView(withId(R.id.activity_main_login)).perform(click());
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password);
        onView(withId(R.id.activity_feed_view_profile_button))
                .check(matches(withText((R.string.go_to_profile))));
    }

}
