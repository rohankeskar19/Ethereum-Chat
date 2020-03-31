package com.ethereumchat.Helpers;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2Utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class CompressionHandler {

    public static String compressProfile(String profileImage){
        try{
            String compressedImage = "";
            InputStream inputStream = new ByteArrayInputStream(profileImage.getBytes());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();




            BZip2CompressorOutputStream bZip2CompressorOutputStream = new BZip2CompressorOutputStream(byteArrayOutputStream);

            final byte[] buffer = new byte[2000];

            int n = 0;
            while(-1 != (n = inputStream.read(buffer))){
                bZip2CompressorOutputStream.write(buffer,0,n);
            }
            bZip2CompressorOutputStream.close();
            inputStream.close();
            compressedImage = new String(buffer);

            System.out.println("Length of original image "  + profileImage.length());
            System.out.println("Length after encrypting " + compressedImage.length());
            return compressedImage;


        }
        catch (Exception e){
            e.printStackTrace();
        }



        return "error";
    }


}
