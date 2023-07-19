package algonquin.cst2335.sinc0141;

// textView myEditText loginBtn

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        // Delay execution for 500 milliseconds
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Set the password to "12345"
        onView(withId(R.id.myEditText))
                .perform(click())  // Click on the password field
                .perform(replaceText("12345"))  // Enter the password "12345"
                .perform(pressImeActionButton());  // Press the IME action button (e.g., "Done")

        // Click the "Login" button
        onView(withId(R.id.loginBtn))
                .perform(click());

        // Verify that the correct error message is displayed
        onView(withId(R.id.textView))
                .check(matches(withText("You shall not pass!")));
    }

    /**
     * Test case to check if the password has a missing uppercase letter.
     */
    @Test
    public void testFindMissingUppercase() {
        ViewInteraction appCompatEditText = onView(withId(R.id.myEditText));
        appCompatEditText.perform(replaceText("password123#$*"));  // Set the password with missing uppercase

        ViewInteraction materialButton = onView(withId(R.id.loginBtn));
        materialButton.perform(click());

        ViewInteraction textView = onView((withId(R.id.textView)));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Test case to check if the password has a missing lowercase letter.
     */
    @Test
    public void testFindMissingLowercase() {
        ViewInteraction appCompatEditText = onView(withId(R.id.myEditText));
        appCompatEditText.perform(replaceText("PASSWORD123#$*"));  // Set the password with missing lowercase

        ViewInteraction materialButton = onView(withId(R.id.loginBtn));
        materialButton.perform(click());

        ViewInteraction textView = onView((withId(R.id.textView)));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Test case to check if the password has a missing number.
     */
    @Test
    public void testFindMissingNumber() {
        ViewInteraction appCompatEditText = onView(withId(R.id.myEditText));
        appCompatEditText.perform(replaceText("Password#$*"));  // Set the password with missing number

        ViewInteraction materialButton = onView(withId(R.id.loginBtn));
        materialButton.perform(click());

        ViewInteraction textView = onView((withId(R.id.textView)));
        textView.check(matches(withText("You shall not pass!")));
    }

    /**
     * Test case to check if the password has a missing special character.
     */
    @Test
    public void testFindMissingSpecialCharacter() {
        ViewInteraction appCompatEditText = onView(withId(R.id.myEditText));
        appCompatEditText.perform(replaceText("Password123"));  // Set the password with missing special character

        ViewInteraction materialButton = onView(withId(R.id.loginBtn));
        materialButton.perform(click());

        ViewInteraction textView = onView((withId(R.id.textView)));
        textView.check(matches(withText("You shall not pass!")));
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