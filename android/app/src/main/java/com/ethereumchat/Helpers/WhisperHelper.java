package com.ethereumchat.Helpers;

import android.util.Log;

import org.ethereum.geth.Context;
import org.ethereum.geth.Geth;
import org.ethereum.geth.WhisperClient;

import java.nio.charset.StandardCharsets;

public class WhisperHelper {

    private static final String TAG = "WhisperHelper";
    public static String getPublicKey(String keyPair){
        try{
            Log.d(TAG, "getPublicKey: " + keyPair);
            Context context = Geth.newContext();

            WhisperClient whisperClient = ClientHolder.getWhisperClient();


            byte[] key = whisperClient.getPublicKey(context,keyPair);

            String publicKey = new String(key, "UTF-8");
            Log.d(TAG, "getPublicKey: " + publicKey);
            return publicKey;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "error";

    }


}
