package com.example.kanj.base

import android.os.Bundle

interface MainActivityScene : AbstractActivityScene {
    fun showPullFragment(args: Bundle)

    fun removePullFragment()

    fun showInvalidInputError()
}
