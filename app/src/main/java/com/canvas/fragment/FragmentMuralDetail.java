package com.canvas.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.canvas.R;
import com.canvas.adpater.TagsAdapter;
import com.canvas.common.CommonFragment;
import com.canvas.common.Constants;
import com.canvas.common.GlobalReferences;
import com.canvas.controller.RealmController;
import com.canvas.io.http.BaseTask;
import com.canvas.io.http.BaseTaskJson;
import com.canvas.io.listener.AppRequest;
import com.canvas.model.BookmarkedMural;
import com.canvas.model.FavoriteMural;
import com.canvas.model.Murals;
import com.canvas.model.SeenMural;

import org.json.JSONObject;

import static android.content.ContentValues.TAG;

/**
 * Created by ashish on 08/12/17.
 */

public class FragmentMuralDetail extends CommonFragment implements AppRequest {


RelativeLayout relativeLayout_flag;
String selected_flag;
    TextView textView_location,textView_about_mural,textView_about_artist,
        textView_direction,textView_add_link_first,textView_add_link_second,textView_add_link_third;
    ImageView imageView;
    RecyclerView recyclerView_tags;
    TagsAdapter tagsAdapter;
    TextView tv_mural,tv_author;
    double lat,lon;
    private ImageView fav_img,book_img,seen_img;
    private CardView favoriteCard,bookmarks_btn,seen_btn,fresh_mural_tag;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View muralview = inflater.inflate(R.layout.fragment_mural_details,null);
        setHasOptionsMenu(true);
        Bundle bundle=getArguments();
        final Murals muralsObject = bundle.getParcelable("mural");
        final String img_id=bundle.getString("image_id");
        final String image_url="https://canvs.cruxcode.nyc/mural_large_"+img_id+".jpg?size=large&requestType=image";
        favoriteCard = muralview.findViewById(R.id.favorite_btn);
        bookmarks_btn  = muralview.findViewById(R.id.bookmarks_btn);
        seen_btn = muralview.findViewById(R.id.seen_btn);
        seen_img = muralview.findViewById(R.id.seen_img);
        fresh_mural_tag = muralview.findViewById(R.id.fresh_mural_tag);
        fav_img = muralview.findViewById(R.id.fav_img);
        book_img = muralview.findViewById(R.id.book_img);

        if (RealmController.getInstance().isFavoriteMuralExist(muralsObject.getId())) {
            fav_img.setColorFilter(Color.parseColor("#5ab3a4"), PorterDuff.Mode.SRC_IN);
        }else {
            fav_img.setColorFilter(Color.parseColor("#908B8A89"), PorterDuff.Mode.SRC_IN);
        }

        if (RealmController.getInstance().isBookMarhedMuralExist(muralsObject.getId())) {
            book_img.setColorFilter(Color.parseColor("#5ab3a4"), PorterDuff.Mode.SRC_IN);
        }else {
            book_img.setColorFilter(Color.parseColor("#908B8A89"), PorterDuff.Mode.SRC_IN);
        }

        if (RealmController.getInstance().isSeenMuralExist(muralsObject.getId())) {
            seen_img.setColorFilter(Color.parseColor("#5ab3a4"), PorterDuff.Mode.SRC_IN);
        }else {
            seen_img.setColorFilter(Color.parseColor("#908B8A89"), PorterDuff.Mode.SRC_IN);
        }

        String location=bundle.getString("location_text");
        lat=bundle.getDouble("lat");
        lon=bundle.getDouble("lon");
        Log.e(TAG, "onCreateView: "+img_id );
        Log.e(TAG, "onCreateView: "+location );
        imageView=muralview.findViewById(R.id.iv_image_detail);
        relativeLayout_flag=muralview.findViewById(R.id.flag);
        relativeLayout_flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity());
                View sheetView = getActivity().getLayoutInflater().inflate(R.layout.flag_part_first, null);

                TextView tv_cancel=sheetView.findViewById(R.id.tv_cancel);
                tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBottomSheetDialog.dismiss();
                    }
                });

                CardView cardView_wrong_info=sheetView.findViewById(R.id.flag_wrong_info);
                cardView_wrong_info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selected_flag="Wrong Information";
                      showFlagPartSecond();
                    }
                });
                CardView cardView_inaccurate=sheetView.findViewById(R.id.inaccurate_info);
                cardView_inaccurate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selected_flag="Inaccurate location";
                      showFlagPartSecond();
                    }
                });
                CardView cardView_damaged=sheetView.findViewById(R.id.damaged_removed);
                cardView_damaged.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     selected_flag=   "Damaged/removed mural";
                      showFlagPartSecond();
                    }
                });
                CardView cardView_other=sheetView.findViewById(R.id.other);
                cardView_other.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selected_flag="Other";
                      showFlagPartSecond();
                    }
                });


                mBottomSheetDialog.setContentView(sheetView);
                mBottomSheetDialog.show();
            }
        });

        textView_location=muralview.findViewById(R.id.tv_location);
        //seen_btn = muralview.findViewById(R.id.seen_btn);
        favoriteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavoriteMural favoriteMural = new FavoriteMural();
                favoriteMural.setId(muralsObject.getId());
                favoriteMural.setImageResourceID(img_id);
                favoriteMural.setArtistName(muralsObject.getArtist_text()+"");
                favoriteMural.setMuralTitle(muralsObject.getTitle()+"");
                favoriteMural.setActive(muralsObject.getActive()+"");
                favoriteMural.setLocationText(muralsObject.getLocation_text());
                favoriteMural.setAboutThisText(muralsObject.getAbout_text());
                favoriteMural.setTags(muralsObject.getTags());
                favoriteMural.setAdditionalLink1(muralsObject.getAdditional_link_first());
                favoriteMural.setAdditionalLink2(muralsObject.getAdditional_link_second());
                favoriteMural.setAdditionalLink3(muralsObject.getAdditional_limk_third());
                favoriteMural.setLatitude(muralsObject.getLatitude()+"");
                favoriteMural.setLongitude(muralsObject.getLongitude()+"");

                if(RealmController.getInstance().isFavoriteMuralExist(muralsObject.getId())){
                    Log.e("Record exist","Record Exist");
                    RealmController.getInstance().deleteFavoriteMural(favoriteMural.getId());
                    fav_img.setColorFilter(Color.parseColor("#908B8A89"), PorterDuff.Mode.SRC_IN);
                }else{
                    Log.e("Record exist not ","Record Exist not");
                    RealmController.getInstance().addFavoriteMural(favoriteMural);
                    fav_img.setColorFilter(Color.parseColor("#5ab3a4"), PorterDuff.Mode.SRC_IN);
                }
            }
        });

        seen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SeenMural favoriteMural = new SeenMural();
                favoriteMural.setId(muralsObject.getId());
                favoriteMural.setImageResourceID(img_id);
                favoriteMural.setArtistName(muralsObject.getArtist_text()+"");
                favoriteMural.setMuralTitle(muralsObject.getTitle()+"");
                favoriteMural.setActive(muralsObject.getActive()+"");
                favoriteMural.setLocationText(muralsObject.getLocation_text());
                favoriteMural.setAboutThisText(muralsObject.getAbout_text());
                favoriteMural.setTags(muralsObject.getTags());
                favoriteMural.setAdditionalLink1(muralsObject.getAdditional_link_first());
                favoriteMural.setAdditionalLink2(muralsObject.getAdditional_link_second());
                favoriteMural.setAdditionalLink3(muralsObject.getAdditional_limk_third());
                favoriteMural.setLatitude(muralsObject.getLatitude()+"");
                favoriteMural.setLongitude(muralsObject.getLongitude()+"");

                if(RealmController.getInstance().isSeenMuralExist(muralsObject.getId())){
                    Log.e("Record exist","Record Exist");
                    RealmController.getInstance().deleteSeenMarkedMural(favoriteMural.getId());
                    seen_img.setColorFilter(Color.parseColor("#908B8A89"), PorterDuff.Mode.SRC_IN);
                }else{
                    Log.e("Record exist not ","Record Exist not");
                    RealmController.getInstance().addSeenMural(favoriteMural);
                    seen_img.setColorFilter(Color.parseColor("#5ab3a4"), PorterDuff.Mode.SRC_IN);
                }
            }
        });
        bookmarks_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookmarkedMural favoriteMural = new BookmarkedMural();
                favoriteMural.setId(muralsObject.getId());
                favoriteMural.setImageResourceID(img_id);
                favoriteMural.setArtistName(muralsObject.getArtist_text()+"");
                favoriteMural.setMuralTitle(muralsObject.getTitle()+"");
                favoriteMural.setActive(muralsObject.getActive()+"");
                favoriteMural.setLocationText(muralsObject.getLocation_text());
                favoriteMural.setAboutThisText(muralsObject.getAbout_text());
                favoriteMural.setTags(muralsObject.getTags());
                favoriteMural.setAdditionalLink1(muralsObject.getAdditional_link_first());
                favoriteMural.setAdditionalLink2(muralsObject.getAdditional_link_second());
                favoriteMural.setAdditionalLink3(muralsObject.getAdditional_limk_third());
                favoriteMural.setLatitude(muralsObject.getLatitude()+"");
                favoriteMural.setLongitude(muralsObject.getLongitude()+"");

                if(RealmController.getInstance().isBookMarhedMuralExist(muralsObject.getId())){
                    Log.e("Record exist","Record Exist");
                    RealmController.getInstance().deleteBookMarkedMural(favoriteMural.getId());
                    book_img.setColorFilter(Color.parseColor("#908B8A89"), PorterDuff.Mode.SRC_IN);
                }else{
                    Log.e("Record exist not ","Record Exist not");
                    RealmController.getInstance().addBookMarkedMural(favoriteMural);
                    book_img.setColorFilter(Color.parseColor("#5ab3a4"), PorterDuff.Mode.SRC_IN);
                }
            }
        });


        tv_author=muralview.findViewById(R.id.tv_author);
        textView_direction=muralview.findViewById(R.id.tv_get_direction);
        textView_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://maps.google.com/maps?f=d&daddr=" + lat + "," + lon + "&dirflg=d";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });
        tv_author.setText(bundle.getString("artist"));
        tv_mural=muralview.findViewById(R.id.tv_mural);
        tv_mural.setText(bundle.getString("name"));
        textView_about_artist=muralview.findViewById(R.id.tv_about_artist);
        textView_about_artist.setText(bundle.getString("artist_text"));
        textView_about_mural=muralview.findViewById(R.id.tv_about_mural);
        textView_about_mural.setText(bundle.getString("about_text"));
        textView_add_link_first=muralview.findViewById(R.id.tv_add_link_1);
        textView_add_link_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                            openLink(textView_add_link_first.getText().toString());
            }
        });
        textView_add_link_second=muralview.findViewById(R.id.tv_add_link_2);
        textView_add_link_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(textView_add_link_second.getText().toString());
            }
        });
        textView_add_link_third=muralview.findViewById(R.id.tv_add_link_3);
        textView_add_link_third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(textView_add_link_third.getText().toString());
            }
        });
        String add_link_1=bundle.getString("addlink1");
        if(add_link_1!=null&&!add_link_1.equals("null")){
            textView_add_link_first.setText(add_link_1);
        }else{
            textView_add_link_first.setVisibility(View.GONE);
        }
        String add_link_2=bundle.getString("addlink2");
        if(add_link_2!=null&&!add_link_2.equals("null")){
            textView_add_link_second.setText(add_link_2);
        }else{
            textView_add_link_second.setVisibility(View.GONE);
        }
        String add_link_3=bundle.getString("addlink3");
        if(add_link_3!=null&&!add_link_3.equals("null")){
            textView_add_link_second.setText(add_link_3);
        }else{
            textView_add_link_second.setVisibility(View.GONE);
        }


        Log.e(TAG, "onCreateView: "+image_url );

        Glide.with(GlobalReferences.getInstance().baseActivity).load(image_url)
                .thumbnail(0.5f).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                if(muralsObject!=null){
                    if(muralsObject.getFreshWhenAdded()!=null&&muralsObject.getFreshWhenAdded().equalsIgnoreCase("1")){
                        fresh_mural_tag.setVisibility(View.VISIBLE);
                    }else{
                        fresh_mural_tag.setVisibility(View.GONE);
                    }
                }
                return false;
            }
        })
//                .placeholder(R.color.grey_)
//                .error(R.color.grey_)
                .into(imageView);
        textView_location.setText(location);
        String tags=bundle.getString("tags");
        Log.e(TAG, "onCreateView: "+tags );

        String[] tags_list = tags.split(",");
        Log.e(TAG, "onCreateView: "+tags_list.length );

        tagsAdapter=new TagsAdapter(tags_list,GlobalReferences.getInstance().baseActivity);

        recyclerView_tags = muralview.findViewById(R.id.recycler_tags);


        recyclerView_tags.setItemAnimator(new DefaultItemAnimator());
        recyclerView_tags.setLayoutManager(new LinearLayoutManager(GlobalReferences.getInstance().baseActivity, LinearLayoutManager.HORIZONTAL, false));
        recyclerView_tags.setAdapter(tagsAdapter);
        return muralview;
    }

    private void showFlagPartSecond(){
      final  BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity());
        View sheetView = getActivity().getLayoutInflater().inflate(R.layout.flag_part_second, null);
        TextView tv_cancel=sheetView.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
        TextView textView=sheetView.findViewById(R.id.selected_flag);
        textView.setText(selected_flag);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
    }

    private void openLink(String link){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
    }
    @Override
    public <T> void onRequestStarted(BaseTask<T> listener, Constants.RequestParam requestParam) {

    }

    @Override
    public <T> void onRequestCompleted(BaseTask<T> listener, Constants.RequestParam requestParam) {

    }

    @Override
    public <T> void onRequestFailed(BaseTask<T> listener, Constants.RequestParam requestParam) {

    }

    @Override
    public <T> void onRequestStarted(BaseTaskJson<JSONObject> listener, Constants.RequestParam requestParam) {

    }

    @Override
    public <T> void onRequestCompleted(BaseTaskJson<JSONObject> listener, Constants.RequestParam requestParam) {

    }

    @Override
    public <T> void onRequestFailed(BaseTaskJson<JSONObject> listener, Constants.RequestParam requestParam) {

    }

    @Override
    public void onResponse(JSONObject jsonObject) {

    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
    }
}
