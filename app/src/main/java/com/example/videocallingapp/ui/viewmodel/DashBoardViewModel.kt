package com.example.videocallingapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.videocallingapp.firebaseAuth.FirebaseAuthManager
import com.example.videocallingapp.firebaseDB.FirebaseDatabaseManager
import com.example.videocallingapp.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val authManager: FirebaseAuthManager,
    private val firebaseDatabase: FirebaseDatabaseManager
) : ViewModel() {

    val userList = MutableStateFlow(arrayListOf<User>())

    fun getOnlineUser(){
        firebaseDatabase.getOnlineUser {
            userList.value = it as ArrayList<User>
        }
    }
}