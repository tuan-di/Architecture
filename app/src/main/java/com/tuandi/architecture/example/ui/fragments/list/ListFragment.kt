package com.tuandi.architecture.example.ui.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.tuandi.architecture.R
import com.tuandi.architecture.base.BaseFragment
import com.tuandi.architecture.databinding.FragmentListBinding
import com.tuandi.architecture.example.ui.adapters.PokemonAdapter
import com.tuandi.architecture.extensions.onFailure
import com.tuandi.architecture.extensions.onSuccess
import com.tuandi.architecture.extensions.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : BaseFragment(R.layout.fragment_list) {
    private val viewModel: ListViewModel by viewModels()
    private val mAdapter: PokemonAdapter by lazy {
        PokemonAdapter {
            findNavController().navigate(
                ListFragmentDirections.actionListFragmentToDetailFragment(
                    mAdapter.currentList[it]
                )
            )
        }
    }
    lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = binding<FragmentListBinding>(
            inflater,
            container
        ).apply {
            adapter = mAdapter
            refresh.setOnRefreshListener {
                viewModel.getPokemon()
            }
            vm = viewModel
            lifecycleOwner = this@ListFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            pokemonList.observe(viewLifecycleOwner, Observer {
                it.onSuccess {
                    mAdapter.submitList(this)
                }.onFailure {
                    requireContext().toast(this.errorMessage)
                }
            })
            getPokemon()
        }
    }
}