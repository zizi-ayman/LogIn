package com.example.login_app;


import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class DBConnection {

    InputStream is = null;
    JSONObject jObj = null;
    String error = "";
    StringBuilder sb = null;
    String TAG = "LOG";

    public JSONObject makeHttpRequest(String url, ArrayList params) {

        try {
            //An HttpClient can be used to send requests and retrieve their responses
            HttpClient httpClient = new DefaultHttpClient();
            //The HttpPost represents the HTTP POST request
            HttpPost httpPost = new HttpPost(url);
            //The UrlEncodedFormEntity is an entity composed of a list of url-encoded pairs
            httpPost.setEntity(new UrlEncodedFormEntity(params));
            try {
                Log.e(TAG, " 39 username & password: " +convertStreamToString(httpPost.getEntity().getContent()));
                Log.e(TAG," 40 URL: "+httpPost.getURI().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            //HTTP Response sent by a server to the client. The response is used to provide the client with the resource it requested
            HttpResponse httpResponse = httpClient.execute(httpPost);
            Log.e(TAG," 46 response status: "+httpResponse.getStatusLine().getStatusCode());
            error= String.valueOf(httpResponse.getStatusLine().getStatusCode());
            //An HTTP entity is the majority of an HTTP request or response, consisting of some of the headers and the body, if present
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            Log.e(TAG, "result in 68: " + sb.toString());
            jObj = new JSONObject(sb.toString());
        } catch (Exception e) {
            Log.e(TAG, "Error converting result in 71: " + e.toString());
        }
        return jObj;
    }

    private String convertStreamToString(InputStream is) throws Exception {
        //used to read the text from a character-based input stream
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        //StringBuilder in Java represents a mutable sequence of characters
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        is.close();
        return sb.toString();
    }
}