package com.example.login_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Menu extends AppCompatActivity {

    Button logoutbtn;
    TextView msgfield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        logoutbtn = (Button)findViewById(R.id.logoutbtn);
        msgfield = (TextView)findViewById(R.id.msgfield);

        Intent intent = getIntent();
        String str = intent.getStringExtra("message_key");
        msgfield.setText(str);

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogpage();
            }
        });
    }
    private void openLogpage(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}