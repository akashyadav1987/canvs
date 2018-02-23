package com.canvas.fragment;


import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.canvas.BaseActivity;
import com.canvas.R;
import com.canvas.adpater.ViewPagerLayoutOnMap;
import com.canvas.common.CommonFragment;
import com.canvas.common.Constants;
import com.canvas.common.GlobalReferences;
import com.canvas.controller.RealmController;
import com.canvas.io.http.ApiRequests;
import com.canvas.io.http.BaseTask;
import com.canvas.io.http.BaseTaskJson;
import com.canvas.io.listener.AppRequest;
import com.canvas.io.listener.HuntListener;
import com.canvas.listener.OnSearchClick;
import com.canvas.listener.SetViewPagerVisibilityGone;
import com.canvas.locationUtil.LocationHelper;
import com.canvas.locationUtil.MyItem;
import com.canvas.locationUtil.OwnIconRendered;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

import static android.content.ContentValues.TAG;
import static com.canvas.R.drawable.murals;

/**
 * Created by akashyadav on 11/27/17.
 */

public class CanvsMapFragment extends CommonFragment implements HuntListener, OnMapReadyCallback, AppRequest, GoogleMap.OnMarkerClickListener, com.google.android.gms.location.LocationListener, GoogleApiClient.ConnectionCallbacks,SetViewPagerVisibilityGone, GoogleApiClient.OnConnectionFailedListener,
        ActivityCompat.OnRequestPermissionsResultCallback, OnSearchClick, GoogleMap.OnMapClickListener, ClusterManager.OnClusterItemClickListener<MyItem>

{
    private GoogleMap mMap;
    private MapView mapView;
    GoogleApiClient mGoogleApiClient;
    LatLngBounds.Builder builder;
    ArrayList<Murals> list_murals;
    ImageView imageView_filter;
    private MenuItem bookmarks, seen, fav, about;
    CardView fresh_mural_tag;
    ViewPager cardView_dialog;
    RobotoBoldTextView textView_title;
    RobotoMediumTextView textView_author;
    RobotoRegularTextView textView_more;
    ImageView imageView;
    CardView cardView_my_location;
    Location location;
    LocationRequest mLocationRequest;
    Marker marker_previous;
    int previous_tag;
    TextView tv_hunt_mode;
    LocationHelper locationHelper;
    private ClusterManager<MyItem> mClusterManager;
    MyItem myItem_previous;
    OwnIconRendered ownIconRendered;
    MyItem previous_item;
    int current_pos = -1;
    long mRequestStartTime;
    ViewPagerLayoutOnMap viewPagerLayoutOnMap;

    public CanvsMapFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mapViewLayout = inflater.inflate(R.layout.canvs_map_fragment, null);
        locationHelper = new LocationHelper(getActivity());
        locationHelper.checkpermission();
        mapView = (MapView) mapViewLayout.findViewById(R.id.mapview);
        //setHasOptionsMenu(false);
        mapView.onCreate(savedInstanceState);
        screenTitle = "BOOKMARKS";
        builder = new LatLngBounds.Builder();
//        GlobalReferences.getInstance().searchView.showVoice(true);
//        GlobalReferences.getInstance().searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                //Do some magic
//                Log.e("hhhhhhb",query+"");
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                //Do some magic
//                //Log.e("Query _text chnages","query text chnage"+newText+"");
//
//                RealmController.getInstance().searchForMural(newText.toLowerCase(), new SearchResultFound() {
//                    @Override
//                    public void onSearchResultFound(RealmResults<Murals> realmResults) {
//                        Log.e("rows",realmResults+"");
//                        if(realmResults!=null){
//                            String strings[] = new String[realmResults.size()];
//                            for (int i =0;i<realmResults.size();i++) {
//                                Murals murals = realmResults.get(i);
//                                strings[i] =murals.getTitle();
//                            }
//                            Log.e("showing result","showww ="+strings);
//
//
//                        }
//                    }
//                });
//
//                String s[] = new String[]{"11","1"};
//                GlobalReferences.getInstance().searchView.setSuggestions(s);
//                //GlobalReferences.getInstance().searchView.setAdapter(new AdapterSearchView(getActivity(),strings));
//
//               // GlobalReferences.getInstance().searchView.showSuggestions();
//
//                return false;
//            }
//        });
//
//        GlobalReferences.getInstance().searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
//            @Override
//            public void onSearchViewShown() {
//                //Do some magic
//            }
//
//            @Override
//            public void onSearchViewClosed() {
//                //Do some magic
//            }
//        });


        list_murals = new ArrayList<>();
        try {
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap gmap) {
                mMap = gmap;
                mMap.getUiSettings().setTiltGesturesEnabled(false);
                mMap.getUiSettings().setCompassEnabled(false);
                mMap.getUiSettings().setScrollGesturesEnabled(true);
                mMap.getUiSettings().setZoomGesturesEnabled(true);
                mMap.getUiSettings().setRotateGesturesEnabled(false);
                mMap.setBuildingsEnabled(false);
                mMap.setOnMapClickListener(CanvsMapFragment.this);

                mClusterManager = new ClusterManager<MyItem>(GlobalReferences.getInstance().baseActivity, mMap);
                ownIconRendered = new OwnIconRendered(GlobalReferences.getInstance().baseActivity.getApplicationContext(), mMap, mClusterManager);

                mClusterManager.setRenderer(ownIconRendered);
                mClusterManager.setOnClusterItemClickListener(CanvsMapFragment.this);
                mMap.setOnCameraIdleListener(mClusterManager);
                mMap.setOnMarkerClickListener(mClusterManager);


                mClusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MyItem>() {
                            @Override
                            public boolean onClusterClick(final Cluster<MyItem> cluster) {
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                        cluster.getPosition(), (float) Math.floor(mMap.getCameraPosition().zoom + 1)), 300,
                                        null);
                                return true;
                            }
                        });
            }
        });


        cardView_my_location = (CardView) mapViewLayout.findViewById(R.id.my_location);
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
        tv_hunt_mode = (TextView) mapViewLayout.findViewById(R.id.tv_hunt_mode);
        huntState(GlobalReferences.getInstance().pref.getHuntMode());
        tv_hunt_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!GlobalReferences.getInstance().pref.getHuntMode()) {
                    if (GlobalReferences.getInstance().pref.getDontShowThisMsg()) {
                        GlobalReferences.getInstance().pref.setHuntMode(true);
                        huntState(true);
                    } else {
                        CustomAlertDialog customAlertDialog = new CustomAlertDialog(GlobalReferences.getInstance().baseActivity, CanvsMapFragment.this);
                        customAlertDialog.show();
                    }
                } else {
                    GlobalReferences.getInstance().pref.setHuntMode(false);
                    GlobalReferences.getInstance().pref.setHuntMode(false);
                    huntState(false);
                }
            }
        });
        boolean isHunton = GlobalReferences.getInstance().pref.getHuntMode();
        if (isHunton) {
            //cardView_hunt_mode.setBackgroundColor(GlobalReferences.getInstance().baseActivity.getResources().getColor(R.color.orange));
            tv_hunt_mode.setText("HUNT MODE ON");
        } else {
            tv_hunt_mode.setText("HUNT MODE OFF");
            //cardView_hunt_mode.setBackgroundColor(GlobalReferences.getInstance().baseActivity.getResources().getColor(R.color.grey));
        }
        //Call api here


//        BottomNavigationView bottomNavigationView = (BottomNavigationView) mapViewLayout.findViewById(R.id.bottom_navigation);
//        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);


        BottomNavigationViewEx bnve = (BottomNavigationViewEx) mapViewLayout.findViewById(R.id.bnve_no_animation);

//        bnve.enableAnimation(false);
//
//        bnve.enableShiftingMode(false);
//
//        bnve.enableItemShiftingMode(false);
        bnve.enableAnimation(false);
        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);
        bnve.setActivated(true);


        bnve.setIconSize(20, 20);

        bnve.setTextSize(12);
        bnve.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//        bottomNavigationView.setSelectedItemId(-1);
       // Menu menu = bottomNavigationView.getMenu();
         Menu menu = bnve.getMenu();


        menu.getItem(0).setCheckable(false);

        bookmarks = menu.findItem(R.id.bookmarks);
        seen = menu.findItem(R.id.seen);
        fav = menu.findItem(R.id.fav);
        about = menu.findItem(R.id.about);
        bookmarks.setCheckable(true);
        seen.setCheckable(true);
        fav.setCheckable(true);
        about.setCheckable(true);

        bookmarks.setIcon(R.drawable.bookmarks);
        seen.setIcon(R.drawable.seen);
        fav.setIcon(R.drawable.favourites);
        about.setIcon(R.drawable.about);
        bnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                updateTab(item.getItemId());
                return false;
            }
        });

//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                updateTab(item.getItemId());
//                return false;
//            }
//        });


        textView_title = (RobotoBoldTextView) mapViewLayout.findViewById(R.id.title);
        imageView = (ImageView) mapViewLayout.findViewById(R.id.iv_map);
        textView_author = (RobotoMediumTextView) mapViewLayout.findViewById(R.id.author);
        textView_more = (RobotoRegularTextView) mapViewLayout.findViewById(R.id.tv_more);
        fresh_mural_tag = (CardView) mapViewLayout.findViewById(R.id.fresh_mural_tag);
        cardView_dialog = (ViewPager) mapViewLayout.findViewById(R.id.dialogue_view_pager);

        cardView_dialog.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        cardView_dialog.addOnPageChangeListener(new ViewPager.OnPageChangeListener(GlobalReferences.getInstance().baseActivity) {
//            public void onSwipeTop() {
//                //Toast.makeText(GlobalReferences.getInstance().baseActivity, "top", Toast.LENGTH_SHORT).show();
//            }
//
//            public void onSwipeRight() {
//                    current_pos++;
//                    if(current_pos==list_murals.size()-1){
//                        current_pos=0;
//                    }
//                    try {
//                        Murals murals = list_murals.get(current_pos);
//                        if(murals!=null){
//                            buildCardDialogue(murals);
//                        }
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//
//
//                //Toast.makeText(GlobalReferences.getInstance().baseActivity, "right", Toast.LENGTH_SHORT).show();
//            }
//
//            public void onSwipeLeft() {
//                current_pos--;
//                if(current_pos==0){
//                    current_pos=list_murals.size()-1;
//                }
//                try {
//                    Murals murals = list_murals.get(current_pos);
//                    if(murals!=null){
//                        buildCardDialogue(murals);
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                //Toast.makeText(GlobalReferences.getInstance().baseActivity, "left", Toast.LENGTH_SHORT).show();
//            }
//
//            public void onSwipeBottom() {
//                //Toast.makeText(GlobalReferences.getInstance().baseActivity, "bottom", Toast.LENGTH_SHORT).show();
//            }
//
//        });
        //GlobalReferences.getInstance().toolbar = (Toolbar) mapViewLayout.findViewById(R.id.toolbar_top);

//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            checkLocationPermission();
//        }
        // check availability of play services
        if (locationHelper.checkPlayServices()) {

            // Building the GoogleApi client
            locationHelper.buildGoogleApiClient(this);
        }

        if (list_murals != null) {
            list_murals.clear();
            if (mMap != null)
                mMap.clear();
        } else {
            list_murals = new ArrayList<>();
        }
        marker_previous = null;
        if (cardView_dialog != null) {
            // cardView_dialog.setVisibility(View.GONE);
        }
        if (Utility.isNetworkAvailable(GlobalReferences.getInstance().baseActivity)) {
            ApiRequests.getInstance().get_murals(GlobalReferences.getInstance().baseActivity, this);
            mRequestStartTime = System.currentTimeMillis();
        } else {
            Utility.showNoInternetConnectionToast();
        }
        return mapViewLayout;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach: " + "call");
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        Log.e(TAG, "onAttach: " + "call");

    }

    public void callApiAgain() {
        try {
            if (list_murals != null) {
                list_murals.clear();
                if (mMap != null)
                    mMap.clear();
            } else {
                list_murals = new ArrayList<>();
            }
            if(mClusterManager!=null){
                mClusterManager.clearItems();
            }
            marker_previous = null;
            if (cardView_dialog != null) {
                cardView_dialog.setVisibility(View.GONE);
            }
            if (Utility.isNetworkAvailable(GlobalReferences.getInstance().baseActivity)) {
                ApiRequests.getInstance().get_murals(GlobalReferences.getInstance().baseActivity, this);
            } else {
                Utility.showNoInternetConnectionToast();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTab(int selectedTab) {

        switch (selectedTab) {

            case R.id.bookmarks:
                bookmarks.setIcon(R.drawable.bookmarks);
                seen.setIcon(R.drawable.seen);
                fav.setIcon(R.drawable.favourites);
                about.setIcon(R.drawable.about);
                ((BaseActivity) GlobalReferences.getInstance().baseActivity).addFragmentWithBackStack(new BookMarkFragment(), true);

                break;
            case R.id.seen:
                bookmarks.setIcon(R.drawable.bookmarks);
                seen.setIcon(R.drawable.seen);
                fav.setIcon(R.drawable.favourites);
                about.setIcon(R.drawable.about);
                ((BaseActivity) GlobalReferences.getInstance().baseActivity).addFragmentWithBackStack(new SeenFragment(), true);

                break;
            case R.id.fav:
                bookmarks.setIcon(R.drawable.bookmarks);
                seen.setIcon(R.drawable.seen);
                fav.setIcon(R.drawable.favourites);
                about.setIcon(R.drawable.about);
                ((BaseActivity) GlobalReferences.getInstance().baseActivity).addFragmentWithBackStack(new FavoritesFragment(), true);

                break;
            case R.id.about:
                ((BaseActivity) GlobalReferences.getInstance().baseActivity).addFragmentWithBackStack(new AboutUsFragment(), true);
                bookmarks.setIcon(R.drawable.bookmarks);
                seen.setIcon(R.drawable.seen);
                fav.setIcon(R.drawable.favourites);
                about.setIcon(R.drawable.about);
                break;
            default:
                bookmarks.setIcon(R.drawable.bookmarks);
                seen.setIcon(R.drawable.seen);
                fav.setIcon(R.drawable.favourites);
                about.setIcon(R.drawable.about);
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
            long totalRequestTime = System.currentTimeMillis() - mRequestStartTime;
            Log.e(TAG, "onRequestCompleted: "+totalRequestTime );
            Gson gson = new Gson();
            mClusterManager.clearItems();
            //Murals murals = gson.fromJson();
            List<MuralsArray> list = new ArrayList<MuralsArray>();

            Type type = new TypeToken<List<MuralsArray>>() {
            }.getType();
            String json = gson.toJson(list, type);
            System.out.println(json);

            List<MuralsArray> fromJson = gson.fromJson(json, type);
            JSONArray jsonArray = listener.getJsonArrayResponse();

//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject object = jsonArray.getJSONObject(i);
//                Murals murals = new Murals();
//                murals.setId(object.getInt("id"));
//                murals.setFreshWhenAdded(object.getString("freshWhenAdded"));
//                murals.setDerelict(object.getInt("derelict"));
//                murals.setActive(object.getInt("active"));
//                murals.setPopularity(object.getInt("popularity"));
//                murals.setLatitude(object.getDouble("latitude"));
//                murals.setLongitude(object.getDouble("longitude"));
//                murals.setImage_path(object.getString("additionalLink2"));
//                murals.setAuthor(object.getString("artistName"));
//                try {
//                    if (object.getString("muralTitle") == null || object.getString("muralTitle").equalsIgnoreCase("null")) {
//                        murals.setTitle("Untitled");
//                    } else
//                        murals.setTitle(object.getString("muralTitle"));
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//
//                murals.setImage_resource_id(object.getString("imageResourceID"));
//                murals.setAbout_text(object.getString("aboutThisText"));
//                murals.setLocation_text(object.getString("locationText"));
//                murals.setArtist_text(object.getString("aboutArtistText"));
//                murals.setTags(object.getString("tags"));
//                murals.setAdditional_link_first(object.getString("additionalLink1"));
//                murals.setAdditional_link_second(object.getString("additionalLink2"));
//                murals.setAdditional_link_third(object.getString("additionalLink3"));
//
//                double distanceInKms = 0.0;
//                if (location != null && murals.getLatitude() != 0.0 && murals.getLongitude() != 0.0) {
//                    distanceInKms = distance(location.getLatitude(), location.getLongitude(), murals.getLatitude(), murals.getLongitude());
//                    //Log.e("distance in=", distanceInKms + "");
//                }

            new LoadData().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,jsonArray);
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject object = jsonArray.getJSONObject(i);
//                Murals murals = new Murals();
//                murals.setId(object.getInt("id"));
//                murals.setFreshWhenAdded(object.getString("freshWhenAdded"));
//                murals.setDerelict(object.getInt("derelict"));
//                murals.setActive(object.getInt("active"));
//                murals.setPopularity(object.getInt("popularity"));
//                murals.setLatitude(object.getDouble("latitude"));
//                murals.setLongitude(object.getDouble("longitude"));
//                murals.setImage_path(object.getString("additionalLink2"));
//                murals.setAuthor(object.getString("artistName"));
//                if (object.getString("muralTitle") == null) {
//                    murals.setTitle(object.getString("Untitled"));
//                } else
//                    murals.setTitle(object.getString("muralTitle"));
//
//                Log.e("mural title=", murals.getTitle() + "");
//                murals.setImage_resource_id(object.getString("imageResourceID"));
//                murals.setAbout_text(object.getString("aboutThisText"));
//                murals.setLocation_text(object.getString("locationText"));
//                murals.setArtist_text(object.getString("aboutArtistText"));
//                murals.setTags(object.getString("tags"));
//                murals.setAdditional_link_first(object.getString("additionalLink1"));
//                murals.setAdditional_link_second(object.getString("additionalLink2"));
//                murals.setAdditional_link_third(object.getString("additionalLink3"));
//
//
//                if (location != null) {
////                    ProgressBar   progressBar = new ProgressBar(youractivity.this,null,android.R.attr.progressBarStyleLarge);
////                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100,100);
////                    params.addRule(RelativeLayout.CENTER_IN_PARENT);
////                    layout.addView(progressBar,params);
////                    progressBar.setVisibility(View.VISIBLE);  //To show ProgressBar
////                    progressBar.setVisibility(View.GONE);
//                }
//
//                double distanceInKms = 0.0;
//                if (location != null && murals.getLatitude() != 0.0 && murals.getLongitude() != 0.0) {
//                    distanceInKms = distance(location.getLatitude(), location.getLongitude(), murals.getLatitude(), murals.getLongitude());
//                    //Log.e("distance in=", distanceInKms + "");
//                }
//
//                RealmController.getInstance().addMural(murals);
//
//
//                boolean freshMuralFilter = GlobalReferences.getInstance().pref.getFreshFilter();
//                boolean favoriteMuralFilter = GlobalReferences.getInstance().pref.getFavFilter();
//                boolean seenMuralFilter = GlobalReferences.getInstance().pref.getSeenFilter();
//                boolean bookmarkedFilter = GlobalReferences.getInstance().pref.getBookmarkedFilter();
//                boolean nearByFilter = GlobalReferences.getInstance().pref.getNearbyFilter();
//
//                boolean isAnyFilterSelected = false;
//
//                if (freshMuralFilter || favoriteMuralFilter || seenMuralFilter || bookmarkedFilter || nearByFilter) {
//                    isAnyFilterSelected = true;
//                } else {
//                    isAnyFilterSelected = false;
//                }
//
//                if (isAnyFilterSelected) {
//                    list_murals.add(murals);
//                    if (freshMuralFilter) {
//                        if (murals.getFreshWhenAdded().equalsIgnoreCase("1")) {
//                            BitmapDescriptor markerIcon = null;
//                            if (Build.VERSION.SDK_INT >= 21) {
//                                // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue,null);
//                                markerIcon = getMarkerIconFromDrawableFeatured(getActivity().getResources().getDrawable(R.drawable.features, null), false);
//                            } else {
//                                // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue);
//                                markerIcon = getMarkerIconFromDrawableFeatured(getActivity().getResources().getDrawable(R.drawable.features), false);
//                            }
//                            MyItem offsetItem = new MyItem(object.getDouble("latitude"), object.getDouble("longitude"), markerIcon, i);
//                            mClusterManager.addItem(offsetItem);
//
//
//                            // mMap.addMarker(new MarkerOptions().position(new LatLng(object.getDouble("latitude"), object.getDouble("longitude"))).icon(markerIcon)).setTag(i);
//                            //list_murals.add(murals);
//                            continue;
//                        }
//                    }
//
//                    if (favoriteMuralFilter) {
//                        if (RealmController.getInstance().isFavoriteMuralExist(murals.getId())) {
//                            BitmapDescriptor markerIcon = null;
//                            if (Build.VERSION.SDK_INT >= 21) {
//                                // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue,null);
//                                markerIcon = getMarkerIconFromDrawable(getActivity().getResources().getDrawable(R.drawable.murals, null), false);
//                            } else {
//                                // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue);
//                                markerIcon = getMarkerIconFromDrawable(getActivity().getResources().getDrawable(R.drawable.murals), false);
//                            }
//
//                            MyItem offsetItem = new MyItem(object.getDouble("latitude"), object.getDouble("longitude"), markerIcon, i);
//                            mClusterManager.addItem(offsetItem);
//
//
//                            // mMap.addMarker(new MarkerOptions().position(new LatLng(object.getDouble("latitude"), object.getDouble("longitude"))).icon(markerIcon)).setTag(i);
//                            //list_murals.add(murals);
//                            continue;
//                        }
//                    }
//
//                    if (seenMuralFilter) {
//                        if (RealmController.getInstance().isSeenMuralExist(murals.getId())) {
//                            BitmapDescriptor markerIcon = null;
//                            if (Build.VERSION.SDK_INT >= 21) {
//                                // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue,null);
//                                markerIcon = getMarkerIconFromDrawable(getActivity().getResources().getDrawable(R.drawable.murals, null), false);
//                            } else {
//                                // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue);
//                                markerIcon = getMarkerIconFromDrawable(getActivity().getResources().getDrawable(R.drawable.murals), false);
//                            }
//                            MyItem offsetItem = new MyItem(object.getDouble("latitude"), object.getDouble("longitude"), markerIcon, i);
//                            mClusterManager.addItem(offsetItem);
//
//                            // mMap.addMarker(new MarkerOptions().position(new LatLng(object.getDouble("latitude"), object.getDouble("longitude"))).icon(markerIcon)).setTag(i);
//                            //list_murals.add(murals);
//                            continue;
//                        }
//                    }
//
//                    if (bookmarkedFilter) {
//                        if (RealmController.getInstance().isBookMarhedMuralExist(murals.getId())) {
//                            BitmapDescriptor markerIcon = null;
//                            if (Build.VERSION.SDK_INT >= 21) {
//                                // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue,null);
//                                markerIcon = getMarkerIconFromDrawable(getActivity().getResources().getDrawable(R.drawable.murals, null), false);
//                            } else {
//                                // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue);
//                                markerIcon = getMarkerIconFromDrawable(getActivity().getResources().getDrawable(R.drawable.murals), false);
//                            }
//
//                            MyItem offsetItem = new MyItem(object.getDouble("latitude"), object.getDouble("longitude"), markerIcon, i);
//
//                            mClusterManager.addItem(offsetItem);
//                            //mMap.addMarker(new MarkerOptions().position(new LatLng(object.getDouble("latitude"), object.getDouble("longitude"))).icon(markerIcon)).setTag(i);
//                            //list_murals.add(murals);
//                            continue;
//                        }
//                    }
//
//                    if (nearByFilter) {
//                        if (distanceInKms > 0.0 && distanceInKms <= 500.00) {
//                            BitmapDescriptor markerIcon = null;
//                            if (Build.VERSION.SDK_INT >= 21) {
//                                // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue,null);
//                                markerIcon = BitmapDescriptorFactory.fromBitmap(drawNearByMurals(murals, String.valueOf((int) distanceInKms), false));
//                                murals.setDistanceInKms(distanceInKms);
//                            } else {
//                                // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue);
//                                markerIcon = BitmapDescriptorFactory.fromBitmap(drawNearByMurals(murals, String.valueOf((int) distanceInKms), false));
//                                murals.setDistanceInKms(distanceInKms);
//
//                            }
//                            murals.setNearBy(true);
//
//                            MyItem offsetItem = new MyItem(object.getDouble("latitude"), object.getDouble("longitude"), markerIcon, i);
//
//                            mClusterManager.addItem(offsetItem);
//
//                            // mMap.addMarker(new MarkerOptions().position(new LatLng(object.getDouble("latitude"), object.getDouble("longitude"))).icon(markerIcon)).setTag(i);
//                            // list_murals.add(murals);
//                            continue;
//
//                        }
//                    }
//                } else {
//
//                    if (distanceInKms > 0.0 && distanceInKms <= 500.00) {
//                        BitmapDescriptor markerIcon = null;
//                        if (Build.VERSION.SDK_INT >= 21) {
//                            // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue,null);
//                            markerIcon = BitmapDescriptorFactory.fromBitmap(drawNearByMurals(murals, String.valueOf((int) distanceInKms), false));
//                            murals.setDistanceInKms(distanceInKms);
//                        } else {
//                            // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue);
//                            markerIcon = BitmapDescriptorFactory.fromBitmap(drawNearByMurals(murals, String.valueOf((int) distanceInKms), false));
//                            murals.setDistanceInKms(distanceInKms);
//
//                        }
//                        murals.setNearBy(true);
//
//                        MyItem offsetItem = new MyItem(object.getDouble("latitude"), object.getDouble("longitude"), markerIcon, i);
//                        mClusterManager.addItem(offsetItem);
//                        //mMap.addMarker(new MarkerOptions().position(new LatLng(object.getDouble("latitude"), object.getDouble("longitude"))).icon(markerIcon)).setTag(i);
//
//                    } else if (murals.getFreshWhenAdded().equalsIgnoreCase("1")) {
//                        BitmapDescriptor markerIcon = null;
//                        if (Build.VERSION.SDK_INT >= 21) {
//                            // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue,null);
//                            markerIcon = getMarkerIconFromDrawableFeatured(getActivity().getResources().getDrawable(R.drawable.features, null), false);
//                        } else {
//                            // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue);
//                            markerIcon = getMarkerIconFromDrawableFeatured(getActivity().getResources().getDrawable(R.drawable.features), false);
//                        }
//
//                        MyItem offsetItem = new MyItem(object.getDouble("latitude"), object.getDouble("longitude"), markerIcon, i);
//                        mClusterManager.addItem(offsetItem);
//                        // mMap.addMarker(new MarkerOptions().position(new LatLng(object.getDouble("latitude"), object.getDouble("longitude"))).icon(markerIcon)).setTag(i);
//                    } else {
//                        BitmapDescriptor markerIcon = null;
//                        if (Build.VERSION.SDK_INT >= 21) {
//                            // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue,null);
//                            markerIcon = getMarkerIconFromDrawable(getActivity().getResources().getDrawable(R.drawable.murals, null), false);
//                        } else {
//                            // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue);
//                            markerIcon = getMarkerIconFromDrawable(getActivity().getResources().getDrawable(R.drawable.murals), false);
//                        }
//
//                        MyItem offsetItem = new MyItem(object.getDouble("latitude"), object.getDouble("longitude"), markerIcon, i);
//                        mClusterManager.addItem(offsetItem);
//                        // mMap.addMarker(new MarkerOptions().position(new LatLng(object.getDouble("latitude"), object.getDouble("longitude"))).icon(markerIcon)).setTag(i);
//                    }
//                    list_murals.add(murals);
//                }
//
//                if (i == jsonArray.length() - 1) {
//                    if (location != null) {
//                        setCurrentLocation(new LatLng(location.getLatitude(), location.getLongitude()));
//                    }
//                }
//                // builder.include(new LatLng(object.getDouble("latitude"),object.getDouble("longitude")));
//
//
//               /*Check filters*/
//
//
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSeeMoreButtonClicked() {
        if(cardView_dialog!=null){
            cardView_dialog.setVisibility(View.GONE);
        }
    }

    private class LoadData extends AsyncTask<JSONArray,Void,Void>{

        Realm realm;
        boolean isFavMuralExist = false,isSeenMuralExist = false,isBookMarkedMuralExist;
        @Override
        protected Void doInBackground(JSONArray... jsonArrays) {
            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
            try {
              final  JSONArray jsonArray = jsonArrays[0];

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    final Murals murals = new Murals();
                    murals.setId(object.getInt("id"));
                    murals.setFreshWhenAdded(object.getString("freshWhenAdded"));
                    murals.setDerelict(object.getInt("derelict"));
                    murals.setActive(object.getInt("active"));
                    murals.setPopularity(object.getInt("popularity"));
                    murals.setLatitude(object.getDouble("latitude"));
                    murals.setLongitude(object.getDouble("longitude"));
                    murals.setImage_path(object.getString("additionalLink2"));
                    murals.setAuthor(object.getString("artistName"));
                    try {
                        if (object.getString("muralTitle") == null || object.getString("muralTitle").equalsIgnoreCase("null")) {
                            murals.setTitle("Untitled");
                        } else
                            murals.setTitle(object.getString("muralTitle"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    murals.setImage_resource_id(object.getString("imageResourceID"));
                    murals.setAbout_text(object.getString("aboutThisText"));
                    murals.setLocation_text(object.getString("locationText"));
                    murals.setArtist_text(object.getString("aboutArtistText"));
                    murals.setTags(object.getString("tags"));
                    murals.setAdditional_link_first(object.getString("additionalLink1"));
                    murals.setAdditional_link_second(object.getString("additionalLink2"));
                    murals.setAdditional_link_third(object.getString("additionalLink3"));

                    double distanceInKms = 0.0;
                    if (location != null && murals.getLatitude() != 0.0 && murals.getLongitude() != 0.0) {
                        distanceInKms = distance(location.getLatitude(), location.getLongitude(), murals.getLatitude(), murals.getLongitude());
                        //Log.e("distance in=", distanceInKms + "");
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RealmController.getInstance().addMural(murals);
                        }
                    });

                    boolean freshMuralFilter = GlobalReferences.getInstance().pref.getFreshFilter();
                    boolean favoriteMuralFilter = GlobalReferences.getInstance().pref.getFavFilter();
                    boolean seenMuralFilter = GlobalReferences.getInstance().pref.getSeenFilter();
                    boolean bookmarkedFilter = GlobalReferences.getInstance().pref.getBookmarkedFilter();
                    boolean nearByFilter = GlobalReferences.getInstance().pref.getNearbyFilter();

                    boolean isAnyFilterSelected = false;

                    if (freshMuralFilter || favoriteMuralFilter || seenMuralFilter || bookmarkedFilter || nearByFilter) {
                        isAnyFilterSelected = true;
                    } else {
                        isAnyFilterSelected = false;
                    }


                    if (isAnyFilterSelected) {
                        list_murals.add(murals);
                        if (freshMuralFilter) {
                            Log.e("Inside fre mural", "Inside fre mural");
                            if (murals.getFreshWhenAdded().equalsIgnoreCase("1")) {
                                BitmapDescriptor markerIcon = null;
                                if (Build.VERSION.SDK_INT >= 21) {
                                    // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue,null);
                                    markerIcon = getMarkerIconFromDrawableFeatured(getActivity().getResources().getDrawable(R.drawable.features, null), false);
                                } else {
                                    // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue);
                                    markerIcon = getMarkerIconFromDrawableFeatured(getActivity().getResources().getDrawable(R.drawable.features), false);
                                }
                                MyItem offsetItem = new MyItem(object.getDouble("latitude"), object.getDouble("longitude"), markerIcon, i);
                                mClusterManager.addItem(offsetItem);
                                Log.e("Inside fre mural ", "Inside fre mural added");

                                // mMap.addMarker(new MarkerOptions().position(new LatLng(object.getDouble("latitude"), object.getDouble("longitude"))).icon(markerIcon)).setTag(i);
                                //list_murals.add(murals);
                                continue;
                            }
                        }

                        if (favoriteMuralFilter) {
                            //Log.e("Inside fav mural", "Inside fav mural");
                            Realm realm = null;
                            try {
                                realm = Realm.getDefaultInstance();
                                if ( RealmController.getInstance().isFavoriteMuralExist(murals.getId(),realm)) {
                                    Log.e("#Inside fav mural ", "Inside fav mural added");

                                    BitmapDescriptor markerIcon = null;
                                    if (Build.VERSION.SDK_INT >= 21) {
                                        // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue,null);
                                        markerIcon = getMarkerIconFromDrawable(getActivity().getResources().getDrawable(R.drawable.murals, null), false);
                                    } else {
                                        // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue);
                                        markerIcon = getMarkerIconFromDrawable(getActivity().getResources().getDrawable(R.drawable.murals), false);
                                    }

                                    MyItem offsetItem = new MyItem(object.getDouble("latitude"), object.getDouble("longitude"), markerIcon, i);
                                    mClusterManager.addItem(offsetItem);
                                    isFavMuralExist =false;
                                    Log.e("#Inside fav mural ", "Inside fav mural end");

                                    // mMap.addMarker(new MarkerOptions().position(new LatLng(object.getDouble("latitude"), object.getDouble("longitude"))).icon(markerIcon)).setTag(i);
                                    //list_murals.add(murals);
                                    // isFavMuralExist =false;
                                    continue;
                                }
                            } finally {
                                if (realm != null) {
                                    realm.close();
                                }
                            }
                            Log.e("#isFavMuralExist=",isFavMuralExist+"");

                        }

                        if (seenMuralFilter) {
                            //Log.e("Inside seen mural", "Inside seen mural");

                            Realm realm = null;
                            try {
                                realm = Realm.getDefaultInstance();
                                if (RealmController.getInstance().isSeenMuralExist(murals.getId(),realm)) {
                                    Log.e("#Inside seen mural", "Inside seen mural added");

                                    BitmapDescriptor markerIcon = null;
                                    if (Build.VERSION.SDK_INT >= 21) {
                                        // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue,null);
                                        markerIcon = getMarkerIconFromDrawable(getActivity().getResources().getDrawable(R.drawable.murals, null), false);
                                    } else {
                                        // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue);
                                        markerIcon = getMarkerIconFromDrawable(getActivity().getResources().getDrawable(R.drawable.murals), false);
                                    }
                                    MyItem offsetItem = new MyItem(object.getDouble("latitude"), object.getDouble("longitude"), markerIcon, i);
                                    mClusterManager.addItem(offsetItem);
                                    Log.e("Inside seen mural ", "Inside seen mural end");
                                    isSeenMuralExist = false;
                                    // mMap.addMarker(new MarkerOptions().position(new LatLng(object.getDouble("latitude"), object.getDouble("longitude"))).icon(markerIcon)).setTag(i);
                                    //list_murals.add(murals);
                                    continue;
                                }
                            } finally {
                                if (realm != null) {
                                    realm.close();
                                }
                            }
                        }
                        if (bookmarkedFilter) {
                           // Log.e("Inside bookmarked mural", "Inside bboke mural"+murals.getId());
                            Realm realm = null;
                            try {
                                realm = Realm.getDefaultInstance();
                                if ( RealmController.getInstance().isBookMarhedMuralExist(murals.getId(),realm)) {
                                    Log.e("#Inside book mural", "Inside bboke mural added");

                                    BitmapDescriptor markerIcon = null;
                                    if (Build.VERSION.SDK_INT >= 21) {
                                        // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue,null);
                                        markerIcon = getMarkerIconFromDrawable(getActivity().getResources().getDrawable(R.drawable.murals, null), false);
                                    } else {
                                        // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue);
                                        markerIcon = getMarkerIconFromDrawable(getActivity().getResources().getDrawable(R.drawable.murals), false);
                                    }

                                    MyItem offsetItem = new MyItem(object.getDouble("latitude"), object.getDouble("longitude"), markerIcon, i);

                                    mClusterManager.addItem(offsetItem);
                                    Log.e("#Inside book mural", "Inside bboke mural End");

                                    isBookMarkedMuralExist =false;
                                    //mMap.addMarker(new MarkerOptions().position(new LatLng(object.getDouble("latitude"), object.getDouble("longitude"))).icon(markerIcon)).setTag(i);
                                    //list_murals.add(murals);
                                    continue;
                                }
                            } finally {
                                if (realm != null) {
                                    realm.close();
                                }
                            }

                        }

                        if (nearByFilter) {
                            Log.e("Inside disss mural", "Inside diss mural");

                            if (distanceInKms > 0.0 && distanceInKms <= 500.00) {
                                BitmapDescriptor markerIcon = null;
                                if (Build.VERSION.SDK_INT >= 21) {
                                    // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue,null);
                                    markerIcon = BitmapDescriptorFactory.fromBitmap(drawNearByMurals(murals, String.valueOf((int) distanceInKms), false));
                                    murals.setDistanceInKms(distanceInKms);
                                } else {
                                    // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue);
                                    markerIcon = BitmapDescriptorFactory.fromBitmap(drawNearByMurals(murals, String.valueOf((int) distanceInKms), false));
                                    murals.setDistanceInKms(distanceInKms);
                                }
                                murals.setNearBy(true);

                                MyItem offsetItem = new MyItem(object.getDouble("latitude"), object.getDouble("longitude"), markerIcon, i);

                                mClusterManager.addItem(offsetItem);
                                Log.e("Inside disss mural", "Inside diss mural added");

                                // mMap.addMarker(new MarkerOptions().position(new LatLng(object.getDouble("latitude"), object.getDouble("longitude"))).icon(markerIcon)).setTag(i);
                                // list_murals.add(murals);
                                continue;
                            }
                        }
                    } else {

                        if (distanceInKms > 0.0 && distanceInKms <= 500.00) {
                            Log.e("in else", "in else");
                            BitmapDescriptor markerIcon = null;
                            if (Build.VERSION.SDK_INT >= 21) {
                                // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue,null);
                                markerIcon = BitmapDescriptorFactory.fromBitmap(drawNearByMurals(murals, String.valueOf((int) distanceInKms), false));
                                murals.setDistanceInKms(distanceInKms);
                            } else {
                                // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue);
                                markerIcon = BitmapDescriptorFactory.fromBitmap(drawNearByMurals(murals, String.valueOf((int) distanceInKms), false));
                                murals.setDistanceInKms(distanceInKms);

                            }
                            murals.setNearBy(true);

                            MyItem offsetItem = new MyItem(object.getDouble("latitude"), object.getDouble("longitude"), markerIcon, i);
                            mClusterManager.addItem(offsetItem);
                            //mMap.addMarker(new MarkerOptions().position(new LatLng(object.getDouble("latitude"), object.getDouble("longitude"))).icon(markerIcon)).setTag(i);

                        } else if (murals.getFreshWhenAdded().equalsIgnoreCase("1")) {
                            BitmapDescriptor markerIcon = null;
                            if (Build.VERSION.SDK_INT >= 21) {
                                // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue,null);
                                markerIcon = getMarkerIconFromDrawableFeatured(getActivity().getResources().getDrawable(R.drawable.features, null), false);
                            } else {
                                // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue);
                                markerIcon = getMarkerIconFromDrawableFeatured(getActivity().getResources().getDrawable(R.drawable.features), false);
                            }

                            MyItem offsetItem = new MyItem(object.getDouble("latitude"), object.getDouble("longitude"), markerIcon, i);
                            mClusterManager.addItem(offsetItem);
                            // mMap.addMarker(new MarkerOptions().position(new LatLng(object.getDouble("latitude"), object.getDouble("longitude"))).icon(markerIcon)).setTag(i);
                        } else {
                            BitmapDescriptor markerIcon = null;
                            if (Build.VERSION.SDK_INT >= 21) {
                                // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue,null);
                                markerIcon = getMarkerIconFromDrawable(getActivity().getResources().getDrawable(R.drawable.murals, null), false);
                            }else {
                                // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue);
                                markerIcon = getMarkerIconFromDrawable(getActivity().getResources().getDrawable(R.drawable.murals), false);
                            }

                            MyItem offsetItem = new MyItem(object.getDouble("latitude"), object.getDouble("longitude"), markerIcon, i);
                            mClusterManager.addItem(offsetItem);
                            // mMap.addMarker(new MarkerOptions().position(new LatLng(object.getDouble("latitude"), object.getDouble("longitude"))).icon(markerIcon)).setTag(i);
                        }
                        list_murals.add(murals);
                    }



                }

                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                    if (location != null) {
                                        setCurrentLocation(new LatLng(location.getLatitude(), location.getLongitude()));
                                    }

                            }
                        });
            }catch (Exception ex){
                Log.e(TAG, "doInBackground: "+ex.getMessage() );

            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
             viewPagerLayoutOnMap = new ViewPagerLayoutOnMap(CanvsMapFragment.this,list_murals,getActivity().getSupportFragmentManager());
            cardView_dialog.setAdapter(viewPagerLayoutOnMap);

        }
    }



    public Bitmap drawNearByMurals(Murals murals, String distance, boolean isClick) {
        Bitmap bmp = null;
        try {

            bmp = BitmapFactory.decodeResource(getActivity().getResources(),
                    R.drawable.near_by_murals);

            android.graphics.Bitmap.Config bitmapConfig = bmp.getConfig();

            // set default bitmap config if none
            if (bitmapConfig == null)
                bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;

            bmp = bmp.copy(bitmapConfig, true);
            Canvas canvas1 = new Canvas(bmp);

            Paint color = new Paint();
            color.setTextSize(35);
            color.setColor(Color.WHITE);
            color.setTypeface(Utility.getFontRobotoCondensedBold());
            if (isClick) {
                ColorFilter filter = new PorterDuffColorFilter(ContextCompat.getColor(getActivity(), R.color.orange), PorterDuff.Mode.SRC_IN);
                color.setColorFilter(filter);
            } else {

            }

            color.setTextAlign(Paint.Align.CENTER);
            int xPos = (canvas1.getWidth() / 2);
            int yPos = (int) ((canvas1.getHeight() / 2) - ((color.descent() + color.ascent()) / 2));
            canvas1.drawBitmap(BitmapFactory.decodeResource(getResources(),
                    R.drawable.near_by_murals), 0, 0, color);
            canvas1.drawText(distance, xPos, yPos, color);
            // bmp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }


    public void setCurrentLocation(LatLng latlng) {

        Log.e("get currnt loc", latlng + "");
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

//        if (marker_previous != null) {
//            Murals murals = (Murals) list_murals.get((int) marker_previous.getTag());
//            if (murals.isNearBy()) {
//                Bitmap markerIcon = null;
//                if (Build.VERSION.SDK_INT >= 21) {
//                    // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue,null);
//                    markerIcon = drawNearByMurals(murals, String.valueOf((int) murals.getDistanceInKms()) + "", false);
//                } else {
//                    // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue);
//                    markerIcon = drawNearByMurals(murals, String.valueOf((int) murals.getDistanceInKms()) + "", false);
//                }
//                marker_previous.setIcon(BitmapDescriptorFactory.fromBitmap(markerIcon));
//
//            } else if (murals.getFreshWhenAdded().equalsIgnoreCase("1")) {
//                BitmapDescriptor markerIcon = null;
//                if (Build.VERSION.SDK_INT >= 21) {
//                    // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue,null);
//                    markerIcon = getMarkerIconFromDrawableFeatured(getActivity().getResources().getDrawable(R.drawable.features, null), false);
//                } else {
//                    // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue);
//                    markerIcon = getMarkerIconFromDrawableFeatured(getActivity().getResources().getDrawable(R.drawable.features), false);
//                }
//                marker_previous.setIcon(markerIcon);
//            } else {
//                BitmapDescriptor markerIcon = null;
//                if (Build.VERSION.SDK_INT >= 21) {
//                    Drawable circleDrawable = getResources().getDrawable(R.drawable.murals, null);
//                    markerIcon = getMarkerIconFromDrawable(circleDrawable, false);
//                    marker.setIcon(markerIcon);
//
//                } else {
//                    Drawable circleDrawable = getResources().getDrawable(R.drawable.murals);
//                    markerIcon = getMarkerIconFromDrawable(circleDrawable, false);
//                    marker.setIcon(markerIcon);
//                }
//                marker_previous.setIcon(markerIcon);
//            }
//        }
//        marker_previous = marker;
//
//        final Murals murals = list_murals.get((int) marker.getTag());
//
//        if (murals.isNearBy()) {
//            Bitmap markerIcon = null;
//            if (Build.VERSION.SDK_INT >= 21) {
//                // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue,null);
//                markerIcon = drawNearByMurals(murals, 99 + "", true);
//            } else {
//                // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue);
//                markerIcon = drawNearByMurals(murals, 99 + "", true);
//            }
//            marker_previous.setIcon(BitmapDescriptorFactory.fromBitmap(markerIcon));
//
//        } else if (murals.getFreshWhenAdded().equalsIgnoreCase("1")) {
//            BitmapDescriptor markerIcon = null;
//            if (Build.VERSION.SDK_INT >= 21) {
//                // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue,null);
//                markerIcon = getMarkerIconFromDrawableFeatured(getActivity().getResources().getDrawable(R.drawable.features, null), false);
//            } else {
//                // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue);
//                markerIcon = getMarkerIconFromDrawableFeatured(getActivity().getResources().getDrawable(R.drawable.features), false);
//            }
//            marker_previous.setIcon(markerIcon);
//        } else {
//            if (Build.VERSION.SDK_INT >= 21) {
//                Drawable circleDrawable = getResources().getDrawable(R.drawable.murals, null);
//                BitmapDescriptor markerIcon = getMarkerIconFromDrawable(circleDrawable, true);
//                marker.setIcon(markerIcon);
//
//            } else {
//                Drawable circleDrawable = getResources().getDrawable(R.drawable.murals);
//                BitmapDescriptor markerIcon = getMarkerIconFromDrawable(circleDrawable, true);
//                marker.setIcon(markerIcon);
//            }
//        }
//        try {
//            if (murals.getFreshWhenAdded().equalsIgnoreCase("1")) {
//                fresh_mural_tag.setVisibility(View.VISIBLE);
//            } else {
//                fresh_mural_tag.setVisibility(View.GONE);
//            }
//        } catch (Exception e) {
//
//        }
//        cardView_dialog.setVisibility(View.VISIBLE);
//        try {
//            final String image_id = murals.getImage_resource_id().toLowerCase();
//            String image_url = "https://canvs.cruxcode.nyc/mural_thumb_" + image_id.toLowerCase() + ".jpg?size=thumb&requestType=image";
//            textView_more.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    FragmentMuralDetail fragmentMuralDetail = new FragmentMuralDetail();
//                    Bundle bundle = new Bundle();
//                    bundle.putParcelable("mural", murals);
//                    bundle.putString("image_id", image_id);
//                    bundle.putString("location_text", murals.getLocation_text());
//
//                    bundle.putString("artist_text", murals.getArtist_text());
//                    bundle.putString("about_text", murals.getAbout_text());
//                    bundle.putString("tags", murals.getTags());
//                    bundle.putString("addlink1", murals.getAdditional_link_first());
//                    bundle.putString("addlink2", murals.getAdditional_link_second());
//                    bundle.putString("addlink3", murals.getAdditional_limk_third());
//                    bundle.putString("artist", murals.getAuthor());
//                    bundle.putString("name", murals.getTitle());
//                    bundle.putDouble("lat", murals.getLatitude());
//                    bundle.putDouble("lon", murals.getLongitude());
//                    fragmentMuralDetail.setArguments(bundle);
//                    ((BaseActivity) GlobalReferences.getInstance().baseActivity).addFragmentWithBackStack(fragmentMuralDetail, true);
//                    cardView_dialog.setVisibility(View.GONE);
//                }
//            });
//            textView_title.setText(murals.getTitle());
//
//            textView_author.setText(murals.getAuthor());
//
//            Log.e(TAG, "onCreate: " + image_url);
//            Glide.with(GlobalReferences.getInstance().baseActivity).load(image_url)
//                    .thumbnail(1)
//                    .placeholder(R.color.grey_)
//                    .error(R.color.grey_)
//                    .into(imageView);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
        mMap.animateCamera(cameraUpdate);
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable, boolean isClicked) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeResource(getActivity().getResources(), murals);

            android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();

            // set default bitmap config if none
            if (bitmapConfig == null)
                bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;

            bitmap = bitmap.copy(bitmapConfig, true);
            Canvas canvas1 = new Canvas(bitmap);

            Paint color = new Paint();
            color.setTypeface(Utility.getFontRobotoCondensedBold());
            if (isClicked) {
                ColorFilter filter = new PorterDuffColorFilter(ContextCompat.getColor(getActivity(), R.color.orange), PorterDuff.Mode.SRC_IN);
                color.setColorFilter(filter);
            }

            color.setTextAlign(Paint.Align.CENTER);

            canvas1.drawBitmap(BitmapFactory.decodeResource(getResources(),
                    murals), 0, 0, color);

        } catch (Exception e) {

        }
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private BitmapDescriptor getMarkerIconFromDrawableFeatured(Drawable drawable, boolean isClicked) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.features);

            android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();

            // set default bitmap config if none
            if (bitmapConfig == null)
                bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;

            bitmap = bitmap.copy(bitmapConfig, true);
            Canvas canvas1 = new Canvas(bitmap);

            Paint color = new Paint();
            color.setTypeface(Utility.getFontRobotoCondensedBold());
            if (isClicked) {
                ColorFilter filter = new PorterDuffColorFilter(ContextCompat.getColor(getActivity(), R.color.orange), PorterDuff.Mode.LIGHTEN);
                color.setColorFilter(filter);
            }

            color.setTextAlign(Paint.Align.CENTER);

            canvas1.drawBitmap(BitmapFactory.decodeResource(getResources(),
                    R.drawable.features), 0, 0, color);
        } catch (Exception e) {

        }
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public Bitmap GetBitmapMarker(Context mContext, int resourceId, String mText) {
        try {
            Resources resources = mContext.getResources();
            float scale = resources.getDisplayMetrics().density;
            // Bitmap bitmap = BitmapFactory.decodeResource(resources, resourceId);

            Drawable drawable = ContextCompat.getDrawable(mContext, resourceId);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                drawable = (DrawableCompat.wrap(drawable)).mutate();
            }

            Bitmap bitmap = BitmapFactory.decodeResource(resources, resourceId);

            android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();

            // set default bitmap config if none
            if (bitmapConfig == null)
                bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;

            bitmap = bitmap.copy(bitmapConfig, true);

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER)); // Text Overlapping Pattern

            paint.setColor(Color.WHITE);
            paint.setTextSize((int) (14 * scale));
            paint.setShadowLayer(1f, 0f, 1f, Color.DKGRAY);

            // draw text to the Canvas center
            Rect bounds = new Rect();
            paint.getTextBounds(mText, 0, mText.length(), bounds);
            int x = (bitmap.getWidth() - bounds.width()) / 2;
            int y = (bitmap.getHeight() + bounds.height()) / 2;

            canvas.drawText(mText, x * scale, y * scale, paint);

            return bitmap;

        } catch (Exception e) {
            return null;
        }
    }

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
        locationHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // other 'case' lines to check for other permissions this app might request.
        // You can add here other case statements according to your requirement.
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
        location = locationHelper.getLocation();
        Log.e("location=", location + "");
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        // PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if(mapView!=null)
        mapView.onResume();
        locationHelper.checkPlayServices();

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
        if (huntmode) {
            tv_hunt_mode.setText("HUNT MODE ON");
            // cardView_hunt_mode.setRadius(12f);
            //DrawableCompat.setTint(tv_hunt_mode.getDr.getDrawable(), ContextCompat.getColor(context, R.color.another_nice_color));

            tv_hunt_mode.setBackground(getActivity().getResources().getDrawable(R.drawable.round_btn_orange));
        } else {
            tv_hunt_mode.setText("HUNT MODE OFF");
            tv_hunt_mode.setBackground(getActivity().getResources().getDrawable(R.drawable.round_btn_));

        }
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    @Override
    public void setOnMap(Murals murals) {

        if (murals != null) {
            Log.e("Setting mural on sad", murals + "");


            try {
                if (cardView_dialog != null) {
                    cardView_dialog.setVisibility(View.GONE);
                }
                setCurrentLocation(new LatLng(murals.getLatitude(), murals.getLongitude()));


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        cardView_dialog.setVisibility(View.GONE);
    }

    @Override
    public boolean onClusterItemClick(final MyItem myItem) {


        Marker marker = ownIconRendered.getMarker(myItem);

        if (marker_previous != null) {
            // marker_previous.setIcon(previous_icon);

            Murals murals = (Murals) list_murals.get(previous_item.getMarkerPosition());
            if (murals.isNearBy()) {
                Bitmap markerIcon = null;
                BitmapDescriptor icon = null;

                if (Build.VERSION.SDK_INT >= 21) {
                    // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue,null);
                    markerIcon = drawNearByMurals(murals, String.valueOf((int) murals.getDistanceInKms()) + "", false);
                } else {
                    // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue);
                    markerIcon = drawNearByMurals(murals, String.valueOf((int) murals.getDistanceInKms()) + "", false);
                }
                icon = BitmapDescriptorFactory.fromBitmap(markerIcon);

                if (marker_previous!=null&&marker_previous.isVisible())  {
                    try {
                        marker_previous.setIcon(icon);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            } else if (murals.getFreshWhenAdded().equalsIgnoreCase("1")) {
                BitmapDescriptor markerIcon = null;
                if (Build.VERSION.SDK_INT >= 21) {
                    // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue,null);
                    markerIcon = getMarkerIconFromDrawableFeatured(getActivity().getResources().getDrawable(R.drawable.features, null), false);
                } else {
                    // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue);
                    markerIcon = getMarkerIconFromDrawableFeatured(getActivity().getResources().getDrawable(R.drawable.features), false);
                }
                if (marker_previous!=null&&marker_previous.isVisible())  {
                    try {
                        marker_previous.setIcon(markerIcon);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            } else {
                BitmapDescriptor markerIcon = null;
                if (Build.VERSION.SDK_INT >= 21) {
                    Drawable circleDrawable = getResources().getDrawable(R.drawable.murals, null);
                    markerIcon = getMarkerIconFromDrawable(circleDrawable, false);
                    marker.setIcon(markerIcon);

                } else {
                    Drawable circleDrawable = getResources().getDrawable(R.drawable.murals);
                    markerIcon = getMarkerIconFromDrawable(circleDrawable, false);
                    marker.setIcon(markerIcon);
                }
                if (marker_previous!=null&&marker_previous.isVisible())  {
                    try {
                        marker_previous.setIcon(markerIcon);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        marker_previous = marker;
        previous_item = myItem;

        final Murals murals = list_murals.get(myItem.getMarkerPosition());
        current_pos=myItem.getMarkerPosition();

        if (murals.isNearBy()) {

            Bitmap markerIcon = null;
            BitmapDescriptor icon;
            if (Build.VERSION.SDK_INT >= 21) {
                // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue,null);
                markerIcon = drawNearByMurals(murals, 99 + "", true);
            } else {
                // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue);
                markerIcon = drawNearByMurals(murals, 99 + "", true);
            }
            icon = BitmapDescriptorFactory.fromBitmap(markerIcon);
            marker_previous.setIcon(icon);

        } else if (murals.getFreshWhenAdded().equalsIgnoreCase("1")) {
            BitmapDescriptor markerIcon = null;
            if (Build.VERSION.SDK_INT >= 21) {
                // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue,null);
                markerIcon = getMarkerIconFromDrawableFeatured(getActivity().getResources().getDrawable(R.drawable.features, null), false);
            } else {
                // Drawable circleDrawable = getResources().getDrawable(R.drawable.circle_shape_blue);
                markerIcon = getMarkerIconFromDrawableFeatured(getActivity().getResources().getDrawable(R.drawable.features), false);
            }
            marker_previous.setIcon(markerIcon);
        } else {
            if (Build.VERSION.SDK_INT >= 21) {
                Drawable circleDrawable = getResources().getDrawable(R.drawable.murals, null);
                BitmapDescriptor markerIcon = getMarkerIconFromDrawable(circleDrawable, true);

                marker.setIcon(markerIcon);

            } else {
                Drawable circleDrawable = getResources().getDrawable(R.drawable.murals);
                BitmapDescriptor markerIcon = getMarkerIconFromDrawable(circleDrawable, true);
                marker.setIcon(markerIcon);
            }
        }
        try {
            if (murals.getFreshWhenAdded().equalsIgnoreCase("1")) {
                fresh_mural_tag.setVisibility(View.VISIBLE);
            } else {
                fresh_mural_tag.setVisibility(View.GONE);
            }
        } catch (Exception e) {

        }
        if(viewPagerLayoutOnMap!=null){
            viewPagerLayoutOnMap.notifyDataSetChanged();
            cardView_dialog.setCurrentItem(myItem.getMarkerPosition());
        }
        cardView_dialog.setVisibility(View.VISIBLE);


        return true;
    }

    public void buildCardDialogue(final Murals murals){
        try {
            if (murals.getFreshWhenAdded().equalsIgnoreCase("1")) {
                fresh_mural_tag.setVisibility(View.VISIBLE);
            } else {
                fresh_mural_tag.setVisibility(View.GONE);
            }
        } catch (Exception e) {

        }
        try {
            cardView_dialog.setVisibility(View.VISIBLE);

            final String image_id = murals.getImage_resource_id().toLowerCase();
            String image_url = "https://canvs.cruxcode.nyc/mural_thumb_" + image_id.toLowerCase() + ".jpg?size=thumb&requestType=image";
            textView_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FragmentMuralDetail fragmentMuralDetail = new FragmentMuralDetail();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("mural", murals);
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
                    .thumbnail(1)
                    .placeholder(R.color.grey_)
                    .error(R.color.grey_)
                    .into(imageView);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

