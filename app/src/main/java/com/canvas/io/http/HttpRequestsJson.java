package com.canvas.io.http;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.canvas.common.Constants;
import com.canvas.io.listener.AppRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class HttpRequestsJson extends BaseTaskJson {
    private static HttpRequestsJson instance = null;
    private Map<String, String> headers = new HashMap<String, String>();
    private Context mContext;
    private AppRequest appRequest;
    private Map<String, String> mParams = new HashMap<String, String>();
    private Constants.RequestParam requestParam;

    public HttpRequestsJson(int method, String url, JSONObject jsonObject, Response.ErrorListener listener,
                            AppRequest appRequest,HashMap<String , String> params) {

        //super(method, url, listener, requestTag, requestListener);
        super(method, url,jsonObject,listener,appRequest);

        this.appRequest = appRequest;
        //mParams.put("item","12");
        Log.e("Content tye","dfddf");
        headers.put("Content-Type","application/json; charset=utf-8");
        //Log.e("authorization =", GlobalReferences.getInstance().pref.getAccessToken()+"");
       // if(GlobalReferences.getInstance().pref.getAccessToken()!=null&&!GlobalReferences.getInstance().pref.getAccessToken().isEmpty())
        //headers.put("authorization", GlobalReferences.getInstance().pref.getAccessToken()+"");

        //setHeaders("Content-Type", "application/json");
        //mParams =params;

       // mParams.put("access_token",GlobalReferences.getInstance().pref.getAccessToken());
        this.requestParam = requestParam;
    }


    public static HttpRequestsJson getInstance(int method, Constants.RequestParam requestParam, Response.ErrorListener listener,
                                               AppRequest appRequest, Map<String, String> mParams, boolean isPayU) {
        if (instance == null) {
            //instance = new HttpRequestsJson(method, requestParam, listener, appRequest, mParams);
        }
        return instance;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(String title, String content) {
        headers.put(title, content);
    }

    @Override
    protected Map<String, String> getParams()  {
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
    public String getBodyContentType() {
       return "application/json";
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

    @Override
    public int getPosition() {
        return super.getPosition();
    }
}
