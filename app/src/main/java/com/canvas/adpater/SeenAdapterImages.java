package com.canvas.adpater;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.canvas.R;
import com.canvas.common.GlobalReferences;
import com.canvas.controller.RealmController;
import com.canvas.fragment.SeenFragment;
import com.canvas.model.Murals;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.realm.RealmResults;

/**
 * Created by akashyadav on 12/3/17.
 */

public class SeenAdapterImages extends RecyclerView.Adapter<SeenAdapterImages.MyViewHolder> {
    private RealmResults<Murals> images;
    private SeenFragment seenMurals;
    private ArrayList<Murals> seen;

    public SeenAdapterImages(SeenFragment seenMurals, RealmResults<Murals> images) {
        this.images = images;
        this.seenMurals = seenMurals;
        seen = new ArrayList<>();
    }

    @Override
    public SeenAdapterImages.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View images = GlobalReferences.getInstance().baseActivity.getLayoutInflater().inflate(R.layout.seen_adapter_images,null);
        return new MyViewHolder(images);
    }

    @Override
    public void onBindViewHolder(SeenAdapterImages.MyViewHolder holder, int position) {
        try {
            Murals murals = images.get(position);
            try {
                try {
                    String image_url = "https://canvs.cruxcode.nyc/mural_thumb_" + murals.getImage_resource_id().toLowerCase() + ".jpg?size=thumb&requestType=image";
                    Picasso.with(GlobalReferences.getInstance().baseActivity).load(image_url).placeholder(R.color.grey_).error(R.color.grey_).into(holder.imageView);
                    if(RealmController.getInstance().isSeenMuralExist(murals.getId())){
                        holder.gray_color_img.setVisibility(View.VISIBLE);
                     //holder.imageView.setColorFilter(Color.parseColor("#10CFCECF"), PorterDuff.Mode.SRC_IN);
                        seen.add(murals);
                    }else {
                        holder.gray_color_img.setVisibility(View.GONE);
                    }
                    if(position==images.size()-1){
                        try{
                           // ((SeenMurals)seenMurals).seen(seen);
                            Log.e("seen size",seen.size()+"");
                        }catch (Exception e){
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        private ImageView imageView,gray_color_img;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.images);
            gray_color_img =itemView.findViewById(R.id.gray_color_img);
        }
    }
}
