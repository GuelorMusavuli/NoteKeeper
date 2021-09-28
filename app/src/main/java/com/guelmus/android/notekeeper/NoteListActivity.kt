package com.guelmus.android.notekeeper

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.jwhh.notekeeper.CourseRecyclerAdapter
import kotlinx.android.synthetic.main.activity_note_list.*
import kotlinx.android.synthetic.main.content_note_list.*



class NoteListActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    //Fields that associate note layout manager and RecyclerViewAdapter with the Rv
    private val noteLayoutManager by lazy{ LinearLayoutManager(this) }
    private val noteRecyclerAdapter by lazy { NoteRecyclerAdapter(this, DataManager.notes) }

    //Fields that associate course layout manager and RecyclerViewAdapter with the Rv
    private val courseLayoutManager by lazy{ GridLayoutManager(this, 2) }
    private val courseRecyclerAdapter by lazy {
        CourseRecyclerAdapter(this, DataManager.courses.values.toList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)

        setSupportActionBar(toolbar)

        displayNotes()

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
        toggle.syncState()//let the toggle knows whether the current state of the navDrawer is opened or closed

        //Set this activity as the Listener for handling NavView's options selection
        nav_view.setNavigationItemSelectedListener(this)

    }

    /**Method for handling the navigation view 's options click*/
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_notes ->{
                displayNotes()
            }
            R.id.nav_courses ->{
                displayCourses()
            }
            R.id.nav_share ->{

            }
            R.id.nav_send ->{

            }

        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    /**
     * Methods to display the list of notes
     * in response to the user's NavigationView selection
     * */
    private fun displayNotes() {

        //Populate the listItems_rv with a collections of notes from the adapter
        // and set the position to display items
        listItems_rv.layoutManager = noteLayoutManager
        listItems_rv.adapter = noteRecyclerAdapter

        //Set the notes option within the NavView highlighted at the first  launch of the app.
        nav_view.menu.findItem(R.id.nav_notes).isChecked = true
    }

    /**
     * Methods to display the list of courses
     * in response to the user's NavigationView selection
     * */
    private fun displayCourses() {

        //Populate the listItems_rv with a collections of notes from the adapter
        // and set the position to display items
        listItems_rv.layoutManager = courseLayoutManager
        listItems_rv.adapter = courseRecyclerAdapter

        //Always set the courses option within the NavView to checked any time this method is called
        nav_view.menu.findItem(R.id.nav_courses).isChecked = true
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