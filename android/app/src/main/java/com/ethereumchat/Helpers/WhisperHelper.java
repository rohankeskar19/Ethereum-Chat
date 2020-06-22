package com.ethereumchat.Helpers;

import android.content.SharedPreferences;
import android.util.Log;



import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class WhisperHelper {


    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();
    private static final int MASK_BYTE_SIZE = 0xFF;
    private static final int MASK_SECOND_TUPLE = 0x0F;
    private static final int SHIFT_FIRST_TUPLE = 4;

    public static final String ip = "13.233.157.232";
    public static final String rpcPort = "8545";
    public static final String wsPort = "8546";


    private static final String TAG = "WhisperHelper";


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

    public static String toHex(String str) {
        StringBuffer sb = new StringBuffer();
        //Converting string to character array
        char ch[] = str.toCharArray();
        for(int i = 0; i < ch.length; i++) {
            String hexString = Integer.toHexString(ch[i]);
            sb.append(hexString);
        }
        String result = sb.toString();
        return result;
    }


}
