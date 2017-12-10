package com.canvas;

import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.canvas.common.CommonFragment;
import com.canvas.common.GlobalReferences;
import com.canvas.fragment.AboutUsFragment;
import com.canvas.fragment.BookMarkFragment;
import com.canvas.fragment.CanvsMapFragment;
import com.canvas.fragment.FavoritesFragment;
import com.canvas.fragment.SeenFragment;
import com.canvas.utils.BottomNavigationViewHelper;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

public class MotherActivity extends BaseActivity {
    private MenuItem bookmarks, seen, fav, about;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mother);
        addFragmentWithBackStack(new CanvsMapFragment(), true);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setSelectedItemId(-1);
        Menu menu = bottomNavigationView.getMenu();
        GlobalReferences.getInstance().baseActivity = this;
        GlobalReferences.getInstance().toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        GlobalReferences.getInstance().progresBar = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
        bookmarks = menu.findItem(R.id.bookmarks);
        seen = menu.findItem(R.id.seen);
        fav = menu.findItem(R.id.fav);
        about = menu.findItem(R.id.about);
        bookmarks.setCheckable(true);
        seen.setCheckable(true);
        fav.setCheckable(true);
        about.setCheckable(true);
        String uid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.e("####uid",uid+"");

        setSupportActionBar(GlobalReferences.getInstance().toolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);

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
                        } else if (GlobalReferences.getInstance().mCommonFragment instanceof FavoritesFragment) {
                            updateTab(R.id.fav);
                        }
                        else if (GlobalReferences.getInstance().mCommonFragment instanceof FavoritesFragment) {
                            updateTab(R.id.fav);
                        }
                        else if (GlobalReferences.getInstance().mCommonFragment instanceof CanvsMapFragment) {
                            updateTab(-1);
                        }
                        else if (GlobalReferences.getInstance().mCommonFragment instanceof SeenFragment) {
                            updateTab(R.id.seen);
                        }
                        else if (GlobalReferences.getInstance().mCommonFragment instanceof AboutUsFragment) {
                            updateTab(R.id.about);
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
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
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
                ((BaseActivity) GlobalReferences.getInstance().baseActivity).addFragmentWithBackStack(new BookMarkFragment(), true);

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
                ((BaseActivity) GlobalReferences.getInstance().baseActivity).addFragmentWithBackStack(new SeenFragment(), true);

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
                ((BaseActivity) GlobalReferences.getInstance().baseActivity).addFragmentWithBackStack(new FavoritesFragment(), true);

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
                ((BaseActivity) GlobalReferences.getInstance().baseActivity).addFragmentWithBackStack(new AboutUsFragment(), true);

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
}
