package com.guelmus.android.notekeeper

import android.content.Intent
import android.widget.RemoteViewsService

/**
 * This class provides the AppWidgetRemoteViewsFactory to the RemoteViews
 * */
class AppWidgetRemoteViewsService : RemoteViewsService() {

    //Return an instance of a RemoteViewsFactory
    override fun onGetViewFactory(p0: Intent?): RemoteViewsFactory {

        //We don't wanna tie it to a specific activity context as parameter,
        // as this will work even when the application is closed
        return AppWidgetRemoteViewsFactory(applicationContext)
    }
}