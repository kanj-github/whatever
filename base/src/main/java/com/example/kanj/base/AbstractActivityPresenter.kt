package com.example.kanj.base

import android.app.Application
import android.os.Bundle

interface AbstractActivityPresenter<T : AbstractActivityScene> : Application.ActivityLifecycleCallbacks {
    fun onSceneAdded(scene: T, savedState: Bundle?)
}