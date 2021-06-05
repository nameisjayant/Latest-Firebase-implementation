package com.codingwithjks.firebase.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.codingwithjks.firebase.data.User
import com.codingwithjks.firebase.databinding.ActivityAnotherBinding
import com.codingwithjks.firebase.util.checkNetwork
import com.codingwithjks.firebase.util.showMsg
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class AnotherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnotherBinding
    private val mainViewModel: MainViewModel by viewModels()

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnotherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        writeData()
    }

    @ExperimentalCoroutinesApi
    private fun writeData() {
        binding.apply {
            save.setOnClickListener {

                if (!TextUtils.isEmpty(username.text.toString()) && !TextUtils.isEmpty(
                        password.text.toString()
                    )
                ) {
                    mainViewModel.writeData(
                        User(
                            username.text.toString().trim(),
                            password.text.toString().trim()
                        )
                    )
                    showMsg("Data added successfully..")
                    startActivity(Intent(this@AnotherActivity, MainActivity::class.java))
                } else {
                    Toast.makeText(
                        this@AnotherActivity,
                        "please fill all the fields...",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        binding.apply {
            username.setText("")
            password.setText("")
        }
    }
}
