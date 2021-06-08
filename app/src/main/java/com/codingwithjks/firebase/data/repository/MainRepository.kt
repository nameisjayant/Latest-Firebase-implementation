package com.codingwithjks.firebase.data.repository

import com.codingwithjks.firebase.data.User
import com.codingwithjks.firebase.util.LoginState
import com.codingwithjks.firebase.util.SignUpState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val auth: FirebaseAuth
) {

    @ExperimentalCoroutinesApi
    fun signUp(email:String, password:String): Flow<String> = callbackFlow {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    offer("Register successful")
                }
            }.addOnFailureListener {
                offer("${it.message}")
            }
        awaitClose {

        }
    }

    @ExperimentalCoroutinesApi
    fun login(email:String, password: String) : Flow<String> = callbackFlow {
        auth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                offer("Login Successful")
            }.addOnFailureListener {
                offer("${it.message}")
            }
        awaitClose {

        }
    }

}