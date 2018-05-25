package com.example.kanj.base

interface PullListPresenter : AbstractFragmentPresenter<PullListScene> {
    companion object {
        val ARG_USER = "ARG_USER"
        val ARG_REPO = "ARG_REPO"
    }

    //fun fetchMore(): Boolean
}
