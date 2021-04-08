package com.tuandi.architecture.example.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.tuandi.architecture.R
import com.tuandi.architecture.base.BaseFragment
import com.tuandi.architecture.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding<FragmentHomeBinding>(
            inflater,
            container
        ).apply {
            lifecycleOwner = this@HomeFragment
            btnPaging.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPagingFragment())
            }
            btnVVM.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToListFragment())
            }
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}