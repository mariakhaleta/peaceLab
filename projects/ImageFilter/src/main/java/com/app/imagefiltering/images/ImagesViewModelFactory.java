package com.app.imagefiltering.images;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.app.imagefiltering.images.ui.ImagesActivity;

public class ImagesViewModelFactory implements ViewModelProvider.Factory {
    private ImagesActivity mActivity;

    ImagesViewModelFactory(ImagesActivity activity) {
        mActivity = activity;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ImagesViewModel.class)) {
            return (T) new ImagesViewModel(mActivity);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
