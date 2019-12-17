package com.app.imagefiltering.images.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.imagefiltering.R;
import com.app.imagefiltering.images.ImagesViewModel;
import com.app.imagefiltering.images.ImagesViewModelFactory;
import com.library.pictureeditorlib.PictureFilters;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;

public class ImagesActivity extends AppCompatActivity implements IImageClickListener {

    private static final int GRID_SPAN_COUNT = 4;
    @BindView(R.id.images_recycler_view)
    RecyclerView mImagesRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @Inject
    ImagesViewModelFactory mImagesViewModelFactory;
    private ImagesViewModel mImagesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.images_activity);

        ButterKnife.bind(this);

        mImagesViewModel =
                ViewModelProviders.of(this, mImagesViewModelFactory).get(ImagesViewModel.class);

        mImagesViewModel.isImagesLoading().observe(this, mImagesLoading -> {
            if (mImagesLoading != null) {
                progressBar.setVisibility(mImagesLoading ? View.VISIBLE : View.GONE);
            }
        });

        mImagesViewModel.getImages().observe(this, mImages -> {
            if (mImages != null) {
                mImagesRecyclerView.setAdapter(new ImagesRecyclerViewAdapter(mImages, this));
                mImagesRecyclerView.setLayoutManager(new GridLayoutManager(this, GRID_SPAN_COUNT));
            }
        });

        requestPermissions();
    }

    private void requestPermissions() {
        requestPermissions(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mImagesViewModel.fetchImages();
        }
    }

    @Override
    public void onClicked(String path) {
        String pathSaved = mImagesViewModel.filterImage(PictureFilters.COLORMAP_AUTUMN, path);

        Toast.makeText(this, "Picture saved in:" + pathSaved, Toast.LENGTH_LONG).show();
    }
}
