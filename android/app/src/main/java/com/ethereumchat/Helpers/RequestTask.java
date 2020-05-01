package com.ethereumchat.Helpers;

import com.facebook.react.bridge.Callback;

import org.json.JSONObject;

public interface RequestTask {
    void onResponse(JSONObject response, String method, JSONObject data, Callback callback);

}
