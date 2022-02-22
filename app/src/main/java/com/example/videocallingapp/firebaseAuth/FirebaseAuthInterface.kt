package com.example.videocallingapp.firebaseAuth

interface FirebaseAuthInterface {
    fun login(email: String, password: String, onResult: (Boolean) -> Unit)

    fun register(email: String, password: String, userName: String, onResult: (Boolean) -> Unit)

    fun getUserId(): String

    fun getUserName(): String

    fun logOut(onResult: (String) -> Unit)
}