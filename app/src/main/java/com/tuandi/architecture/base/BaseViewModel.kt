package com.tuandi.architecture.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    val loading = ObservableBoolean(false)

    fun hideLoading() {
        loading.set(false)
    }

    fun showLoading() {
        loading.set(true)
    }

    open fun retry() {

    }
}