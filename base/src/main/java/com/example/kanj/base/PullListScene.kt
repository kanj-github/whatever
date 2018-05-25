package com.example.kanj.base

import android.arch.paging.PagedList
import com.example.kanj.api.response.PullRequest

interface PullListScene : AbstractFragmentScene {
    fun setPagedPullList(pagedList: PagedList<PullRequest>)

    fun setToolbarTitle(title: String)

    fun showNoDataError()

    fun hideProgressBar()
}