package com.app.imagefiltering.images;

import com.app.imagefiltering.images.ui.ImagesActivity;
import com.library.pictureeditorlib.PictureEditorEngine;

import dagger.Module;
import dagger.Provides;

@Module
public class ImagesModel {

    @Provides
    ImagesViewModelFactory provideImagesViewModelFactory(ImagesActivity activity) {
        return new ImagesViewModelFactory(activity);
    }
}
