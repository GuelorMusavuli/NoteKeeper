package com.guelmus.android.notekeeper

import org.jetbrains.annotations.TestOnly
import org.junit.Assert.*

class DataManagerTest{

    @TestOnly
    fun addNote(){
        
        //Variables for each of the parameters
        val course = DataManager.courses["android_async"]!!
        val noteTitle = "This is a test note"
        val noteText = "This is the body of the test note"
        
        //Get back the note based on the index that addNote() returns


        /**
         * Test to see whether addNote() works correctly by checking if the value of the note
         * we get back  based on the index returned by the function, is as expected.
         * In other words,it has the course, noteTitle, and noteText that we passed into addNote().
         * 0r check if the course for the specified note corresponds to the course that
         * we added with the function addNote()
         * */
        val index = DataManager.addNote(course, noteTitle, noteText)
        val note = DataManager.notes[index]
        assertEquals(course, note.course)
        assertEquals(noteTitle, note.noteTitle)
        assertEquals(noteText, note.noteContent)


        


    }
}