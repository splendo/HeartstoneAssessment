package com.lakhmotkin.heartstonecards.di;


import com.lakhmotkin.heartstonecards.App;

import dagger.Module;
import dagger.Provides;

/**
 * Created by igorlakhmotkin on 23/02/2018.
 */

@Module
public class AppModule {
    private final App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    App provideApp() {
        return app;
    }
}
