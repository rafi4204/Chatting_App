package com.example.videocallingapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.videocallingapp.firebaseAuth.FirebaseAuthManager
import com.example.videocallingapp.model.Response
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authManager: FirebaseAuthManager,
    val firebaseDatabase: FirebaseDatabase
) : ViewModel() {
    var isLoggedIn = MutableStateFlow<Response>(Response.Empty)
    fun login(email: String, password: String) {
        authManager.login(email, password) {
            if (it) {
                isLoggedIn.value = Response.Success
            } else {
                isLoggedIn.value = Response.Failed("Something went wrong")
            }
        }
    }
}