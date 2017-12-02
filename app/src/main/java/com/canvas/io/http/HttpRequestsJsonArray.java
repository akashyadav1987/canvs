package com.canvas.io.http;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.canvas.common.Constants;
import com.canvas.io.listener.AppRequest;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;


public class HttpRequestsJsonArray extends BaseTask<JSONArray> {
    private static HttpRequestsJsonArray instance = null;
    private Map<String, String> headers = new HashMap<String, String>();
    private Context mContext;
    private AppRequest appRequest;
    private Map<String, String> mParams = new HashMap<String, String>();
    private Constants.RequestParam requestParam;

    public HttpRequestsJsonArray(int method, Constants.RequestParam requestParam, Response.ErrorListener listener,
                                 AppRequest appRequest, Map<String, String> mParams) {

        super(method, requestParam.getComleteUrl(), listener, requestParam.getRequestTag(), mParams);
        this.appRequest = appRequest;
        this.mParams = mParams;
        headers.put("Content-Type","application/json");
        //setHeaders("authorization", GlobalReferences.getInstance().pref.getAccessToken()+"");
       // headers.put("authorization", GlobalReferences.getInstance().pref.getAccessToken()+"");
        this.requestParam = requestParam;
    }

    public HttpRequestsJsonArray(int method, String url, String requestTag, Response.ErrorListener listener,
                                 AppRequest appRequest, Map<String, String> mParams) {

        super(method, url, listener, requestTag, mParams);
        this.appRequest = appRequest;
        this.mParams = mParams;
        headers.put("Content-Type","application/json");
        this.requestParam = requestParam;
        //setHeaders("authorization", GlobalReferences.getInstance().pref.getAccessToken()+"");

    }
    public static HttpRequestsJsonArray getInstance(int method, Constants.RequestParam requestParam, Response.ErrorListener listener,
                                                    AppRequest appRequest, Map<String, String> mParams, boolean isPayU) {
        if (instance == null) {
            instance = new HttpRequestsJsonArray(method, requestParam, listener, appRequest, mParams);
        }
        return instance;
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        headers.put("Content-Type","application/json");
       // headers.put("authorization", GlobalReferences.getInstance().pref.getAccessToken()+"");
        return headers;
    }

    @Override
    public String getBodyContentType() {
        return "application/json";
    }

    public void setHeaders(String title, String content) {
        headers.put(title, content);
    }

    @Override
    public Map<String, String> getParams() {
        return mParams;
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
            VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
            volleyError = error;
        }
        return volleyError;
    }

    @Override
    protected void deliverResponse(JSONArray jsonArray) {
        try {

            if(jsonArray!=null) {
                setJsonArrayResponse(jsonArray);
                appRequest.onRequestCompleted(this, requestParam);
            }else
                appRequest.onRequestFailed(this,requestParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        JSONArray json = null;
        try {
                json = new JSONArray(new String(response.data));
        } catch (final Exception e) {

            e.printStackTrace();
        }
        Log.e("##Json response = ", json + "");
        return Response.success(json, HttpHeaderParser.parseCacheHeaders(response));
    }
}
