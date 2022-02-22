package com.example.videocallingapp.firebaseAuth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import javax.inject.Inject

class FirebaseAuthManager @Inject constructor(private val authentication: FirebaseAuth): FirebaseAuthInterface {
    override fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
        authentication.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            onResult(it.isComplete && it.isSuccessful)
        }
    }

    override fun register(email: String, password: String, userName: String, onResult: (Boolean) -> Unit) {
        authentication.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isComplete && it.isSuccessful) {
                authentication.currentUser?.updateProfile(
                    UserProfileChangeRequest
                    .Builder()
                    .setDisplayName(userName)
                    .build())
                onResult(true)
            } else {
                onResult(false)
            }
        }
    }

    override fun getUserId(): String = authentication.currentUser?.uid ?: ""

    override fun getUserName(): String = authentication.currentUser?.displayName ?: ""

    override fun logOut(onResult: (String) -> Unit) {
        val id = getUserId()
        authentication.signOut()
        onResult(id)
    }


}