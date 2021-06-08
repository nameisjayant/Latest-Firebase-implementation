package com.codingwithjks.firebase.util

import com.codingwithjks.firebase.data.User

sealed class SignUpState{
    object Loading : SignUpState()
    class Success(val msg:String) : SignUpState()
    class Failure(val error:String): SignUpState()
}
