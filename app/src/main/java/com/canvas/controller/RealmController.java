package com.canvas.controller;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.canvas.listener.SearchResultFound;
import com.canvas.model.BookmarkedMural;
import com.canvas.model.FavoriteMural;
import com.canvas.model.Murals;
import com.canvas.model.SeenMural;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.R.attr.id;
import static io.fabric.sdk.android.Fabric.TAG;

/**
 * Created by akashyadav on 12/9/17.
 */

public class RealmController {
    private static RealmController instance;
    private final Realm realm;


    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }





    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public void addFavoriteMural(FavoriteMural mural) {
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(mural);
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addBookMarkedMural(BookmarkedMural mural) {
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(mural);
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addSeenMural(SeenMural mural) {
        try {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(mural);
            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addMural(final Murals mural) {
        try {
           // Realm realm=Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(mural);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static RealmController getInstance() {
        return instance;
    }

    public Realm getRealm() {
        return realm;
    }

    //Refresh the realm instance
    public void refresh() {
        realm.refresh();
    }

    //clear all objects from Book.class
    public void clearAll() {
        realm.beginTransaction();
        //realm..clear(FavoriteMural.class);
        realm.commitTransaction();
    }

    //find all objects in the Book.class
    public RealmResults<FavoriteMural> getAllFavoriteMurals() {
        return realm.where(FavoriteMural.class).findAll();
    }

    public RealmResults<SeenMural> getAllSeenMurals() {
        return realm.where(SeenMural.class).findAll();
    }

    public RealmResults<BookmarkedMural> getAllBookMurals() {
        return realm.where(BookmarkedMural.class).findAll();
    }

    public RealmResults<Murals> getAllMurals() {
        return realm.where(Murals.class).findAll();
    }
    public boolean isFavoriteMuralExist(int id) {
        try {
            Log.e("id",id+"");
           // Realm realm=Realm.getDefaultInstance();
            FavoriteMural favoriteMural = realm.where(FavoriteMural.class).equalTo("id", id).findFirst();
            Log.e("Is exist or not =", favoriteMural + "");
            if (favoriteMural != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean isBookMarhedMuralExist(int id) {
        try {
            Log.e("id",id+"");
           // Realm realm=Realm.getDefaultInstance();
            RealmResults<BookmarkedMural> list=realm.where(BookmarkedMural.class).findAll();
            Log.e(TAG, "isBookMarhedMuralExist: size"+list.size() );
            BookmarkedMural favoriteMural = realm.where(BookmarkedMural.class).equalTo("id", id).findFirst();
            Log.e("Is exist or not =", favoriteMural + "");
            if (favoriteMural != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "isBookMarhedMuralExist: "+e.getMessage() );
        }
        return false;
    }
    public Murals findMuralByTitle(String title) {
        try {
            Log.e("id",id+"");

            Murals murals = realm.where(Murals.class).equalTo("title", title).or().equalTo("artist_text",title).findFirst();
            Log.e("Is exist or not =", murals + "");
             return murals;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isSeenMuralExist(int id) {
        try {
            Log.e("id",id+"");
           // Realm realm=Realm.getDefaultInstance();
            SeenMural seenMural = realm.where(SeenMural.class).equalTo("id", id).findFirst();
            Log.e("Is exist or not =", seenMural + "");
            if (seenMural != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "isSeenMuralExist: "+e.getMessage() );
        }
        return false;
    }
    //query example
    public RealmResults<FavoriteMural> getSeenByContainsMuralId(int id) {
        return realm.where(FavoriteMural.class).contains("id", id+"").or().findAll();
    }
    public void deleteFavoriteMural(final int id){
        try{
           // Realm realm=Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<FavoriteMural> rows = realm.where(FavoriteMural.class).equalTo("id",id).findAll();
                    rows.deleteAllFromRealm();
                    Log.e("record deleted==",rows+"");
                }
            });
        }catch (Exception e){

        }
    }
    public void deleteBookMarkedMural(final int id){
        try{
           // Realm realm=Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<BookmarkedMural> rows = realm.where(BookmarkedMural.class).equalTo("id",id).findAll();
                    rows.deleteAllFromRealm();
                    Log.e("record deleted book==",rows+"");
                }
            });
        }catch (Exception e){

        }
    }

    public void deleteSeenMarkedMural(final int id){
        try{
           // Realm realm=Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<SeenMural> rows = realm.where(SeenMural.class).equalTo("id",id).findAll();
                    rows.deleteAllFromRealm();
                    Log.e("record deleted book==",rows+"");
                }
            });
        }catch (Exception e){

        }
    }
    public void deleteAllMural(){
        try{
            //Realm realm=Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<BookmarkedMural> rows = realm.where(BookmarkedMural.class).findAll();
                    rows.deleteAllFromRealm();
                    Log.e("record deleted book==",rows+"");
                }
            });
        }catch (Exception e){

        }
    }
    public void searchForMural(final String searchQuery, final SearchResultFound searchResultFound){
        RealmResults<Murals> rowsAC =null;
        try{
            //Realm realm=Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<Murals> rows = realm.where(Murals.class).contains("title",searchQuery.toLowerCase()).or().contains("author",searchQuery.toLowerCase()).or().contains("tags",searchQuery.toLowerCase()).findAll();
                    rows.size();
                    searchResultFound.onSearchResultFound(rows);


                }
            });
        }catch (Exception e){

        }
    }
}

