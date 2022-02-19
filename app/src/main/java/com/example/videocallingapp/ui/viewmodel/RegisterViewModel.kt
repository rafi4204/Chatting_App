package com.example.videocallingapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.videocallingapp.firebaseAuth.FirebaseAuthManager
import com.example.videocallingapp.firebaseDB.FirebaseDatabaseManager
import com.example.videocallingapp.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    val authManager: FirebaseAuthManager,
    val firebaseDatabase: FirebaseDatabaseManager
) : ViewModel() {

    val isRegistered = MutableStateFlow<Response>(Response.Empty)

    fun createNewUser(email: String, name: String, password: String) {
        authManager.register(email, password, name) {
            if (it) {
                firebaseDatabase.createUser(authManager.getUserId(), name, email)
                isRegistered.value = Response.Success
            } else{
                isRegistered.value = Response.Failed("Failed to Register!!")
            }
        }
    }
}