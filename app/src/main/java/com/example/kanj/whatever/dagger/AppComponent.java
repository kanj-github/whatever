package com.example.kanj.whatever.dagger;

import android.content.SharedPreferences;

import com.example.kanj.api.GithubService;
import com.example.kanj.api.dagger.ApiModule;
import com.example.kanj.logic.dagger.LogicAppComponent;
import com.example.kanj.whatever.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ApiModule.class})
public interface AppComponent extends LogicAppComponent {
    GithubService getGithubService();

    SharedPreferences getSharedPreferences();
}
