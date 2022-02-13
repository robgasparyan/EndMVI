package com.end.mvi.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.end.mvi.R
import com.end.mvi.databinding.EndClothesFragmentBinding
import com.end.mvi.utils.binding.viewBinding
import com.end.mvi.viewmodels.EndViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EndClothesFragment : Fragment(R.layout.end_clothes_fragment) {

    private val endViewModel: EndViewModel by viewModel()
    private val binding by viewBinding(EndClothesFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        endViewModel.abc()
    }

}