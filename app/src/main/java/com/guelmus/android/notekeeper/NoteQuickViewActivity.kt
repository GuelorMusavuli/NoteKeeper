package com.guelmus.android.notekeeper

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_note_quick_view.*

/**
 * This activity lets users review their note
 * and then delete it if they are done with it.
 * It is a special activity that provides users
 * an alternate way to interact with the note
 * */
class NoteQuickViewActivity : AppCompatActivity() {

  private var notePosition = POSITION_NOT_SET

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_note_quick_view)

    notePosition =
      savedInstanceState?.getInt(EXTRA_NOTE_POSITION, POSITION_NOT_SET) ?: intent.getIntExtra(
      EXTRA_NOTE_POSITION,
        POSITION_NOT_SET
      )

    initLayout()
    setNote()
  }

  private fun initLayout() {
    deleteButton.setOnClickListener {
      DataManager.notes.removeAt(notePosition)
      finish()
    }
  }

  private fun setNote() {
    if (notePosition != POSITION_NOT_SET) {
      val note = DataManager.notes[notePosition]
      textNoteTitle.text = note.noteTitle
      textNoteText.text = note.noteContent
      course.text = note.course?.courseTitle
    } else {
      Snackbar.make(note_quick_view_layout, "Error loading note", Snackbar.LENGTH_INDEFINITE)
        .show()
    }
  }

  /**Save the instance state of notePosition when the activity is destroyed */
  override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
    super.onSaveInstanceState(outState, outPersistentState)
    outState.putInt(NOTE_POSITION, notePosition)
  }

  companion object {
    const val EXTRA_NOTE_POSITION = "notePosition"
    fun getIntent(context: Context, notePosition: Int): Intent {
      val intent = Intent(context, NoteQuickViewActivity::class.java)
      intent.putExtra(EXTRA_NOTE_POSITION, notePosition)
      return intent
    }
  }
}
