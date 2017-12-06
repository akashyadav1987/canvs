package com.canvas.adpater;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.canvas.R;
import com.canvas.common.GlobalReferences;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by akashyadav on 12/3/17.
 */

public class SeenAdapterImages extends RecyclerView.Adapter<SeenAdapterImages.MyViewHolder> {
    private ArrayList<String> images;

    public SeenAdapterImages(ArrayList<String> images) {
        this.images = images;
    }

    @Override
    public SeenAdapterImages.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View images = GlobalReferences.getInstance().baseActivity.getLayoutInflater().inflate(R.layout.seen_adapter_images,null);
        return new MyViewHolder(images);
    }

    @Override
    public void onBindViewHolder(SeenAdapterImages.MyViewHolder holder, int position) {
        try {
            String url = images.get(position);
            try {
                Picasso.with(GlobalReferences.getInstance().baseActivity).load(url).placeholder(R.color.grey_).error(R.color.grey_);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
