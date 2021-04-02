package com.tuandi.architecture.example.ui.activities

import android.os.Bundle
import com.tuandi.architecture.R
import com.tuandi.architecture.base.BaseActivity
import com.tuandi.architecture.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val binding: ActivityMainBinding by binding(R.layout.activity_main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            lifecycleOwner = this@MainActivity
        }
    }
}