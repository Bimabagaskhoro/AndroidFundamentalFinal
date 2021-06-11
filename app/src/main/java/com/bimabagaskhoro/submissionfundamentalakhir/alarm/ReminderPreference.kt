package com.bimabagaskhoro.submissionfundamentalakhir.alarm

import android.content.Context

class ReminderPreference(context: Context) {

    companion object {
        const val NAME_PREFERENCE = "preference"
        private const val REMINDER = "reminder"
    }

    private val preference = context.getSharedPreferences(NAME_PREFERENCE, Context.MODE_PRIVATE)

    fun setAlarmReminder(util: Reminder) {
        val editor = preference.edit()
        editor.putBoolean(REMINDER, util.alarmReminder)
        editor.apply()
    }

    fun getAlarmReminder(): Reminder {
        val model = Reminder()
        model.alarmReminder = preference.getBoolean(REMINDER, false)
        return model
    }
}