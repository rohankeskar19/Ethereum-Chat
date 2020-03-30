package com.ethereumchat;

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
import org.json.JSONArray;
import org.json.JSONObject;


public class EthereumChatMessagingModule extends ReactContextBaseJavaModule {

    private static final String TAG = "EthereumChatMessagingModule";


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
        JSONArray Contacts = new JSONArray();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + ChatContract.ContactEntry.TABLE_NAME + " WHERE " + ChatContract.ContactEntry.COLUMN_NAME + "LIKE '" + name + "'";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do{
                JSONObject Contact = new JSONObject();
                Contact.putString("name",cursor.getString(0));
                Contact.putString("public_key",cursor.getString(1));
                Contact.putString("profile_in_string",cursor.getString(2));
                Contacts.add(Contact);
            }while (cursor.moveToNext());
            success.invoke(Contacts.toString());
        }
        else{
            err.invoke();
        }
        cursor.close();


    }
}