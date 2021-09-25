package com.guelmus.android.notekeeper

import android.content.Context
import android.content.Intent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteRecyclerAdapter(private val context : Context, private  val notes : List<NoteInfo>):
RecyclerView.Adapter<NoteRecyclerAdapter.NoteViewHolder>(){

    //Field to inflate item_note_list layout resource
    private val layoutInflater = LayoutInflater.from(context)


    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //Reference the views within item_note_list to display data from DataManager
        val textCourse = itemView.findViewById(R.id.textCourse) as TextView
        val textTitle = itemView.findViewById(R.id.textTitle) as TextView
        var notePosition = 0

        //ClickHandler at the top-level view for a particular item
        init{
            itemView.setOnClickListener {
                //launch NoteActivity, passing in the position of the note selected
                val intent = Intent(context, NoteActivity::class.java)
                intent.putExtra(NOTE_POSITION, notePosition)
                context.startActivity(intent)
            }
        }

    }

    //Create the itemView that the RV will recycle to display the individual item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = layoutInflater.inflate(R.layout.item_note_list, parent, false)
        return NoteViewHolder(itemView)
    }

    //Bind data with those views the RV's recycling
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        //Display info from the note
        val currentNote = notes[position]
        holder.textCourse.text = currentNote.course?.courseTitle
        holder.textTitle.text = currentNote.noteTitle

        //Keep track of the position of the note that's currently associated with the viewHolder
        holder.notePosition = position


    }

    //Return the total numbers pf items to be display
    override fun getItemCount() = notes.size

}