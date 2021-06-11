package com.bimabagaskhoro.submissionfundamentalakhir.alarm

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.bimabagaskhoro.submissionfundamentalakhir.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    companion object {
        const val TIME_FORMAT = "HH:mm"
        const val EXTRA_MESSAGE = "message"
        const val EXTRA_TYPE = "type"
        const val ID_NOTIFICATION = 1

        private const val ID_REPEATING = 101
        private const val ID_CHANNEL = "channel"
        private const val NAME_CHANNEL = "reminder_github_user"

    }

    override fun onReceive(context: Context, intent: Intent) {
        showAlarmNotification(context)
    }

    private fun showAlarmNotification(context: Context) {
        val mIntent =
            context.packageManager.getLaunchIntentForPackage("com.bimabagaskhoro.submissionfundamentalakhir")
        val pendingIntent = PendingIntent.getActivity(context, 0, mIntent, 0)

        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, ID_CHANNEL)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_baseline_access_alarm)
            .setContentTitle("Hello")
            .setContentText("Don't forget to back in application")
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                ID_CHANNEL,
                NAME_CHANNEL,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            builder.setChannelId(ID_CHANNEL)
            notificationManagerCompat.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManagerCompat.notify(ID_NOTIFICATION, notification)
    }

    fun setRepeatingAlarm(context: Context, type: String, time: String, message: String) {

        if (TIME_FORMAT.isTimeInvalid(time)) return

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_TYPE, type)

        val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
        Toast.makeText(context, "Repeating alarm set up", Toast.LENGTH_SHORT).show()
    }

    fun setOffRepeatingAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val mIntent = Intent(context, AlarmManager::class.java)
        val codeReq = ID_REPEATING

        val pendingIntent = PendingIntent.getBroadcast(context, codeReq, mIntent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, "Reminder turn ff", Toast.LENGTH_SHORT).show()
    }

    private fun String.isTimeInvalid(time: String): Boolean {
        return try {
            val df = SimpleDateFormat(this, Locale.getDefault())
            df.isLenient = false
            df.parse(time)
            false
        } catch (e: ParseException) {
            true
        }
    }
}