package com.ethereumchat.Helpers;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ethereumchat.Database.ChatContract;
import com.ethereumchat.Database.ChatDBHelper;
import com.ethereumchat.MainApplication;
import com.ethereumchat.Models.Message;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.CatalystInstance;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

import javax.annotation.Nullable;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class EthereumWebSocketListener extends WebSocketListener {
    private static final int NORMAL_CLOSURE_STATUS = 1000;

    private static final String TAG = "EthereumWebSocketListen";

    private final @Nullable ReactApplicationContext mReactApplicationContext;

    public EthereumWebSocketListener() {
        mReactApplicationContext = null;
    }

    public EthereumWebSocketListener(@Nullable ReactApplicationContext reactContext) {
        mReactApplicationContext = reactContext;
    }



    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);

    }

    @Override
    @ReactMethod
    public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        try{
            Log.d(TAG, "onMessage: " + text);
            JSONObject response = new JSONObject(text);
            if(response.has("method")){
                String method = response.getString("method");
                switch (method){
                    case "shh_subscription":
                        JSONObject params = response.getJSONObject("params");
                        JSONObject result = params.getJSONObject("result");
                        String payload = result.getString("payload");
                        byte[] bytes = WhisperHelper.hexStringToByteArray(payload);
                        String message = new String(bytes, StandardCharsets.UTF_8);
                        Log.d(TAG, "onMessage: " + message);
                        JSONObject messageObject = response.getJSONObject("message");
                        String messageFrom = messageObject.getString("public_key");
                        String messageText = messageObject.getString("text");

                        if (messageObject.has("app")){
                            String app = messageObject.getString("app");

                            if(app.equals("EthereumChat")){
                                String timeStamp = messageObject.getString("time_stamp");
                                String isImage = messageObject.getString("is_image");
                                String messageTo = "self";


                                Message msg = new Message(timeStamp,messageText,isImage,messageFrom,messageTo);

                                ChatDBHelper chatDBHelper = new ChatDBHelper(mReactApplicationContext);

                                chatDBHelper.addMessage(msg);
                                JSONObject messageObjectToSend = new JSONObject();
                                messageObjectToSend.put("from",messageFrom);
                                messageObjectToSend.put("message_body",payload);
                                messageObjectToSend.put("time",timeStamp);
                                // Add code to send signals to react native ui
                                WritableMap data = Arguments.createMap();
                                data.putString("message",messageObjectToSend.toString());
                                mReactApplicationContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                                        .emit("newMessage",data);
                            }
                            else{
                                throw new Exception("Not for this application");
                            }
                        }
                        break;
                    default:
                        break;
                }
            }


        }
        catch (Exception e){
            e.printStackTrace();
        }


    }


    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        super.onMessage(webSocket, bytes);

    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        super.onClosing(webSocket, code, reason);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        super.onClosed(webSocket, code, reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        super.onFailure(webSocket, t, response);
       // Log.d(TAG, "onFailure: " + response.message());
    }
}
