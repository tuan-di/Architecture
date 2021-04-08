package com.tuandi.architecture.example.ui.fragments.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.tuandi.architecture.R
import com.tuandi.architecture.base.BaseFragment
import com.tuandi.architecture.databinding.FragmentDetailBinding
import com.tuandi.architecture.example.network.models.Pokemon
import com.tuandi.architecture.extensions.onSuccess
import com.tuandi.architecture.extensions.toast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : BaseFragment(R.layout.fragment_detail) {
    private val args: DetailFragmentArgs by navArgs()
    private val pokemon: Pokemon by lazy {
        args.pokemon
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding<FragmentDetailBinding>(
            inflater,
            container
        ).apply {
            lifecycleOwner = this@DetailFragment
        }.root
    }

    @Inject
    lateinit var detailViewModelFactory: DetailViewModel.AssistedFactory

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModel.provideFactory(detailViewModelFactory, pokemon.name)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
            pokemonInfo.observe(viewLifecycleOwner, {
                it.onSuccess {
                    requireContext().toast(this.name)
                }
            })
        }
    }
}