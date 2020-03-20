package com.ethereumchat.Helpers;

import org.ethereum.geth.Geth;
import org.ethereum.geth.WhisperClient;

public class ClientHolder  {

    static WhisperClient whisperClient;


    public static WhisperClient getWhisperClient(){
        if(whisperClient == null){
            try{
                whisperClient = Geth.newWhisperClient("ws://10.0.2.2:8546");
                return whisperClient;
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            return whisperClient;
        }
        return null;
    }






}
