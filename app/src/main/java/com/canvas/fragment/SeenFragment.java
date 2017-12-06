package com.canvas.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.canvas.R;
import com.canvas.adpater.SeenAdapterImages;
import com.canvas.common.CommonFragment;
import com.canvas.common.Constants;
import com.canvas.common.GlobalReferences;
import com.canvas.io.http.BaseTask;
import com.canvas.io.http.BaseTaskJson;
import com.canvas.io.listener.AppRequest;
import com.canvas.utils.GridInsetDecoration;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by akashyadav on 12/3/17.
 */

public class SeenFragment extends CommonFragment implements AppRequest {
    @Nullable
    private RecyclerView seen_images_list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View favoritesView = inflater.inflate(R.layout.seen_fragment,null);
        seen_images_list = favoritesView.findViewById(R.id.seen_images_list);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        seen_images_list.addItemDecoration(new GridInsetDecoration(getActivity()));
        screenTitle="SEEN";
        GridLayoutManager gridLayoutManager = new GridLayoutManager(GlobalReferences.getInstance().baseActivity,4,GridLayoutManager.VERTICAL,false);
        seen_images_list.setLayoutManager(gridLayoutManager);
        ArrayList<String> favorites = new ArrayList<>();

        favorites.add("1");
        favorites.add("2");
        favorites.add("3");
        favorites.add("1");
        favorites.add("2");
        favorites.add("3");
        favorites.add("1");
        favorites.add("2");
        favorites.add("3");

        SeenAdapterImages seenAdapterImages = new SeenAdapterImages(favorites);
        seen_images_list.setAdapter(seenAdapterImages);
        return favoritesView;
    }

    @Override
    public <T> void onRequestStarted(BaseTask<T> listener, Constants.RequestParam requestParam) {
        try{

        }catch (Exception e){
            e.printStackTrace();
        }
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

    @Override
    public void onRefresh() {
        super.onRefresh();
    }
}
