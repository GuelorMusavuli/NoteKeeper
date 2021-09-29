package com.guelmus.android.notekeeper

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.*
import org.hamcrest.Matchers.*
import java.util.stream.IntStream.range

/**
 * Test case to verify whether the iteration through notes by tapping on the next menu item
 * at the toolbar behaves as expected.
 * */
@RunWith(AndroidJUnit4::class)
class NextThroughNotesTest {

    @Rule @JvmField //launch this NoteListActivity that will be tested
    val noteListActivity = ActivityScenarioRule(NoteListActivity::class.java)

    @Test
    fun nextThroughNotes() {

        //Select the first note in the RecyclerView within NoteListActivity
        onData(allOf(instanceOf(NoteInfo::class.java), equalTo(DataManager.notes[0]))).perform(click())

        //Walk through that list of notes verifying that the expected values for each note
        // are being displayed within the views in MainActivity.
        for (index in range(0, DataManager.notes.lastIndex)){

            val note = DataManager.notes[index] //note at the position
            onView(withId(R.id.spinnerCourses)).check(matches(withSpinnerText(note.course?.courseTitle)))
            onView(withId(R.id.textNoteTitle)).check(matches(withText(note.noteTitle)))
            onView(withId(R.id.textNoteText)).check(matches(withText(note.noteContent)))

            //Check the state of the next menu item after all the values are displayed
            // As long as we're not on the last note, hence the next menu item is enabled.
            onView(allOf(withId(R.id.action_next), isEnabled())).perform(click())
        }

        //Check if the next menu item is disabled. on the last note
        onView(withId(R.id.action_next)).check(matches(isEnabled()))
    }
}






