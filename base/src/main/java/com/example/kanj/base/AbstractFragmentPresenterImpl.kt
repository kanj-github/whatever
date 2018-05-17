package com.example.kanj.base

import android.os.Bundle

abstract class AbstractFragmentPresenterImpl<T : AbstractFragmentScene> : AbstractFragmentPresenter<T> {
    var fragmentScene: T? = null

    open override fun onFragmentAdded(fragmentScene: T, data: Bundle?, savedInstanceState: Bundle?) {
        this.fragmentScene = fragmentScene
    }

    override fun onFragmentRemoved() {
        this.fragmentScene = null
    }
}