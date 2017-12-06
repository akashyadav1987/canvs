package com.canvas.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

/**
 * Created by akashyadav on 12/2/17.
 */

public class BookMarkFragment extends CommonFragment implements AppRequest {
    private RecyclerView favorites_list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View favoritesView = inflater.inflate(R.layout.favorites_fragment,null);
        favorites_list = favoritesView.findViewById(R.id.favorites_list);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        favorites_list.addItemDecoration(new GridInsetDecoration(getActivity()));
        screenTitle="BOOKMARKS";
        GridLayoutManager gridLayoutManager = new GridLayoutManager(GlobalReferences.getInstance().baseActivity,2,GridLayoutManager.VERTICAL,false);
        favorites_list.setLayoutManager(gridLayoutManager);
        ArrayList<Favorite> favorites = new ArrayList<>();
        Favorite favorite = new Favorite();
        favorite.setTitle("The Two Dudes 2");
        favorite.setArtistName("Artist Name 2");
        favorites.add(favorite);

        Favorite favorite1 = new Favorite();
        favorite1.setTitle("Marry Had a little Lamb Whats This");
        favorite1.setArtistName("Artist Name3");
        favorites.add(favorite1);


        Favorite favorite2 = new Favorite();
        favorite2.setTitle("The Two Dudes4");
        favorite2.setArtistName("Artist Name4");
        favorites.add(favorite2);

        FavoritesAndBookMarkAdapter favoritesAndBookMarkAdapter = new FavoritesAndBookMarkAdapter(favorites);
        favorites_list.setAdapter(favoritesAndBookMarkAdapter);
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
