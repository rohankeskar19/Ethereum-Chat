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
<<<<<<< HEAD
                whisperClient = Geth.newWhisperClient("ws://13.232.144.149:8546");
=======
                whisperClient = Geth.newWhisperClient("ws://10.0.2.2:8546");
>>>>>>> 541fc7c6504c4cd98c24495c03d908fcbef17ac2
                return whisperClient;
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return whisperClient;
    }








}
