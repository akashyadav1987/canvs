package com.canvas.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.canvas.R;
import com.canvas.common.CommonFragment;

/**
 * Created by akashyadav on 12/19/17.
 */

public class FeedBackFragment extends CommonFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View feedbackScreen = inflater.inflate(R.layout.feedback_layout,null);
        setHasOptionsMenu(true);
        screenTitle = "FEEDBACK";
        return feedbackScreen;
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
    }
}
