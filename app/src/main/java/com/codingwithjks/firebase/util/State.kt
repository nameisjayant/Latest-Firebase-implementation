package com.codingwithjks.firebase.util

import com.codingwithjks.firebase.data.User
import com.google.firebase.database.DatabaseError

sealed class State {

    class Success(val data: List<User?>) : State()
    class Failure(val error: DatabaseError) : State()

}