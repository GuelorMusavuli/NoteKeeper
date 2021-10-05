package com.guelmus.android.notekeeper

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.RemoteInput

class NotificationBroadCastReceiver : BroadcastReceiver() {

    // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
    override fun onReceive(context: Context, intent: Intent) {

        //Grab the bundle from replyIntent
        val bundle = RemoteInput.getResultsFromIntent(intent)

        //if the bundle is not null,Grab the NOTE POSITION passed with it.
        // Grab the comment(text) entered by the user into the RemoteInput,
        // and if the value is null, use an empty string.
        // Finally, add the comment to the list of comments in the referenced note position.
        // Null name is passed in NoteComment because the notification framework
        // will use the name provided to the Messaging Style
        if (bundle != null) {
            val notePosition = intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET)
            val text = bundle.getCharSequence(ReminderNotification.KEY_TEXT_REPLY)?.toString()?:""
            DataManager
                .notes[notePosition]
                .comments.add(0, NoteComment(null, text, System.currentTimeMillis()))

            //Update the notification when we're done by simply firing off a new notification.
            //The Notification will reflect a new comment we just added,
            // and also reset the direct reply action.
            ReminderNotification.notify(context, DataManager.notes[notePosition], notePosition)

        }

    }
}