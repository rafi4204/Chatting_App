package com.example.videocallingapp.network

import com.example.videocallingapp.model.FCMResult
import com.example.videocallingapp.model.FCMSendData
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiInterface {

    @POST("fcm/send")
    suspend fun sendNotification(@Body body: FCMSendData?): FCMResult
}