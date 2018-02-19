package com.canvas.adpater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.canvas.fragment.CardDIalougeFragment;
import com.canvas.listener.SetViewPagerVisibilityGone;
import com.canvas.model.Murals;

import java.util.ArrayList;

/**
 * Created by akashyadav on 2/18/18.
 */

public class ViewPagerLayoutOnMap extends FragmentStatePagerAdapter
{
    private ArrayList<Murals> list_murals;
    private SetViewPagerVisibilityGone setViewPagerVisibilityGone;
    public ViewPagerLayoutOnMap(SetViewPagerVisibilityGone setViewPagerVisibilityGone,ArrayList<Murals> list_murals, FragmentManager fm) {
        super(fm);
        this.list_murals =list_murals;
        this.setViewPagerVisibilityGone =setViewPagerVisibilityGone;
    }

    @Override
    public Fragment getItem(int position) {
        CardDIalougeFragment cardDIalougeFragment =null;
        try {
            if (list_murals != null) {
                Murals murals = list_murals.get(position);
                cardDIalougeFragment = new CardDIalougeFragment();
                cardDIalougeFragment.setMural(setViewPagerVisibilityGone, murals);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return cardDIalougeFragment;
    }

    @Override
    public int getCount() {
        return list_murals.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
