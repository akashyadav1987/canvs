package com.canvas.adpater;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.canvas.R;
import com.canvas.common.GlobalReferences;
import com.canvas.model.Favorite;

import java.util.ArrayList;

/**
 * Created by akashyadav on 12/2/17.
 */

public class FavoritesAndBookMarkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Favorite> favoriteArrayList =null;
    public FavoritesAndBookMarkAdapter(ArrayList<Favorite> list){
        this.favoriteArrayList =list;

    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View adapterItem = GlobalReferences.getInstance().baseActivity.getLayoutInflater().inflate(R.layout.item_fav_and_bookmark,null);
        return new ViewHolder(adapterItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
     try{

     }catch (Exception e){

     }
    }

    @Override
    public int getItemCount() {
        return favoriteArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
