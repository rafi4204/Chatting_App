package com.example.videocallingapp.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.videocallingapp.R
import com.example.videocallingapp.databinding.ActivityMainBinding
import com.example.videocallingapp.model.Response
import com.example.videocallingapp.ui.viewmodel.MainViewModel
import com.example.videocallingapp.utils.DataStoreManager
import com.example.videocallingapp.utils.Key.TOKEN
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.tasks.OnCompleteListener


import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        const val CHANNEL_ID = "1"
        const val notificationId = 2
    }

    val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var dataStoreManager: DataStoreManager
    private lateinit var binding: ActivityMainBinding
    private val messageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            // text_view_notification.text = intent.extras?.getString("message")
            Toast.makeText(context, intent.extras?.getString("message"), Toast.LENGTH_SHORT).show()
        }
    }

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        createNotificationChannel()

        if (checkGooglePlayServices())
            deviceToken()
        observe()
        listener()
    }


    fun listener() {

        binding.signInButton.setOnClickListener {
            lifecycleScope.launch {
                dataStoreManager.readValue(
                    stringPreferencesKey(TOKEN)
                ) {
                    viewModel.login(
                        binding.etEmail.text.toString(),
                        binding.etPassword.text.toString(),
                        this
                    )
                }
            }
        }
        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoggedIn.collect {
                    when (it) {
                        is Response.Success -> {
                            Toast.makeText(
                                this@MainActivity,
                                "Success",
                                Toast.LENGTH_LONG
                            ).show()
                            startActivity(Intent(this@MainActivity, DashBoardActivity::class.java))
                        }
                        is Response.Failed ->
                            Toast.makeText(this@MainActivity, it.errorMsg, Toast.LENGTH_LONG).show()

                    }
                }
            }
        }
    }


    private fun deviceToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("tag", "getInstanceId failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            lifecycleScope.launch {
                dataStoreManager.storeValue(stringPreferencesKey(TOKEN), token)
            }
            val msg = token
            Log.d("tag", msg)

        })
    }

    private fun checkGooglePlayServices(): Boolean {
        val status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
        return if (status != ConnectionResult.SUCCESS) {
            Log.e("tag", "Error")

            false
        } else {
            Log.i("tag", "Google play services updated")
            true
        }
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver)

    }


    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(messageReceiver, IntentFilter("MyData"))

    }


}