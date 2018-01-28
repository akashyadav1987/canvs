package com.canvas;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.canvas.common.CommonFragment;
import com.canvas.common.GlobalReferences;
import com.canvas.controller.RealmController;
import com.canvas.db.Pref;
import com.canvas.fragment.AboutUsFragment;
import com.canvas.fragment.BookMarkFragment;
import com.canvas.fragment.CanvsMapFragment;
import com.canvas.fragment.FavoritesFragment;
import com.canvas.fragment.SeenFragment;
import com.canvas.listener.SearchResultFound;
import com.canvas.model.Murals;
import com.canvas.utils.BottomNavigationViewHelper;
import com.crashlytics.android.Crashlytics;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;
import io.realm.RealmResults;


public class MotherActivity extends BaseActivity {
    private MenuItem bookmarks, seen, fav, about;
    private MenuItem mSearchItem;
    private MaterialSearchView searchView;
    private RealmResults<Murals> realmResultsSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_mother);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setSelectedItemId(-1);
        Menu menu = bottomNavigationView.getMenu();
        GlobalReferences.getInstance().baseActivity = this;
        RealmController.with(this);
        RealmController.getInstance().deleteAllMural();

        Log.e("All mural deleted","ALl mural deleted");
        GlobalReferences.getInstance().toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        GlobalReferences.getInstance().pref  = new Pref(this);
        GlobalReferences.getInstance().progresBar = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
        bookmarks = menu.findItem(R.id.bookmarks);
        seen = menu.findItem(R.id.seen);
        fav = menu.findItem(R.id.fav);
        about = menu.findItem(R.id.about);
        bookmarks.setCheckable(true);
        seen.setCheckable(true);
        fav.setCheckable(true);
        about.setCheckable(true);
        //RealmController.with(this).;
        String uid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.e("####uid",uid+"");
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // updateTab(item.getItemId());
                switch (item.getItemId()) {
                    case R.id.bookmarks:
                        ((BaseActivity) GlobalReferences.getInstance().baseActivity).addFragmentWithBackStack(new BookMarkFragment(), true);
                        break;
                    case R.id.seen:
                        ((BaseActivity) GlobalReferences.getInstance().baseActivity).addFragmentWithBackStack(new SeenFragment(), true);
                        break;
                    case R.id.fav:
                        ((BaseActivity) GlobalReferences.getInstance().baseActivity).addFragmentWithBackStack(new FavoritesFragment(), true);
                        break;
                    case R.id.about:
                        ((BaseActivity) GlobalReferences.getInstance().baseActivity).addFragmentWithBackStack(new AboutUsFragment(), true);
                        break;
                }
                return false;
            }
        });
        setSupportActionBar(GlobalReferences.getInstance().toolbar);
       // getSupportActionBar().setDisplayShowHomeEnabled(false);
       // getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                Log.i("Tag", "back stack changed ");
                int backCount = getSupportFragmentManager().getBackStackEntryCount();

                if (backCount > 0) {
                    FragmentManager.BackStackEntry backStackEntry = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1);
                    String str = backStackEntry.getName();
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag(str);
                    GlobalReferences.getInstance().mCommonFragment = (CommonFragment) fragment;
                    try {
                        ((CommonFragment) GlobalReferences.getInstance().mCommonFragment).onRefresh();

                        if (GlobalReferences.getInstance().mCommonFragment instanceof BookMarkFragment) {
                            updateTab(R.id.bookmarks);
                            if(searchView!=null){
                                if(searchView.isSearchOpen()){
                                    searchView.closeSearch();
                                }
                            }
                        } else if (GlobalReferences.getInstance().mCommonFragment instanceof FavoritesFragment) {
                            updateTab(R.id.fav);
                            if(searchView!=null){
                                if(searchView.isSearchOpen()){
                                    searchView.closeSearch();
                                }
                            }
                        }
                        else if (GlobalReferences.getInstance().mCommonFragment instanceof FavoritesFragment) {
                            updateTab(R.id.fav);
                            if(searchView!=null){
                                if(searchView.isSearchOpen()){
                                    searchView.closeSearch();
                                }
                            }
                        }
                        else if (GlobalReferences.getInstance().mCommonFragment instanceof CanvsMapFragment) {
                            updateTab(-1);
                        }
                        else if (GlobalReferences.getInstance().mCommonFragment instanceof SeenFragment) {
                            updateTab(R.id.seen);
                            if(searchView!=null){
                                if(searchView.isSearchOpen()){
                                    searchView.closeSearch();
                                }
                            }
                        }
                        else if (GlobalReferences.getInstance().mCommonFragment instanceof AboutUsFragment) {
                            updateTab(R.id.about);
                            if(searchView!=null){
                                if(searchView.isSearchOpen()){
                                    searchView.closeSearch();
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
                    if (fragment != null) {
                        GlobalReferences.getInstance().mCommonFragment = (CommonFragment) fragment;
                        try {
                            ((CommonFragment) GlobalReferences.getInstance().mCommonFragment).onRefresh();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        bookmarks.setIcon(new IconDrawable(this, FontAwesomeIcons.fa_bookmark)
                .colorRes(R.color.grey)
                .actionBarSize());
        seen.setIcon(new IconDrawable(this, FontAwesomeIcons.fa_check)
                .colorRes(R.color.grey)
                .actionBarSize());
        fav.setIcon(new IconDrawable(this, FontAwesomeIcons.fa_heart)
                .colorRes(R.color.grey)
                .actionBarSize());
        about.setIcon(new IconDrawable(this, FontAwesomeIcons.fa_info_circle)
                .colorRes(R.color.grey)
                .actionBarSize());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                updateTab(item.getItemId());
                return false;
            }
        });


       searchView = (MaterialSearchView) findViewById(R.id.search_view);
        //int id = GlobalReferences.getInstance().searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
       // TextView textView = (TextView) GlobalReferences.getInstance().searchView.findViewById(id);
        //textView.setTextColor(Color.parseColor("#8B8A89"));
        searchView.setVoiceSearch(true);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);
        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("asaaa","asa");

            }
        });

        searchView.setSubmitOnClick(true);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e("query submittt",query+"");
                Murals murals=  RealmController.getInstance().findMuralByTitle(query);
                if(murals!=null){
                    if(GlobalReferences.getInstance().mCommonFragment instanceof CanvsMapFragment){
                        ((CanvsMapFragment)GlobalReferences.getInstance().mCommonFragment).setOnMap(murals);
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                if(!newText.trim().isEmpty()){
                    RealmController.getInstance().searchForMural(newText.toLowerCase(), new SearchResultFound() {
                    @Override
                    public void onSearchResultFound(RealmResults<Murals> realmResults) {
                        Log.e("rows",realmResults+"");
                        realmResultsSearch =null;
                        realmResultsSearch= realmResults;
                        if(realmResults!=null){
                            String strings[] = new String[realmResults.size()];
                            for (int i =0;i<realmResults.size();i++) {
                                Murals murals = realmResults.get(i);
                                if(murals.getTitle()!=null)
                                strings[i] =murals.getTitle();

                            }
                            Log.e("showing result","showww ="+strings);
                            searchView.setSuggestions(strings);
                        }
                    }
                });
                }
                return false;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });


        addFragmentWithBackStack(new CanvsMapFragment(), true);

    }


    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            if(searchView.isSearchOpen()){
                searchView.closeSearch();
            }else {
                finish();
            }
        } else {
                super.onBackPressed();

        }

    }

    public void updateTab(int selectedTab) {

        switch (selectedTab) {
            case R.id.bookmarks:
                bookmarks.setIcon(new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_bookmark)
                        .colorRes(R.color.red)
                        .actionBarSize());

                seen.setIcon(new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_check)
                        .colorRes(R.color.grey)
                        .actionBarSize());
                fav.setIcon(new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_heart)
                        .colorRes(R.color.grey)
                        .actionBarSize());
                about.setIcon(new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_info_circle)
                        .colorRes(R.color.grey)
                        .actionBarSize());
                //((BaseActivity) GlobalReferences.getInstance().baseActivity).addFragmentWithBackStack(new BookMarkFragment(), true);

                break;
            case R.id.seen:
                seen.setIcon(new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_check)
                        .colorRes(R.color.red)
                        .actionBarSize());
                bookmarks.setIcon(new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_bookmark)
                        .colorRes(R.color.grey)
                        .actionBarSize());
                fav.setIcon(new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_heart)
                        .colorRes(R.color.grey)
                        .actionBarSize());
                about.setIcon(new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_info_circle)
                        .colorRes(R.color.grey)
                        .actionBarSize());
               // ((BaseActivity) GlobalReferences.getInstance().baseActivity).addFragmentWithBackStack(new SeenFragment(), true);

                break;
            case R.id.fav:
                bookmarks.setIcon(new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_bookmark)
                        .colorRes(R.color.grey)
                        .actionBarSize());
                seen.setIcon(new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_check)
                        .colorRes(R.color.grey)
                        .actionBarSize());
                fav.setIcon(new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_heart)
                        .colorRes(R.color.red)
                        .actionBarSize());
                about.setIcon(new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_info_circle)
                        .colorRes(R.color.grey)
                        .actionBarSize());
                //((BaseActivity) GlobalReferences.getInstance().baseActivity).addFragmentWithBackStack(new FavoritesFragment(), true);

                break;
            case R.id.about:
                about.setIcon(new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_info_circle)
                        .colorRes(R.color.red)
                        .actionBarSize());
                bookmarks.setIcon(new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_bookmark)
                        .colorRes(R.color.grey)
                        .actionBarSize());
                seen.setIcon(new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_check)
                        .colorRes(R.color.grey)
                        .actionBarSize());
                fav.setIcon(new IconDrawable(getApplicationContext(), FontAwesomeIcons.fa_heart)
                        .colorRes(R.color.grey)
                        .actionBarSize());
                //((BaseActivity) GlobalReferences.getInstance().baseActivity).addFragmentWithBackStack(new AboutUsFragment(), true);

                break;
            default:
                bookmarks.setIcon(new IconDrawable(this, FontAwesomeIcons.fa_bookmark)
                        .colorRes(R.color.grey)
                        .actionBarSize());
                seen.setIcon(new IconDrawable(this, FontAwesomeIcons.fa_check)
                        .colorRes(R.color.grey)
                        .actionBarSize());
                fav.setIcon(new IconDrawable(this, FontAwesomeIcons.fa_heart)
                        .colorRes(R.color.grey)
                        .actionBarSize());
                about.setIcon(new IconDrawable(this, FontAwesomeIcons.fa_info_circle)
                        .colorRes(R.color.grey)
                        .actionBarSize());
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    public void animateSearchToolbar(int numberOfMenuIcon, boolean containsOverflow, boolean show) {

        GlobalReferences.getInstance().toolbar.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
       // mDrawerLayout.setStatusBarBackgroundColor(ContextCompat.getColor(this, R.color.quantum_grey_600));

        if (show) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int width =  GlobalReferences.getInstance().toolbar.getWidth() -
                        (containsOverflow ? getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material) : 0) -
                        ((getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) * numberOfMenuIcon) / 2);
                Animator createCircularReveal = ViewAnimationUtils.createCircularReveal( GlobalReferences.getInstance().toolbar,
                        isRtl(getResources()) ?  GlobalReferences.getInstance().toolbar.getWidth() - width : width,  GlobalReferences.getInstance().toolbar.getHeight() / 2, 0.0f, (float) width);
                createCircularReveal.setDuration(250);
                createCircularReveal.start();
            } else {
                TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, (float) (- GlobalReferences.getInstance().toolbar.getHeight()), 0.0f);
                translateAnimation.setDuration(220);
                GlobalReferences.getInstance().toolbar.clearAnimation();
                GlobalReferences.getInstance().toolbar.startAnimation(translateAnimation);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int width =  GlobalReferences.getInstance().toolbar.getWidth() -
                        (containsOverflow ? getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_overflow_material) : 0) -
                        ((getResources().getDimensionPixelSize(R.dimen.abc_action_button_min_width_material) * numberOfMenuIcon) / 2);
                Animator createCircularReveal = ViewAnimationUtils.createCircularReveal( GlobalReferences.getInstance().toolbar,
                        isRtl(getResources()) ?  GlobalReferences.getInstance().toolbar.getWidth() - width : width,  GlobalReferences.getInstance().toolbar.getHeight() / 2, (float) width, 0.0f);
                createCircularReveal.setDuration(250);
                createCircularReveal.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        GlobalReferences.getInstance().toolbar.setBackgroundColor(getThemeColor(GlobalReferences.getInstance().baseActivity, R.attr.colorPrimary));
                        //mDrawerLayout.setStatusBarBackgroundColor(getThemeColor(GlobalReferences.getInstance().baseActivity, R.attr.colorPrimaryDark));
                    }
                });
                createCircularReveal.start();
            } else {
                AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
                Animation translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, (float) (-GlobalReferences.getInstance().toolbar.getHeight()));
                AnimationSet animationSet = new AnimationSet(true);
                animationSet.addAnimation(alphaAnimation);
                animationSet.addAnimation(translateAnimation);
                animationSet.setDuration(220);
                animationSet.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        GlobalReferences.getInstance().toolbar.setBackgroundColor(getThemeColor(getApplication(), R.attr.colorPrimary));
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                GlobalReferences.getInstance().toolbar.startAnimation(animationSet);
            }
           // mDrawerLayout.setStatusBarBackgroundColor(ThemeUtils.getThemeColor(MainActivity.this, R.attr.colorPrimaryDark));
        }
    }

    private boolean isRtl(Resources resources) {
        return resources.getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
    }

    private static int getThemeColor(Context context, int id) {
        Resources.Theme theme = context.getTheme();
        TypedArray a = theme.obtainStyledAttributes(new int[]{id});
        int result = a.getColor(0, 0);
        a.recycle();
        return result;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                   searchView.setQuery(searchWrd, false);
                    Log.e("searchWrd",searchWrd+"");
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
