package com.canvas.widget;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.canvas.R;
import com.canvas.common.GlobalReferences;
import com.canvas.model.Murals;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by ashish on 28/10/17.
 */

public class CustomAlertDialog extends Dialog implements android.view.View.OnClickListener {

    public Activity activity;
    Murals mMurals;

    public CustomAlertDialog(Activity activity) {
        super(activity);

        // TODO Auto-generated constructor stub
        this.activity = activity;

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.hunt_dialog);
        TextView textView=findViewById(R.id.tv_ok);
        CheckBox checkBox=findViewById(R.id.check_dont_show);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });



    }


    @Override
    public void onClick(View v) {

        dismiss();
    }
}
