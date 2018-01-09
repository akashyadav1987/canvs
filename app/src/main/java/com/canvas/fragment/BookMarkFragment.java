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
import com.canvas.adpater.BookMarkAdapter;
import com.canvas.common.CommonFragment;
import com.canvas.common.Constants;
import com.canvas.common.GlobalReferences;
import com.canvas.controller.RealmController;
import com.canvas.io.http.BaseTask;
import com.canvas.io.http.BaseTaskJson;
import com.canvas.io.listener.AppRequest;
import com.canvas.model.BookmarkedMural;
import com.canvas.utils.GridInsetDecoration;

import org.json.JSONObject;

import io.realm.RealmResults;

/**
 * Created by akashyadav on 12/2/17.
 */

public class BookMarkFragment extends CommonFragment implements AppRequest {
    private RecyclerView favorites_list;
    private TextView no_con;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View favoritesView = inflater.inflate(R.layout.bookmark_fragment,null);
        setHasOptionsMenu(true);
        favorites_list = favoritesView.findViewById(R.id.favorites_list);
        no_con = favoritesView.findViewById(R.id.no_con);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        favorites_list.addItemDecoration(new GridInsetDecoration(getActivity()));
        screenTitle="BOOKMARKS";
        GridLayoutManager gridLayoutManager = new GridLayoutManager(GlobalReferences.getInstance().baseActivity,2,GridLayoutManager.VERTICAL,false);
        favorites_list.setLayoutManager(gridLayoutManager);
        RealmResults<BookmarkedMural> bookmarkedMurals = RealmController.getInstance().getAllBookMurals();
        if(bookmarkedMurals.size()==0){
            no_con.setVisibility(View.VISIBLE);
        }else {
            no_con.setVisibility(View.GONE);
        }
        BookMarkAdapter bookMarkAdapter = new BookMarkAdapter(bookmarkedMurals);
        favorites_list.setAdapter(bookMarkAdapter);
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
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
    }
}
