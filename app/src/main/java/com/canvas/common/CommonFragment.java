package com.canvas.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.canvas.R;
import com.canvas.fragment.CanvsMapFragment;

/**
 * Created by akashyadav on 11/27/17.
 */

public class CommonFragment extends Fragment {
    protected String screenTitle="";
    private ImageView toolbarImage;
    private TextView toolBarText;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //updateToolbarTitle();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void updateToolbarTitle() {
        try{
            toolbarImage = GlobalReferences.getInstance().toolbar.findViewById(R.id.toolbar_image);
            toolBarText = GlobalReferences.getInstance().toolbar.findViewById(R.id.tool_bar_title);
            if(GlobalReferences.getInstance().mCommonFragment instanceof CanvsMapFragment){
                toolbarImage.setVisibility(View.VISIBLE);
                toolBarText.setVisibility(View.GONE);
            } else{
                toolbarImage.setVisibility(View.GONE);
                toolBarText.setVisibility(View.VISIBLE);
                toolBarText.setText(screenTitle+"");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
       // updateToolbarTitle();
        super.onViewCreated(view, savedInstanceState);
    }

    public void onRefresh(){
        updateToolbarTitle();
    }
}
