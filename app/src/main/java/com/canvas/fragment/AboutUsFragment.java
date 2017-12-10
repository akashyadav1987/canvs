package com.canvas.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.canvas.R;
import com.canvas.common.CommonFragment;

/**
 * Created by akashyadav on 12/7/17.
 */

public class AboutUsFragment extends CommonFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_us,null);
        screenTitle="ABOUT US";
        return view;
    }
}
