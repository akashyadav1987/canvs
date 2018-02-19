package com.canvas.adpater;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.canvas.fragment.FragmentMuralDetail;
import com.canvas.model.Murals;

import io.realm.RealmResults;

/**
 * Created by akashyadav on 2/18/18.
 */

public class ViewPagerAdapterMuralDetails extends FragmentStatePagerAdapter
{
    private RealmResults<Murals> list_mural;
    public ViewPagerAdapterMuralDetails(RealmResults<Murals> list_murals, FragmentManager fm) {
        super(fm);
        this.list_mural =list_murals;
    }

    @Override
    public Fragment getItem(int position) {
        FragmentMuralDetail fragmentMuralDetail =null;
        try {
            if (list_mural != null) {
                Murals murals = list_mural.get(position);
                fragmentMuralDetail = new FragmentMuralDetail();
                fragmentMuralDetail.setMural(murals);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return fragmentMuralDetail;
    }

    @Override
    public int getCount() {
        return list_mural.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
