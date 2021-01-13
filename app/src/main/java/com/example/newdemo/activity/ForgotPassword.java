package com.example.newdemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.newdemo.R;
import com.google.android.material.textfield.TextInputLayout;

public class ForgotPassword extends AppCompatActivity {
    TextInputLayout textmobile,textpassword;
    Button submitting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        textmobile=findViewById(R.id.layoutmobile);
        textpassword=findViewById(R.id.layoutpassword);

        submitting=findViewById(R.id.submit);

        submitting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ForgotPassword.this, LoginScreen.class);
                startActivity(intent);
            }
        });
    }
}
