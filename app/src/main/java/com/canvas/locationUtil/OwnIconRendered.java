package com.canvas.locationUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;

import com.canvas.R;
import com.canvas.common.GlobalReferences;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

/**
 * Created by ashish on 11/01/18.
 */

public class OwnIconRendered extends DefaultClusterRenderer<MyItem> {
    private final IconGenerator mClusterIconGenerator = new IconGenerator(GlobalReferences.getInstance().baseActivity.getApplicationContext());

    public OwnIconRendered(Context context, GoogleMap map,
                           ClusterManager<MyItem> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {
        markerOptions.icon(item.getIcon());
        markerOptions.snippet(item.getSnippet());
        markerOptions.title(item.getTitle());

       // super.onBeforeClusterItemRendered(item, markerOptions);
    }

    @Override
    protected int getColor(int clusterSize) {


        return Color.parseColor("#5ab3a4");
        //return super.getColor(Color.parseColor("#5ab3a4"));
    }

    @Override
    protected void onBeforeClusterRendered(Cluster<MyItem> cluster, MarkerOptions markerOptions) {
//            mClusterIconGenerator.setBackground(GlobalReferences.getInstance().baseActivity.getResources().getDrawable(R.drawable.features));
//
//            Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
//
//
//        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));


        super.onBeforeClusterRendered(cluster, markerOptions);
    }
}