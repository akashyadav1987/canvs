package com.canvas.widget;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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
    public Dialog d;
    public Button yes, no;
    Murals mMurals;


    ListView listView;
    public CustomAlertDialog(Activity activity, Murals murals) {
        super(activity);

        // TODO Auto-generated constructor stub
        this.activity = activity;
        mMurals=murals;

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.map_dialog);
        TextView textView_title=findViewById(R.id.title);
        textView_title.setText(mMurals.getTitle());
        TextView textView_author=findViewById(R.id.author);
        textView_author.setText(mMurals.getAuthor());
        ImageView imageView=findViewById(R.id.iv_map);
        String imge_id=mMurals.getImage_resource_id().toLowerCase();
        String image_url="https://canvs.cruxcode.nyc/mural_thumb_"+imge_id+".jpg?size=thumb&requestType=image";
        Log.e(TAG, "onCreate: "+image_url );
        Glide.with(GlobalReferences.getInstance().baseActivity).load(image_url)
                .thumbnail(0.5f)
//                .placeholder(R.drawable.iv)
//                .error(R.drawable.profile)
                .into(imageView);
        TextView textView_more=findViewById(R.id.tv_more);



    }


    @Override
    public void onClick(View v) {

        dismiss();
    }
}
