package com.canvas.adpater;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.canvas.R;
import com.canvas.widget.RobotoCondensedTextView;

/**
 * Created by akashyadav on 12/17/17.
 */

public class AdapterSearchView extends BaseAdapter {
    private String[] list;
    private Activity activity;
    public  AdapterSearchView(Activity activities,String[] list){
        this.list =list;
        this.activity =activities;
    }


    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem(int i) {
        return list[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.e("i =",list[i]);
        ViewHolder viewHolder =null;
        if(view==null){
            view =  activity.getLayoutInflater().inflate(R.layout.search_adpater,null);
            viewHolder =new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        try{
            if(list[i]!=null)
            viewHolder.title.setText(list[i]+"");
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }
    public class ViewHolder {
        private RobotoCondensedTextView title;
        public ViewHolder(View view){
            title = view.findViewById(R.id.searched_item);
        }
    }
}
