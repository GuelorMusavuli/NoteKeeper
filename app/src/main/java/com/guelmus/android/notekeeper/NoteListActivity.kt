package com.guelmus.android.notekeeper

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
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
            startActivity(Intent(this, NoteActivity::class.java))
        }

        //Toggle(open and close) the navigation drawer on hamburger's click
        // at the left-edge of the toolbar. The strings passed in are provided for screen reader
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()//let the toggle knows whether the current state of the navDrawer is opened or closedg


    }

    /**
     *If the drawer is open on back button pressed, close it.
     * Otherwise, close the activity
     * */
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    /**
     * Notify the Adapter that the data might've changed when the user goes back
     * to the NoteListActivity, so as to refresh t that's being displayed*/
    override fun onResume() {
        super.onResume()
        listItems_rv.adapter?.notifyDataSetChanged()
    }
}