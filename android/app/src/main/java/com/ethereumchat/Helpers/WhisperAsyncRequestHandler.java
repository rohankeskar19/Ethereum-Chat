package com.ethereumchat.Helpers;

import android.os.AsyncTask;
import android.util.Log;

import com.facebook.react.bridge.Callback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WhisperAsyncRequestHandler extends AsyncTask<JSONObject, JSONObject, JSONObject> {

    private static final String TAG = "WhisperAsyncRequestHand";
    private RequestTask requestTask;
    private Callback callback;
    private JSONObject data;
    String method;

    public WhisperAsyncRequestHandler(RequestTask requestTask, Callback callback,JSONObject data){
        this.requestTask = requestTask;
        this.callback = callback;
        this.data = data;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        Log.d(TAG, "onPostExecute: " + jsonObject.toString());
        if (requestTask != null){
            requestTask.onResponse(jsonObject,method,data,callback);
        }

    }




    @Override
    protected JSONObject doInBackground(JSONObject... jsonObjects) {
        try{
            JSONObject response = sendEthereumRequest(jsonObjects[0]);
            method = jsonObjects[0].getString("method");
            return response;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private void writeByteArrayBody(HttpURLConnection httpURLConnection, byte[] postQuery) throws IOException {
        OutputStream os = httpURLConnection.getOutputStream();
        BufferedOutputStream writer = new BufferedOutputStream(os);
        writer.write(postQuery);
        writer.flush();
        writer.close();
        os.close();
    }

    private JSONObject sendEthereumRequest(JSONObject ethereumJsonRpcObject)
            throws MalformedURLException, IOException, JSONException {

        HttpURLConnection httpConnection = getHttpConnection();

        httpConnection.setDoOutput(true);
        httpConnection.setDoInput(true);

        httpConnection.setRequestMethod("POST");

        byte[] bodyAsByteArray = ethereumJsonRpcObject.toString().getBytes();
        httpConnection.addRequestProperty("Content-Length", Integer.toString(bodyAsByteArray.length));
        writeByteArrayBody(httpConnection, bodyAsByteArray);

        httpConnection.connect();

        // int responseCode = httpConnection.getResponseCode();

        return new JSONObject(readStringResponse(httpConnection));

    }

    private String readStringResponse(HttpURLConnection httpURLConnection) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        String responseAsString = response.toString();
        return responseAsString;
    }

    private HttpURLConnection getHttpConnection() throws MalformedURLException, IOException {

        HttpURLConnection httpConnection = (HttpURLConnection) new URL("http://" + WhisperHelper.ip + ":" + WhisperHelper.rpcPort).openConnection();
        httpConnection.addRequestProperty("Content-Type", "application/json");
        return httpConnection;
    }
}
