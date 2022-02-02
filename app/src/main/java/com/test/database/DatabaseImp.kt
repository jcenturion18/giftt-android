/**
 * Created by Julian Centurion on 02/02/2022.
 */

package com.test.database

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query.Direction.DESCENDING
import com.test.dto.User

class DatabaseImp : DataBaseInterface {

    private companion object {
        const val DATABASE_NAME = "users"
    }

    var db = FirebaseFirestore.getInstance()

    override fun store(userName: String) {
        val user = mapOf(userName to "name")

        db.collection(DATABASE_NAME)
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    override fun loadUserName(callback: (User) -> Unit) {
        db.collection(DATABASE_NAME)
            .whereNotEqualTo("name", "")
            .orderBy("name", DESCENDING)
            .limit(1)
            .get()
            .addOnSuccessListener {
                it.first()?.run {
                    val user = toObject(User::class.java)
                    callback(user)
                }
            }.addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }
}
