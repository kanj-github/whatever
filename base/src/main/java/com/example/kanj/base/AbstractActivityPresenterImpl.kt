package com.example.kanj.base

import android.app.Activity
import android.os.Bundle

abstract class AbstractActivityPresenterImpl<T : AbstractActivityScene> : AbstractActivityPresenter<T>{
    var scene: T? = null

    open override fun onSceneAdded(scene: T, savedState: Bundle?) {
        this.scene = scene
    }
    override fun onActivityDestroyed(activity: Activity?) {
        scene = null
    }
    override fun onActivityPaused(activity: Activity?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityResumed(activity: Activity?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityStarted(activity: Activity?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityStopped(activity: Activity?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}