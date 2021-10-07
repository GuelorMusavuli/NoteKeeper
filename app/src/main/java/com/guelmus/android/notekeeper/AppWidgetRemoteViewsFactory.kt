package com.guelmus.android.notekeeper

import android.content.Context
import android.widget.RemoteViews
import android.widget.RemoteViewsService

/**
 * This is the adapter class that will bind
 * the list of notes from DataManager to a regular list view, to get displayed.
 * */
class AppWidgetRemoteViewsFactory(val context : Context) :
    RemoteViewsService.RemoteViewsFactory {

        //Initialize the dataset or DB. H
        // However, the data for this app doesn't need any initialization
        override fun onCreate() {
            TODO("Not yet implemented")
        }

        //Observe the underlying data change and address it
        //nothing to be put here due to data simplicity
        override fun onDataSetChanged() {
            TODO("Not yet implemented")
        }

        //Handy method to define views to be shown while data is initializing or loading
        override fun getLoadingView(): RemoteViews? {
            return null //no need to load any data
        }

        //Return a unique ID that pertains to a specific item in the dataset
        // based on the position in the collection view
        override fun getItemId(position: Int) : Long{
           return position.toLong()
        }

        //Return true if the same ID always refers to the same item in dataset
        override fun hasStableIds() : Boolean{
            return true
        }

        //Bind the data to the RemoteViews to be drawn in the widget
        override fun getViewAt(position: Int): RemoteViews {
            val rv = RemoteViews(context.packageName, R.layout.item_note_widget)
            rv.setTextViewText(R.id.note_title, DataManager.notes[position].noteTitle)

            return  rv
        }

        //Get the total number of notes to be displayed
        override fun getCount() = DataManager.notes.size

        //Return the number of types of views to be returned in this factory
        override fun getViewTypeCount() : Int{
            return 1
        }

        //Clean up any data or cursors we may have opened. It will get called when
        // the last RemoteViews adapter associated with this factory gets unbound.
        override fun onDestroy() {
            TODO("Not yet implemented")
        }
}