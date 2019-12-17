package com.app.imagefiltering.di;

import com.app.imagefiltering.images.ImagesModel;
import com.app.imagefiltering.images.ui.ImagesActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector(modules = ImagesModel.class)
    abstract ImagesActivity bindImagesActivity();
}
