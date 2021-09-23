package com.guelmus.android.notekeeper



import androidx.test.espresso.Espresso.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith



/**
 * The test will take care of launching NoteListActivity,
 * it'll then click on the floating action button to display MainActvity,
 * and then when the MainActivity is display, it'll enter in values for the note's title
 * and the note's text.
 */

@RunWith(AndroidJUnit4::class)
class CreateNewNoteTest{

    @Rule  @JvmField//Ensure that NoteListActivity is up and running to launch the test
    val noteListActivity = ActivityScenarioRule(MainActivity::class.java)

    @Test //Verify whether the app properly handles the creation of the new note
    fun createNewNote(){
        //Input variables for the test to run
        val noteTitle = "Test note title"
        val noteText = "This is the body of our test note"

        //locate the float button and click on it to launch MainActivity
        onView(withId(R.id.fab)).perform(click())

        //Interact with the MainActivity to handle the note creation
        onView(withId(R.id.textNoteTitle)).perform(typeText(noteTitle))
        onView(withId(R.id.textNoteText)).perform(typeText(noteText))

    }
}