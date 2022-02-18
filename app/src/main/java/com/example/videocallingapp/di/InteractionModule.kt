package com.example.videocallingapp.di

import com.example.videocallingapp.firebaseAuth.FirebaseAuthInterface
import com.example.videocallingapp.firebaseAuth.FirebaseAuthManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class InteractionModule {

    @Binds
    abstract fun authentication(authentication: FirebaseAuthManager): FirebaseAuthInterface


}