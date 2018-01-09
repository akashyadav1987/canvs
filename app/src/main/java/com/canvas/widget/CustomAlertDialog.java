package com.canvas.widget;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.canvas.R;
import com.canvas.common.GlobalReferences;
import com.canvas.fragment.CanvsMapFragment;
import com.canvas.model.Murals;

/**
 * Created by ashish on 28/10/17.
 */

public class CustomAlertDialog extends Dialog implements android.view.View.OnClickListener {

    public Activity activity;
    Murals mMurals;
    CanvsMapFragment canvsMapFragment;
    private CheckBox check_dont_show;

    public CustomAlertDialog(Activity activity, CanvsMapFragment fragment) {
        super(activity);

        // TODO Auto-generated constructor stub
        this.activity = activity;
        canvsMapFragment=fragment;

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.hunt_dialog);
        TextView textView=findViewById(R.id.tv_ok);
        CheckBox checkBox=findViewById(R.id.check_dont_show);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    GlobalReferences.getInstance().pref.setDontShowThisMsg(true);
                }
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             boolean isHunt=   GlobalReferences.getInstance().pref.getHuntMode();
                GlobalReferences.getInstance().pref.setHuntMode(!isHunt);
                canvsMapFragment.huntState(!isHunt);
                dismiss();
            }
        });



    }


    @Override
    public void onClick(View v) {

        dismiss();
    }
}
