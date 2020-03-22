package com.ethereumchat;

import android.accounts.AccountManager;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.ethereumchat.Database.ChatDBHelper;
import com.ethereumchat.Helpers.ClientHolder;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.uimanager.IllegalViewOperationException;

import org.ethereum.geth.Context;
import org.ethereum.geth.Geth;
import org.ethereum.geth.KeyStore;
import org.ethereum.geth.Node;
import org.ethereum.geth.NodeConfig;
import org.ethereum.geth.WhisperClient;


public class EthereumChatAccountModule extends ReactContextBaseJavaModule {

    private static final String TAG = "EthereumChatAccountModu";





    
    public EthereumChatAccountModule(@NonNull ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @NonNull
    @Override
    public String getName() {
        return "EthereumChatAccountModule";
    }

    @ReactMethod
    public void createAccount( String name,String password,Callback errorCallback,Callback successCallback){
        try{
            WhisperClient whisperClient = ClientHolder.getWhisperClient();

            Context context = Geth.newContext();

            String keyPair = whisperClient.newKeyPair(context);

            //@TODO
            // Save account in Database with name and password


            SharedPreferences sharedPreferences = getCurrentActivity().getPreferences(android.content.Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("name",name);
            editor.putString("password",password);
            editor.putString("key_pair",keyPair);

            editor.commit();


            successCallback.invoke(keyPair);


        }
        catch (Exception e){
            e.printStackTrace();
            errorCallback.invoke(e.getMessage());
        }

    }

    @ReactMethod
    public void checkAccountCreated(Callback notCreated,Callback created){
        SharedPreferences sharedPreferences = getCurrentActivity().getPreferences(   android.content.Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name","name");
            if(name.equals("name")){
                notCreated.invoke();
            }
            else{
                created.invoke();
            }


    }



}
