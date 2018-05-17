package com.example.kanj.whatever

import android.app.Application
import com.example.kanj.whatever.dagger.AppComponent
import com.example.kanj.whatever.dagger.AppModule
import com.example.kanj.whatever.dagger.DaggerAppComponent

class MyApplication : Application() {
    val component: AppComponent

    init {
        component = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}
