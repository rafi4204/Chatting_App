package com.example.videocallingapp.firebaseDB

import com.example.videocallingapp.model.User
import com.google.firebase.database.*

import javax.inject.Inject

private const val KEY_USER = "user"
private const val KEY_ONLINE = "online"
private const val KEY_TOKEN = "token"

class FirebaseDatabaseManager @Inject constructor(
    private val database: FirebaseDatabase
) : FirebaseDatabaseInterface {

    override fun createUser(id: String, name: String, email: String) {
        val user = User(id, name, email)
        database
            .reference
            .child(KEY_USER)
            .child(id)
            .setValue(user)
    }
    override fun createToken(id: String, token: String) {

        database
            .reference
            .child(KEY_TOKEN)
            .child(id)
            .setValue(token)
    }

    override fun addUserToOnline(id: String, name: String, email: String) {
        val user = User(id, name, email)
        database
            .reference
            .child(KEY_ONLINE)
            .child(id)
            .setValue(user)
    }

    override fun removeUserFromOnline(id: String) {
        database
            .reference
            .child(KEY_ONLINE)
            .child(id)
            .removeValue()
    }


    override fun getProfile(id: String, onResult: (User) -> Unit) {
        database.reference
            .child(KEY_USER)
            .child(id)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) = Unit

                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue(User::class.java)

                    user?.run { onResult(User(id, name, email)) }
                }
            })
    }

    override fun getOnlineUser(onResult: (List<User>) -> Unit) {
        database.reference
            .child(KEY_ONLINE)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) = Unit

                override fun onDataChange(snapshot: DataSnapshot) {
                    val userList = arrayListOf<User>()
                    snapshot.children.forEach {
                        it.getValue(User::class.java)?.let { it1 -> userList.add(it1) }
                    }
                    userList.run { onResult(userList) }
                }
            })
    }
}