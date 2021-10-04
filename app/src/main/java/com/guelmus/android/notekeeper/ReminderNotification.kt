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

/**
 * Helper class for showing and canceling reminder
 * notifications.
 *
 *
 * This class makes heavy use of the [NotificationCompat.Builder] helper
 * class to create notifications in a backward-compatible way.
 */
object ReminderNotification {

    //Unique identifier used by Android Framework for tracking
    // this type of notification.
    private const val NOTIFICATION_TAG = "Reminder"

    //Unique identifier for channel
     const val REMINDER_CHANNEL = "reminders"

  /**
   * This is the method we call whenever we want to create a notification.
   *
   * It shows the notification, or updates a previously shown notification of
   * this type, with the given parameters.

   * @see .cancel
   */
  @SuppressLint("UnspecifiedImmutableFlag")
  fun notify(context: Context, titleText: String, bodyText: String, notePosition: Int) {

      //Intent to fire up noteActivity
      val intent = Intent(context, NoteActivity::class.java)
      intent.putExtra(NOTE_POSITION, notePosition)

      //Navigate to NoteActivity with an appropriate back stack in place,
      // using NoteActivity's parent
      val pendingIntent = TaskStackBuilder.create(context)
          .addNextIntentWithParentStack(intent)
          .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

      //Share the note reminder
      val shareIntent = PendingIntent.getActivity(context,
          0,
          Intent.createChooser(Intent(Intent.ACTION_SEND)
              .setType("text/plain")
              .putExtra(Intent.EXTRA_TEXT,bodyText),
              "Share Note Reminder"),
          PendingIntent.FLAG_UPDATE_CURRENT)


      /**
       * Set appropriate defaults for the notification light, sound,and vibration.(.setDefaults)
       * Set required fields, including the small icon, the notification title, and text.
       *
       * All fields below the above lines are optional.
       *
       * Use a default priority (recognized on devices running Android 4.1 or later)(Priority)
       * Set ticker text (preview) information for this notification.
       *
       * */
    val builder = NotificationCompat.Builder(context, REMINDER_CHANNEL)
          .setDefaults(Notification.DEFAULT_ALL)
          .setSmallIcon(R.drawable.ic_stat_reminder)
          .setContentTitle(titleText)
          .setContentText(bodyText)
          .setPriority(NotificationCompat.PRIORITY_DEFAULT)
          .setTicker(titleText)
          .setContentIntent( // Pending intent to be initiated when the user touches the notification.
            pendingIntent
          )

          // Automatically dismiss the notification when it is touched.
          .setAutoCancel(true)

          // Add a share action
          .addAction(R.drawable.ic_share_black_24dp,"Share", shareIntent)

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
    @TargetApi(Build.VERSION_CODES.ECLAIR)
    fun cancel(context: Context) {
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nm.cancel(NOTIFICATION_TAG, 0)
    }
}