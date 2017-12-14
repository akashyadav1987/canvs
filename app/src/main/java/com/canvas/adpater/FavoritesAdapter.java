package com.canvas.adpater;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.canvas.BaseActivity;
import com.canvas.R;
import com.canvas.common.GlobalReferences;
import com.canvas.fragment.FragmentMuralDetail;
import com.canvas.model.FavoriteMural;
import com.canvas.model.Murals;
import com.squareup.picasso.Picasso;

import io.realm.RealmResults;

/**
 * Created by akashyadav on 12/2/17.
 */

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.MyViewHolder> {
    private RealmResults<FavoriteMural> favoriteArrayList;

    public FavoritesAdapter(RealmResults<FavoriteMural> list) {
        this.favoriteArrayList = list;
    }

    @Override
    public FavoritesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View adapterItem = GlobalReferences.getInstance().baseActivity.getLayoutInflater().inflate(R.layout.item_fav_and_bookmark, null);
        return new MyViewHolder(adapterItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            final FavoriteMural favorite = favoriteArrayList.get(position);
            try {
                final String image_url = "https://canvs.cruxcode.nyc/mural_large_" + favorite.getImageResourceID() + ".jpg?size=large&requestType=image";
                Picasso.with(GlobalReferences.getInstance().baseActivity).load(image_url).placeholder(R.color.grey_).error(R.color.grey_);
            } catch (Exception e) {
                e.printStackTrace();
            }
            holder.title.setText(favorite.getMuralTitle() + "");
            holder.artistName.setText(favorite.getArtistName() + "");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    FragmentMuralDetail fragmentMuralDetail=new FragmentMuralDetail();
                    Bundle bundle=new Bundle();
                    Murals murals = new Murals();

                    try {
                        try {
                            murals.setId(favorite.getId());

                        } catch (Exception e) {

                        }
                        murals.setTitle(favorite.getMuralTitle());
                        murals.setTags(favorite.getTags());
                        murals.setAbout_text(favorite.getAboutThisText());
                        murals.setActive(Integer.parseInt(favorite.getActive()));
                        try {
                            murals.setAdditional_link_first(favorite.getAdditionalLink1());

                        } catch (Exception e) {

                        }
                        try {
                            murals.setAdditional_link_second(favorite.getAdditionalLink2());
                        } catch (Exception e) {

                        }
                        try {
                            murals.setAdditional_link_third(favorite.getAdditionalLink3());
                        } catch (Exception e) {

                        }
                        try {
                            murals.setTitle(favorite.getMuralTitle());
                        } catch (Exception e) {

                        }
                        try {
                            murals.setArtist_text(favorite.getAboutArtistText());
                        } catch (Exception e) {

                        }
                        try {
                            murals.setAuthor(favorite.getArtistName());
                        } catch (Exception e) {

                        }
                        try {
                            murals.setImage_path(favorite.getImageResourceID());
                        } catch (Exception e) {

                        }
                        try {
                            murals.setDerelict(Integer.parseInt(favorite.getDerelict()));
                        } catch (Exception e) {

                        }
                        try {
                            murals.setLocation_text(favorite.getLocationText());

                        } catch (Exception e) {

                        }
                        try {
                            murals.setLatitude(Double.parseDouble(favorite.getLatitude()));
                            murals.setLongitude(Double.parseDouble(favorite.getLongitude()));
                            murals.setPopularity(Integer.parseInt(favorite.getPopularity()));
                        } catch (Exception e) {

                        }

                        bundle.putParcelable("mural", murals);
                        bundle.putString("image_id", favorite.getImageResourceID());
                        bundle.putString("location_text", favorite.getLocationText());
                        bundle.putString("artist_text", favorite.getAboutArtistText());
                        bundle.putString("about_text", favorite.getAboutThisText());
                        bundle.putString("tags", favorite.getTags());
                        bundle.putString("addlink1", favorite.getAdditionalLink1());
                        bundle.putString("addlink2", favorite.getAdditionalLink2());
                        bundle.putString("addlink3", favorite.getAdditionalLink3());
                        bundle.putString("artist", favorite.getArtistName());
                        bundle.putString("name", favorite.getMuralTitle());
                        bundle.putDouble("lat", Double.parseDouble(favorite.getLatitude()));
                        bundle.putDouble("lon", Double.parseDouble(favorite.getLongitude()));
                        fragmentMuralDetail.setArguments(bundle);
                        ((BaseActivity) GlobalReferences.getInstance().baseActivity).addFragmentWithBackStack(fragmentMuralDetail, true);
                    }catch (Exception e){

                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return favoriteArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mural_image, right_ico;
        public TextView title, artistName;

        public MyViewHolder(View itemView) {
            super(itemView);
            mural_image = itemView.findViewById(R.id.mural_image);
            title = itemView.findViewById(R.id.title);
            artistName = itemView.findViewById(R.id.artistname);
            right_ico = itemView.findViewById(R.id.right_ico);
        }
    }
}
