package com.ethereumchat;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ethereumchat.Helpers.ClientHolder;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.uimanager.IllegalViewOperationException;

import org.ethereum.geth.Context;
import org.ethereum.geth.Geth;
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
    public void createAccount(Callback errorCallback,Callback successCallback){
        try{
            WhisperClient whisperClient = ClientHolder.getWhisperClient();

            Context context = Geth.newContext();

            String keyPair = whisperClient.newKeyPair(context);

            successCallback.invoke(keyPair);

        }
        catch (Exception e){
            errorCallback.invoke(e.getMessage());
        }

    }

    @ReactMethod
    public void saveAccountAndLogin(){

    }

}
