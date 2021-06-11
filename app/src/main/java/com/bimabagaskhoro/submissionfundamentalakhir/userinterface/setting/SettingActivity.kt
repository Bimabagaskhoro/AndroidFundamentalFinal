package com.bimabagaskhoro.submissionfundamentalakhir.userinterface.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import com.bimabagaskhoro.submissionfundamentalakhir.R
import com.bimabagaskhoro.submissionfundamentalakhir.alarm.AlarmReceiver
import com.bimabagaskhoro.submissionfundamentalakhir.alarm.Reminder
import com.bimabagaskhoro.submissionfundamentalakhir.alarm.ReminderPreference
import com.bimabagaskhoro.submissionfundamentalakhir.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var settingBinding: ActivitySettingBinding
    private lateinit var reminder: Reminder

    companion object {
        const val PREFERENCE_NAME = "Setting Preference"
        const val SET_TIME = "09:00"
        const val REMINDER_USER = "Reminder User"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingBinding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(settingBinding.root)

        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.setting)
        val reminderPref = ReminderPreference(this)
        settingBinding.swDaily.isChecked = reminderPref.getAlarmReminder().alarmReminder

        settingBinding.btnSetLanguage.setOnClickListener(this)
        alarmReceiver = AlarmReceiver()

        settingBinding.swDaily.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                true.saveAlarm()
                alarmReceiver.setRepeatingAlarm(
                    this,
                    PREFERENCE_NAME, SET_TIME, REMINDER_USER
                )
                Toast.makeText(
                    this, resources.getString(R.string.daily_reminder_at_09),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                alarmReceiver.setOffRepeatingAlarm(this)
                Toast.makeText(
                    this, resources.getString(R.string.cancel_alarm),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun Boolean.saveAlarm() {
        val reminderPreference = ReminderPreference(this@SettingActivity)
        reminder = Reminder()

        reminder.alarmReminder = this
        reminderPreference.setAlarmReminder(reminder)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_set_language -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
            }
        }
    }
}