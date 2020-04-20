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

import com.ethereumchat.Database.ChatContract;
import com.ethereumchat.Database.ChatDBHelper;
import com.ethereumchat.Helpers.ClientHolder;
import com.ethereumchat.Helpers.WhisperHelper;
import com.ethereumchat.Models.Contact;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.uimanager.IllegalViewOperationException;


import org.json.JSONArray;
import org.json.JSONObject;

import com.ethereumchat.Database.ChatDBHelper.*;

import geth.Context;
import geth.Criteria;
import geth.Geth;
import geth.Message;
import geth.NewMessage;
import geth.NewMessageHandler;
import geth.Subscription;
import geth.WhisperClient;


public class EthereumChatMessagingModule extends ReactContextBaseJavaModule {

    private static final String TAG = "EthereumChatMessagingMo";


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
    public void postMessage(String message,String publicKey){
        try{
            publicKey = publicKey.substring(2,publicKey.length());

            NewMessage message1 = Geth.newNewMessage();

            message1.setPayload(message.getBytes());
            message1.setPublicKey(publicKey);
            message1.setTTL(60);
            message1.setPowTime(2);
            message1.setPowTarget(2.5);

            WhisperClient whisperClient = ClientHolder.getWhisperClient();

            Context context = Geth.newContext();

            whisperClient.post(context,message1);
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
              return;
          }

          WhisperClient whisperClient = ClientHolder.getWhisperClient();

          Context context = Geth.newContext();

          String publicKey = WhisperHelper.getPublicKey(keyPair);


          Criteria criteria = Geth.newCriteria("Ethereum Chat".getBytes());


          criteria.setPrivateKeyID(keyPair);



          new Thread(new Runnable() {
              @Override
              public void run() {
                  try{
                      Log.d(TAG, "run: executed");


                      NewMessageHandler messageHandler = new NewMessageHandler() {
                          @Override
                          public void onError(String s) {

                          }

                          @Override
                          public void onNewMessage(Message message) {
                              String newMessage = new String(message.getPayload());
                              System.out.println("New message arrived! " + newMessage);
                              Log.d(TAG, "onNewMessage: " + newMessage);



                          }
                      };

                      whisperClient.subscribeMessages(context, criteria, messageHandler,1000);




                  }
                  catch (Exception e){
                      e.printStackTrace();
                  }

              }
          }).start();









          newMessageCallback.invoke("success");
      }
      catch (Exception e){
          error.invoke("error occured");
          e.printStackTrace();
      }




    }
}