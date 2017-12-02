package com.canvas.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.canvas.R;
import com.canvas.common.CommonFragment;
import com.canvas.common.Constants;
import com.canvas.common.GlobalReferences;
import com.canvas.io.http.ApiRequests;
import com.canvas.io.http.BaseTask;
import com.canvas.io.http.BaseTaskJson;
import com.canvas.io.listener.AppRequest;
import com.canvas.utils.Utility;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by akashyadav on 11/27/17.
 */

public class CanvsMapFragment extends CommonFragment implements OnMapReadyCallback ,AppRequest{
    private GoogleMap mMap;
    private MapView mapView;
    GoogleApiClient mGoogleApiClient;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mapViewLayout = inflater.inflate(R.layout.canvs_map_fragment,null);
        mapView = (MapView)mapViewLayout.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap gmap) {
                mMap = gmap;
                mMap.getUiSettings().setScrollGesturesEnabled(true);
                mMap.getUiSettings().setTiltGesturesEnabled(true);
                mMap.getUiSettings().setScrollGesturesEnabled(true);
                mMap.getUiSettings().setCompassEnabled(true);
                mMap.getUiSettings().setScrollGesturesEnabled(true);
                mMap.getUiSettings().setZoomGesturesEnabled(true);
                mMap.getUiSettings().setRotateGesturesEnabled(true);

            }
        });
        mapView.onResume();
        try {
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Call api here
        if(Utility.isNetworkAvailable(GlobalReferences.getInstance().baseActivity)){
            ApiRequests.getInstance().get_murals(GlobalReferences.getInstance().baseActivity,this);
        }else{
            Utility.showNoInternetConnectionToast();
        }

        return mapViewLayout;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @Override
    public <T> void onRequestStarted(BaseTask<T> listener, Constants.RequestParam requestParam) {
      try{
          //Request started
          //GET CALL

          GlobalReferences.getInstance().progresBar.setVisibility(View.VISIBLE);

      }catch (Exception e){
          e.printStackTrace();
      }
    }

    @Override
    public <T> void onRequestCompleted(BaseTask<T> listener, Constants.RequestParam requestParam) {
        try{
            Log.e("response",listener.getJsonArrayResponse()+"");
            GlobalReferences.getInstance().progresBar.setVisibility(View.GONE);
            Gson gson = new Gson();
            //Murals murals = gson.fromJson();
        }catch (Exception e){

        }
    }

    @Override
    public <T> void onRequestFailed(BaseTask<T> listener, Constants.RequestParam requestParam) {

    }

    @Override
    public <T> void onRequestStarted(BaseTaskJson<JSONObject> listener, Constants.RequestParam requestParam) {

    }

    @Override
    public <T> void onRequestCompleted(BaseTaskJson<JSONObject> listener, Constants.RequestParam requestParam) {

    }

    @Override
    public <T> void onRequestFailed(BaseTaskJson<JSONObject> listener, Constants.RequestParam requestParam) {

    }

    @Override
    public void onResponse(JSONObject jsonObject) {

    }
}
