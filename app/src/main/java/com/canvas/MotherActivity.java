package com.canvas;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.canvas.common.GlobalReferences;
import com.canvas.fragment.CanvsMapFragment;
import com.canvas.fragment.FavoritesFragment;
import com.canvas.utils.BottomNavigationViewHelper;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

public class MotherActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mother);
        addFragmentWithBackStack(new CanvsMapFragment(),true);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setSelectedItemId(-1);
        Menu menu = bottomNavigationView.getMenu();
        GlobalReferences.getInstance().baseActivity =this;
        GlobalReferences.getInstance().progresBar = (LinearLayout) findViewById(R.id.linlaHeaderProgress);
        final MenuItem bookmarks =  menu.findItem(R.id.bookmarks);
        final MenuItem seen =  menu.findItem(R.id.seen);
        final MenuItem fav =  menu.findItem(R.id.fav);
        final MenuItem about =  menu.findItem(R.id.about);
        bookmarks.setCheckable(true);
        seen.setCheckable(true);
        fav.setCheckable(true);
        about.setCheckable(true);

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
                switch (item.getItemId()){
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
                        ((BaseActivity)GlobalReferences.getInstance().baseActivity).addFragmentWithBackStack(new FavoritesFragment(),true);

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
                        break;
                    default:
                        break;
                }
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

            }catch (Exception e){
                e.printStackTrace();
            }
            super.onBackPressed();
        }
    }
}
