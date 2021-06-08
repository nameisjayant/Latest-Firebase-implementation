package com.codingwithjks.firebase.util

sealed class LoginState{
    object Loading : LoginState()
    class Success(val msg:String) : LoginState()
    class Failure(val error:String): LoginState()
}
