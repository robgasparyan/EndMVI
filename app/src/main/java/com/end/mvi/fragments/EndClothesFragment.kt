package com.end.mvi.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.end.mvi.R
import com.end.mvi.adapters.EndRVAdapter
import com.end.mvi.databinding.EndClothesFragmentBinding
import com.end.mvi.state.EndMainState
import com.end.mvi.utils.binding.viewBinding
import com.end.mvi.utils.toast
import com.end.mvi.viewmodels.EndViewModel

class EndClothesFragment : Fragment(R.layout.end_clothes_fragment), MavericksView {

    private val endViewModel: EndViewModel by fragmentViewModel()
    private val binding by viewBinding(EndClothesFragmentBinding::bind)
    private val endRVAdapter: EndRVAdapter by lazy {
        EndRVAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupClicks()
        endViewModel.getClothes()
    }

    private fun setupClicks() {
        endRVAdapter.onItemClicked = {

        }
    }

    override fun invalidate() {
        withState(endViewModel) {
            when (it) {
                is EndMainState.Loading -> {
                    binding.contentLoaderProgressBar.show()
                }
                is EndMainState.Data -> {
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
                is EndMainState.Exception -> {
                    binding.contentLoaderProgressBar.hide()
                    requireContext().toast(it.massage)
                }
                is EndMainState.Click -> {

                }
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