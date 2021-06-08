package com.codingwithjks.firebase.ui

import androidx.lifecycle.ViewModel
import com.codingwithjks.firebase.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(private val mainRepository: MainRepository) : ViewModel() {

    @ExperimentalCoroutinesApi
    fun singUp(email:String, password:String) = mainRepository.signUp(email,password)

    @ExperimentalCoroutinesApi
    fun login(email:String, password:String) = mainRepository.login(email,password)
}