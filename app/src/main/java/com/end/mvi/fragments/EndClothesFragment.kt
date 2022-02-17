package com.end.mvi.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.end.mvi.R
import com.end.mvi.adapters.EndRVAdapter
import com.end.mvi.databinding.EndClothesFragmentBinding
import com.end.mvi.models.ClothesShoesModel
import com.end.mvi.utils.EndAction
import com.end.mvi.utils.EndMainPageUIState
import com.end.mvi.utils.binding.viewBinding
import com.end.mvi.utils.toast
import com.end.mvi.viewmodels.EndIntention
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel

class EndClothesFragment : BaseEndFragment(R.layout.end_clothes_fragment) {

    private val endIntention: EndIntention by viewModel()
    private val binding by viewBinding(EndClothesFragmentBinding::bind)
    private val endRVAdapter: EndRVAdapter by lazy {
        EndRVAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupObservers()
    }

    private fun setupObservers() {
        disposablesOnDestroy.add(endIntention.endAction(EndAction.LoadEndClothesItems)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                when (it) {
                    is EndMainPageUIState.SucceedEndClothes -> {
                        val response = it.data as ClothesShoesModel
                        with(binding) {
                            toolbarLayout.toolbarTitleTextView.text = response.title
                            binding.contentLoaderProgressBar.hide()
                            binding.itemsCountTextView.text =
                                requireContext().getString(
                                    R.string.items_count,
                                    response.product_count.toString()
                                )
                        }
                        endRVAdapter.setItems(response.products)
                    }
                    is EndMainPageUIState.Loading -> {
                        binding.contentLoaderProgressBar.show()
                    }
                    is EndMainPageUIState.Error -> {
                        binding.contentLoaderProgressBar.hide()
                        requireContext().toast(it.massage)
                    }
                }

            }
        )
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