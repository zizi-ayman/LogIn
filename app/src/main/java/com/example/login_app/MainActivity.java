package com.example.login_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    EditText passwordfield, usernamefield, emailfield;
    TextView infield, errorfield, answer, question;
    TextInputLayout emailcontainer;
    Button loginbtn;
    String msg ="",batteryinfo="";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            infield=(TextView)findViewById(R.id.in);
            errorfield=(TextView)findViewById(R.id.errorfield);
            answer=(TextView)findViewById(R.id.answer);
            question=(TextView)findViewById(R.id.question);

            usernamefield=(EditText)findViewById(R.id.usernamefield);
            passwordfield=(EditText)findViewById(R.id.passwordfield);
            emailfield=(EditText)findViewById(R.id.emailfield);
            emailcontainer = findViewById(R.id.emailcontainer);

            loginbtn=(Button)findViewById(R.id.loginbtn);


            loginbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    errorfield.setVisibility(View.GONE);
                    if(valid()){
                        if(infield.getText().toString().equals("SignUp")){
                            AttemptLogin attemptLogin = new AttemptLogin();
                            attemptLogin.execute(usernamefield.getText().toString(),passwordfield.getText().toString(),emailfield.getText().toString());
                        }else {
                            AttemptLogin attemptlogin = new AttemptLogin();
                            attemptlogin.execute(usernamefield.getText().toString(),passwordfield.getText().toString(),"");
                        }
                    }else {
                        errorfield.setVisibility(View.VISIBLE);
                        errorfield.setText("All fields are required!");
                    }
                }
            });
            answer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    errorfield.setVisibility(View.GONE);
                    if(infield.getText().toString().equals("Login")) {
                        emailfield.setText(null);
                        emailfield.setVisibility(View.VISIBLE);
                        emailcontainer.setVisibility(View.VISIBLE);
                        infield.setText("SignUp");
                        loginbtn.setText("SignUp");
                        question.setText("Already have an account? ");
                        answer.setText("Login");
                    }else{
                        emailfield.setVisibility(View.GONE);
                        emailcontainer.setVisibility(View.GONE);
                        infield.setText("Login");
                        loginbtn.setText("Login");
                        question.setText("Don't have an account? ");
                        answer.setText("Signup");
                    }
                }
            });
        }
    private class AttemptLogin extends AsyncTask<String, String, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            String email = args[2];
            String password = args[1];
            String username= args[0];

            DBConnection dbconnection = new DBConnection();
            JSONObject json = dbconnection.makeHttpRequest(username,password,email);

            return json;
        }

        protected void onPostExecute(JSONObject result) {
            try {
                if (result != null) {
                    errorfield.setText("exists");
                    msg = result.getString("message");
                    Log.e("TAG", "onPostExecute: 114 "+msg.equals("Error in registering. Probably the username or email already exists"));
                    //Toast.makeText(getApplicationContext(),result.getString("message"),Toast.LENGTH_LONG).show();
                    errorfield.setVisibility(View.VISIBLE);
                    errorfield.setTextColor(Color.parseColor("#ff000d"));
                    Log.e("TAG", "onPostExecute: 119 "+msg.equals("Error in registering. Probably the username or email already exists"));
                    if(msg.equals("Error in registering. Probably the username or email already exists")){
                        Log.e("TAG", "onPostExecute: 120" );
                        errorfield.setText("the username or email already exists");
                    }else if(msg.equals("Error in registering. Email Address is not valid")){
                        errorfield.setText("Email Address is not valid");
                    }
                    else if(msg.equals("Incorrect details")){
                        errorfield.setText("Incorrect details");
                    }else{
                        batteryinfo = result.getString("result");
                        msg = "Hello "+usernamefield.getText().toString()+" "+batteryinfo;
                        openMenu();
                    }
                } else {
                    //msg = result.getString("message");
                    errorfield.setVisibility(View.VISIBLE);
                    errorfield.setText("Unable to retrieve any data from server");
                    //Toast.makeText(getApplicationContext(), "Unable to retrieve any data from server", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    private void openMenu(){
        Intent intent = new Intent(this, Menu.class);
        intent.putExtra("message_key", msg);
        startActivity(intent);
    }
    private boolean valid(){
        boolean v = true;
        if (usernamefield.getText().toString().isEmpty())v= false;
        if (passwordfield.getText().toString().isEmpty())v= false;
        if(infield.getText().toString().equals("SignUp"))if (emailfield.getText().toString().isEmpty())v= false;
        return v;
    }

}