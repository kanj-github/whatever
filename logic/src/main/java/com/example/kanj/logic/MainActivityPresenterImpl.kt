package com.example.kanj.logic

import android.app.Activity
import android.os.Bundle
import com.example.kanj.base.AbstractActivityPresenterImpl
import com.example.kanj.base.MainActivityPresenter
import com.example.kanj.base.MainActivityScene
import com.example.kanj.base.PullListPresenter
import javax.inject.Inject

const val ARG_REPO_ADDED = "ARG_REPO_ADDED"

class MainActivityPresenterImpl
    @Inject constructor(): AbstractActivityPresenterImpl<MainActivityScene>(), MainActivityPresenter {
    var repoAdded: Boolean = false

    override fun onSceneAdded(scene: MainActivityScene, savedState: Bundle?) {
        super.onSceneAdded(scene, savedState)
        savedState?.let {
            repoAdded = it.getBoolean(ARG_REPO_ADDED)
        }
    }

    override fun onRemoveRepoClick() {
        scene?.removePullFragment()
        repoAdded = false
    }

    override fun onRepoInput(input: String) {
        val slash = input.indexOf("/")
        if (slash == -1 || slash == 0 || slash == input.length - 1) {
            scene?.showInvalidInputError()
        } else {
            val args = Bundle()
            args.putString(PullListPresenter.ARG_USER, input.substring(0, slash))
            args.putString(PullListPresenter.ARG_REPO, input.substring(slash + 1, input.length))
            scene?.showPullFragment(args)
            repoAdded = true
        }
    }

    override fun isRepoAdded() = repoAdded

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        outState.putBoolean(ARG_REPO_ADDED, repoAdded)
    }
}
