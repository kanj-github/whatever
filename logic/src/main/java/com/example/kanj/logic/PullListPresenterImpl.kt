package com.example.kanj.logic

import android.os.Bundle
import com.example.kanj.api.GithubService
import com.example.kanj.api.response.PullRequest
import com.example.kanj.base.AbstractFragmentPresenterImpl
import com.example.kanj.base.PullListPresenter
import com.example.kanj.base.PullListScene
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

const val GITHUB_API_DEAFULT_PAGE_SIZE = 30

class PullListPresenterImpl @Inject constructor(private val githubService: GithubService)
    : AbstractFragmentPresenterImpl<PullListScene>(), PullListPresenter {
    var hasMorePages = true
    var currentPage = 1
    var fetchPageSubscription: Disposable? = null
    lateinit var user: String
    lateinit var repo: String

    override fun onFragmentAdded(fragmentScene: PullListScene, data: Bundle?, savedInstanceState: Bundle?) {
        super.onFragmentAdded(fragmentScene, data, savedInstanceState)
        savedInstanceState?.let {
            user = it.getString(PullListPresenter.ARG_USER, "")
            repo = it.getString(PullListPresenter.ARG_REPO, "")
        } ?: data?.let {
            user = it.getString(PullListPresenter.ARG_USER, "")
            repo = it.getString(PullListPresenter.ARG_REPO, "")
        }

        if (user != "" && repo != "") {
            fragmentScene.setToolbarTitle(user + " - " + repo)
            fetchPage(1)
        }
    }

    override fun fetchMore(): Boolean {
        if (hasMorePages) {
            currentPage++
            fetchPage(currentPage)
            return true
        } else {
            return false
        }
    }

    fun fetchPage(page: Int) {
        fetchPageSubscription = githubService.getOpenPulls(user, repo, /*PULL_STATE_OPEN,*/ page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({response->
                    response?.let {
                        handleFetchPageResponse(ArrayList(it))
                    }
                }, {
                    fragmentScene?.showNoDataError()
                })
    }

    /*fun fetchPage(page: Int) {
        val start = when(page) {
            1 -> 1
            2 -> 31
            3 -> 61
            else -> 0
        }
        val end = when(page) {
            1 -> 31
            2 -> 61
            3 -> 66
            else -> 0
        }
        fetchPageSubscription = Observable.just(PullRequest.mock(start, end))
                .delay(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({response->
                    response?.let {
                        Log.v("What", "Fetched " + it.size)
                        handleFetchPageResponse(ArrayList(it))
                    }
                }, {
                    it.printStackTrace()
                })
    }*/

    fun handleFetchPageResponse(items: ArrayList<PullRequest>) {
        if (items.size > 0) {
            fragmentScene?.addPullItemsToList(items)
        } else {
            fragmentScene?.showNoDataError()
        }
        if (items.size < GITHUB_API_DEAFULT_PAGE_SIZE) {
            hasMorePages = false
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(PullListPresenter.ARG_USER, user)
        outState.putString(PullListPresenter.ARG_REPO, repo)
    }

    override fun onSceneViewDestroyed() {
        fetchPageSubscription?.dispose()
    }
}
