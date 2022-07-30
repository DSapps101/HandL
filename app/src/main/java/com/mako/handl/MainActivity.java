package com.mako.handl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnView;
    String strUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnView = findViewById(R.id.btnView);

        strUser = getIntent().getStringExtra("USERMail");

        initiateListners();

    }

    public void initiateListners(){

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openItemActivity();
            }
        });

    }

    public void openItemActivity(){
        Intent intent = new Intent(this, ItemActivity.class);
        intent.putExtra("strUser", strUser);
        startActivity(intent);
    }
}