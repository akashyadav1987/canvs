package com.canvas.fragment;


import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.canvas.BaseActivity;
import com.canvas.R;
import com.canvas.common.CommonFragment;
import com.canvas.common.Constants;
import com.canvas.common.GlobalReferences;
import com.canvas.io.http.ApiRequests;
import com.canvas.io.http.BaseTask;
import com.canvas.io.http.BaseTaskJson;
import com.canvas.io.listener.AppRequest;
import com.canvas.io.listener.HuntListener;
import com.canvas.model.Murals;
import com.canvas.model.MuralsArray;
import com.canvas.utils.BottomNavigationViewHelper;
import com.canvas.utils.Utility;
import com.canvas.widget.CustomAlertDialog;
import com.canvas.widget.RobotoBoldTextView;
import com.canvas.widget.RobotoMediumTextView;
import com.canvas.widget.RobotoRegularTextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.lang.reflect.Type;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by akashyadav on 11/27/17.
 */

public class CanvsMapFragment extends CommonFragment implements HuntListener, OnMapReadyCallback ,AppRequest,GoogleMap.OnMarkerClickListener,com.google.android.gms.location.LocationListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener

{
    private GoogleMap mMap;
    private MapView mapView;
    GoogleApiClient mGoogleApiClient;
    LatLngBounds.Builder builder;
    ArrayList<Murals> list_murals;
    ImageView imageView_filter;
    private MenuItem bookmarks, seen, fav, about;
    CardView cardView_dialog;
    RobotoBoldTextView textView_title;
    RobotoMediumTextView textView_author;
    RobotoRegularTextView textView_more;
    ImageView imageView;
    CardView cardView_my_location, cardView_hunt_mode;
    Location location;
    LocationRequest mLocationRequest;
   Marker marker_previous;
   int previous_tag;
   TextView tv_hunt_mode;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mapViewLayout = inflater.inflate(R.layout.canvs_map_fragment, null);
        mapView = (MapView) mapViewLayout.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        screenTitle = "BOOKMARKS";
        builder = new LatLngBounds.Builder();

        list_murals = new ArrayList<>();
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap gmap) {
                mMap = gmap;
                mMap.getUiSettings().setScrollGesturesEnabled(true);
                mMap.getUiSettings().setTiltGesturesEnabled(true);
                mMap.getUiSettings().setScrollGesturesEnabled(true);
                mMap.getUiSettings().setCompassEnabled(false);
                mMap.getUiSettings().setScrollGesturesEnabled(true);
                mMap.getUiSettings().setZoomGesturesEnabled(true);
                mMap.getUiSettings().setRotateGesturesEnabled(true);
                mMap.setOnMarkerClickListener(CanvsMapFragment.this);


            }
        });
        cardView_my_location = mapViewLayout.findViewById(R.id.my_location);
        cardView_my_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (location != null) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
                    mMap.animateCamera(cameraUpdate);
                }
            }
        });
        cardView_hunt_mode = mapViewLayout.findViewById(R.id.hunt_mode);
        cardView_hunt_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomAlertDialog customAlertDialog = new CustomAlertDialog(GlobalReferences.getInstance().baseActivity,CanvsMapFragment.this);
                customAlertDialog.show();
            }
        });
        tv_hunt_mode=mapViewLayout.findViewById(R.id.tv_hunt_mode);
      boolean isHunton=  GlobalReferences.getInstance().pref.getHuntMode();
      if(isHunton){
          cardView_hunt_mode.setBackgroundColor(GlobalReferences.getInstance().baseActivity.getResources().getColor(R.color.orange));
      tv_hunt_mode.setText("HUNT MODE ON");
      }else{
          tv_hunt_mode.setText("HUNT MODE OFF");
          cardView_hunt_mode.setBackgroundColor(GlobalReferences.getInstance().baseActivity.getResources().getColor(R.color.grey));

      }



        imageView_filter = mapViewLayout.findViewById(R.id.filter);
        imageView_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BottomSheetDialogFragment bottomSheetDialogFragment = new BottomSheetDialogFragment();
//                bottomSheetDialogFragment.show(getFragmentManager(), bottomSheetDialogFragment.getTag());

                BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity());
                View sheetView = getActivity().getLayoutInflater().inflate(R.layout.fragment_bottom_sheet, null);
                mBottomSheetDialog.setContentView(sheetView);
                mBottomSheetDialog.show();


            }
        });
        mapView.onResume();
        try {
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Call api here
        if (Utility.isNetworkAvailable(GlobalReferences.getInstance().baseActivity)) {
            ApiRequests.getInstance().get_murals(GlobalReferences.getInstance().baseActivity, this);
        } else {
            Utility.showNoInternetConnectionToast();
        }


        BottomNavigationView bottomNavigationView = (BottomNavigationView) mapViewLayout.findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setSelectedItemId(-1);
        Menu menu = bottomNavigationView.getMenu();

        bookmarks = menu.findItem(R.id.bookmarks);
        seen = menu.findItem(R.id.seen);
        fav = menu.findItem(R.id.fav);
        about = menu.findItem(R.id.about);
        bookmarks.setCheckable(true);
        seen.setCheckable(true);
        fav.setCheckable(true);
        about.setCheckable(true);

        bookmarks.setIcon(new IconDrawable((BaseActivity) GlobalReferences.getInstance().baseActivity, FontAwesomeIcons.fa_bookmark)
                .colorRes(R.color.grey)
                .actionBarSize());
        seen.setIcon(new IconDrawable((BaseActivity) GlobalReferences.getInstance().baseActivity, FontAwesomeIcons.fa_check)
                .colorRes(R.color.grey)
                .actionBarSize());
        fav.setIcon(new IconDrawable((BaseActivity) GlobalReferences.getInstance().baseActivity, FontAwesomeIcons.fa_heart)
                .colorRes(R.color.grey)
                .actionBarSize());
        about.setIcon(new IconDrawable((BaseActivity) GlobalReferences.getInstance().baseActivity, FontAwesomeIcons.fa_info_circle)
                .colorRes(R.color.grey)
                .actionBarSize());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                updateTab(item.getItemId());
                return false;
            }
        });


        textView_title = mapViewLayout.findViewById(R.id.title);
        imageView = mapViewLayout.findViewById(R.id.iv_map);
        textView_author = mapViewLayout.findViewById(R.id.author);
        textView_more = mapViewLayout.findViewById(R.id.tv_more);
        cardView_dialog = mapViewLayout.findViewById(R.id.card_dialog);
        GlobalReferences.getInstance().toolbar = (Toolbar) mapViewLayout.findViewById(R.id.toolbar_top);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        return mapViewLayout;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach: "+"call" );
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        Log.e(TAG, "onAttach: "+"call" );

    }

    public void updateTab(int selectedTab) {

        switch (selectedTab) {

            case R.id.bookmarks:
                bookmarks.setIcon(new IconDrawable((BaseActivity) GlobalReferences.getInstance().baseActivity, FontAwesomeIcons.fa_bookmark)
                        .colorRes(R.color.red)
                        .actionBarSize());

                seen.setIcon(new IconDrawable((BaseActivity) GlobalReferences.getInstance().baseActivity, FontAwesomeIcons.fa_check)
                        .colorRes(R.color.grey)
                        .actionBarSize());
                fav.setIcon(new IconDrawable((BaseActivity) GlobalReferences.getInstance().baseActivity, FontAwesomeIcons.fa_heart)
                        .colorRes(R.color.grey)
                        .actionBarSize());
                about.setIcon(new IconDrawable((BaseActivity) GlobalReferences.getInstance().baseActivity, FontAwesomeIcons.fa_info_circle)
                        .colorRes(R.color.grey)
                        .actionBarSize());
                ((BaseActivity) GlobalReferences.getInstance().baseActivity).addFragmentWithBackStack(new BookMarkFragment(), true);

                break;
            case R.id.seen:
                seen.setIcon(new IconDrawable((BaseActivity) GlobalReferences.getInstance().baseActivity, FontAwesomeIcons.fa_check)
                        .colorRes(R.color.red)
                        .actionBarSize());
                bookmarks.setIcon(new IconDrawable((BaseActivity) GlobalReferences.getInstance().baseActivity, FontAwesomeIcons.fa_bookmark)
                        .colorRes(R.color.grey)
                        .actionBarSize());
                fav.setIcon(new IconDrawable((BaseActivity) GlobalReferences.getInstance().baseActivity, FontAwesomeIcons.fa_heart)
                        .colorRes(R.color.grey)
                        .actionBarSize());
                about.setIcon(new IconDrawable((BaseActivity) GlobalReferences.getInstance().baseActivity, FontAwesomeIcons.fa_info_circle)
                        .colorRes(R.color.grey)
                        .actionBarSize());
                ((BaseActivity) GlobalReferences.getInstance().baseActivity).addFragmentWithBackStack(new SeenFragment(), true);

                break;
            case R.id.fav:
                bookmarks.setIcon(new IconDrawable((BaseActivity) GlobalReferences.getInstance().baseActivity, FontAwesomeIcons.fa_bookmark)
                        .colorRes(R.color.grey)
                        .actionBarSize());
                seen.setIcon(new IconDrawable((BaseActivity) GlobalReferences.getInstance().baseActivity, FontAwesomeIcons.fa_check)
                        .colorRes(R.color.grey)
                        .actionBarSize());
                fav.setIcon(new IconDrawable((BaseActivity) GlobalReferences.getInstance().baseActivity, FontAwesomeIcons.fa_heart)
                        .colorRes(R.color.red)
                        .actionBarSize());
                about.setIcon(new IconDrawable((BaseActivity) GlobalReferences.getInstance().baseActivity, FontAwesomeIcons.fa_info_circle)
                        .colorRes(R.color.grey)
                        .actionBarSize());
                ((BaseActivity) GlobalReferences.getInstance().baseActivity).addFragmentWithBackStack(new FavoritesFragment(), true);

                break;
            case R.id.about:
                ((BaseActivity) GlobalReferences.getInstance().baseActivity).addFragmentWithBackStack(new AboutUsFragment(), true);
                about.setIcon(new IconDrawable((BaseActivity) GlobalReferences.getInstance().baseActivity, FontAwesomeIcons.fa_info_circle)
                        .colorRes(R.color.red)
                        .actionBarSize());
                bookmarks.setIcon(new IconDrawable((BaseActivity) GlobalReferences.getInstance().baseActivity, FontAwesomeIcons.fa_bookmark)
                        .colorRes(R.color.grey)
                        .actionBarSize());
                seen.setIcon(new IconDrawable((BaseActivity) GlobalReferences.getInstance().baseActivity, FontAwesomeIcons.fa_check)
                        .colorRes(R.color.grey)
                        .actionBarSize());
                fav.setIcon(new IconDrawable((BaseActivity) GlobalReferences.getInstance().baseActivity, FontAwesomeIcons.fa_heart)
                        .colorRes(R.color.grey)
                        .actionBarSize());
                break;
            default:
                bookmarks.setIcon(new IconDrawable((BaseActivity) GlobalReferences.getInstance().baseActivity, FontAwesomeIcons.fa_bookmark)
                        .colorRes(R.color.grey)
                        .actionBarSize());
                seen.setIcon(new IconDrawable((BaseActivity) GlobalReferences.getInstance().baseActivity, FontAwesomeIcons.fa_check)
                        .colorRes(R.color.grey)
                        .actionBarSize());
                fav.setIcon(new IconDrawable((BaseActivity) GlobalReferences.getInstance().baseActivity, FontAwesomeIcons.fa_heart)
                        .colorRes(R.color.grey)
                        .actionBarSize());
                about.setIcon(new IconDrawable((BaseActivity) GlobalReferences.getInstance().baseActivity, FontAwesomeIcons.fa_info_circle)
                        .colorRes(R.color.grey)
                        .actionBarSize());
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @Override
    public <T> void onRequestStarted(BaseTask<T> listener, Constants.RequestParam requestParam) {
        try {
            //Request started
            //GET CALL

            GlobalReferences.getInstance().progresBar.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> void onRequestCompleted(BaseTask<T> listener, Constants.RequestParam requestParam) {
        try {
            Log.e("response", listener.getJsonArrayResponse() + "");
            GlobalReferences.getInstance().progresBar.setVisibility(View.GONE);
            Gson gson = new Gson();
            //Murals murals = gson.fromJson();
            List<MuralsArray> list = new ArrayList<MuralsArray>();

            Type type = new TypeToken<List<MuralsArray>>() {
            }.getType();
            String json = gson.toJson(list, type);
            System.out.println(json);

            List<MuralsArray> fromJson = gson.fromJson(json, type);
            JSONArray jsonArray = listener.getJsonArrayResponse();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Murals murals = new Murals();
                murals.setActive(object.getInt("id"));
                murals.setDerelict(object.getInt("derelict"));
                murals.setActive(object.getInt("active"));
                murals.setPopularity(object.getInt("popularity"));
                murals.setLatitude(object.getDouble("latitude"));
                murals.setLongitude(object.getDouble("longitude"));
                murals.setImage_path(object.getString("additionalLink2"));
                murals.setAuthor(object.getString("artistName"));
                murals.setTitle(object.getString("muralTitle"));
                murals.setImage_resource_id(object.getString("imageResourceID"));
                murals.setAbout_text(object.getString("aboutThisText"));
                murals.setLocation_text(object.getString("locationText"));
                murals.setArtist_text(object.getString("aboutArtistText"));
                murals.setTags(object.getString("tags"));
                murals.setAdditional_link_first(object.getString("additionalLink1"));
                murals.setAdditional_link_second(object.getString("additionalLink2"));
                murals.setAdditional_link_third(object.getString("additionalLink3"));
                // builder.include(new LatLng(object.getDouble("latitude"),object.getDouble("longitude")));
                mMap.addMarker(new MarkerOptions().position(new LatLng(object.getDouble("latitude"), object.getDouble("longitude"))).icon(BitmapDescriptorFactory.fromResource(R.drawable.murals))).setTag(i);
                list_murals.add(murals);


                if (i == jsonArray.length() - 1) {
                    setCurrentLocation(new LatLng(object.getDouble("latitude"), object.getDouble("longitude")));


                }
            }


            LatLngBounds bounds = builder.build();

            // mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20));

        } catch (Exception e) {

        }
    }

    public void setCurrentLocation(LatLng latlng) {


        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latlng.latitude, latlng.longitude), 13));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latlng)      // Sets the center of the map to location user
                .zoom(17)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
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

    @Override
    public boolean onMarkerClick(Marker marker) {


        if(marker_previous!=null){
            marker_previous.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.murals));
        }
        marker_previous=marker;
       marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.circular_red));



        final Murals murals = list_murals.get((int) marker.getTag());
//        LayoutInflater inflater = LayoutInflater.from(getContext());
//        final Dialog dialog1 = new Dialog(getContext());
//        Window window = dialog1.getWindow();
//        window.setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        WindowManager.LayoutParams wlp = window.getAttributes();
//        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//        lp.copyFrom(window.getAttributes());
//         //This makes the dialog take up the full width
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        window.setAttributes(lp);
//        dialog1.getWindow().setAttributes(lp);
//        wlp.gravity = Gravity.BOTTOM; // Here you can set window top or bottom
//        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//        window.setAttributes(wlp);
//        View view_dialog = inflater.inflate(R.layout.map_dialog, null);
//        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        cardView_dialog.setVisibility(View.VISIBLE);

        final String image_id = murals.getImage_resource_id().toLowerCase();
        String image_url = "https://canvs.cruxcode.nyc/mural_thumb_" + image_id + ".jpg?size=thumb&requestType=image";
        textView_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentMuralDetail fragmentMuralDetail = new FragmentMuralDetail();
                Bundle bundle = new Bundle();
                bundle.putString("image_id", image_id);
                bundle.putString("location_text", murals.getLocation_text());

                bundle.putString("artist_text", murals.getArtist_text());
                bundle.putString("about_text", murals.getAbout_text());
                bundle.putString("tags", murals.getTags());
                bundle.putString("addlink1", murals.getAdditional_link_first());
                bundle.putString("addlink2", murals.getAdditional_link_second());
                bundle.putString("addlink3", murals.getAdditional_limk_third());
                bundle.putString("artist", murals.getAuthor());
                bundle.putString("name", murals.getTitle());
                bundle.putDouble("lat", murals.getLatitude());
                bundle.putDouble("lon", murals.getLongitude());
                fragmentMuralDetail.setArguments(bundle);
                ((BaseActivity) GlobalReferences.getInstance().baseActivity).addFragmentWithBackStack(fragmentMuralDetail, true);
                cardView_dialog.setVisibility(View.GONE);
            }
        });
        textView_title.setText(murals.getTitle());

        textView_author.setText(murals.getAuthor());

        Log.e(TAG, "onCreate: " + image_url);
        Glide.with(GlobalReferences.getInstance().baseActivity).load(image_url)
                .thumbnail(0.5f)
//                .placeholder(R.drawable.iv)
//                .error(R.drawable.profile)
                .into(imageView);


        // dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog1.setContentView(view_dialog);
//        dialog1.show();
//        Window window1 = dialog1.getWindow();
//        window1.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        CustomAlertDialog customAlertDialog =new CustomAlertDialog(getActivity(),murals);
//        customAlertDialog.show();
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {

        this.location = location;
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
        mMap.animateCamera(cameraUpdate);

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(GlobalReferences.getInstance().baseActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(GlobalReferences.getInstance().baseActivity, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(GlobalReferences.getInstance().baseActivity, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(GlobalReferences.getInstance().baseActivity, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            buildGoogleApiClient();
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(GlobalReferences.getInstance().baseActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        //  mMap.setMyLocationEnabled(true);
                    }

                } else {
                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(GlobalReferences.getInstance().baseActivity, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(GlobalReferences.getInstance().baseActivity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(CanvsMapFragment.this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(GlobalReferences.getInstance().baseActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&

                ContextCompat.checkSelfPermission(GlobalReferences.getInstance()
                        .baseActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //   Gmap.setMyLocationEnabled(true);


        }
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(600000); //1 minute
        mLocationRequest.setSmallestDisplacement(1f);
        mLocationRequest.setFastestInterval(5000); //10seconds
        mLocationRequest.setExpirationDuration(600000);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.setInterval(5000);

        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.setFastestInterval(10000);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onStart() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
        super.onStart();
    }

    @Override
    public void onStop() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void huntState(boolean huntmode) {
        if(huntmode){
            tv_hunt_mode.setText("HUNT MODE ON");
           // cardView_hunt_mode.setRadius(12f);

            cardView_hunt_mode.setCardBackgroundColor(GlobalReferences.getInstance().baseActivity.getResources().getColor(R.color.orange));
        }else{
            tv_hunt_mode.setText("HUNT MODE OFF");
            cardView_hunt_mode.setCardBackgroundColor(GlobalReferences.getInstance().baseActivity.getResources().getColor(R.color.grey));

        }


    }
}

