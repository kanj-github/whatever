package com.example.kanj.base

import com.example.kanj.api.response.PullRequest

interface PullListScene : AbstractFragmentScene {
    fun addPullItemsToList(items: ArrayList<PullRequest>)

    fun setToolbarTitle(title: String)

    fun showNoDataError()
}