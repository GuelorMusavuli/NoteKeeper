package com.guelmus.android.notekeeper


/**
 * Singleton class that servers as a point of management for the instances of NoteKeeperData
 * */
object DataManager {

    //Property to hold a collection of courses.
    // The first param is used for lookups(will be looking the courses by the course ID)
    // and the second param is the value that will be stored ( courseInfo in this case)
    val courses = HashMap<String, CourseInfo>()

    //Property to hold a collection of notes associated with the course
    val notes = ArrayList<NoteInfo>()

    init {
        //Initialize the courses and notes whenever an instance of this class is created
        initializeCourse()
        initializeNotes()
    }

    /**This function will loop through the notes collection to find the note that has the passed values */
    fun addNote(courseInfo: CourseInfo, noteTitle : String, noteContent:String) : Int {
        val note = NoteInfo(courseInfo, noteTitle, noteContent)
        notes.add(note)
        return notes.lastIndex
    }

    /**Function that makes it easy to add a new note to the DataManager's collection following
     * the test-driven development testing technique, and return the index of that newly created note*/
    fun findNote(courseInfo: CourseInfo, noteTitle : String, noteContent:String) : NoteInfo? {
       for (note in notes) {
           if (courseInfo == note.course && noteTitle == note.noteTitle && noteContent == note.noteContent) {
               return note
           }
       }
       return null
    }



    fun initializeCourse(){

        var course = CourseInfo("android_intents", "Android Programming with Intent ")
        courses[course.courseId] = course //add course to the list

        course = CourseInfo("android_async", "Android Async Programming and Services ")
        courses[course.courseId] = course

        course = CourseInfo("Java Fundamentals : Java Language ", "java_lang")
        courses[course.courseId] = course

        course = CourseInfo("java_lang","Java Fundamentals : Java Language ")
        courses[course.courseId] = course

        course = CourseInfo(courseTitle = "Java Fundamentals: The Core Platform", courseId ="java_core")
        courses.set(course.courseId, course)

    }

    private fun initializeNotes() {
        var course = courses["android_intents"]!!
        var note = NoteInfo(course, "Dynamic intent resolution",
            "Wow, intents allow components to be resolved at runtime")
        notes.add(note)

        note = NoteInfo(course, "Delegating intents",
            "PendingIntents are powerful; they delegate much more than just a component invocation")
        notes.add(note)

        course = courses["android_async"]!!
        note = NoteInfo(course, "Service default threads",
            "Did you know that by default an Android Service will tie up the UI thread?")
        notes.add(note)
        note = NoteInfo(course, "Long running operations",
            "Foreground Services can be tied to a notification icon")
        notes.add(note)

        course = courses["java_lang"]!!
        note = NoteInfo(course, "Parameters",
            "Leverage variable-length parameter lists")
        notes.add(note)
        note = NoteInfo(course, "Anonymous classes",
            "Anonymous classes simplify implementing one-use types")
        notes.add(note)

        course = courses["java_core"]!!
        note = NoteInfo(course, "Compiler options",
            "The -jar option isn't compatible with with the -cp option")
        notes.add(note)
        note = NoteInfo(course, "Serialization",
            "Remember to include SerialVersionUID to assure version compatibility")
        notes.add(note)

//        notes.add(
//            NoteInfo(
//                CourseInfo("java_core", "Android Programming with Intents"),
//                "Compiler options",
//                "The -jar option isn't compatible with with the -cp option"
//            )
//        )
    }

}