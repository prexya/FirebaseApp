package com.prekshashukla.firebaseapp.MainActivityTest;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;
import com.prekshashukla.firebaseapp.activities.FeedActivity;
import com.prekshashukla.firebaseapp.activities.MainActivity;
import com.prekshashukla.firebaseapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * test class to test that on button click of first activity, navigation to second page via intent works
 *
 * Created by preksha-pc on 6/29/2017.
 */
@RunWith(AndroidJUnit4.class)
public class MainLoginTest {

    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void clickLogin_showFeedScreen() {
        String email = "preksha@gmail.com";
        String password = "qwerty12";

        //find the email edit text and type in the first name
        onView(ViewMatchers.withId(R.id.activity_main_username)).perform(typeText(email), closeSoftKeyboard());

        //find the password edit text and type in the last name
        onView(withId(R.id.activity_main_password)).perform(typeText(password), closeSoftKeyboard());

        //click the signin button
        onView(withId(R.id.activity_main_login)).perform(click());

        //check if the feed screen is displayed by asserting that the view profile button is displayed
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password);
        intended(hasComponent(FeedActivity.class.getName()));

    }

}
