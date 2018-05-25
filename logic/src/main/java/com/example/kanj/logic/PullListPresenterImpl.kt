package com.example.kanj.logic

import android.arch.paging.DataSource
import android.arch.paging.PageKeyedDataSource
import android.arch.paging.RxPagedListBuilder
import android.os.Bundle
import com.example.kanj.api.GithubService
import com.example.kanj.api.response.PullRequest
import com.example.kanj.base.AbstractFragmentPresenterImpl
import com.example.kanj.base.PullListPresenter
import com.example.kanj.base.PullListScene
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.io.IOException
import javax.inject.Inject

const val GITHUB_API_DEAFULT_PAGE_SIZE = 30
const val DATA_STATE_ERROR  = 0
const val DATA_STATE_BLANK  = 1
const val DATA_STATE_OK  = 2

class PullListPresenterImpl @Inject constructor(private val githubService: GithubService)
    : AbstractFragmentPresenterImpl<PullListScene>(), PullListPresenter {
    val dataState = PublishSubject.create<Int>()
    var fetchPageSubscription: Disposable? = null
    var dataStateSubscription: Disposable? = null
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
            //fetchPage(1)

            val pagedPullList = RxPagedListBuilder<Int, PullRequest>(PullsDataSourceFactory(), GITHUB_API_DEAFULT_PAGE_SIZE)
                    .setFetchScheduler(Schedulers.io())
                    .setNotifyScheduler(AndroidSchedulers.mainThread())
                    .buildObservable()
            fetchPageSubscription = pagedPullList
                    .subscribe({
                        fragmentScene.setPagedPullList(it)
                    }, {
                        it.printStackTrace()
                    })

            dataStateSubscription = dataState
                    .observeOn(AndroidSchedulers.mainThread()) //Doesn't happen by default; people are liars
                    .subscribe({
                        when (it) {
                            DATA_STATE_ERROR, DATA_STATE_BLANK -> fragmentScene.showNoDataError()
                            DATA_STATE_OK -> fragmentScene.hideProgressBar()
                        }
                    })
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(PullListPresenter.ARG_USER, user)
        outState.putString(PullListPresenter.ARG_REPO, repo)
    }

    override fun onSceneViewDestroyed() {
        fetchPageSubscription?.dispose()
        dataStateSubscription?.dispose()
    }

    inner class PullsDataSourceFactory : DataSource.Factory<Int, PullRequest>() {
        override fun create(): DataSource<Int, PullRequest> = PullsDataSource()
    }

    inner class PullsDataSource: PageKeyedDataSource<Int, PullRequest>() {
        override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, PullRequest>) {
            try {
                val pulls = githubService.getOpenPulls(user, repo, 1).execute().body()
                if (pulls != null) {
                    val nextKey = if (pulls.size == 30) {
                        2
                    } else {
                        null
                    }
                    callback.onResult(pulls, null, nextKey)
                    if (pulls.size == 0) {
                        dataState.onNext(DATA_STATE_BLANK)
                    } else {
                        dataState.onNext(DATA_STATE_OK)
                    }
                } else {
                    callback.onResult(ArrayList<PullRequest>(), null, null)
                    dataState.onNext(DATA_STATE_BLANK)
                }
            } catch (ioe: IOException) {
                // What do you want from me?
                dataState.onNext(DATA_STATE_ERROR)
            }
        }

        override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PullRequest>) {
            try {
                val pulls = githubService.getOpenPulls(user, repo, params.key).execute().body()
                if (pulls != null) {
                    val nextKey = if (pulls.size == 30) {
                        params.key + 1
                    } else {
                        null
                    }
                    callback.onResult(pulls, nextKey)
                } else {
                    callback.onResult(ArrayList<PullRequest>(), null)
                }
            } catch (ioe: IOException) {
                // What do you want from me?
                dataState.onNext(DATA_STATE_ERROR)
            }
        }

        override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PullRequest>) {
            // I don't think I need this
        }
    }
}
