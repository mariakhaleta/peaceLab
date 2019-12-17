package com.app.imagefiltering.images;

import android.graphics.Bitmap;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.imagefiltering.images.logic.ImagesLoader;
import com.app.imagefiltering.images.ui.ImagesActivity;
import com.library.pictureeditorlib.PictureEditorEngine;
import com.library.pictureeditorlib.PictureFilters;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ImagesViewModel extends ViewModel {

    private final MutableLiveData<Boolean> mImagesLoading = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Pair<String, Bitmap>>> mImages = new MutableLiveData<>();
    private CompositeDisposable mCompositeDisposable;
    private ImagesActivity mActivity;

    private PictureEditorEngine mPictureEditorEngine = new PictureEditorEngine();

    ImagesViewModel(ImagesActivity activity) {
        mActivity = activity;
        mCompositeDisposable = new CompositeDisposable();
        mPictureEditorEngine.setPicturePath("");
    }

    public LiveData<Boolean> isImagesLoading() {
        return mImagesLoading;
    }

    public LiveData<ArrayList<Pair<String, Bitmap>>> getImages() {
        return mImages;
    }

    public void fetchImages() {
        mImagesLoading.setValue(true);

        mCompositeDisposable.add(ImagesLoader.getAllImagesPath(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<Pair<String, Bitmap>>>() {

                    @Override
                    public void onSuccess(ArrayList<Pair<String, Bitmap>> bitmaps) {
                        mImages.setValue(bitmaps);
                        mImagesLoading.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mImagesLoading.setValue(false);
                    }
                }));

    }

    public String filterImage(PictureFilters pictureFilter, String path) {
        int filter = pictureFilter.getValue();
        double[] params = new double[1];
        params[0] = filter;
        mPictureEditorEngine.filterPicture(params, path);
        String pathToSave = "sdcard/filteredPicture.png";
        mPictureEditorEngine.saveFilteredPicture("sdcard/filteredPicture.png");

        return pathToSave;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }
    }
}
