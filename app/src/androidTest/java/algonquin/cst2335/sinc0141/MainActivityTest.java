package algonquin.cst2335.sinc0141;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import algonquin.cst2335.sinc0141.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        ViewInteraction appCompatEditText = onView( withId(R.id.myEditText));
        appCompatEditText.perform(replaceText("12345"), closeSoftKeyboard());

        ViewInteraction materialButton = onView( withId(R.id.loginBtn));
        materialButton.perform(click());

        ViewInteraction textView = onView( withId(R.id.textView));
        textView.check(matches(withText("You shall not pass!")));
    }
/*
Testing password inputs for when a password is wrong.
 */
    @Test
    public void testFindMissingUpperCase() {

        // Step 1: Find the EditText and perform replacing the text and closing the keyboard
        ViewInteraction editText = Espresso.onView(ViewMatchers.withId(R.id.myEditText));
        editText.perform(replaceText("12345"), closeSoftKeyboard());

        // Step 2: Find the button and perform clicking
        ViewInteraction button = Espresso.onView(ViewMatchers.withId(R.id.loginBtn));
        button.perform(click());

        // Step 3: Find the TextView and check the text
        ViewInteraction textView = Espresso.onView(ViewMatchers.withId(R.id.textView));
        textView.check(matches(withText("You shall not pass!")));
    }

    @Test
    public void testPasswordMissingDigit() {
        // Step 1: Find the EditText and perform replacing the text and closing the keyboard
        ViewInteraction editText = Espresso.onView(ViewMatchers.withId(R.id.myEditText));
        editText.perform(replaceText("Password#"), closeSoftKeyboard());

        // Step 2: Find the button and perform clicking
        ViewInteraction button = Espresso.onView(ViewMatchers.withId(R.id.loginBtn));
        button.perform(click());

        // Step 3: Find the TextView and check the text
        ViewInteraction textView = Espresso.onView(ViewMatchers.withId(R.id.textView));
        textView.check(matches(withText("Password should contain a digit.")));
    }

    @Test
    public void testPasswordMissingUpperCase() {
        // Step 1: Find the EditText and perform replacing the text and closing the keyboard
        ViewInteraction editText = Espresso.onView(ViewMatchers.withId(R.id.myEditText));
        editText.perform(replaceText("password1#"), closeSoftKeyboard());

        // Step 2: Find the button and perform clicking
        ViewInteraction button = Espresso.onView(ViewMatchers.withId(R.id.loginBtn));
        button.perform(click());

        // Step 3: Find the TextView and check the text
        ViewInteraction textView = Espresso.onView(ViewMatchers.withId(R.id.textView));
        textView.check(matches(withText("Password should contain an uppercase letter.")));
    }

    @Test
    public void testPasswordMissingLowerCase() {
        // Step 1: Find the EditText and perform replacing the text and closing the keyboard
        ViewInteraction editText = Espresso.onView(ViewMatchers.withId(R.id.myEditText));
        editText.perform(replaceText("PASSWORD1#"), closeSoftKeyboard());

        // Step 2: Find the button and perform clicking
        ViewInteraction button = Espresso.onView(ViewMatchers.withId(R.id.loginBtn));
        button.perform(click());

        // Step 3: Find the TextView and check the text
        ViewInteraction textView = Espresso.onView(ViewMatchers.withId(R.id.textView));
        textView.check(matches(withText("Password should contain a lowercase letter.")));
    }

    @Test
    public void testPasswordMissingSpecialCharacter() {
        // Step 1: Find the EditText and perform replacing the text and closing the keyboard
        ViewInteraction editText = Espresso.onView(ViewMatchers.withId(R.id.myEditText));
        editText.perform(replaceText("Password1"), closeSoftKeyboard());

        // Step 2: Find the button and perform clicking
        ViewInteraction button = Espresso.onView(ViewMatchers.withId(R.id.loginBtn));
        button.perform(click());

        // Step 3: Find the TextView and check the text
        ViewInteraction textView = Espresso.onView(ViewMatchers.withId(R.id.textView));
        textView.check(matches(withText("Password should contain a special character.")));
    }

    /*
    this tests a working password
     */
    public void testPasswordComplex() {
        // Step 1: Find the EditText and perform replacing the text and closing the keyboard
        ViewInteraction editText = Espresso.onView(ViewMatchers.withId(R.id.myEditText));
        editText.perform(replaceText("Password123#$*"), closeSoftKeyboard());

        // Step 2: Find the button and perform clicking
        ViewInteraction button = Espresso.onView(ViewMatchers.withId(R.id.loginBtn));
        button.perform(click());

        // Step 3: Find the TextView and check the text
        ViewInteraction textView = Espresso.onView(ViewMatchers.withId(R.id.textView));
        textView.check(matches(withText("Your password is complex enough.")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
