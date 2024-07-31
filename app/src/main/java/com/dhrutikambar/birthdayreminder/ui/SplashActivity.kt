package com.dhrutikambar.birthdayreminder.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dhrutikambar.birthdayreminder.R
import com.dhrutikambar.birthdayreminder.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        loadUI()
    }

    private fun loadUI() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    goToMainActivity()
                }

                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                    showRedirectToSettingsUI()
                }

                else -> {
                    requestPermissionUI()
                }

            }
        } else {
            goToMainActivity()
        }
    }

    override fun onResume() {
        super.onResume()
        resumeUI()
    }

    private fun resumeUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    goToMainActivity()
                }

                shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                    showRedirectToSettingsUI()
                }
            }
        } else {
            goToMainActivity()
        }
    }

    private fun showRedirectToSettingsUI() {

        binding.tvDescription.text =
            "Looks like you have disable your notification. For that reason we are unable to remind your friend's birthday. Kindly enable notification in settings."
        binding.tvDescription.visibility = View.VISIBLE
        binding.allowButton.text = "Go to Settings"
        binding.allowButton.visibility = View.VISIBLE


        binding.allowButton.setOnClickListener {
            openNotificationSettings()
        }
    }


    private fun openNotificationSettings() {
        val intent = Intent()
        intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
        intent.putExtra("android.provider.extra.APP_PACKAGE", this.packageName)
        this.startActivity(intent)
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestPermissionUI() {
        binding.tvDescription.text =
            "Celebrate your loved ones! Enable notifications to receive birthday reminders and never miss a special day"
        binding.tvDescription.visibility = View.VISIBLE
        binding.allowButton.text = "Enable Permission"
        binding.allowButton.visibility = View.VISIBLE

        val permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) {
                    goToMainActivity()
                }
            }
        binding.allowButton.setOnClickListener {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

    }
}