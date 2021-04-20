package com.tuandi.architecture.example.ui.activities

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.tuandi.architecture.R
import com.tuandi.architecture.base.BaseActivity
import com.tuandi.architecture.databinding.ActivityMainBinding
import com.tuandi.architecture.example.datastore.DataStoreManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val binding: ActivityMainBinding by binding(R.layout.activity_main)
    @Inject
    lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            lifecycleOwner = this@MainActivity
        }
        setupUI()
    }

    private fun setupUI() {
        lifecycleScope.launchWhenCreated {
            dataStoreManager.themeMode.collect {

            }
        }
    }
}