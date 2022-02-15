package com.end.mvi.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.end.mvi.R
import com.end.mvi.adapters.EndRVAdapter
import com.end.mvi.databinding.EndClothesFragmentBinding
import com.end.mvi.utils.EndUIState
import com.end.mvi.utils.NavigationToNextScreen
import com.end.mvi.utils.binding.viewBinding
import com.end.mvi.utils.collectWhileStarted
import com.end.mvi.utils.toast
import com.end.mvi.viewmodels.EndViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EndClothesFragment : Fragment(R.layout.end_clothes_fragment) {

    private val endViewModel: EndViewModel by viewModel()
    private val binding by viewBinding(EndClothesFragmentBinding::bind)
    private val endRVAdapter: EndRVAdapter by lazy {
        EndRVAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObservers()
        setupClicks()
        endViewModel.getClothes()
    }

    private fun setupClicks() {
        endRVAdapter.onItemClicked = {
            endViewModel.onRVItemClicked(it)
        }
    }

    private fun setupObservers() {
        endViewModel.container.state.collectWhileStarted(viewLifecycleOwner) {
            when (it) {
                is EndUIState.Loading -> {
                    binding.contentLoaderProgressBar.show()
                }
                is EndUIState.Data -> {
                    with(binding) {
                        toolbarLayout.toolbarTitleTextView.text = it.data.title
                        binding.contentLoaderProgressBar.hide()
                        binding.itemsCountTextView.text =
                            requireContext().getString(
                                R.string.items_count,
                                it.data.product_count.toString()
                            )
                    }
                    endRVAdapter.setItems(it.data.products)
                }
                is EndUIState.Fail.Exception -> {
                    binding.contentLoaderProgressBar.hide()
                    requireContext().toast(it.massage)
                }
                is EndUIState.Fail.NoInternet -> {
                    binding.contentLoaderProgressBar.hide()
                    requireContext().toast(getString(R.string.no_internet_connection))
                }
            }
        }
        endViewModel.container.sideEffect.collectWhileStarted(viewLifecycleOwner) {
            if (it is NavigationToNextScreen.NavigationToDetailsScreen) {
                it.bundle?.get("navId")?.let { it1 -> Log.i("EndLoggerTag", it1.toString()) }
            }
        }
    }

    private fun setupView() {
        with(binding) {
            //RV area
            endClothesRV.layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            endClothesRV.setHasFixedSize(true)
            endClothesRV.adapter = endRVAdapter
            //End RV are
            toolbarLayout.backButtonImageView.isVisible = false
        }
    }

}