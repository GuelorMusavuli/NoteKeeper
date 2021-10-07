package com.guelmus.android.notekeeper

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.app.TaskStackBuilder

/**
 * Implementation of App Widget functionality.
 */
class NoteKeeperAppWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    //Receive and respond to any broadcast sent for the widget
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {
            //Update and refresh the views for each instance of the widget
            val manager = AppWidgetManager.getInstance(context)
            val componentName = ComponentName(context, NoteKeeperAppWidget::class.java)

            //get all ids for instances of the widget and associate this update to the list view
            manager.notifyAppWidgetViewDataChanged(manager.getAppWidgetIds(componentName),
                R.id.notes_list)
        }
        super.onReceive(context, intent)
    }


    companion object {

        internal fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val widgetText = context.getString(R.string.appwidget_text) //title
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.note_keeper_app_widget)
            views.setTextViewText(R.id.appwidget_text, widgetText)
            views.setRemoteAdapter(R.id.notes_list, Intent(context, AppWidgetRemoteViewsService::class.java))

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)

        }

        //This will cause the data on widget to refresh when a new data is created
       fun sendRefreshBroadcast(context: Context) {
           val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
           intent.component = ComponentName(context, NoteKeeperAppWidget::class.java)
           context.sendBroadcast(intent) //fire off the broadcast
       }




   }

}



