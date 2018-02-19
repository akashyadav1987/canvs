package com.canvas.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import com.canvas.utils.Utility;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import io.realm.RealmResults;

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
     Murals muralsObject =null;
    GestureDetector gestureDetector;
    //int position;
    RealmResults<Murals> list_mural;
    ArrayList<String> tags_list;
    //ArrayList<Murals> list;
    private  BottomSheetDialog mBottomSheetDialog;
    public void setMural(Murals mural){
        this.muralsObject =mural;
    }
    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View muralview = inflater.inflate(R.layout.fragment_mural_details,null);
        setHasOptionsMenu(true);
        Bundle bundle=getArguments();
        //position=bundle.getInt("position");
        //muralsObject = bundle.getParcelable("mural");

        final String img_id=muralsObject.getImage_resource_id().toLowerCase();
        final String image_url="https://canvs.cruxcode.nyc/mural_large_"+img_id+".jpg?size=large&requestType=image";
        favoriteCard = (CardView) muralview.findViewById(R.id.favorite_btn);
        bookmarks_btn  = (CardView) muralview.findViewById(R.id.bookmarks_btn);

        seen_btn = (CardView) muralview.findViewById(R.id.seen_btn);
        seen_img = (ImageView) muralview.findViewById(R.id.seen_img);
        fresh_mural_tag = (CardView) muralview.findViewById(R.id.fresh_mural_tag);
        fav_img = (ImageView) muralview.findViewById(R.id.fav_img);
        book_img = (ImageView) muralview.findViewById(R.id.book_img);

        //list_mural=RealmController.getInstance().getAllMurals();
        //list=bundle.getParcelableArrayList("list");

        if (RealmController.getInstance().isFavoriteMuralExist(muralsObject.getId())) {
            fav_img.setColorFilter(Color.parseColor("#5ab3a4"), PorterDuff.Mode.SRC_IN);
            favoriteCard.setCardBackgroundColor(Color.parseColor("#305ab3a4"));

        }else {
            fav_img.setColorFilter(Color.parseColor("#908B8A89"), PorterDuff.Mode.SRC_IN);
            favoriteCard.setCardBackgroundColor(Color.parseColor("#ffffff"));

        }

        if (RealmController.getInstance().isBookMarhedMuralExist(muralsObject.getId())) {
            book_img.setColorFilter(Color.parseColor("#5ab3a4"), PorterDuff.Mode.SRC_IN);
            bookmarks_btn.setCardBackgroundColor(Color.parseColor("#305ab3a4"));

        }else {
            book_img.setColorFilter(Color.parseColor("#908B8A89"), PorterDuff.Mode.SRC_IN);
            bookmarks_btn.setCardBackgroundColor(Color.parseColor("#ffffff"));

        }

        if (RealmController.getInstance().isSeenMuralExist(muralsObject.getId())) {
            seen_img.setColorFilter(Color.parseColor("#5ab3a4"), PorterDuff.Mode.SRC_IN);
            seen_btn.setCardBackgroundColor(Color.parseColor("#305ab3a4"));

        }else {
            seen_img.setColorFilter(Color.parseColor("#908B8A89"), PorterDuff.Mode.SRC_IN);
            seen_btn.setCardBackgroundColor(Color.parseColor("#ffffff"));

        }

        String location=muralsObject.getLocation_text()==null?"":muralsObject.getLocation_text();
        lat=muralsObject.getLatitude();
        lon=muralsObject.getLongitude();
        Log.e(TAG, "onCreateView: "+img_id );
        Log.e(TAG, "onCreateView: "+location );
        imageView= (ImageView) muralview.findViewById(R.id.iv_image_detail);
        relativeLayout_flag= (RelativeLayout) muralview.findViewById(R.id.flag);
        relativeLayout_flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 mBottomSheetDialog = new BottomSheetDialog(getActivity());
                View sheetView = getActivity().getLayoutInflater().inflate(R.layout.flag_part_first, null);

                TextView tv_cancel= (TextView) sheetView.findViewById(R.id.tv_cancel);
                tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBottomSheetDialog.dismiss();
                    }
                });

                CardView cardView_wrong_info= (CardView) sheetView.findViewById(R.id.flag_wrong_info);
                cardView_wrong_info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selected_flag="Wrong Information";
                      showFlagPartSecond();
                    }
                });
                CardView cardView_inaccurate= (CardView) sheetView.findViewById(R.id.inaccurate_info);
                cardView_inaccurate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selected_flag="Inaccurate location";
                      showFlagPartSecond();
                    }
                });
                CardView cardView_damaged= (CardView) sheetView.findViewById(R.id.damaged_removed);
                cardView_damaged.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     selected_flag=   "Damaged/removed mural";
                      showFlagPartSecond();
                    }
                });
                CardView cardView_other= (CardView) sheetView.findViewById(R.id.other);
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

        textView_location= (TextView) muralview.findViewById(R.id.tv_location);
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
                    favoriteCard.setCardBackgroundColor(Color.parseColor("#ffffff"));
                }else{
                    Log.e("Record exist not ","Record Exist not");
                    RealmController.getInstance().addFavoriteMural(favoriteMural);
                    fav_img.setColorFilter(Color.parseColor("#5ab3a4"), PorterDuff.Mode.SRC_IN);
                    favoriteCard.setCardBackgroundColor(Color.parseColor("#305ab3a4"));

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
                    seen_btn.setCardBackgroundColor(Color.parseColor("#ffffff"));

                }else{
                    Log.e("Record exist not ","Record Exist not");
                    RealmController.getInstance().addSeenMural(favoriteMural);
                    seen_img.setColorFilter(Color.parseColor("#5ab3a4"), PorterDuff.Mode.SRC_IN);
                    seen_btn.setCardBackgroundColor(Color.parseColor("#305ab3a4"));

                }
            }
        });


        bookmarks_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BookmarkedMural favoriteMural = new BookmarkedMural();
                Log.e(TAG, "onClick: "+muralsObject.getId() );
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
                    bookmarks_btn.setCardBackgroundColor(Color.parseColor("#ffffff"));

                }else{


                    Log.e("Record exist not   ","Record Exist not");
                    RealmController.getInstance().addBookMarkedMural(favoriteMural);
                    book_img.setColorFilter(Color.parseColor("#5ab3a4"), PorterDuff.Mode.SRC_IN);
                    bookmarks_btn.setCardBackgroundColor(Color.parseColor("#305ab3a4"));

                }
            }
        });


        tv_author= (TextView) muralview.findViewById(R.id.tv_author);
        textView_direction= (TextView) muralview.findViewById(R.id.tv_get_direction);
        textView_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://maps.google.com/maps?f=d&daddr=" + lat + "," + lon + "&dirflg=d";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                startActivity(intent);
            }
        });

        tv_author.setText(muralsObject.getArtist_text()+"");
        tv_mural= (TextView) muralview.findViewById(R.id.tv_mural);
        tv_mural.setText(muralsObject.getTitle()+"");
        textView_about_artist= (TextView) muralview.findViewById(R.id.tv_about_artist);
        textView_about_artist.setText(muralsObject.getArtist_text()+"");
        textView_about_mural= (TextView) muralview.findViewById(R.id.tv_about_mural);
        textView_about_mural.setText(muralsObject.getAbout_text()+"");
        textView_add_link_first= (TextView) muralview.findViewById(R.id.tv_add_link_1);
        textView_add_link_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                            openLink(textView_add_link_first.getText().toString());
            }
        });
        textView_add_link_second= (TextView) muralview.findViewById(R.id.tv_add_link_2);
        textView_add_link_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(textView_add_link_second.getText().toString());
            }
        });
        textView_add_link_third= (TextView) muralview.findViewById(R.id.tv_add_link_3);
        textView_add_link_third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLink(textView_add_link_third.getText().toString());
            }
        });
        String add_link_1=muralsObject.getAdditional_link_first()+"";
        if(add_link_1!=null&&!add_link_1.equals("null")){
            textView_add_link_first.setText(add_link_1);
        }else{
            textView_add_link_first.setVisibility(View.GONE);
        }
        String add_link_2=muralsObject.getAdditional_link_second()+"";
        if(add_link_2!=null&&!add_link_2.equals("null")){
            textView_add_link_second.setText(add_link_2);
        }else{
            textView_add_link_second.setVisibility(View.GONE);
        }
        String add_link_3=muralsObject.getAdditional_limk_third()+"";
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
        String tags=muralsObject.getTags();
        Log.e(TAG, "onCreateView: "+tags );
        tags_list=new ArrayList<>(new ArrayList<>(Arrays.asList(tags.split(","))));

        Log.e(TAG, "onCreateView: "+tags_list.size() );

        tagsAdapter=new TagsAdapter(tags_list,GlobalReferences.getInstance().baseActivity);

        recyclerView_tags = (RecyclerView) muralview.findViewById(R.id.recycler_tags);


        recyclerView_tags.setItemAnimator(new DefaultItemAnimator());
        recyclerView_tags.setLayoutManager(new LinearLayoutManager(GlobalReferences.getInstance().baseActivity, LinearLayoutManager.HORIZONTAL, false));
        recyclerView_tags.setAdapter(tagsAdapter);

        NestedScrollView scrollView= (NestedScrollView) muralview.findViewById(R.id.scroll);
//        scrollView.setOnTouchListener(new OnSwipeTouchListener(GlobalReferences.getInstance().baseActivity) {
//            public void onSwipeTop() {
//                //Toast.makeText(GlobalReferences.getInstance().baseActivity, "top", Toast.LENGTH_SHORT).show();
//            }
//
//            public void onSwipeRight() {
//                position++;
//                if(position==list_mural.size()-1){
//                    position=0;
//                }
//                Log.e(TAG, "onSwipeRight: "+position+"list_position"+list_mural.size() );
//                try {
//                    Murals murals = list_mural.get(position);
//                    if(murals!=null){
//                       updateView(murals);
//
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//
//
//                //Toast.makeText(GlobalReferences.getInstance().baseActivity, "right", Toast.LENGTH_SHORT).show();
//            }
//
//            public void onSwipeLeft() {
//                position--;
//                if(position==0){
//                    position=list_mural.size()-1;
//                }
//                Log.e(TAG, "onSwipeRight: "+position+"list_position"+list_mural.size() );
//                try {
//                    Murals murals = list_mural.get(position);
//                    if(murals!=null){
//                        updateView(murals);
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                //Toast.makeText(GlobalReferences.getInstance().baseActivity, "left", Toast.LENGTH_SHORT).show();
//            }
//
//            public void onSwipeBottom() {
//                //Toast.makeText(GlobalReferences.getInstance().baseActivity, "bottom", Toast.LENGTH_SHORT).show();
//            }
//
//        });




        return muralview;
    }


    private void updateView(final Murals mural){
        Log.e(TAG, "updateView: "+mural.getImage_path() );
          muralsObject=mural;

        final String image_id = mural.getImage_resource_id().toLowerCase();
        String image_url = "https://canvs.cruxcode.nyc/mural_large_"+image_id+".jpg?size=large&requestType=image";

        Glide.with(GlobalReferences.getInstance().baseActivity).load(image_url)
                .thumbnail(0.5f).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                if(mural!=null){
                    if(mural.getFreshWhenAdded()!=null&&mural.getFreshWhenAdded().equalsIgnoreCase("1")){
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
        tv_author.setText(mural.getAuthor());

        tv_mural.setText(mural.getTitle());
        textView_about_artist.setText(mural.getAuthor());

        textView_about_mural.setText(mural.getAbout_text());
        textView_location.setText(mural.getLocation_text());

        String add_link_1=mural.getAdditional_link_first();
        if(add_link_1!=null&&!add_link_1.equals("null")){
            textView_add_link_first.setText(add_link_1);
        }else{
            textView_add_link_first.setVisibility(View.GONE);
        }
        String add_link_2=mural.getAdditional_link_second();
        if(add_link_2!=null&&!add_link_2.equals("null")){
            textView_add_link_second.setText(add_link_2);
        }else{
            textView_add_link_second.setVisibility(View.GONE);
        }
        String add_link_3=mural.getAdditional_limk_third();
        if(add_link_3!=null&&!add_link_3.equals("null")){
            textView_add_link_second.setText(add_link_3);
        }else{
            textView_add_link_second.setVisibility(View.GONE);
        }
        String tags=mural.getTags();
        Log.e(TAG, "updateView: tags"+tags );
        tags_list.clear();
        String [] strings = tags.split(",");
        for (int i=0;i<strings.length;i++){
            tags_list.add(strings[i]);

        }
        Log.e(TAG, "updateView: length"+tags_list.size() );
        tagsAdapter.notifyDataSetChanged();
        lat=mural.getLatitude();
        lon=mural.getLatitude();

        if (RealmController.getInstance().isFavoriteMuralExist(mural.getId())) {
            fav_img.setColorFilter(Color.parseColor("#5ab3a4"), PorterDuff.Mode.SRC_IN);
            favoriteCard.setCardBackgroundColor(Color.parseColor("#305ab3a4"));

        }else {
            fav_img.setColorFilter(Color.parseColor("#908B8A89"), PorterDuff.Mode.SRC_IN);
            favoriteCard.setCardBackgroundColor(Color.parseColor("#ffffff"));

        }

        if (RealmController.getInstance().isBookMarhedMuralExist(mural.getId())) {
            book_img.setColorFilter(Color.parseColor("#5ab3a4"), PorterDuff.Mode.SRC_IN);
            bookmarks_btn.setCardBackgroundColor(Color.parseColor("#305ab3a4"));

        }else {
            book_img.setColorFilter(Color.parseColor("#908B8A89"), PorterDuff.Mode.SRC_IN);
            bookmarks_btn.setCardBackgroundColor(Color.parseColor("#ffffff"));

        }

        if (RealmController.getInstance().isSeenMuralExist(mural.getId())) {
            seen_img.setColorFilter(Color.parseColor("#5ab3a4"), PorterDuff.Mode.SRC_IN);
            seen_btn.setCardBackgroundColor(Color.parseColor("#305ab3a4"));

        }else {
            seen_img.setColorFilter(Color.parseColor("#908B8A89"), PorterDuff.Mode.SRC_IN);
            seen_btn.setCardBackgroundColor(Color.parseColor("#ffffff"));

        }
        updateView(muralsObject);
    }




    private void showFlagPartSecond(){
      final  BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity());
        View sheetView = getActivity().getLayoutInflater().inflate(R.layout.flag_part_second, null);
        TextView tv_cancel= (TextView) sheetView.findViewById(R.id.tv_cancel);
        CardView send= (CardView) sheetView.findViewById(R.id.btn_send);
        final EditText feedback= (EditText) sheetView.findViewById(R.id.et_feedback);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new uploadFeedBack().execute(feedback.getText().toString());



            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
        TextView textView= (TextView) sheetView.findViewById(R.id.selected_flag);
        textView.setText(selected_flag);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
    }

    private void openLink(String link){
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            startActivity(browserIntent);
        }catch (Exception ex){}
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
    public void shareIntent(){
        try{
            StringBuilder share_text = new StringBuilder();
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





    private  class uploadFeedBack extends AsyncTask<String,Void,Void>{
        @Override
        protected void onPreExecute() {
            try{
                GlobalReferences.getInstance().progresBar.setVisibility(View.VISIBLE);

            }catch (Exception ex){

            }


            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try{
                GlobalReferences.getInstance().progresBar.setVisibility(View.VISIBLE);

            }catch (Exception ex){

            }

        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                RequestQueue requestQueue = Volley.newRequestQueue(GlobalReferences.getInstance().baseActivity);
                String URL = "https://api.mailgun.net/v3/mg.cruxcode.nyc/messages";
                JSONObject jsonBody = new JSONObject();
                //jsonBody.put("feedback", );
                final String requestBody = strings[0];

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("VOLLEY", response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                        try {
                            mBottomSheetDialog.dismiss();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        Utility.showToastMsg("Not able to send your feedback at this movement! please try again later");

                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                            return null;
                        }
                    }

                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null) {
                            responseString = String.valueOf(response.statusCode);
                            // can get more details such as response.headers
                            try {
                                mBottomSheetDialog.dismiss();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<String, String>();
                        String user_name ="api";
                        String password = "key-55b16ec1891ce21b42a45dbdaa1c2f6c";
                        String loginString = user_name+":"+password;
                        String base64EncodedString = Base64.encodeToString(loginString.getBytes(), Base64.NO_WRAP);
                        String auth = "Basic " + base64EncodedString;

                        //params.put("Authorization", auth);

                        params.put("Authorization",auth);

//                        username: `api`
//                        password: `key-55b16ec1891ce21b42a45dbdaa1c2f6c`
                            return params;
                       // return super.getHeaders();
                    }
                };

                requestQueue.add(stringRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
