package com.guelmus.android.notekeeper

import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class DataManagerTest{

    /**
     * Before each test in this class run, completely empty out
     * the DataManager's notes collection, and initialize it with a starting set of notes;
     * to ensure test reliability and consistency.
     *
     * */
    @Before
    fun setUp() {
        DataManager.notes.clear()
        DataManager.initializeCourse()
    }

    /** Test case for adding note operation */
    @Test
    fun addNote(){

        //Variables for each of the parameters
        val course = DataManager.courses["android_async"]!!
        val noteTitle = "This is a test note"
        val noteText = "This is the body of the test note"

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

    /**Test whether the findNote() works perfectly in a particular edge case
     * it confirms if we creates two notes that are similar,
     * that findNotes() still return the correct note*/
    @Test
    fun findSimilarNotes() {

        val course = DataManager.courses["android_async"]!!
        val noteTitle = "This is a test note"
        val noteText1 = "This is the body of the test note"
        val noteText2 = "This is the body of the second test note"

        //Capture the indexes of the notes added
        val index1 = DataManager.addNote(course, noteTitle, noteText1)
        val index2 = DataManager.addNote(course, noteTitle, noteText2)

        //Get the reference to the first  and second notes, and verify if the returned indexes
        // are the correct indexes for the notes added
        val note1 = DataManager.findNote(course, noteTitle, noteText1)
        val foundIndex1 = DataManager.notes.indexOf(note1)
        assertEquals(index1, foundIndex1)

        val note2 = DataManager.findNote(course, noteTitle, noteText2)
        val foundIndex2 = DataManager.notes.indexOf(note2)
        assertEquals(index2, foundIndex2)

    }

}