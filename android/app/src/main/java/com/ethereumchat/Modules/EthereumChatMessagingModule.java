package com.ethereumchat.Modules;

import android.accounts.AccountManager;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;

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

import org.ethereum.geth.Context;
import org.ethereum.geth.Criteria;
import org.ethereum.geth.Geth;
import org.ethereum.geth.KeyStore;
import org.ethereum.geth.Message;
import org.ethereum.geth.Messages;
import org.ethereum.geth.NewMessage;
import org.ethereum.geth.NewMessageHandler;
import org.ethereum.geth.Node;
import org.ethereum.geth.NodeConfig;
import org.ethereum.geth.WhisperClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ethereumchat.Database.ChatDBHelper.*;


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
            NewMessage message1 = Geth.newNewMessage();
            message1.setPayload(message.getBytes());
            message1.setPublicKey(publicKey.getBytes());
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



          Criteria criteria = Geth.newCriteria(keyPair.getBytes());


          criteria.setPrivateKeyID(keyPair);

          whisperClient.subscribeMessages(context, criteria, new NewMessageHandler() {
              @Override
              public void onError(String s) {
                  Log.d(TAG, "onError: " + s);
              }

              @Override
              public void onNewMessage(Message message) {
                  String newMessage = new String(message.getPayload());
                  Log.d(TAG, "onNewMessage: " + newMessage);
                  newMessageCallback.invoke(newMessage);
              }
          },10);

      }
      catch (Exception e){
          error.invoke("error occured");
          e.printStackTrace();
      }




    }
}