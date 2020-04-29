package com.issues.espressoidling

import android.content.ComponentName
import android.content.Context
import android.util.Log
import androidx.core.view.KeyEventDispatcher
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.ActivityAction
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.junit.After
import org.junit.Assert
import org.junit.Rule
import org.junit.Test


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainActivity2Test {

    private lateinit var mIdlingResource: IdlingResource

    @Rule
    @JvmField
    var mActivityRule: IntentsTestRule<MainActivity2> = IntentsTestRule(MainActivity2::class.java)

//    @Before
//    fun stubAllExternalIntents() {
//        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
//        // every test run. In this case all external Intents will be blocked.
//        intending(not(isInternal()))
//            .respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))
//    }

    @Test
    fun useAppContext() {
//        Intents.init()
        ActivityScenario.launch(MainActivity2::class.java).use({ scenario ->
            Log.d("+++", "state: " + scenario.getState())
            scenario.onActivity(ActivityAction<MainActivity2> { activity ->
                mIdlingResource = activity.getIdlingResource()
                // To prove that the test fails, omit this call:
                //                    IdlingRegistry.getInstance().register(mIdlingResource);
            })
            Assert.assertTrue(scenario.getState() == Lifecycle.State.RESUMED)
            Log.d("+++", "" + MainActivity2::class.java.getName())
            intended(hasComponent(com.issues.espressoidling.MainActivity2::class.java.name))
            Log.d("+++", "0")
            Espresso.onView(withText("Button")).perform(ViewActions.click())
            Log.d("+++", "1")
            Espresso.onView(withText("Sleeping...")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            Log.d("+++", "2")
            IdlingRegistry.getInstance().register(mIdlingResource)
            Log.d("+++", "3")
            Espresso.onView(withText("Button")).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            Log.d("+++", "4")
//            Intents.release()
        })
    }

    @After
    fun unregisterIdlingResource() {
        Log.d("+++", "After: unregisterIdlingResource")
        IdlingRegistry.getInstance().unregister(mIdlingResource)
    }
}