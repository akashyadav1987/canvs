package com.canvas.io.http;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.canvas.common.Constants;
import com.canvas.common.GlobalReferences;
import com.canvas.io.listener.AppRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class ApiRequests {
    public static final String PREFERENCES_FILE = "CatalogPreference";
    private static ApiRequests apiRequests = null;
    private final VolleyErrorListener error;
    public RequestQueue mRequestQueue;
    private HashMap<String, String> mParams;
    private Constants.RequestParam requestParam;

    public static ApiRequests getInstance() {
        return new ApiRequests();
    }

    public ApiRequests() {
        if (mParams == null) {
            mParams = new HashMap<String, String>();
        } else {
            mParams.clear();
        }
        mRequestQueue = RequestManager.getnstance(GlobalReferences.getInstance().baseActivity);
        error = new VolleyErrorListener();
    }

    public void get_murals(Context context, AppRequest appRequest) {
        if (context != null) {

            requestParam = Constants.RequestParam.GET_MURALS;
            String url = Constants.RequestParam.GET_MURALS.getComleteUrl();
            Log.e("Url",url+"");
            String requestTag = "get_active_murals";
            HttpRequests requests = new HttpRequests(Request.Method.GET, url, requestTag, error, appRequest, mParams);
            error.setRequestLister(appRequest, requests, requestParam.getRequestTag());
            if (mRequestQueue != null) {
                mRequestQueue.cancelAll(requestParam.getRequestTag());
            }
            requests.setTag(requestParam.getRequestTag());
            mRequestQueue.add(requests);
            appRequest.onRequestStarted(requests, requestParam);
        }
    }

    public void hit_prediction(Context context, AppRequest appRequest, String text) {
        if (context != null) {
            String prediction_url = null;
            try {
                prediction_url = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=" + text + "&types=establishment&key=AIzaSyAwFNrU1CKYFvbKmoQ3_usslVhdT7UHI-o";
            } catch (Exception e) {
                e.printStackTrace();
            }
           // requestParam = Constants.RequestParam.PREDICTION;
            String requestTag = "predictions";
            HttpRequests requests = new HttpRequests(Request.Method.GET, prediction_url, requestTag, error, appRequest, mParams);
            error.setRequestLister(appRequest, requests, requestParam.getRequestTag());
            if (mRequestQueue != null) {
                mRequestQueue.cancelAll(requestParam.getRequestTag());
            }
            requests.setTag(requestParam.getRequestTag());
            mRequestQueue.add(requests);
            appRequest.onRequestStarted(requests, requestParam);
        }
    }


    public String trimMessage(String json, String key) {
        String trimmedString = null;
        try {
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return trimmedString;
    }

    /**
     * Will be reponsible for listening errors
     * <p/>
     * *
     */
    class VolleyErrorListener implements Response.ErrorListener {
        private AppRequest listener;
        private BaseTask<?> task;
        private BaseTaskJson<JSONObject> task1;
        private String api_name;

        void setRequestLister(AppRequest listener, BaseTask<?> task, String api_name) {
            this.listener = listener;
            this.task = task;
            this.api_name = api_name;
        }

        void setRequestLister(AppRequest listener, BaseTaskJson<JSONObject> task, String api_name) {
            this.listener = listener;
            this.task1 = task;
            this.api_name = api_name;
        }

        @Override
        public void onErrorResponse(VolleyError error) {

            String json = null;
            NetworkResponse response = error.networkResponse;
            if (response != null && response.data != null) {
                json = new String(response.data);
                json = trimMessage(json, "message");
                //   if (json != null)
                //Utility.displayToastMessage(response.statusCode, json + " on " + api_name == null ? "" : api_name);

            } else {
                // Utility.displayMessage(500, "Unexpected server error on " + api_name == null ? "" : api_name);
            }
            if (listener != null) {
                if (task != null)
                    listener.onRequestFailed(task, requestParam);
                else if (task1 != null) {
                     listener.onRequestFailed(task1, requestParam);

                }
            }

        }
    }
}