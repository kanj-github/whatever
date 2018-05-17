package com.example.kanj.logic.dagger;

import com.example.kanj.base.MainActivityPresenter;
import com.example.kanj.base.PullListPresenter;
import com.example.kanj.logic.MainActivityPresenterImpl;
import com.example.kanj.logic.PullListPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class LogicModule {
    @Provides
    public MainActivityPresenter provideMainActivityPresenter(MainActivityPresenterImpl presenter) {
        return presenter;
    }

    @Provides
    public PullListPresenter providePullListPresenter(PullListPresenterImpl presenter) {
        return presenter;
    }
}
