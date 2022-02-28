package com.example.videocallingapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.videocallingapp.databinding.ActivityDashBoardBinding
import com.example.videocallingapp.model.User
import com.example.videocallingapp.ui.adapter.DashBoardAdapter
import com.example.videocallingapp.ui.viewmodel.DashBoardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.ArrayList

@AndroidEntryPoint
class DashBoardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashBoardBinding
    val viewModel: DashBoardViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashBoardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        listener()
        viewModel.getOnlineUser()
        observer()
    }

    private fun listener() {
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun observer() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userList.collect {
                    showOnlineUser(it)
                }
            }
        }
    }

    private fun showOnlineUser(list: ArrayList<User>) {
        val adapter = DashBoardAdapter(list) { user: User -> onClickListener(user) }
        binding.rvOnlineUser.layoutManager = LinearLayoutManager(this)
        binding.rvOnlineUser.adapter = adapter
    }

    private fun onClickListener(user: User) {
        lifecycleScope.launch {
          //  viewModel.sendPushNotification()
        }
    }


}