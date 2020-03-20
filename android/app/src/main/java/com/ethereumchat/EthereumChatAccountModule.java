package com.ethereumchat;

import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.uimanager.IllegalViewOperationException;
import statusgo.Statusgo;

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
            String accountData = Statusgo.startOnboarding(1,12);
            Log.d(TAG, "createAccount: " + accountData);
            successCallback.invoke(accountData);
        }
        catch (IllegalViewOperationException e){
           errorCallback.invoke(e.getMessage());
        }
    }

    @ReactMethod
    public void saveAccountAndLogin(String accountData,String password,String nodeConfig,String multiAccounts){
        Log.d(TAG, "saveAccountAndLogin: called with " + accountData + "\n" + password + "\n" + nodeConfig + "\n" + multiAccounts );



        String result = Statusgo.saveAccountAndLogin(accountData, password, "{}", nodeConfig, multiAccounts);
        if (result.startsWith("{\"error\":\"\"")) {
            Log.d(TAG, "saveAccountAndLogin result12: " + result);

            String adminInfo = Statusgo.callPrivateRPC("admin_peers");

            Log.d(TAG, "Geth node started" + adminInfo);
            } else {
            Log.e(TAG, "saveAccountAndLogin failed: " + result);
        }
    }

}
