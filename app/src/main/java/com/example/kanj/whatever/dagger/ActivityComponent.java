package com.example.kanj.whatever.dagger;

import com.example.kanj.base.dagger.PerActivity;
import com.example.kanj.logic.dagger.LogicModule;
import com.example.kanj.whatever.MainActivity;
import com.example.kanj.whatever.PullListFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, LogicModule.class})
public interface ActivityComponent {
    void inject(MainActivity activity);

    void inject(PullListFragment frag);
}
