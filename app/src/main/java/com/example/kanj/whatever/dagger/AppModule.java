package com.example.kanj.whatever.dagger;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.kanj.whatever.MyApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private static final String SHARED_PREFS_NAME = "whatever_data";
    private MyApplication app;

    public AppModule(MyApplication app) {
        this.app = app;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences() {
        return app.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }
}
