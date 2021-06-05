package com.codingwithjks.firebase.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.codingwithjks.firebase.R
import com.codingwithjks.firebase.data.User
import com.codingwithjks.firebase.data.adapter.UserAdapter
import com.codingwithjks.firebase.databinding.ActivityMainBinding
import com.codingwithjks.firebase.util.State
import com.codingwithjks.firebase.util.checkNetwork
import com.codingwithjks.firebase.util.showMsg
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    @Inject
    lateinit var userAdapter: UserAdapter

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerview()
        readData()

        binding.floatingButton.setOnClickListener {
            startActivity(Intent(this,AnotherActivity::class.java))
        }
    }

    private fun initRecyclerview() {
        binding.apply {
            recyclerview.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = userAdapter
            }
        }
    }

    @ExperimentalCoroutinesApi
    private fun readData() {
        lifecycleScope.launchWhenStarted {
            mainViewModel.readData.collect {
                when (it) {
                    is State.Success -> {
                        binding.apply {
                            progressBar.isVisible = false
                            recyclerview.isVisible=true
                        }
                        userAdapter.submitList(it.data)
                    }
                    is State.Failure -> {
                        binding.apply {
                            progressBar.isVisible = false
                            recyclerview.isVisible=false
                        }
                        Log.d("main", "error: ${it.error}")
                    }
                }
            }
        }
    }


}
