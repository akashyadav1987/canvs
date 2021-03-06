package com.canvas.common;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.canvas.R;
import com.canvas.fragment.CanvsMapFragment;
import com.canvas.fragment.FragmentMuralDetailsParent;

/**
 * Created by akashyadav on 11/27/17.
 */

public class CommonFragment extends Fragment {
    protected String screenTitle = "";
    protected ImageView toolbarImage, imageView_back,imageview_share;

    private TextView toolBarText;
    boolean isBookMarkedSelected =GlobalReferences.getInstance().pref.getBookmarkedFilter(),
            isSeenSelected =GlobalReferences.getInstance().pref.getSeenFilter(),
            isFavSheet = GlobalReferences.getInstance().pref.getFavFilter(),
            isFreshMural = GlobalReferences.getInstance().pref.getFreshFilter(),
            isNearBySelected = GlobalReferences.getInstance().pref.getNearbyFilter();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //updateToolbarTitle();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void updateToolbarTitle() {
        try {
            imageView_back = (ImageView) GlobalReferences.getInstance().toolbar.findViewById(R.id.filter);
            toolbarImage = (ImageView) GlobalReferences.getInstance().toolbar.findViewById(R.id.toolbar_image);
            toolBarText = (TextView) GlobalReferences.getInstance().toolbar.findViewById(R.id.tool_bar_title);
            imageview_share = (ImageView) GlobalReferences.getInstance().baseActivity.findViewById(R.id.share_icon);
//            search_box = GlobalReferences.getInstance().toolbar.findViewById(R.id.search_box);
//            search_box.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    try{
//                    GlobalReferences.getInstance().searchView.setVisibility(View.VISIBLE);
//                    }catch (Exception e){
//
//                    }
//                }
//            });
            if (GlobalReferences.getInstance().mCommonFragment instanceof CanvsMapFragment) {
                imageview_share.setVisibility(View.GONE);
                toolbarImage.setVisibility(View.VISIBLE);
                toolBarText.setVisibility(View.GONE);
                imageView_back.setImageResource(R.drawable.filter);
                // ImageView imageView_filter = GlobalReferences.getInstance().toolbar.findViewById(R.id.filter);
                imageView_back.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
                imageView_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isBookMarkedSelected =GlobalReferences.getInstance().pref.getBookmarkedFilter();
                                isSeenSelected =GlobalReferences.getInstance().pref.getSeenFilter();
                                isFavSheet = GlobalReferences.getInstance().pref.getFavFilter();
                                isFreshMural = GlobalReferences.getInstance().pref.getFreshFilter();
                                isNearBySelected = GlobalReferences.getInstance().pref.getNearbyFilter();
                        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity());
                        View sheetView = getActivity().getLayoutInflater().inflate(R.layout.fragment_bottom_sheet, null);
                        final ImageView bookmarks = (ImageView) sheetView.findViewById(R.id.bookmarks);
                        final ImageView seen_sheet = (ImageView) sheetView.findViewById(R.id.seen_sheet);
                        final ImageView fav_sheet = (ImageView) sheetView.findViewById(R.id.fav_sheet);
                        final ImageView fresh_mural = (ImageView) sheetView.findViewById(R.id.fresh_mural);
                        final ImageView near_by_murals = (ImageView) sheetView.findViewById(R.id.near_by_murals);
                        TextView clear_filters = (TextView) sheetView.findViewById(R.id.clear_filters);

                        if(isBookMarkedSelected){
                            bookmarks.setImageResource(R.drawable.bookmark_sheet_selected);
                        }else{
                            bookmarks.setImageResource(R.drawable.bookmark_sheet);
                        }

                        if(isSeenSelected){
                            seen_sheet.setImageResource(R.drawable.seen_sheet_selected);

                        }else{
                            seen_sheet.setImageResource(R.drawable.seen_sheet);
                        }

                        if(isFavSheet){
                            fav_sheet.setImageResource(R.drawable.favorites_sheet_selected);
                        }else{
                            fav_sheet.setImageResource(R.drawable.favorites_sheet);
                        }

                        if(isFreshMural){
                            fresh_mural.setImageResource(R.drawable.fresh_murals_sheet_selected);
                        }else{
                            fresh_mural.setImageResource(R.drawable.fresh_murals_sheet);

                        }

                        if(isNearBySelected){
                            near_by_murals.setImageResource(R.drawable.nearby_murals_sheet_selected);
                        }else{
                            near_by_murals.setImageResource(R.drawable.nearby_murals_sheet);

                        }


                        bookmarks.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                isBookMarkedSelected = isBookMarkedSelected==true?false:true;
                                if(isBookMarkedSelected){
                                    bookmarks.setImageResource(R.drawable.bookmark_sheet_selected);
                                }else{
                                    bookmarks.setImageResource(R.drawable.bookmark_sheet);
                                }
                            }
                        });

                        seen_sheet.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                isSeenSelected = isSeenSelected==true?false:true;
                                if(isSeenSelected){
                                    seen_sheet.setImageResource(R.drawable.seen_sheet_selected);
                                }else{
                                    seen_sheet.setImageResource(R.drawable.seen_sheet);
                                }
                            }
                        });

                        fav_sheet.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                isFavSheet = isFavSheet==true?false:true;
                                if(isFavSheet){
                                    fav_sheet.setImageResource(R.drawable.favorites_sheet_selected);
                                }else{
                                    fav_sheet.setImageResource(R.drawable.favorites_sheet);
                                }
                            }
                        });

                        fresh_mural.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                isFreshMural = isFreshMural==true?false:true;
                                if(isFreshMural){
                                    fresh_mural.setImageResource(R.drawable.fresh_murals_sheet_selected);
                                }else{
                                    fresh_mural.setImageResource(R.drawable.fresh_murals_sheet);
                                }
                            }

                        });

                        near_by_murals.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                isNearBySelected = isNearBySelected==true?false:true;
                                if(isNearBySelected){
                                    near_by_murals.setImageResource(R.drawable.nearby_murals_sheet_selected);
                                }else{
                                    near_by_murals.setImageResource(R.drawable.nearby_murals_sheet);
                                }
                            }
                        });

                        TextView done = (TextView) sheetView.findViewById(R.id.done);
                        done.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                GlobalReferences.getInstance().pref.setBookmarkedFilter(isBookMarkedSelected);
                                GlobalReferences.getInstance().pref.setSeenFilter(isSeenSelected);
                                GlobalReferences.getInstance().pref.setFavFilter(isFavSheet);
                                GlobalReferences.getInstance().pref.setFreshFilter(isFreshMural);
                                GlobalReferences.getInstance().pref.setNearbyFilter(isNearBySelected);
                                mBottomSheetDialog.hide();
                                ((CanvsMapFragment)GlobalReferences.getInstance().mCommonFragment).callApiAgain();

                            }
                        });

                        clear_filters.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                GlobalReferences.getInstance().pref.setBookmarkedFilter(false);
                                GlobalReferences.getInstance().pref.setSeenFilter(false);
                                GlobalReferences.getInstance().pref.setFavFilter(false);
                                GlobalReferences.getInstance().pref.setFreshFilter(false);
                                GlobalReferences.getInstance().pref.setNearbyFilter(false);
                                isBookMarkedSelected =GlobalReferences.getInstance().pref.getBookmarkedFilter();
                                isSeenSelected =GlobalReferences.getInstance().pref.getSeenFilter();
                                isFavSheet = GlobalReferences.getInstance().pref.getFavFilter();
                                isFreshMural = GlobalReferences.getInstance().pref.getFreshFilter();
                                isNearBySelected = GlobalReferences.getInstance().pref.getNearbyFilter();

                                if(isBookMarkedSelected){
                                    bookmarks.setImageResource(R.drawable.bookmark_sheet_selected);
                                }else{
                                    bookmarks.setImageResource(R.drawable.bookmark_sheet);
                                }

                                if(isSeenSelected){
                                    seen_sheet.setImageResource(R.drawable.seen_sheet_selected);

                                }else{
                                    seen_sheet.setImageResource(R.drawable.seen_sheet);
                                }

                                if(isFavSheet){
                                    fav_sheet.setImageResource(R.drawable.favorites_sheet_selected);
                                }else{
                                    fav_sheet.setImageResource(R.drawable.favorites_sheet);
                                }

                                if(isFreshMural){
                                    fresh_mural.setImageResource(R.drawable.fresh_murals_sheet_selected);
                                }else{
                                    fresh_mural.setImageResource(R.drawable.fresh_murals_sheet);
                                }
                                if(isNearBySelected){
                                    near_by_murals.setImageResource(R.drawable.nearby_murals_sheet_selected);
                                }else{
                                    near_by_murals.setImageResource(R.drawable.nearby_murals_sheet);
                                }
                            }
                        });
                        mBottomSheetDialog.setContentView(sheetView);
                        mBottomSheetDialog.show();
                    }
                });
            } else if (GlobalReferences.getInstance().mCommonFragment instanceof FragmentMuralDetailsParent) {
                //toolbarImage.setVisibility(View.VISIBLE);
                imageview_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            ((FragmentMuralDetailsParent) GlobalReferences.getInstance().mCommonFragment).shareIntent();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                imageview_share.setVisibility(View.VISIBLE);
                GlobalReferences.getInstance().progresBar.setVisibility(View.GONE);
                toolBarText.setVisibility(View.GONE);
                imageView_back.setImageDrawable(GlobalReferences.getInstance().baseActivity.getResources().getDrawable(R.drawable.ic_left_arrow));
                imageView_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().getSupportFragmentManager().popBackStackImmediate();
                    }
                });
                // search_box.setVisibility(View.GONE);

            } else {
                imageview_share.setVisibility(View.GONE);
                GlobalReferences.getInstance().progresBar.setVisibility(View.GONE);

                imageView_back.setImageDrawable(GlobalReferences.getInstance().baseActivity.getResources().getDrawable(R.drawable.ic_left_arrow));
                imageView_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().getSupportFragmentManager().popBackStackImmediate();
                    }
                });
                toolbarImage.setVisibility(View.GONE);
                toolBarText.setVisibility(View.VISIBLE);
                toolBarText.setText(screenTitle + "");
                // search_box.setVisibility(View.GONE);

            }
            // search_box.setVisibility(View.GONE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // updateToolbarTitle();
        super.onViewCreated(view, savedInstanceState);
    }

    public void onRefresh() {
        updateToolbarTitle();
    }
}
