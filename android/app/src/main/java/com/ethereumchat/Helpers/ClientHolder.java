package com.ethereumchat.Helpers;

import android.util.Log;

import geth.Geth;
import geth.WhisperClient;


public class ClientHolder  {

    private static WhisperClient whisperClient;
    private static final String TAG = "ClientHolder";

    public static WhisperClient getWhisperClient(){
        if(whisperClient == null){
            try{

                whisperClient = Geth.newWhisperClient("ws://13.235.80.102:8546");

                return whisperClient;
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return whisperClient;
    }








}
