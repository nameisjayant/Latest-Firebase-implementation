package com.codingwithjks.firebase.ui

import androidx.lifecycle.ViewModel
import com.codingwithjks.firebase.data.Repository.MainRepository
import com.codingwithjks.firebase.data.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(private val mainRepository: MainRepository) : ViewModel() {


    fun writeData(user: User) {
        mainRepository.writeData(user)
    }

    @ExperimentalCoroutinesApi
    val readData = mainRepository.readData()
}