package com.guelmus.android.notekeeper

/**
 * Class that represent a course
 * */
data class CourseInfo (val courseId : String, val courseTitle : String) {
    override fun toString(): String {
        //Value to be displayed on the spinner
        return courseTitle
    }
}

/**
 * Class that represent a note associated with a course
 * */
data class NoteInfo(
    var course: CourseInfo? = null,
    var noteTitle: String? = "",
    var noteContent: String? = null,
)



