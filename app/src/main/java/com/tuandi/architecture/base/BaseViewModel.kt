package com.tuandi.architecture.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    val inProgress: LiveData<Boolean>
        get() = _inProgress
    private val _inProgress = MutableLiveData(false)

    fun hideLoading() {
        _inProgress.value = false
    }

    fun showLoading() {
        _inProgress.value = true
    }

    open fun retry() {

    }
}