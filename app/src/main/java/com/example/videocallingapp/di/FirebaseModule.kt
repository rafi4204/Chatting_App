package com.example.videocallingapp.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)

class FirebaseModule {

    @Provides
    fun firebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

}