package com.example.videocallingapp.di

import com.example.videocallingapp.network.ApiInterface
import com.example.videocallingapp.network.RetrofitClient
import dagger.Provides

class NetworkModule {

    @Provides
    fun getRetrofitClient(): ApiInterface? = RetrofitClient().getClient()?.create(ApiInterface::class.java)
}