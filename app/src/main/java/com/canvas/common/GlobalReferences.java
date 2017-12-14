package com.canvas.common;

import android.app.Activity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.canvas.db.Pref;

import io.realm.Realm;


/**
 * Created by akashyadav on 11/27/17.
 */

public class GlobalReferences {
    private static GlobalReferences globalSingleton =null;
    public synchronized static GlobalReferences getInstance(){
        if(globalSingleton ==null){
            globalSingleton = new GlobalReferences();
        }
        return globalSingleton;
    }
    private GlobalReferences(){

    }
    public CommonFragment mCommonFragment;
    public Activity baseActivity;
    public LinearLayout progresBar;
    public Toolbar toolbar;
    public Pref pref;
    public Realm realm;
    public SearchView searchView;

}
