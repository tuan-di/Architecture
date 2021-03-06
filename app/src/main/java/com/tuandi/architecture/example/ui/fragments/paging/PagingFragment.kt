package com.tuandi.architecture.example.ui.fragments.paging

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuandi.architecture.R
import com.tuandi.architecture.base.BaseFragment
import com.tuandi.architecture.databinding.FragmentPagingBinding
import com.tuandi.architecture.example.ui.adapters.ReposAdapter
import com.tuandi.architecture.extensions.onFailure
import com.tuandi.architecture.extensions.onSuccess
import com.tuandi.architecture.extensions.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PagingFragment : BaseFragment(R.layout.fragment_paging) {
    private val viewModel: PagingViewModel by viewModels()
    private val mAdapter = ReposAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding<FragmentPagingBinding>(
            inflater, container
        ).apply {
            adapter = mAdapter
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val totalItemCount = layoutManager.itemCount
                    val visibleItemCount = layoutManager.childCount
                    val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                    viewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
                }
            })
            lifecycleOwner = this@PagingFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            repoResult.observe(viewLifecycleOwner, {
                it.onSuccess {
                    mAdapter.submitList(this)
                }.onFailure {
                    requireContext().toast(this.errorMessage)
                }
            })
        }
    }
}