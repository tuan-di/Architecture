package com.tuandi.architecture.example.ui.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.tuandi.architecture.R
import com.tuandi.architecture.base.BaseFragment
import com.tuandi.architecture.databinding.FragmentListBinding
import com.tuandi.architecture.example.ui.adapters.PokemonAdapter
import com.tuandi.architecture.extensions.observe
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ListFragment : BaseFragment() {
    val viewModel: ListViewModel by viewModels()
    private val mAdapter by lazy { PokemonAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding<FragmentListBinding>(
            inflater,
            R.layout.fragment_list, container
        ).apply {
            vm = viewModel
            adapter = mAdapter
            lifecycleOwner = this@ListFragment
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            pokemons.observe(this@ListFragment) {
                Timber.e(it.size.toString())
            }
            getPokemon()
        }
    }
}