package es.unex.dinopedia.Activities;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.appcompat.app.AppCompatDelegate;
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
public class ModoNocheTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void limpiezaPrevia (){
        DinopediaDatabase.getInstance(getApplicationContext()).getUsuarioDao().deleteAll();
    }

    @Test
    public void modoNocheTest() {

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.bIniciarSesion), withText("Iniciar Sesion"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.frameLayout),
                                        0),
                                1),
                        isDisplayed()));
        materialButton.perform(click());

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.bConfirmar), withText("Confirmar"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialButton2.perform(click());

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.bCuenta), withText("Cuenta"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.frameLayout),
                                        0),
                                4),
                        isDisplayed()));
        materialButton3.perform(click());

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction switch_ = onView(
                allOf(withId(R.id.swModo), withText("Modo Noche"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        switch_.perform(click());

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Se comprueba que sea modo oscuro
        assertEquals(AppCompatDelegate.getDefaultNightMode(),2);

        pressBack();

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.bCuenta), withText("Cuenta"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.frameLayout),
                                        0),
                                4),
                        isDisplayed()));
        materialButton4.perform(click());

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction switch_2 = onView(
                allOf(withId(R.id.swModo), withText("Modo Noche"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        switch_2.perform(click());

        //Se comprueba que sea modo claro
        assertEquals(AppCompatDelegate.getDefaultNightMode(),1);

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

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.bCuenta), withText("Cuenta"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.frameLayout),
                                        0),
                                4),
                        isDisplayed()));
        materialButton5.perform(click());

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.bCerrarSesion), withText("Cerrar Sesion"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        materialButton6.perform(click());

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
