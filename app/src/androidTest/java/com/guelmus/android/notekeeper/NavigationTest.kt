package com.guelmus.android.notekeeper

/**
 * Annotation imports from JUnit
 */
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Test runner imports
 */
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Assert.*
import org.hamcrest.Matchers.*

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*

/**
 * Imports for testing NavigationDrawer and RecyclerView
 * Requires a contrib related gradle dependency:
 * implementation 'androidx.test.espresso:espresso-contrib:3.4.0'
 */

import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import com.jwhh.notekeeper.CourseRecyclerAdapter

/** Automated UI Test that incorporates interaction wih both the drawer navigation
 * and RecyclerView which are found in NoteListActivity*/

@RunWith(AndroidJUnit4::class)
class NavigationTest{

    @Rule @JvmField //launch the activity to be tested
    val noteListActivity = ActivityScenarioRule(NoteListActivity::class.java)


    /**
     * Verifying when the user switches from viewing the courses to viewing the notes
     * to make a selection within the RecyclerView, works correctly.
     * */
    @Test
    fun selectNoteAfterNavigationDrawerChange(){

        //Open the Navigation drawer and select courses option within it for the RecyclerView
        // to display a list of courses
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_courses))

        //Interact with  RecyclerView showing courses to select the first course that's being displayed
        val coursePosition = 0
        onView(withId(R.id.listItems_rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<CourseRecyclerAdapter.ViewHolder>(coursePosition, click())
        )

        //Test case to display a list of note and make a selection. Similar to the previous test
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_notes))
        val notePosition = 0
        onView(withId(R.id.listItems_rv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<NoteRecyclerAdapter.NoteViewHolder>(notePosition, click())
        )



    }





}