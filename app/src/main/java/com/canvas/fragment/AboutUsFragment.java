package com.canvas.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.canvas.BaseActivity;
import com.canvas.R;
import com.canvas.common.CommonFragment;
import com.canvas.common.GlobalReferences;

/**
 * Created by akashyadav on 12/7/17.
 */

public class AboutUsFragment extends CommonFragment {
    Button button_mean,button_crux;
    private ImageView twitter,facebook,instagram,crux_facebook,crux_twitter,crux_instagram;
    private LinearLayout follow_on_insta,share_your_feedback;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_us,null);
        setHasOptionsMenu(true);
        follow_on_insta = (LinearLayout) view.findViewById(R.id.follow_on_insta);
        twitter = (ImageView) view.findViewById(R.id.twitter);
        facebook = (ImageView) view.findViewById(R.id.facebook);
        instagram = (ImageView) view.findViewById(R.id.instagram);
        crux_facebook = (ImageView) view.findViewById(R.id.crux_facebook);
        crux_twitter = (ImageView) view.findViewById(R.id.crux_twitter);
        crux_instagram = (ImageView) view.findViewById(R.id.crux_instagram);
        share_your_feedback = (LinearLayout) view.findViewById(R.id.share_your_feedback);

        follow_on_insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/canvsart"));
                startActivity(browserIntent);
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/Mean_Genius"));
                startActivity(browserIntent);
            }
        });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/MeanGeniusIdeas"));
                startActivity(browserIntent);
            }
        });
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/mean.genius"));
                startActivity(browserIntent);
            }
        });
        crux_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                startActivity(browserIntent);
            }
        });
        crux_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                startActivity(browserIntent);
            }
        });
        share_your_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    ((BaseActivity)GlobalReferences.getInstance().baseActivity).addFragmentWithBackStack(new FeedBackFragment(),true);
                }catch (Exception e){

                }
            }
        });
        screenTitle="ABOUT US";
        button_mean= (Button) view.findViewById(R.id.mean_button);
        button_mean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink("http://www.meangenius.com");
            }
        });
        button_crux= (Button) view.findViewById(R.id.crux_button);
        button_crux.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              openLink("http://www.meangenius.com");
            }
        });
        return view;
    }
    private void openLink(String link){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
    }
}
