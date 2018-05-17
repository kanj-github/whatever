package com.example.kanj.base

import android.os.Bundle

interface AbstractFragmentPresenter<T : AbstractFragmentScene> {
    fun onFragmentAdded(fragmentScene: T, data: Bundle?, savedInstanceState: Bundle?)

    fun onFragmentRemoved()

    fun onSceneViewDestroyed()

    fun onSaveInstanceState(outState: Bundle)
}