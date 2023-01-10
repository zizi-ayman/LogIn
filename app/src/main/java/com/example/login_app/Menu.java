package com.example.login_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class Menu extends AppCompatActivity {

    Button logoutbtn,sendnote;
    TextView msgfield, errorfield;
    EditText sendmsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        logoutbtn = (Button)findViewById(R.id.logoutbtn);
        sendnote = (Button)findViewById(R.id.sendnote);
        msgfield = (TextView)findViewById(R.id.msgfield);
        errorfield = (TextView)findViewById(R.id.errorfield);
        sendmsg = (EditText)findViewById(R.id.sendmsgfield);


        Intent intent = getIntent();
        String str = intent.getStringExtra("message_key");
        msgfield.setText(str);

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogpage();
            }
        });
        sendnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = sendmsg.getText().toString();
                if(!msg.equals("")) {
                    errorfield.setText("");
                    AttemptLogin attemptLogin = new AttemptLogin();
                    attemptLogin.execute(msg);
                }else {
                    errorfield.setText("Fill Please the Field!");
                }
            }
        });
    }
    private void openLogpage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private class AttemptLogin extends AsyncTask<String, String, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            String email = "";
            String password = "";
            String msg= args[0];

            DBConnection dbconnection = new DBConnection();
            JSONObject json = dbconnection.makeHttpRequest(msg,password,email);

            return json;
        }

        protected void onPostExecute(JSONObject result) {}
    }
}