package com.canvas.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.canvas.R;
import com.canvas.common.CommonFragment;

/**
 * Created by akashyadav on 12/7/17.
 */

public class AboutUsFragment extends CommonFragment {
    Button button_mean,button_crux;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_us,null);
        screenTitle="ABOUT US";
        button_mean=view.findViewById(R.id.mean_button);
        button_mean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink("http://www.meangenius.com");
            }
        });
        button_crux=view.findViewById(R.id.crux_button);
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
}
