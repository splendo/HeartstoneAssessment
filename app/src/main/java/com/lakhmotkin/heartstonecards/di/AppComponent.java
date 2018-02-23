package com.lakhmotkin.heartstonecards.di;


import com.lakhmotkin.heartstonecards.App;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by igorlakhmotkin on 23/02/2018.
 */

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        BrodjeListActivityModule.class,
        ToolsModule.class
})
public interface AppComponent {
    void inject(App application);
}
