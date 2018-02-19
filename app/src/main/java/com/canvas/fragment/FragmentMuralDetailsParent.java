package com.canvas.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.canvas.R;
import com.canvas.adpater.ViewPagerAdapterMuralDetails;
import com.canvas.common.CommonFragment;
import com.canvas.common.GlobalReferences;
import com.canvas.controller.RealmController;
import com.canvas.model.Murals;

import io.realm.RealmResults;

/**
 * Created by akashyadav on 2/18/18.
 */

public class FragmentMuralDetailsParent extends CommonFragment {
    private int pos=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.view_pager_layout,null);
        if (getArguments()!=null){
            pos=getArguments().getInt("position");
        }
        Log.e("FragmentMuralDsParent","pos = "+pos);
        ViewPager mural_details_view_pager = (ViewPager) view.findViewById(R.id.mural_details_view_pager);
        RealmResults<Murals> list_mural= RealmController.getInstance().getAllMurals();
        ViewPagerAdapterMuralDetails viewPagerAdapterMuralDetails = new ViewPagerAdapterMuralDetails(list_mural,getActivity().getSupportFragmentManager());
        mural_details_view_pager.setAdapter(viewPagerAdapterMuralDetails);
        mural_details_view_pager.setCurrentItem(pos,true);
        return view;

    }
    public void shareIntent(){
        try{
            StringBuilder share_text = new StringBuilder();
            RealmResults<Murals> list_mural= RealmController.getInstance().getAllMurals();
            Murals muralsObject =  list_mural.get(pos);
            if(muralsObject!=null){
                share_text.append(muralsObject.getTitle());
                share_text.append(" By");
                share_text.append(" ");
                share_text.append("Check out this mural ");
                share_text.append(" ");
                Double latitude = muralsObject.getLatitude();
                Double longitude = muralsObject.getLongitude();

                String uri = "http://maps.google.com/maps?saddr=" +latitude+","+longitude;

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String ShareSub = share_text.toString()
                        +" Here is the location";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, ShareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, uri);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));

            }else{
                Toast.makeText(GlobalReferences.getInstance().baseActivity,"Mural Can't be shared",Toast.LENGTH_SHORT).show();
                //share_text.append("I am sharing this mural details");
            }
//            Intent sendIntent = new Intent();
//            sendIntent.setAction(Intent.ACTION_SEND);
//            sendIntent.putExtra(Intent.EXTRA_TEXT,
//                    share_text.toString());
//            sendIntent.setType("text/plain");
//            startActivity(sendIntent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
