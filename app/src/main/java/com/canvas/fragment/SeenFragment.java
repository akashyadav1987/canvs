package com.canvas.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.canvas.R;
import com.canvas.adpater.SeenAdapterImages;
import com.canvas.common.CommonFragment;
import com.canvas.common.Constants;
import com.canvas.common.GlobalReferences;
import com.canvas.controller.RealmController;
import com.canvas.io.http.BaseTask;
import com.canvas.io.http.BaseTaskJson;
import com.canvas.io.listener.AppRequest;
import com.canvas.listener.SeenMurals;
import com.canvas.model.Murals;
import com.canvas.utils.GridInsetDecoration;
import com.canvas.widget.TextThumbSeekBar;

import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.RealmResults;

/**
 * Created by akashyadav on 12/3/17.
 */

public class SeenFragment extends CommonFragment implements AppRequest,SeenMurals {
    @Nullable
    private RecyclerView seen_images_list;
    private TextThumbSeekBar seek_bar;
    private TextView progress_text_vew;
    private ArrayList<Integer> seen;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View favoritesView = inflater.inflate(R.layout.seen_fragment,null);
        setHasOptionsMenu(true);
        seen_images_list = (RecyclerView) favoritesView.findViewById(R.id.seen_images_list);
        seen = new ArrayList<>();
        setHasOptionsMenu(true);
        seek_bar = (TextThumbSeekBar) favoritesView.findViewById(R.id.seek_bar);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        seen_images_list.addItemDecoration(new GridInsetDecoration(getActivity()));
        screenTitle="SEEN";
        GridLayoutManager gridLayoutManager = new GridLayoutManager(GlobalReferences.getInstance().baseActivity,4,GridLayoutManager.VERTICAL,false);
        seen_images_list.setLayoutManager(gridLayoutManager);

        RealmResults<Murals> muralses = RealmController.getInstance().getAllMurals();
        seek_bar.setMax(muralses.size());
        for(int i=0;i<muralses.size();i++) {
            if (RealmController.getInstance().isSeenMuralExist(muralses.get(i).getId())) {
                seen.add(i);
            }
        }
        progress_text_vew = (TextView) favoritesView.findViewById(R.id.progress_text_vew);
        progress_text_vew.setText("You've already seen "+ seen.size()+"%" +" of jersy city's murals!Keep going!");
        seek_bar.setProgress(seen.size());

        SeenAdapterImages seenAdapterImages = new SeenAdapterImages(this,muralses);
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

    @Override
    public void seen(ArrayList<Murals> muralses) {
        try
        {

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
    }
}
