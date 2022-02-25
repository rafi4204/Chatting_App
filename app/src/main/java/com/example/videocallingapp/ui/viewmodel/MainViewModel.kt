package com.example.videocallingapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.videocallingapp.firebaseAuth.FirebaseAuthManager
import com.example.videocallingapp.firebaseDB.FirebaseDatabaseManager
import com.example.videocallingapp.model.Response
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authManager: FirebaseAuthManager,
    private val firebaseDatabase: FirebaseDatabaseManager
) : ViewModel() {
    var isLoggedIn = MutableStateFlow<Response>(Response.Empty)
    fun login(email: String, password: String,token:String) {
        authManager.login(email, password) {
            if (it) {
                isLoggedIn.value = Response.Success
                firebaseDatabase.addUserToOnline(authManager.getUserId(),authManager.getUserName(),email)
                firebaseDatabase.createToken(authManager.getUserId(),token)
            } else {
                isLoggedIn.value = Response.Failed("Something went wrong")
            }
        }
    }
}