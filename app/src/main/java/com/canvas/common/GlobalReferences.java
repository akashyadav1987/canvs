package com.canvas.common;

import android.app.Activity;
import android.widget.LinearLayout;

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

}