package com.app.imagefiltering.images.logic;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.util.Pair;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.Single;

public class ImagesLoader {

    public static Single<ArrayList<Pair<String, Bitmap>>> getAllImagesPath(Activity activity) {
        Cursor cursor = activity.getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.MediaColumns.DATA,
                        MediaStore.Images.Media.BUCKET_DISPLAY_NAME},
                null, null, null);

        if (cursor != null) {
            int columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

            ArrayList<Pair<String, Bitmap>> allImages = new ArrayList<>();
            while (cursor.moveToNext()) {
                File file = new File(cursor.getString(columnIndexData));
                if(file.getAbsolutePath().contains(".png")) {
                    allImages.add(new Pair<>(file.getAbsolutePath(), BitmapFactory.decodeFile(file.getAbsolutePath())));
                }
                if (allImages.size() >= 60)
                    break;
            }

            return Single.just(allImages);
        }
        throw new IllegalArgumentException("Unable read images path");
    }

}
