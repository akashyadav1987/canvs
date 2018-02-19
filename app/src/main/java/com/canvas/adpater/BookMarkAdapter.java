package com.canvas.adpater;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.canvas.BaseActivity;
import com.canvas.R;
import com.canvas.common.GlobalReferences;
import com.canvas.controller.RealmController;
import com.canvas.fragment.FragmentMuralDetailsParent;
import com.canvas.model.BookmarkedMural;
import com.canvas.model.Murals;
import com.squareup.picasso.Picasso;

import io.realm.RealmResults;

/**
 * Created by akashyadav on 12/2/17.
 */

public class BookMarkAdapter extends RecyclerView.Adapter<BookMarkAdapter.MyViewHolder> {
    private RealmResults<BookmarkedMural> favoriteArrayList;

    public BookMarkAdapter(RealmResults<BookmarkedMural> list) {
        this.favoriteArrayList = list;
    }

    @Override
    public BookMarkAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View adapterItem = GlobalReferences.getInstance().baseActivity.getLayoutInflater().inflate(R.layout.item_fav_and_bookmark, null);
        return new MyViewHolder(adapterItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            final BookmarkedMural bookmarkedMural = favoriteArrayList.get(position);
            try {
                final String image_url = "https://canvs.cruxcode.nyc/mural_large_" + bookmarkedMural.getImageResourceID() + ".jpg?size=large&requestType=image";
                Picasso.with(GlobalReferences.getInstance().baseActivity).load(image_url).placeholder(R.color.grey_).error(R.color.grey_);
            } catch (Exception e) {
                e.printStackTrace();
            }
            holder.title.setText(bookmarkedMural.getMuralTitle() + "");
            holder.artistName.setText(bookmarkedMural.getArtistName() + "");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Murals murals = null;
                    Bundle bundle=null;
                    FragmentMuralDetailsParent fragmentMuralDetail = null;
                   try {
                        fragmentMuralDetail = new FragmentMuralDetailsParent();
                        bundle = new Bundle();
                       bundle.putInt("type", 1);
                        murals = new Murals();
                       murals.setId(bookmarkedMural.getId());
                       murals.setTitle(bookmarkedMural.getMuralTitle());
                       murals.setTags(bookmarkedMural.getTags());
                       murals.setAbout_text(bookmarkedMural.getAboutThisText());
                       murals.setActive(Integer.parseInt(bookmarkedMural.getActive()));

                       murals.setTitle(bookmarkedMural.getMuralTitle());
                       murals.setArtist_text(bookmarkedMural.getAboutArtistText());
                       murals.setAuthor(bookmarkedMural.getArtistName());
                       murals.setImage_path(bookmarkedMural.getImageResourceID());
                       murals.setDerelict(Integer.parseInt(bookmarkedMural.getDerelict()));
                       murals.setLocation_text(bookmarkedMural.getLocationText());
                       murals.setLatitude(Double.parseDouble(bookmarkedMural.getLatitude()));
                       murals.setLongitude(Double.parseDouble(bookmarkedMural.getLongitude()));
                       murals.setPopularity(Integer.parseInt(bookmarkedMural.getPopularity()));
                   }catch (Exception e){
                       
                   }
                    try{
                        murals.setAdditional_link_first(bookmarkedMural.getAdditionalLink1());

                    }catch (Exception e){

                    }
                    try{
                        murals.setAdditional_link_second(bookmarkedMural.getAdditionalLink2());

                    }catch (Exception e){

                    }
                    try{
                        murals.setAdditional_link_third(bookmarkedMural.getAdditionalLink3());

                    }catch (Exception e){

                    }
                    bundle.putParcelable("mural",murals);

                    bundle.putString("image_id",bookmarkedMural.getImageResourceID());
                    bundle.putString("location_text",bookmarkedMural.getLocationText());

                    bundle.putString("artist_text",bookmarkedMural.getAboutArtistText());
                    bundle.putString("about_text",bookmarkedMural.getAboutThisText());
                    bundle.putString("tags",bookmarkedMural.getTags());
                    bundle.putString("addlink1",bookmarkedMural.getAdditionalLink1());
                    bundle.putString("addlink2",bookmarkedMural.getAdditionalLink2());
                    bundle.putString("addlink3",bookmarkedMural.getAdditionalLink3());
                    bundle.putString("artist",bookmarkedMural.getArtistName());
                    bundle.putString("name",bookmarkedMural.getMuralTitle());
                    bundle.putDouble("lat",Double.parseDouble(bookmarkedMural.getLatitude()));
                    bundle.putDouble("lon",Double.parseDouble(bookmarkedMural.getLongitude()));
                    RealmResults<Murals> list_mural= RealmController.getInstance().getAllMurals();
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
                    //fragmentMuralDetail.setArguments(bundle);
                    ((BaseActivity) GlobalReferences.getInstance().baseActivity).addFragmentWithBackStack(fragmentMuralDetail, true);
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
            mural_image = (ImageView) itemView.findViewById(R.id.mural_image);
            title = (TextView) itemView.findViewById(R.id.title);
            artistName = (TextView) itemView.findViewById(R.id.artistname);
            right_ico = (ImageView) itemView.findViewById(R.id.right_ico);
        }
    }
}
