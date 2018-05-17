package com.example.kanj.logic

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import com.example.kanj.base.AbstractActivityPresenterImpl
import com.example.kanj.base.MainActivityPresenter
import com.example.kanj.base.MainActivityScene
import com.example.kanj.base.PullListPresenter
import javax.inject.Inject

const val ARG_REPO_ADDED = "ARG_REPO_ADDED"
const val SAVED_USER_NAME = "SAVED_USER_NAME"
const val SAVED_REPO_NAME = "SAVED_REPO_NAME"

class MainActivityPresenterImpl @Inject constructor(private val sharedPrefs: SharedPreferences)
    : AbstractActivityPresenterImpl<MainActivityScene>(), MainActivityPresenter {
    var repoAdded: Boolean = false

    override fun onSceneAdded(scene: MainActivityScene, savedState: Bundle?) {
        super.onSceneAdded(scene, savedState)
        savedState?.let {
            repoAdded = it.getBoolean(ARG_REPO_ADDED)
        }
        val savedRepo = sharedPrefs.getString(SAVED_REPO_NAME, "")
        val savedName = sharedPrefs.getString(SAVED_USER_NAME, "")
        if (savedName != "" && savedRepo != "") {
            displayPullListFragment(savedName, savedRepo)
        }
    }

    override fun onRemoveRepoClick() {
        scene?.removePullFragment()
        val prefsEditor = sharedPrefs.edit()
        prefsEditor.remove(SAVED_USER_NAME)
        prefsEditor.remove(SAVED_REPO_NAME)
        prefsEditor.apply()
        repoAdded = false
    }

    override fun onRepoInput(input: String) {
        val slash = input.indexOf("/")
        if (slash == -1 || slash == 0 || slash == input.length - 1) {
            scene?.showInvalidInputError()
        } else {
            val userName = input.substring(0, slash)
            val repoName = input.substring(slash + 1, input.length)
            displayPullListFragment(userName, repoName)
            val prefsEditor = sharedPrefs.edit()
            prefsEditor.putString(SAVED_USER_NAME, userName)
            prefsEditor.putString(SAVED_REPO_NAME, repoName)
            prefsEditor.apply()
        }
    }

    private fun displayPullListFragment(name: String, repo: String) {
        val args = Bundle()
        args.putString(PullListPresenter.ARG_USER, name)
        args.putString(PullListPresenter.ARG_REPO, repo)
        scene?.showPullFragment(args)
        repoAdded = true
    }

    override fun isRepoAdded() = repoAdded

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        outState.putBoolean(ARG_REPO_ADDED, repoAdded)
    }
}
