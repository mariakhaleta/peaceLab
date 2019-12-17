package com.app.imagefiltering.images.ui;

import android.graphics.Bitmap;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.imagefiltering.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImagesRecyclerViewAdapter extends RecyclerView.Adapter<ImagesRecyclerViewAdapter.ImagesViewHolder> {

    private ArrayList<Pair<String, Bitmap>> mImages;
    private IImageClickListener mIImageClickListener;

    public ImagesRecyclerViewAdapter(ArrayList<Pair<String, Bitmap>> images, IImageClickListener listener) {
        mImages = images;
        mIImageClickListener = listener;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_linear_layout
                , parent, false);
        return new ImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesViewHolder holder, int position) {
        Bitmap image = mImages.get(position).second;
        holder.bind(image);

        holder.itemView.setOnClickListener(view -> mIImageClickListener.onClicked(mImages.get(position).first));
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    static final class ImagesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_view)
        ImageView mImageView;

        ImagesViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        void bind(Bitmap image) {
            mImageView.setImageBitmap(image);
        }
    }
}
