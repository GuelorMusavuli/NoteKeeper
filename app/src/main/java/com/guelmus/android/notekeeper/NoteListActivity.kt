package com.guelmus.android.notekeeper

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_note_list.*
import kotlinx.android.synthetic.main.content_note_list.*

class NoteListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        setSupportActionBar(toolbar)

        //Populate the listItems_rv with a collections of notes from the adapter
        // and set the position to display items
        listItems_rv.layoutManager = LinearLayoutManager(this)
        listItems_rv.adapter = NoteRecyclerAdapter(this, DataManager.notes)


        //Launch MainActivity as per the user selected specific note


        //Launch main activity without passed-in note
        fab.setOnClickListener {
            //Leads us to the Edit Note screen
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    /**Notify the ListView about the change made by adding a new note to the list*/
    override fun onResume() {
        super.onResume()


    }
}