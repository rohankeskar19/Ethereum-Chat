package com.ethereumchat.Modules;

import android.accounts.AccountManager;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.HandlerThread;
import android.util.Log;

import androidx.annotation.NonNull;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


import com.ethereumchat.Database.ChatDBHelper;
import com.ethereumchat.Helpers.EthereumWebSocketListener;
import com.ethereumchat.Helpers.WhisperAsyncRequestHandler;
import com.ethereumchat.Helpers.WhisperHelper;

import com.ethereumchat.Models.Conversation;
import com.ethereumchat.Models.Message;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.uimanager.IllegalViewOperationException;


import org.json.JSONArray;
import org.json.JSONObject;




import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;


public class EthereumChatMessagingModule extends ReactContextBaseJavaModule  {

    private static final String TAG = "EthereumChatMessagingMo";



    public void establishConnection(String privateKeyID){
        try{
            Log.d(TAG, "establishConnection: " + privateKeyID);

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(
                    "ws://" + WhisperHelper.ip + ":" + WhisperHelper.wsPort
            ).build();
            SharedPreferences sharedPreferences = getCurrentActivity().getPreferences(android.content.Context.MODE_PRIVATE);

            String selfPublicKey = sharedPreferences.getString("public_key","nodata");

            EthereumWebSocketListener ethereumWebSocketListener = new EthereumWebSocketListener(getReactApplicationContext(),selfPublicKey);
            WebSocket ws = client.newWebSocket(request,ethereumWebSocketListener);
            JSONObject subscribeRequest = new JSONObject("{\"jsonrpc\":\"2.0\",\"method\":\"shh_subscribe\",\"params\":[\"messages\", { \"privateKeyID\": \"" + privateKeyID + "\", \"pow\": 12.3 }],\"id\":1}");
            ws.send(subscribeRequest.toString());

            client.dispatcher().executorService().shutdown();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public EthereumChatMessagingModule(@NonNull ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @NonNull
    @Override
    public String getName() {
        return "EthereumChatMessagingModule";
    }

    @ReactMethod
    public void getContacts(String name, Callback err,Callback success){
        Log.d(TAG, "getContacts: Called");


        ChatDBHelper chatDBHelper = new ChatDBHelper(getReactApplicationContext());
        String contactsArray = chatDBHelper.getAllContacts(name);



        if (contactsArray.equals("error")){
            err.invoke(contactsArray);
        }
        else {

            success.invoke(contactsArray);
        }
    }


    @ReactMethod
    public void getConversations(Callback err,Callback success){
        Log.d(TAG, "getConversations: Called");


        ChatDBHelper chatDBHelper = new ChatDBHelper(getReactApplicationContext());
        String conversationsArray = chatDBHelper.getAllConversations();



        if (conversationsArray.equals("error")){
            err.invoke(conversationsArray);
        }
        else {
            Log.d(TAG, "getConversations: " + conversationsArray);
            success.invoke(conversationsArray);
        }
    }

    @ReactMethod
    public void getMessages(String pubKey,Callback success, Callback err){
        try{
            ChatDBHelper chatDBHelper = new ChatDBHelper(getReactApplicationContext());

            String messages = chatDBHelper.getAllMessages(pubKey);
            Log.d(TAG, "getMessages: " + messages);
            success.invoke(messages);

        }
        catch (Exception e){
            e.printStackTrace();
            err.invoke("Error occured while retrieving messages");
        }
    }


    @ReactMethod
    public void postMessage(String name,String message,String publicKey){
        try{
            SharedPreferences sharedPreferences = getCurrentActivity().getPreferences(android.content.Context.MODE_PRIVATE);

            String selfPublicKey = sharedPreferences.getString("public_key","nodata");
            String selfName = sharedPreferences.getString("name","nodata");
            JSONObject messageBody = new JSONObject();
            messageBody.put("app","EthereumChat");
            messageBody.put("name",selfName);
            messageBody.put("time_stamp",System.currentTimeMillis());
            messageBody.put("text",message);
            messageBody.put("is_image","false");
            messageBody.put("public_key",selfPublicKey);


            String cmd = "{\"jsonrpc\":\"2.0\",\"method\":\"shh_post\",\"params\":[{ \"pubKey\": \"" + publicKey + "\", \"ttl\": 7, \"powTarget\": 2.01, \"powTime\": 2, \"payload\": \"0x" + WhisperHelper.toHex(messageBody.toString()) + "\" }],\"id\":1}";
            JSONObject request = new JSONObject(cmd);
            Log.d(TAG, "postMessage: " + WhisperHelper.toHex(publicKey));
            Log.d(TAG, "postMessage: " + cmd);
            new WhisperAsyncRequestHandler(null,null,null).execute(request,null,null);

            Message msg = new Message(name,String.valueOf(System.currentTimeMillis()),message,"false",selfPublicKey,publicKey);
            ChatDBHelper chatDBHelper = new ChatDBHelper(getReactApplicationContext());
            chatDBHelper.addMessage(msg,selfPublicKey);
            chatDBHelper.updateConversation(publicKey,message,String.valueOf(System.currentTimeMillis()),"true");


        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @ReactMethod
    public void subscribeMessages(Callback newMessageCallback,Callback error){
      try{

          Log.d(TAG, "subscribeMessages: Called");
          SharedPreferences sharedPreferences = getCurrentActivity().getPreferences(android.content.Context.MODE_PRIVATE);

          String keyPair = sharedPreferences.getString("key_pair","null");

          if (keyPair.equals("null")){
              error.invoke("error");
              return;
          }
          else{
              establishConnection(keyPair);
              newMessageCallback.invoke("success");
          }
      }
      catch (Exception e){
          error.invoke("error occured");
          e.printStackTrace();
      }
    }

    @ReactMethod
    public void markAsRead(String publicKey){
        try{

            Log.d(TAG, "markAsRead: Called");
            ChatDBHelper chatDBHelper = new ChatDBHelper(getReactApplicationContext());

            chatDBHelper.setConversationOpened(publicKey,"true");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


}