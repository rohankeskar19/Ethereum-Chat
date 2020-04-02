package com.ethereumchat.Helpers;

import android.util.Log;

import org.ethereum.geth.Context;
import org.ethereum.geth.Geth;
import org.ethereum.geth.WhisperClient;

public class ClientHolder  {

    private static WhisperClient whisperClient;
    private static final String TAG = "ClientHolder";

    public static WhisperClient getWhisperClient(){
        if(whisperClient == null){
            try{
                whisperClient = Geth.newWhisperClient("ws://15.206.88.200:8546");
                return whisperClient;
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return whisperClient;
    }








}
