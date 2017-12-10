package com.canvas.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.canvas.R;
import com.canvas.adpater.FavoritesAndBookMarkAdapter;
import com.canvas.common.CommonFragment;
import com.canvas.common.Constants;
import com.canvas.common.GlobalReferences;
import com.canvas.io.http.BaseTask;
import com.canvas.io.http.BaseTaskJson;
import com.canvas.io.listener.AppRequest;
import com.canvas.model.Favorite;
import com.canvas.utils.GridInsetDecoration;

import org.json.JSONObject;

import java.util.ArrayList;

import adapters.TagsAdapter;

import static android.content.ContentValues.TAG;

/**
 * Created by ashish on 08/12/17.
 */

public class FragmentMuralDetail extends CommonFragment implements AppRequest {
TextView textView_location,textView_about_mural,textView_about_artist,
        textView_direction,textView_add_link_first,textView_add_link_second,textView_add_link_third;
ImageView imageView;
RecyclerView recyclerView_tags;
TagsAdapter tagsAdapter;
TextView tv_mural,tv_author;
double lat,lon;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View muralview = inflater.inflate(R.layout.fragment_mural_details,null);
        Bundle bundle=getArguments();
        String img_id=bundle.getString("image_id");
        String location=bundle.getString("location_text");
        lat=bundle.getDouble("lat");
        lon=bundle.getDouble("lon");
        Log.e(TAG, "onCreateView: "+img_id );
        Log.e(TAG, "onCreateView: "+location );
        imageView=muralview.findViewById(R.id.iv_image_detail);

        textView_location=muralview.findViewById(R.id.tv_location);
        tv_author=muralview.findViewById(R.id.tv_author);
        textView_direction=muralview.findViewById(R.id.tv_get_direction);
        textView_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://maps.google.com/maps?f=d&daddr=" + lat + "," + lon + "&dirflg=d";

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });
        tv_author.setText(bundle.getString("artist"));

        tv_mural=muralview.findViewById(R.id.tv_mural);
        tv_mural.setText(bundle.getString("name"));

        textView_about_artist=muralview.findViewById(R.id.tv_about_artist);
        textView_about_artist.setText(bundle.getString("artist_text"));
        textView_about_mural=muralview.findViewById(R.id.tv_about_mural);
        textView_about_mural.setText(bundle.getString("about_text"));

        textView_add_link_first=muralview.findViewById(R.id.tv_add_link_1);
        textView_add_link_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                            openLink(textView_add_link_first.getText().toString());
            }
        });
        textView_add_link_second=muralview.findViewById(R.id.tv_add_link_2);
        textView_add_link_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(textView_add_link_second.getText().toString());
            }
        });
        textView_add_link_third=muralview.findViewById(R.id.tv_add_link_3);
        textView_add_link_third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(textView_add_link_third.getText().toString());
            }
        });
        String add_link_1=bundle.getString("addlink1");
        if(add_link_1!=null&&!add_link_1.equals("null")){
            textView_add_link_first.setText(add_link_1);
        }else{
            textView_add_link_first.setVisibility(View.GONE);
        }
        String add_link_2=bundle.getString("addlink2");
        if(add_link_2!=null&&!add_link_2.equals("null")){
            textView_add_link_second.setText(add_link_2);
        }else{
            textView_add_link_second.setVisibility(View.GONE);
        }
        String add_link_3=bundle.getString("addlink3");
        if(add_link_3!=null&&!add_link_3.equals("null")){
            textView_add_link_second.setText(add_link_3);
        }else{
            textView_add_link_second.setVisibility(View.GONE);
        }


        String image_url="https://canvs.cruxcode.nyc/mural_large_"+img_id+".jpg?size=large&requestType=image";
        Log.e(TAG, "onCreateView: "+image_url );

        Glide.with(GlobalReferences.getInstance().baseActivity).load(image_url)
                .thumbnail(0.5f)
//                .placeholder(R.drawable.iv)
//                .error(R.drawable.profile)
                .into(imageView);
        textView_location.setText(location);
        String tags=bundle.getString("tags");
        Log.e(TAG, "onCreateView: "+tags );

        String[] tags_list = tags.split(",");
        Log.e(TAG, "onCreateView: "+tags_list.length );

        tagsAdapter=new TagsAdapter(tags_list,GlobalReferences.getInstance().baseActivity);

        recyclerView_tags = muralview.findViewById(R.id.recycler_tags);


        recyclerView_tags.setItemAnimator(new DefaultItemAnimator());
        recyclerView_tags.setLayoutManager(new LinearLayoutManager(GlobalReferences.getInstance().baseActivity, LinearLayoutManager.HORIZONTAL, false));
        recyclerView_tags.setAdapter(tagsAdapter);
        return muralview;
    }

    private void openLink(String link){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
    }
    @Override
    public <T> void onRequestStarted(BaseTask<T> listener, Constants.RequestParam requestParam) {

    }

    @Override
    public <T> void onRequestCompleted(BaseTask<T> listener, Constants.RequestParam requestParam) {

    }

    @Override
    public <T> void onRequestFailed(BaseTask<T> listener, Constants.RequestParam requestParam) {

    }

    @Override
    public <T> void onRequestStarted(BaseTaskJson<JSONObject> listener, Constants.RequestParam requestParam) {

    }

    @Override
    public <T> void onRequestCompleted(BaseTaskJson<JSONObject> listener, Constants.RequestParam requestParam) {

    }

    @Override
    public <T> void onRequestFailed(BaseTaskJson<JSONObject> listener, Constants.RequestParam requestParam) {

    }

    @Override
    public void onResponse(JSONObject jsonObject) {

    }
}
