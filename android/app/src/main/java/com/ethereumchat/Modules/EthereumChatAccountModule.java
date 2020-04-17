package com.ethereumchat.Modules;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.ethereumchat.Database.ChatDBHelper;
import com.ethereumchat.Helpers.ClientHolder;
import com.ethereumchat.Helpers.CompressionHandler;
import com.ethereumchat.Helpers.WhisperHelper;
import com.ethereumchat.Models.Contact;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import org.ethereum.geth.Context;
import org.ethereum.geth.Criteria;
import org.ethereum.geth.Geth;
import org.ethereum.geth.Message;
import org.ethereum.geth.NewMessage;
import org.ethereum.geth.NewMessageHandler;
import org.ethereum.geth.WhisperClient;
import org.json.JSONObject;


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

    @ReactMethod
    public void saveImage(String imageString,Callback err,Callback success){
        try{
            SharedPreferences sharedPreferences = getCurrentActivity().getPreferences(android.content.Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("profile_image",imageString);
            editor.commit();
            success.invoke("saved_image");
        }
        catch (Exception e){
            e.printStackTrace();
            err.invoke("error_occurred");
        }

    }

    @ReactMethod
    public void checkImageSet(Callback err,Callback success){
        SharedPreferences sharedPreferences = getCurrentActivity().getPreferences(android.content.Context.MODE_PRIVATE);
        String image = sharedPreferences.getString("profile_image","not_set");

        if(image.equals("not_set")){
            err.invoke("image_not_set");
        }
        else{
            success.invoke("image_set");
        }
    }

    @ReactMethod
    public void getQRCodeData(Callback err, Callback success){
        SharedPreferences sharedPreferences = getCurrentActivity().getPreferences(android.content.Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name","nodata");
        String keyPair = sharedPreferences.getString("key_pair","nodata");

        if(name.equals("nodata")|| keyPair.equals("nodata")){
            err.invoke("error occured");
        }
        else{
            try{
                JSONObject qrCodeData = new JSONObject();

                qrCodeData.put("name",name);

//                qrCodeData.put("profile_image",finalImage);
                String publicKey = WhisperHelper.getPublicKey(keyPair);
                Log.d(TAG, "getQRCodeData: " + publicKey);
                qrCodeData.put("public_key",publicKey);

                success.invoke(qrCodeData.toString());
            }
            catch (Exception e){
                e.printStackTrace();
                err.invoke("error occured");
            }

        }
    }

    @ReactMethod void getAccountData(Callback err,Callback success){
        SharedPreferences sharedPreferences = getCurrentActivity().getPreferences(android.content.Context.MODE_PRIVATE);
        String name = sharedPreferences.getString("name","nodata");
        String image = sharedPreferences.getString("profile_image","nodata");

        if(name.equals("nodata") || image.equals("nodata")){
            err.invoke("error occured");
        }
        else{
            try{
                JSONObject accountData = new JSONObject();

                accountData.put("name",name);
                accountData.put("profile_image",image);


                success.invoke(accountData.toString());
            }
            catch (Exception e){
                e.printStackTrace();
                err.invoke("error occured");
            }

        }

    }


    @ReactMethod
    public void saveAccount(String name,String publicKey,String image,Callback err,Callback success){
        Contact contact = new Contact(name,publicKey,image);

        ChatDBHelper chatDBHelper = new ChatDBHelper(getReactApplicationContext());

        if(chatDBHelper.addContact(contact)){
            success.invoke("saved contact");
        }
        else{
            err.invoke("error");
        }


    }

    @ReactMethod
    public void changeAccountData(String newAccountData,Callback err,Callback success){
        try{
            JSONObject accountData = new JSONObject(newAccountData);

            String name = accountData.getString("name");
            String image = accountData.getString("profile_image");

            SharedPreferences sharedPreferences = getCurrentActivity().getPreferences(android.content.Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name",name);
            editor.putString("profile_image",image);
            editor.commit();
            success.invoke("updated_profile");

        }
        catch (Exception e){
            e.printStackTrace();
            err.invoke("error occured while updating profile");
        }
    }


}
