package com.canvas;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.canvas.common.CommonFragment;
import com.canvas.common.GlobalReferences;

;

/**
 * Created by Akashyadav on 11/02/17.
 */

public class BaseActivity extends AppCompatActivity {
    protected FragmentManager fragmentManager;
    protected FragmentTransaction fragmentTransaction;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {

        super.onCreate(savedInstanceState, persistentState);
        //fragmentManager =getSupportFragmentManager();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    public void addFragmentWithBackStack(Fragment fragment, boolean addToBackStack){
        try {
            GlobalReferences.getInstance().mCommonFragment = (CommonFragment) fragment;
            String backStateName = fragment.getClass().getName();
            String fragmentTag = backStateName;
            FragmentManager manager = getSupportFragmentManager();
            if(manager!=null) {

                boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

                if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null) { //fragment not in back stack, create it.

                    FragmentTransaction ft = manager.beginTransaction();
                    //  ft.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left);
                    ft.replace(R.id.frame_container, fragment, fragmentTag);

                    // ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    if (addToBackStack)
                        ft.addToBackStack(backStateName);
                    ft.commitAllowingStateLoss();
                } else {
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        onPostResume();
        fragmentManager =getSupportFragmentManager();
        Log.e("manager",getSupportFragmentManager()+"");
    }
}
