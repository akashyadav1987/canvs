package com.canvas.adpater;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.canvas.R;
import com.canvas.common.GlobalReferences;
import com.canvas.model.Favorite;
import com.canvas.utils.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by akashyadav on 12/2/17.
 */

public class FavoritesAndBookMarkAdapter extends RecyclerView.Adapter<FavoritesAndBookMarkAdapter.MyViewHolder> {
    private ArrayList<Favorite> favoriteArrayList = null;

    public FavoritesAndBookMarkAdapter(ArrayList<Favorite> list) {
        this.favoriteArrayList = list;
    }

    @Override
    public FavoritesAndBookMarkAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View adapterItem = GlobalReferences.getInstance().baseActivity.getLayoutInflater().inflate(R.layout.item_fav_and_bookmark, null);
        return new MyViewHolder(adapterItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try{
            Favorite favorite = favoriteArrayList.get(position);
            Picasso.with(GlobalReferences.getInstance().baseActivity).load("").placeholder(R.color.grey_).error(R.color.grey_);
            holder.title.setTypeface(Utility.getFontRobotoMedium());
            holder.title.setText(favorite.getTitle()+"");
            holder.artistName.setText(favorite.getArtistName()+"");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return favoriteArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mural_image;
        public TextView title, artistName;
        public MyViewHolder(View itemView) {
            super(itemView);
            mural_image = itemView.findViewById(R.id.mural_image);
            title = itemView.findViewById(R.id.title);
            artistName = itemView.findViewById(R.id.artistname);
        }
    }
}
