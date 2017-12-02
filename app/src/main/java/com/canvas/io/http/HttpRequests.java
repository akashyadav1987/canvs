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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class HttpRequests extends BaseTask<JSONObject> {
    private static HttpRequests instance = null;
    private Map<String, String> headers = new HashMap<String, String>();
    private Context mContext;
    private AppRequest appRequest;
    private Map<String, String> mParams = new HashMap<String, String>();
    private Constants.RequestParam requestParam;

    public HttpRequests(int method, Constants.RequestParam requestParam, Response.ErrorListener listener,
                        AppRequest appRequest, Map<String, String> mParams) {

        super(method, requestParam.getComleteUrl(), listener, requestParam.getRequestTag(), mParams);
        this.appRequest = appRequest;
        this.mParams = mParams;
        headers.put("Content-Type","application/json");
        //setHeaders("authorization", GlobalReferences.getInstance().pref.getAccessToken()+"");
       // headers.put("authorization", GlobalReferences.getInstance().pref.getAccessToken()+"");
        this.requestParam = requestParam;
    }

    public HttpRequests(int method, String url,String requestTag, Response.ErrorListener listener,
                        AppRequest appRequest, Map<String, String> mParams) {

        super(method, url, listener, requestTag, mParams);
        this.appRequest = appRequest;
        this.mParams = mParams;
        headers.put("Content-Type","application/json");
        this.requestParam = requestParam;
        //setHeaders("authorization", GlobalReferences.getInstance().pref.getAccessToken()+"");

    }
    public static HttpRequests getInstance(int method, Constants.RequestParam requestParam, Response.ErrorListener listener,
                                           AppRequest appRequest, Map<String, String> mParams, boolean isPayU) {
        if (instance == null) {
            instance = new HttpRequests(method, requestParam, listener, appRequest, mParams);
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
    protected void deliverResponse(JSONObject response) {
        try {

            if(response!=null) {
        setJsonResponse(response);
        appRequest.onRequestCompleted(this, requestParam);
            }else
                appRequest.onRequestFailed(this,requestParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        JSONObject json = null;
        try {
            json = new JSONObject(new String(response.data));
        } catch (final Exception e) {

            e.printStackTrace();
        }
        Log.e("##Json response = ", json + "");
        return Response.success(json, HttpHeaderParser.parseCacheHeaders(response));
    }
}
