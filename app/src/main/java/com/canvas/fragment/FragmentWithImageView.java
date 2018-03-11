package com.canvas.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.canvas.R;
import com.canvas.common.CommonFragment;
import com.canvas.common.GlobalReferences;
import com.canvas.model.Murals;
import com.squareup.picasso.Picasso;

/**
 * Created by akashyadav on 3/8/18.
 */

public class FragmentWithImageView extends CommonFragment {
    private Murals murals =null;
    private ImageView mural_image_in_view =null;
    private CardView fresh_mural_tag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_with_image_view,null);
        mural_image_in_view = (ImageView) view.findViewById(R.id.mural_image_in_view);
        fresh_mural_tag = (CardView) view.findViewById(R.id.fresh_mural_tag);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(mural_image_in_view!=null) {
            if (murals != null) {
                final String image_id = murals.getImage_resource_id().toLowerCase();
                String image_url = "https://canvs.cruxcode.nyc/mural_large_" + image_id + ".jpg?size=large&requestType=image";

                Picasso.with(GlobalReferences.getInstance().baseActivity).load(image_url).resize(view.getWidth(),250).placeholder(R.color.grey_).error(R.color.grey_).into(mural_image_in_view);


//                Glide.with(GlobalReferences.getInstance().baseActivity).load(image_url).centerCrop()
//                        .thumbnail(0.5f).listener(new RequestListener<String, GlideDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        if(murals!=null){
//                            if(murals.getFreshWhenAdded()!=null&&murals.getFreshWhenAdded().equalsIgnoreCase("1")){
//                                fresh_mural_tag.setVisibility(View.VISIBLE);
//                            }else{
//                                fresh_mural_tag.setVisibility(View.GONE);
//                            }
//                        }
//                        return false;
//                    }
//                }).placeholder(R.drawable.title_app)
//               .error(R.color.grey_)
//                .into(mural_image_in_view);
//            }}
            }
        }
    }

    public void updateView(final Murals murals){
        if(murals!=null){
            this.murals =murals;
        }
    }
}
