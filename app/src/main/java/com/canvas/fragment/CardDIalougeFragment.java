package com.canvas.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.canvas.BaseActivity;
import com.canvas.R;
import com.canvas.common.CommonFragment;
import com.canvas.common.GlobalReferences;
import com.canvas.controller.RealmController;
import com.canvas.listener.SetViewPagerVisibilityGone;
import com.canvas.model.Murals;
import com.canvas.widget.RobotoBoldTextView;
import com.canvas.widget.RobotoMediumTextView;
import com.canvas.widget.RobotoRegularTextView;

import io.realm.RealmResults;

import static android.content.ContentValues.TAG;

/**
 * Created by akashyadav on 2/18/18.
 */

public class CardDIalougeFragment extends CommonFragment {
    private Murals murals;
    RobotoBoldTextView textView_title;
    RobotoMediumTextView textView_author;
    RobotoRegularTextView textView_more;
    ImageView imageView;
    CardView fresh_mural_tag;
    private SetViewPagerVisibilityGone setViewPagerVisibilityGone;
    public void setMural(SetViewPagerVisibilityGone setViewPagerVisibilityGone,Murals mural){
        this.murals =mural;
        this.setViewPagerVisibilityGone =setViewPagerVisibilityGone;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.card_dialogue_layout,null);
        textView_title = (RobotoBoldTextView) view.findViewById(R.id.title);
        imageView = (ImageView) view.findViewById(R.id.iv_map);
        textView_author = (RobotoMediumTextView) view.findViewById(R.id.author);
        textView_more = (RobotoRegularTextView) view.findViewById(R.id.tv_more);
        fresh_mural_tag = (CardView) view.findViewById(R.id.fresh_mural_tag);
        try {
            if(murals!=null) {
                buildCardDialogue(murals);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
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

            final String image_id = murals.getImage_resource_id().toLowerCase();
            String image_url = "https://canvs.cruxcode.nyc/mural_thumb_" + image_id.toLowerCase() + ".jpg?size=thumb&requestType=image";
            textView_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                      FragmentMuralDetail fragmentMuralDetail = new FragmentMuralDetail();
                      Bundle bundle = new Bundle();
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

//                    fragmentMuralDetail.setArguments(bundle);
                      RealmResults<Murals> list_mural=RealmController.getInstance().getAllMurals();
                      int pos = 0;
                      for (int i=0;i<list_mural.size();i++){
                          Murals murals1 = list_mural.get(i);
                          if(murals.getId()==murals1.getId()){
                              pos = i;
                              break;
                          }
                      }
                      bundle.putInt("position", pos);
                      //int pos = RealmController.getInstance().findIndexOf(murals);
                      Log.e("####pos",pos+"");
                      fragmentMuralDetail.setArguments(bundle);
                    ((BaseActivity) GlobalReferences.getInstance().baseActivity).addFragmentWithBackStack(fragmentMuralDetail, true);
                    if(setViewPagerVisibilityGone!=null){
                        setViewPagerVisibilityGone.onSeeMoreButtonClicked();
                    }
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
