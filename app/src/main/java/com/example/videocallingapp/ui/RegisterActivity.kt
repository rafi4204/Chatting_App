package com.example.videocallingapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.videocallingapp.R
import com.example.videocallingapp.databinding.ActivityMainBinding
import com.example.videocallingapp.databinding.ActivityRegisterBinding
import com.example.videocallingapp.model.Response
import com.example.videocallingapp.ui.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRegisterBinding
    val viewModel : RegisterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        listener()
        observer()
    }

    private fun observer() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isRegistered.collect {
                  when(it){
                      is Response.Success->{
                          Toast.makeText(this@RegisterActivity,"Success",Toast.LENGTH_SHORT).show()
                          startActivity(Intent(this@RegisterActivity,DashBoardActivity::class.java))
                      }
                      is Response.Failed->{
                          Toast.makeText(this@RegisterActivity,it.errorMsg,Toast.LENGTH_SHORT).show()
                      }
                  }
                }
            }
        }
    }

    private fun listener() {
        binding.btnRegister.setOnClickListener {
            viewModel.createNewUser(binding.etEmail.text.toString(),binding.etName.text.toString(),binding.etPassword.text.toString())
        }
    }
}