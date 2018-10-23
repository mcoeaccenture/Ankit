package pachouri.com.albumslist;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.pachouri.albumslist.albumslistmodule.activity.HomeActivity;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Ankit on 10/4/18.
 */

@RunWith(AndroidJUnit4.class)
public class HomeActivityInstrumentationTest {

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule(HomeActivity.class);

    @Test
    public void scrollToSpecificPositionAndClickTest() {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(ViewMatchers.withId(R.id.recyclerViewAlbumsList))
                .perform(RecyclerViewActions.actionOnItemAtPosition(12,
                        click()));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String itemElementText = mActivityRule.getActivity().getResources()
                .getString(R.string.test);
        onView(withText(itemElementText)).check(matches(isDisplayed()));
    }
}
