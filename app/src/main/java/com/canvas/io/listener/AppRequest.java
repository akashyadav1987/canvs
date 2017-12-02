package com.canvas.io.listener;


import com.android.volley.Response;
import com.canvas.common.Constants;
import com.canvas.io.http.BaseTask;
import com.canvas.io.http.BaseTaskJson;

import org.json.JSONObject;

public interface AppRequest extends Response.Listener<JSONObject>{

    public <T> void onRequestStarted(BaseTask<T> listener, Constants.RequestParam requestParam);

    public <T> void onRequestCompleted(BaseTask<T> listener, Constants.RequestParam requestParam);

    public <T> void onRequestFailed(BaseTask<T> listener, Constants.RequestParam requestParam);


    public <T> void onRequestStarted(BaseTaskJson<JSONObject> listener, Constants.RequestParam requestParam);

    public <T> void onRequestCompleted(BaseTaskJson<JSONObject> listener, Constants.RequestParam requestParam);

    public <T> void onRequestFailed(BaseTaskJson<JSONObject> listener, Constants.RequestParam requestParam);
}
