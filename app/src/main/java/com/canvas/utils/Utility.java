package com.canvas.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.widget.Toast;

import com.canvas.R;
import com.canvas.common.GlobalReferences;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * Created by admin on 5/6/2017.
 */
public class Utility {
    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) GlobalReferences.getInstance().baseActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
    public static synchronized void showNoInternetConnectionToast(){
        try{
            Toast.makeText(GlobalReferences.getInstance().baseActivity,
                    GlobalReferences.getInstance().baseActivity.getResources().getString(R.string.no_internet),Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static synchronized void showToastMsg(String  msg){
        try{
            Toast.makeText(GlobalReferences.getInstance().baseActivity,
                   msg,Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static String getBase64FromBitmap(final Bitmap bitmap) {
        String encodedFormImage = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 90,
                    byteArrayOutputStream);
            byte[] uploadBitmap = byteArrayOutputStream.toByteArray();
            encodedFormImage = Base64.encodeToString(uploadBitmap,
                    Base64.DEFAULT);
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return encodedFormImage;
    }
    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();
        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, 1200, 628, true);
    }

    //------- For Profile Module -------//
    public static Bitmap getResizedBitmap(String imageDecodeString) {
        Bitmap bitmap = null;
        try {
            if (imageDecodeString == null) {
                throw new NullPointerException();
            } else {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                options.inPurgeable = Boolean.TRUE;
                bitmap = BitmapFactory.decodeFile(imageDecodeString, options);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;

    }
    public static Typeface getFontRobotoLight() {
        Typeface typeface = Typeface.createFromAsset(GlobalReferences.getInstance().baseActivity.getAssets(), "Roboto-Light.ttf");
        return typeface;
    }

    public static Typeface getFontRobotoMedium() {
        Typeface typeface = Typeface.createFromAsset(GlobalReferences.getInstance().baseActivity.getAssets(), "Roboto-Medium.ttf");
        return typeface;
    }

    public static Typeface getFontRobotoRegular() {
        Typeface typeface = Typeface.createFromAsset(GlobalReferences.getInstance().baseActivity.getAssets(), "Roboto-Regular.ttf");
        return typeface;
    }

    public static Typeface getFontRobotoBoldr() {
        Typeface typeface = Typeface.createFromAsset(GlobalReferences.getInstance().baseActivity.getAssets(), "Roboto-Bold.ttf");
        return typeface;
    }

    public static Typeface getFontRobotoCondensedRegular() {
        Typeface typeface = Typeface.createFromAsset(GlobalReferences.getInstance().baseActivity.getAssets(), "RobotoCondensed-Regular.ttf");
        return typeface;
    }

    public static Typeface getFontRobotoCondensedBold() {
        Typeface typeface = Typeface.createFromAsset(GlobalReferences.getInstance().baseActivity.getAssets(), "RobotoCondensed-Bold.ttf");
        return typeface;
    }
    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }



    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}

