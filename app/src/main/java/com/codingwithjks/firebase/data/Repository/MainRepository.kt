package com.codingwithjks.firebase.data.Repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.codingwithjks.firebase.data.User
import com.codingwithjks.firebase.util.State
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import dagger.Provides
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

class MainRepository
@Inject
constructor(
    private val database: DatabaseReference,
) {

    fun writeData(user: User) {
        database.child("user").push().setValue(user)
    }

    @ExperimentalCoroutinesApi
    fun readData(): Flow<State> = callbackFlow {
        val mRef = database.child("user")
        val callback = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = snapshot.children.map { ds ->
                    ds.getValue(User::class.java)
                }
                Log.d("main", "onDataChange: $users")
                offer(State.Success(users))
            }

            override fun onCancelled(error: DatabaseError) {
                offer(State.Failure(error))
            }
        }

        mRef.addValueEventListener(callback)
        awaitClose {
            mRef.removeEventListener(callback)
        }
    }


}
