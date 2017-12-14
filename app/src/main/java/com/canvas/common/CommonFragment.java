package com.canvas.common;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.canvas.R;
import com.canvas.fragment.CanvsMapFragment;
import com.canvas.fragment.FragmentMuralDetail;
import com.joanzapata.iconify.widget.IconTextView;

/**
 * Created by akashyadav on 11/27/17.
 */

public class CommonFragment extends Fragment {
    protected String screenTitle="";
    protected ImageView toolbarImage,imageView_back;
    private IconTextView search_box;

    private TextView toolBarText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //updateToolbarTitle();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void updateToolbarTitle() {
        try{
            imageView_back= GlobalReferences.getInstance().toolbar.findViewById(R.id.filter);
            toolbarImage = GlobalReferences.getInstance().toolbar.findViewById(R.id.toolbar_image);
            toolBarText = GlobalReferences.getInstance().toolbar.findViewById(R.id.tool_bar_title);
            search_box = GlobalReferences.getInstance().toolbar.findViewById(R.id.search_box);
            search_box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                    GlobalReferences.getInstance().searchView.setVisibility(View.VISIBLE);
                    }catch (Exception e){

                    }
                }
            });
            if(GlobalReferences.getInstance().mCommonFragment instanceof CanvsMapFragment){
                toolbarImage.setVisibility(View.VISIBLE);
                toolBarText.setVisibility(View.GONE);
                imageView_back.setImageResource(R.drawable.equalizer);
               // ImageView imageView_filter = GlobalReferences.getInstance().toolbar.findViewById(R.id.filter);
                imageView_back.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
                imageView_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity());
                        View sheetView = getActivity().getLayoutInflater().inflate(R.layout.fragment_bottom_sheet, null);
                        mBottomSheetDialog.setContentView(sheetView);
                        mBottomSheetDialog.show();
                    }
                });
                //search_box.setVisibility(View.VISIBLE);
            }else if(GlobalReferences.getInstance().mCommonFragment instanceof FragmentMuralDetail){
                toolbarImage.setVisibility(View.VISIBLE);
                GlobalReferences.getInstance().progresBar.setVisibility(View.GONE);
                toolBarText.setVisibility(View.GONE);
                imageView_back.setImageDrawable(GlobalReferences.getInstance().baseActivity.getResources().getDrawable(R.drawable.ic_left_arrow));
                imageView_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().getSupportFragmentManager().popBackStackImmediate();
                    }
                });
               // search_box.setVisibility(View.GONE);

            }else{
                GlobalReferences.getInstance().progresBar.setVisibility(View.GONE);

                imageView_back.setImageDrawable(GlobalReferences.getInstance().baseActivity.getResources().getDrawable(R.drawable.ic_left_arrow));
                imageView_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().getSupportFragmentManager().popBackStackImmediate();
                    }
                });
                toolbarImage.setVisibility(View.GONE);
                toolBarText.setVisibility(View.VISIBLE);
                toolBarText.setText(screenTitle+"");
               // search_box.setVisibility(View.GONE);

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
