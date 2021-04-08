package com.tuandi.architecture.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import timber.log.Timber

abstract class BaseFragment constructor(@LayoutRes val contentLayoutId: Int) :
    Fragment() {

    /*
        override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding<FragmentListBinding>(
            inflater,
             container
        ).apply {
            lifecycleOwner = this@ListFragment
        }.root
    }
    */

    protected inline fun <reified T : ViewDataBinding> binding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): T = DataBindingUtil.inflate(inflater, contentLayoutId, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("#Screen-----------------> ${this::class.java.simpleName}")
    }
}