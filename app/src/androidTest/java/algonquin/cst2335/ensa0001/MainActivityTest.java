package algonquin.cst2335.ensa0001;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    /**
     * In this test case, 12345 passed into the edittext
     * and the button clicks by itself and finally
     * prompt a toast message that is "You shall not pass"
     */
    @Test
    public void mainActivityTest() {
        ViewInteraction appCompatEditText = onView(withId(R.id.passCheck));
        appCompatEditText.perform(replaceText("12345"));

        ViewInteraction materialButton = onView(withId(R.id.loginbtn));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches(withText("You shall not pass")));
    }
    /**
     * In this test case, password1232#$* passed into the edittext
     * and the button clicks by itself and finally
     * prompt a toast message that is "You shall not pass"
     */
    @Test
    public void testFindMissingUpperCase() {
        ViewInteraction appCompatEditText = onView(withId(R.id.passCheck));

        appCompatEditText.perform(replaceText("password1232#$*"));


        ViewInteraction materialButton = onView(withId(R.id.loginbtn));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches(withText("You shall not pass")));
    }

    /**
     * In this test case, PASS1232#$* passed into the edittext
     * and the button clicks by itself and finally
     * prompt a toast message that is "You shall not pass"
     */
    @Test
    public void testFindMissingLowerCase() {
        ViewInteraction appCompatEditText = onView(withId(R.id.passCheck));

        appCompatEditText.perform(replaceText("PASS1232#$*"));


        ViewInteraction materialButton = onView(withId(R.id.loginbtn));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches(withText("You shall not pass")));
    }

    /**
     * In this test case, Pass#$* passed into the edittext
     * and the button clicks by itself and finally
     * prompt a toast message that is "You shall not pass"
     */
    @Test
    public void testFindMissingNumber() {
        ViewInteraction appCompatEditText = onView(withId(R.id.passCheck));

        appCompatEditText.perform(replaceText("Pass#$*"));


        ViewInteraction materialButton = onView(withId(R.id.loginbtn));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches(withText("You shall not pass")));
    }

    /**
     * In this test case, Pass123 passed into the edittext
     * and the button clicks by itself and finally
     * prompt a toast message that is "You shall not pass"
     */
    @Test
    public void testFindMissingSpecialSymbol() {
        ViewInteraction appCompatEditText = onView(withId(R.id.passCheck));

        appCompatEditText.perform(replaceText("Pass123"));


        ViewInteraction materialButton = onView(withId(R.id.loginbtn));
        materialButton.perform(click());

        ViewInteraction textView = onView(withId(R.id.textView));
        textView.check(matches(withText("You shall not pass")));
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
