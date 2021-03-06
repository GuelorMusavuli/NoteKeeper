package com.guelmus.android.notekeeper

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.content_main.*

/** This class displays information for an individual,
 *  and allow the user to create a new one or modify it*/

class NoteActivity : AppCompatActivity() {
    //TAG for the log entries using kotlin reflection dependency
    private var tag = this::class.simpleName

    //Determine whether a note position was set or not
    private var notePosition = POSITION_NOT_SET

    private var isNewNote = false
    private var isCancelling = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        //Populate spinner with courses from DataManager
        val courseAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,//format the current selection
            DataManager.courses.values.toList()//collection of courses retrieve from the map
        ) //format the selections that are displayed within the drop-down list
        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCourses.adapter = courseAdapter

        /**
         * Restore saved notePosition from instance state when the activity is destroyed and then recreated.
        If it is the initial creation of the activity, get notePosition from intent extra,
        and if no notePosition has been passed in the extra, then set the notePosition
        to position not set.
         */
        notePosition =
            savedInstanceState?.getInt(NOTE_POSITION, POSITION_NOT_SET) ?:
            intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET )

        //if there is a notePosition, populate the screen views
        if (notePosition != POSITION_NOT_SET) {
            displayNote()
        } else {
            //Create an empty new note and add it to Data Manager, and then set
            // the position of the current note to the newly empty note created
                isNewNote = true
            DataManager.notes.add(NoteInfo())
            notePosition = DataManager.notes.lastIndex
        }
        Log.d(tag, "OnCreate")

        displayComments()

    }

    /**Save the instance state of notePosition when the activity is destroyed */
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putInt(NOTE_POSITION, notePosition)
    }

    /** Populate views in the main activity with notes from  intent extra*/
    private fun displayNote() {

        //Get note corresponding to the notePosition and set the note's title and text
        // to the string values contained in the note
        val note = DataManager.notes[notePosition]
        textNoteTitle.setText(note.noteTitle)
        textNoteText.setText(note.noteContent)

        //Select the appropriate course from the spinner
        val coursePosition = DataManager.courses.values.indexOf(note.course)
        spinnerCourses.setSelection(coursePosition)
    }

    /**
     * Methods to display the list of users comments on notes
     * */
    private fun displayComments() {
        //Populate the commentsList_rv with a collections of comments from the adapter
        val commentsAdapter = CommentRecyclerAdapter(this, DataManager.notes[notePosition])
        commentsList_rv?.layoutManager = LinearLayoutManager(this)
        commentsList_rv?.adapter = commentsAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_note, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item(menu) clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            //R.id.action_settings -> true
            R.id.action_reminder -> {
                ReminderNotification.notify(this,
                    DataManager.notes[notePosition],
                    notePosition
                )
                true
            }
            R.id.action_cancel -> {
                isCancelling = true
                finish()
                true
            }
            R.id.action_next -> {
                moveNext()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**Move t the next note on the screen */
    private fun moveNext() {
        saveNote()
        ++notePosition
        displayNote()

        //Trigger change to the menu item at runtime
        invalidateOptionsMenu()
    }

    /**Change menu state*/
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        //If user reaches the last notes, changes the appearance of the menu icon
        if (notePosition >= DataManager.notes.lastIndex) {

            Toast.makeText(this, "No more notes left!", Toast.LENGTH_SHORT).show()

            //Reference the menu to be changed
            val menuItem = menu?.findItem(R.id.action_next)
            if (menuItem != null) {
                //Change the next menu icon if at last index and disable click on it
                menuItem.icon = AppCompatResources.getDrawable(this, R.drawable.ic_block_white_24dp)
                menuItem.isEnabled = false
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    /** Save persistent data or notes when user is no longer interacting with this activity*/
    override fun onPause() {
        super.onPause()
        when {
            isCancelling -> {
                if(isNewNote)
                    DataManager.notes.removeAt(notePosition)
            }
            else -> saveNote()
        }
    }

    /**Save the notes from screen into the note list within DataManager*/
    private fun saveNote() {
        //Get current display note and bind views value with properties in the list
        val note = DataManager.notes[notePosition]
        note.noteTitle = textNoteTitle.text.toString()
        note.noteContent = textNoteText.text.toString()
        note.course = spinnerCourses.selectedItem as CourseInfo
        NoteKeeperAppWidget.sendRefreshBroadcast(this)
    }

}