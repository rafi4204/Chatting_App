package com.example.videocallingapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videocallingapp.firebaseAuth.FirebaseAuthManager
import com.example.videocallingapp.firebaseDB.FirebaseDatabaseManager
import com.example.videocallingapp.model.FCMSendData
import com.example.videocallingapp.model.User
import com.example.videocallingapp.network.ApiInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class DashBoardViewModel @Inject constructor(
    private val apiService : ApiInterface,
    private val authManager: FirebaseAuthManager,
    private val firebaseDatabase: FirebaseDatabaseManager
) : ViewModel() {

    val userList = MutableStateFlow(arrayListOf<User>())

    fun getOnlineUser(){
        firebaseDatabase.getOnlineUser {
            userList.value = it as ArrayList<User>
        }
    }

    fun sendPushNotification(sendData:FCMSendData){
        viewModelScope.launch {
            apiService.sendNotification(sendData)
        }
    }

    fun logout(){
        authManager.logOut {
            firebaseDatabase.removeUserFromOnline(it)
        }
    }
}