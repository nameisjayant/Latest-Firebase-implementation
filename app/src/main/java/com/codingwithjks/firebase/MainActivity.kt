package com.codingwithjks.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.codingwithjks.firebase.databinding.ActivityMainBinding
import com.codingwithjks.firebase.ui.MainViewModel
import com.codingwithjks.firebase.util.showMsg
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerUser()
        loginUser()
    }

    @ExperimentalCoroutinesApi
    private fun loginUser() {
        binding.apply {
            btnLogin.setOnClickListener {
                if (!TextUtils.isEmpty(etEmail.text.toString()) && !TextUtils.isEmpty(etPassword.text.toString())) {
                    lifecycleScope.launchWhenStarted {
                        mainViewModel.login(
                            etEmail.text.toString().trim(),
                            etPassword.text.toString().trim()
                        ).onStart {
                            showMsg("Loading....")
                        }.catch { e ->
                            showMsg("${e.message}")
                        }.collect {
                            showMsg(it)
                        }
                    }
                } else {
                    showMsg("please fill all the field")
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    private fun registerUser() {
        binding.apply {
            btnSignUp.setOnClickListener {
                if (!TextUtils.isEmpty(etEmail.text.toString()) && !TextUtils.isEmpty(etPassword.text.toString())) {
                    lifecycleScope.launchWhenStarted {
                        mainViewModel.singUp(
                            etEmail.text.toString().trim(),
                            etPassword.text.toString().trim()
                        )
                            .onStart {
                                showMsg("Loading....")
                            }.catch { e ->
                                showMsg("${e.message}")
                            }.collect {
                                showMsg(it)
                            }
                    }
                } else {
                    showMsg("please fill all the fields..")
                }
            }
        }
    }
}