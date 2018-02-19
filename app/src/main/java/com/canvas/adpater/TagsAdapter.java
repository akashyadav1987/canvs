package com.canvas.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.canvas.R;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by ashish on 08/10/17.
 */

public class TagsAdapter extends RecyclerView.Adapter<TagsAdapter.MyViewHolder> {

    private ArrayList<String> tagStringList;
    Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView_name;

        public MyViewHolder(View view) {
            super(view);

            textView_name= (TextView) view.findViewById(R.id.tv_tag);


        }
    }


    public TagsAdapter(ArrayList<String> services, Context context) {
        this.tagStringList = services;
        this.mContext = context;
    }

    @Override
    public TagsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_tags, parent, false);

        return new TagsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String tag = tagStringList.get(position);

        holder.textView_name.setText(tag);


    }





    @Override
    public int getItemCount() {
        Log.e(TAG, "getItemCount: "+tagStringList.size() );
        return tagStringList.size();
    }
}
