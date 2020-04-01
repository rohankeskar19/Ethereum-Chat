package com.ethereumchat.Helpers;

import android.util.Log;

import org.ethereum.geth.Context;
import org.ethereum.geth.Geth;
import org.ethereum.geth.WhisperClient;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WhisperHelper {


    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();
    private static final int MASK_BYTE_SIZE = 0xFF;
    private static final int MASK_SECOND_TUPLE = 0x0F;
    private static final int SHIFT_FIRST_TUPLE = 4;

    private static final String TAG = "WhisperHelper";
    public static String getPublicKey(String keyPair){
        try{
            Log.d(TAG, "getPublicKey: " + keyPair);
            Context context = Geth.newContext();

            WhisperClient whisperClient = ClientHolder.getWhisperClient();

            byte[] key = whisperClient.getPublicKey(context,keyPair);
            String publicKey = "";


            publicKey = bytesToHexString(key);

            String[] temp = publicKey.split("");

            List<String> temp1 = Arrays.asList(temp);

            ArrayList<String> temp2 = new ArrayList<>(temp1);

            temp2.add(2,"x");
            publicKey = "";
            for(String s : temp2){
                publicKey += s;
            }



            Log.d(TAG, "getPublicKey: " + publicKey);
            return publicKey;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "error";

    }

    public static String bytesToHexString(final byte... bytes) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }
        final char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & MASK_BYTE_SIZE;
            hexChars[j * 2] = HEX_ARRAY[v >>> SHIFT_FIRST_TUPLE];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & MASK_SECOND_TUPLE];
        }
        return new String(hexChars);
    }
}
