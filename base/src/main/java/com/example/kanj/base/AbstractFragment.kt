package com.example.kanj.base

import android.os.Bundle
import android.support.v4.app.Fragment

abstract class AbstractFragment<I, S : AbstractFragmentScene> : Fragment(), AbstractFragmentScene {
    abstract fun injectFragment(injector: I)

    abstract fun getFragmentPresenter(): AbstractFragmentPresenter<S>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        injectFragment((activity as AbstractActivity<I,*,*>).injector())
        getFragmentPresenter().onFragmentAdded(this as S, arguments, savedInstanceState)
    }

    override fun onDetach() {
        super.onDetach()
        getFragmentPresenter().onFragmentRemoved()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        getFragmentPresenter().onSceneViewDestroyed()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        getFragmentPresenter().onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
    }
}
