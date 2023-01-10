package com.example.login_app;


import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class DBConnection {

    public JSONObject makeHttpRequest(String user_name, String password, String email) {
        JSONObject jObj = null;
        String url1 = "http://192.168.43.111/login-server/index.php";
        try {
            URL url = new URL(url1);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data;

            if(user_name.length() != 0 && email.length() == 0 && password.length() == 0){
                Log.e("TAG", " 41  ");
                post_data = URLEncoder.encode("msg","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8");
            }else if (email.length() <= 0){
                //Log.e("TAG", " makeHttpRequest:no email ");
                post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
            }else{
                post_data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"
                        +URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8");
            }
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            StringBuilder sb = new StringBuilder();
            String line="";
            while((line = bufferedReader.readLine())!= null) {
                sb.append(line);
            }
            Log.e("TAG", " 57  "+sb.toString());
            jObj = new JSONObject(sb.toString());
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return jObj;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jObj;
    }
}


