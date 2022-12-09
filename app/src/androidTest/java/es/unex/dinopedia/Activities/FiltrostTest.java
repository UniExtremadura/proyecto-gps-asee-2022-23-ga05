package es.unex.dinopedia.Activities;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotEquals;

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
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.unex.dinopedia.R;
import es.unex.dinopedia.roomdb.DinopediaDatabase;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class FiltrostTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void limpiezaPrevia (){
        DinopediaDatabase.getInstance(getApplicationContext()).getUsuarioDao().deleteAll();
    }

    @Test
    public void filtrostTest2() {

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.enciclopedia), withContentDescription("Enciclopedia"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottomNavigationView),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //Accedo a un elemento
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.my_recycler_view),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(2, click()));

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Obtengo el textView
        ViewInteraction textView = onView(
                allOf(withId(R.id.tNombre),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        pressBack();

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.bAplicar), withText("Aplicar"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.frameLayout),
                                        0),
                                2),
                        isDisplayed()));
        materialButton.perform(click());

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.batalla), withContentDescription("Batalla"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottomNavigationView),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction bottomNavigationItemView3 = onView(
                allOf(withId(R.id.enciclopedia), withContentDescription("Enciclopedia"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottomNavigationView),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView3.perform(click());

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Accedo al mismo elemento
        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.my_recycler_view),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)));
        recyclerView2.perform(actionOnItemAtPosition(2, click()));

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        //Obtengo el segundo textview
        ViewInteraction textView2 = onView(
                allOf(withId(R.id.tNombre),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));

        //comparo los textview
        assertNotEquals(textView,textView2);

    }

    @After
    public void limpiezaPost (){
        DinopediaDatabase.getInstance(getApplicationContext()).getUsuarioDao().deleteAll();
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
