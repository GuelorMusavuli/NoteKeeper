package com.guelmus.android.notekeeper

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import androidx.core.content.ContextCompat

/**
 * Helper class for showing and canceling reminder
 * notifications.
 *
 *
 * This class makes heavy use of the [NotificationCompat.Builder] helper
 * class to create notifications in a backward-compatible way.
 */
object ReminderNotification {

    //Unique identifier for this type of notification.
    private const val NOTIFICATION_TAG = "Reminder"

    //Unique identifier for channel
    const val REMINDER_CHANNEL = "reminders"

    //Will be used by the pending intent to grab the Text of the RemoteInput
    const val KEY_TEXT_REPLY ="keyTextReply"


  /**
   * This is the method we call whenever we want to create a notification.
   *
   * It shows the notification, or updates a previously shown notification of
   * this type, with the given parameters.

   * @see .cancel
   */
  @SuppressLint("UnspecifiedImmutableFlag")
  fun notify(context: Context, note: NoteInfo, notePosition: Int) {

      //Intent to be fire off NoteActivity when taps on notification
      val intent = Intent(context, NoteActivity::class.java)
      intent.putExtra(NOTE_POSITION, notePosition)

      //Navigate to NoteActivity with an appropriate back stack in place
      val pendingIntent = TaskStackBuilder.create(context)
          .addNextIntentWithParentStack(intent)
          .getPendingIntent(0,PendingIntent.FLAG_CANCEL_CURRENT )

      //Three messages that will go directly into the messaging style
      val message1 = NotificationCompat.MessagingStyle.Message(
          note.comments[0].comment,
          note.comments[0].timestamp,
          note.comments[0].name)
      val message2 = NotificationCompat.MessagingStyle.Message(
          note.comments[1].comment,
          note.comments[1].timestamp,
          note.comments[1].name)
      val message3 = NotificationCompat.MessagingStyle.Message(
          note.comments[2].comment,
          note.comments[2].timestamp,
          note.comments[2].name)

      //Object that display the quick reply's text input field to comment a note
      val remoteInput = RemoteInput.Builder(KEY_TEXT_REPLY)
          .setLabel("Add Note")
          .build()

      //Intent that will fire off NotificationBroadCastReceiver
      val replyIntent = Intent(context, NotificationBroadCastReceiver::class.java)
      replyIntent.putExtra(NOTE_POSITION, notePosition)

      //NotificationBroadCastReceiver's pending intent
      val replyPendingIntent = PendingIntent.getBroadcast(
          context,
          100,
          replyIntent,
          PendingIntent.FLAG_UPDATE_CURRENT
      )

      //Reply Action
      val replyAction = NotificationCompat.Action.Builder(
          R.drawable.ic_reply_black_24dp, //icon for direct reply
          "AddNote", //button text
          replyPendingIntent)
          .addRemoteInput(remoteInput)
          .build()

      /**
       * Set appropriate defaults for the notification light, sound,and vibration.(.setDefaults)
       * Set required fields, including the small icon, the notification title, and text.
       *
       * All fields below the above lines are optional.
       *
       * Use a default priority (recognized on devices running Android 4.1 or later)(Priority)
       * Set ticker text (preview) information for this notification for accessibility readers.
       *
       * */
    val builder = NotificationCompat.Builder(context, REMINDER_CHANNEL)
          .setDefaults(Notification.DEFAULT_ALL)
          .setSmallIcon(R.drawable.ic_stat_reminder)
          .setContentTitle("Comments about " + note.noteTitle)
          .setPriority(NotificationCompat.PRIORITY_HIGH)
          .setTicker("Comments about " + note.noteTitle)
          .setContentIntent( // Pending intent to be initiated when the user touches the notification.
            pendingIntent
          )
          .setAutoCancel(true)  // Automatically dismiss the notification when it is touched.
          .setColor(ContextCompat.getColor(context, R.color.darkOrange))//Set the color
          .setColorized(true)
          .setOnlyAlertOnce(true) //alert only the first time
          .setStyle(NotificationCompat.MessagingStyle("Me")//message style notification
              .setConversationTitle(note.noteTitle)
              .addMessage(message3)
              .addMessage(message2)
              .addMessage(message1)
          )
          .addAction(replyAction)// Add a the direct reply action

      notify(context, builder.build())
  }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    private fun notify(context: Context, notification: Notification) {
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.notify(NOTIFICATION_TAG, 0, notification)
    }

    /**
     * Cancels any notifications of this type previously shown using
     * [.notify].
     */
//    @TargetApi(Build.VERSION_CODES.ECLAIR)
//    fun cancel(context: Context) {
//        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        nm.cancel(NOTIFICATION_TAG, 0)
//    }
}