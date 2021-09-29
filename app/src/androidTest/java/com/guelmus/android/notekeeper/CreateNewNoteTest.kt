package com.guelmus.android.notekeeper

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.Espresso.pressBack
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * The test will take care of launching NoteListActivity,
 * it'll then click on the floating action button to display MainActivity,
 * and then when the MainActivity is display, it'll enter in values for the note's title
 * and the note's text.
 */

@RunWith(AndroidJUnit4::class)
class CreateNewNoteTest{

    //Reference to a course that we want to select from the spinner
    private val course = DataManager.courses["android_async"]

    @Rule  @JvmField//Ensure that NoteListActivity is up and running to launch the test
    val noteListActivity = ActivityScenarioRule(NoteActivity::class.java)

    @Test //Verify whether the app properly handles the creation of the new note
    fun createNewNote(){
        //Input values to create the note
        val noteTitle = "Test note title"
        val noteText = "This is the body of our test note"

        //locate the float button and click on it to launch MainActivity
        onView(withId(R.id.fab)).perform(click())


        /**
         * Interact with the MainActivity to verify the note creation.
         *
         * Tap first on the spinner itself, that presents the selection and then select the course
         * of interest from the spinner and perform a click on that course. This logic is specific
         * to the spinners. Most other kinds of AdapterViews don't require that.
         *
         * Secondly, type texts in the noteTitle and noteText editTexts.
         * Lastly, verify when the back button is being pressed.
         */
        onView(withId(R.id.spinnerCourses)).perform(click())
        onData(allOf(instanceOf(CourseInfo::class.java), equalTo(course))).perform(click())
        onView(withId(R.id.textNoteTitle)).perform(typeText(noteTitle))
        onView(withId(R.id.textNoteText)).perform(typeText(noteText), closeSoftKeyboard())
        pressBack()


        //  Verifying the note creation behavior behind the UI interaction based on data,
        //  by checking if what we put in is actually what it returns.
        // E -expected value(what i put in) /A -actual value to check against(what i got out)
        val newlyCreatedNote = DataManager.notes.last() //note created
        assertEquals(course, newlyCreatedNote.course)
        assertEquals(noteTitle, newlyCreatedNote.noteTitle)
        assertEquals(noteText, newlyCreatedNote.noteContent)

    }
}