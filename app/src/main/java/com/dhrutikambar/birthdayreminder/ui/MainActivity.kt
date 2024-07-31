package com.dhrutikambar.birthdayreminder.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhrutikambar.birthdayreminder.databinding.ActivityMainBinding
import com.dhrutikambar.birthdayreminder.utils.BirthdayListAdapter
import com.dhrutikambar.birthdayreminder.utils.BirthdayNotificationReceiver
import com.dhrutikambar.birthdayreminder.viewModel.MainViewModel
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        enableEdgeToEdge()
        setContentView(binding.root)

        addDataForNotification()
        setUpRecyclerView()
        Toast.makeText(this, "", Toast.LENGTH_LONG).show()
    }

    private fun setUpRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        val adapter = BirthdayListAdapter(viewModel, viewModel.getBirthDayList())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }

    private fun addDataForNotification() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager


        viewModel.getBirthDayList().forEachIndexed { index, birthdayDetail ->

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, 2024)
            calendar.set(Calendar.MONTH, Calendar.JULY)
            calendar.set(Calendar.DAY_OF_MONTH, 28)
            calendar.set(Calendar.HOUR_OF_DAY, 13)
            calendar.set(Calendar.MINUTE, 57)
            calendar.set(Calendar.SECOND, 0)


            val intent = Intent(this, BirthdayNotificationReceiver::class.java).putExtra(
                "name",
                birthdayDetail.name
            ).putExtra("id", index)

            val pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            // Schedule the alarm
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        pendingIntent
                    )
                } else {
                    // Open the settings screen where the user can grant the permission
                    val settingsIntent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                    startActivity(settingsIntent)
                }
            } else {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    pendingIntent
                )
            }

        }

    }
}