package com.ethereumchat.Helpers;

import android.content.SharedPreferences;
import android.util.Log;



import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import geth.Context;
import geth.Geth;
import geth.WhisperClient;

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

            temp2.add(2,"x0");
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

    public static String getPrivateKey(String keyPair){
        try{
            Log.d(TAG, "getPrivateKey: " + keyPair);
            Context context = Geth.newContext();

            WhisperClient whisperClient = ClientHolder.getWhisperClient();

            byte[] key = whisperClient.getPrivateKey(context,keyPair);
            String privateKey = "";


            privateKey = bytesToHexString(key);

            String[] temp = privateKey.split("");

            List<String> temp1 = Arrays.asList(temp);

            ArrayList<String> temp2 = new ArrayList<>(temp1);

            temp2.add(2,"x0");
            privateKey = "";
            for(String s : temp2){
                privateKey += s;
            }



            Log.d(TAG, "getPrivateKey: " + privateKey);
            return privateKey;
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

    public static byte[] hexStringToByteArray(String s) {
        Log.d(TAG, "hexStringToByteArray: " + s);
        Log.d(TAG, "hexStringToByteArray: " + s.length());
        s = s.substring(2,s.length());
        Log.d(TAG, "hexStringToByteArray: " + s);
        int len = s.length();
        Log.d(TAG, "hexStringToByteArray: " + s.length());
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static byte[] getByteArray(String str) {
        if (!"0x".equals(str.substring(0,2))) {
            return null; // or throw some exception
        } else {
            String tmp = str.substring(2, str.length()-2);
            int bytes = tmp.length() / 2;
            byte[] array = new byte[bytes];

            for (int i = 0; i < bytes; i++) {
                byte b = (byte) ((Character.digit(tmp.charAt(i*2), 16) << 4)
                        + Character.digit(tmp.charAt(i*2+1), 16));
                array[i] = b;
            }

            return array;
        }
    }
}
