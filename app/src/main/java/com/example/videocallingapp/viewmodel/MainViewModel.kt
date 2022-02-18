package com.example.videocallingapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.videocallingapp.firebaseAuth.FirebaseAuthManager
import com.example.videocallingapp.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val authManager: FirebaseAuthManager
) : ViewModel() {
    var isLoggedIn = MutableStateFlow<Response>(Response.Empty)
    fun login(email: String, password: String) {
        authManager.login(email, password) {
            if (it) {
                isLoggedIn.value = Response.Success
            } else {
                isLoggedIn.value = Response.Failed
            }
        }
    }
}