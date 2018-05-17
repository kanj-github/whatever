package com.example.kanj.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import javax.inject.Inject

abstract class AbstractActivity<I, S: AbstractActivityScene, T : AbstractActivityPresenter<in S>>
    : AppCompatActivity(), AbstractActivityScene {
    @Inject lateinit var presenter: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewAndInject(savedInstanceState)
        presenter.onSceneAdded(this as S, savedInstanceState)
    }

    override fun onDestroy() {
        presenter.onActivityDestroyed(this)
        super.onDestroy()
    }

    abstract fun setupViewAndInject(savedInstanceState: Bundle?)

    abstract fun injector(): I

    abstract fun setToolbarTitle(title: String)

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.let { presenter.onActivitySaveInstanceState(this, it) }
        super.onSaveInstanceState(outState)
    }
}
