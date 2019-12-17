package com.app.imagefiltering.di;

import android.content.Context;

import com.app.imagefiltering.App;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    Context provideContext(App application) {
        return application.getApplicationContext();
    }
}
