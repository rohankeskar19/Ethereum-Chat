package com.ethereumchat.Modules;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.ethereumchat.Database.ChatDBHelper;

import com.ethereumchat.Helpers.CompressionHandler;
import com.ethereumchat.Helpers.EthereumWebSocketListener;
import com.ethereumchat.Helpers.RequestTask;
import com.ethereumchat.Helpers.WhisperAsyncRequestHandler;
import com.ethereumchat.Helpers.WhisperHelper;
import com.ethereumchat.Models.Contact;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;


import org.json.JSONObject;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;


public class EthereumChatAccountModule extends ReactContextBaseJavaModule implements RequestTask {

    private static final String TAG = "EthereumChatAccountModu";

    @Override
    public void onResponse(JSONObject response, String method,JSONObject data,Callback callback) {
        try{
            switch (method){
                case "shh_newKeyPair":
                    String privateKeyID = response.getString("result");
                    SharedPreferences sharedPreferences = getCurrentActivity().getPreferences(android.content.Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPreferences.edit();


                    editor.putString("name",data.getString("name"));
                    editor.putString("password",data.getString("password"));
                    editor.putString("key_pair",privateKeyID);



                    editor.commit();
                    callback.invoke(privateKeyID);
                    establishConnection(privateKeyID);
                    String cmd = "{\"jsonrpc\":\"2.0\",\"method\":\"shh_getPublicKey\",\"params\":[\"" + privateKeyID + "\"],\"id\":1}";
                    JSONObject request = new JSONObject(cmd);
                    new WhisperAsyncRequestHandler(this,null,null).execute(request,null,null);
                    break;
                case "shh_getPublicKey":
                    SharedPreferences sharedPreferences1 = getCurrentActivity().getPreferences(android.content.Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                    String publicKey = response.getString("result");
                    editor1.putString("public_key",publicKey);

                    editor1.commit();
                    break;
                default:
                    break;
            }

            Log.d(TAG, "onResponse: " + response.toString());


        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public void establishConnection(String privateKeyID){
        try{
            Log.d(TAG, "establishConnection: " + privateKeyID);

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(
                    "ws://" + WhisperHelper.ip + ":" + WhisperHelper.wsPort
            ).build();

            EthereumWebSocketListener ethereumWebSocketListener = new EthereumWebSocketListener();
            WebSocket ws = client.newWebSocket(request,ethereumWebSocketListener);
            JSONObject subscribeRequest = new JSONObject("{\"jsonrpc\":\"2.0\",\"method\":\"shh_subscribe\",\"params\":[\"messages\", { \"privateKeyID\": \"" + privateKeyID + "\", \"pow\": 12.3 }],\"id\":1}");
            ws.send(subscribeRequest.toString());

            client.dispatcher().executorService().shutdown();
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

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

            String cmd = "{\"jsonrpc\":\"2.0\",\"method\":\"shh_newKeyPair\",\"id\":1}";

            JSONObject request = new JSONObject(cmd);
            JSONObject data = new JSONObject();
            data.put("name",name);
            data.put("password",password);

            new WhisperAsyncRequestHandler(this,successCallback,data).execute(request,null,null);






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
        String publicKey = sharedPreferences.getString("public_key","nodata");

        if(name.equals("nodata")|| publicKey.equals("nodata")){
            err.invoke("error occured");
        }
        else{
            try{
                JSONObject qrCodeData = new JSONObject();

                qrCodeData.put("name",name);
                qrCodeData.put("public_key",publicKey);
//                qrCodeData.put("profile_image",finalImage);


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

    @ReactMethod 
    public void getPassword(Callback err,Callback success){
        SharedPreferences sharedPreferences = getCurrentActivity().getPreferences(android.content.Context.MODE_PRIVATE);
        String password = sharedPreferences.getString("password","nodata");

        if(password.equals("nodata")){
            err.invoke("error occured");
        }
        else{
            try{
                JSONObject accountData = new JSONObject();

                accountData.put("password",password);


                success.invoke(accountData.toString());
            }
            catch (Exception e){
                e.printStackTrace();
                err.invoke("error occured");
            }

        }

    }

    @ReactMethod
    public void changePassword(String newPassword,Callback err,Callback success){
        try{
            JSONObject accountData = new JSONObject(newPassword);

            String password = accountData.getString("password");

            SharedPreferences sharedPreferences = getCurrentActivity().getPreferences(android.content.Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("password",password);
            editor.commit();
            success.invoke("updated_profile");

        }
        catch (Exception e){
            e.printStackTrace();
            err.invoke("error occured while updating password");
        }
    }

}
