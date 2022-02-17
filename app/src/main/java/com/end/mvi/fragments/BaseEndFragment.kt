package com.end.mvi.fragments

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.disposables.CompositeDisposable

open class BaseEndFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {
    protected var disposablesOnDestroy = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        disposablesOnDestroy.clear()
    }
}