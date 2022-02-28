package com.example.videocallingapp.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient {

    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit? {
      val client = OkHttpClient.Builder().addInterceptor {

            it.proceed(it.request().newBuilder()
                .addHeader("Content-Type","application/json",)
                .addHeader("Authorization","key="+"AAAAQZ_AKKE:APA91bEWTLrEQKXIad-pTBSR0Rxlsientqb9BDRp1SpoWxux9D2Fy70BiEOhv1-ZrI22xvyXSx8wQYyvn4pfPl68zw1uD9w8R0j1nj88xATpE84Mcj6yVc5G591ZtRyo5wln8gMZOh1c").build())
        }.addInterceptor(HttpLoggingInterceptor().apply {
            this.level =  HttpLoggingInterceptor.Level.BODY
      }).build()
        retrofit = Retrofit.Builder()
            .baseUrl("https://fcm.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit
    }

}