package com.example.newdemo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.opengl.ETC1;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.newdemo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Profile_User extends AppCompatActivity {
    TextInputEditText edit_phone, edit_name, edit_email, edit_address, edit_pincode;
    Button update_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edit_phone = findViewById(R.id.user_phono_no);
        edit_name = findViewById(R.id.user_name);
        edit_email = findViewById(R.id.user_email);
        edit_address = findViewById(R.id.user_address);
        edit_pincode = findViewById(R.id.user_pincode);

        update_btn = findViewById(R.id.profile_update);

        edit_phone.setText(getIntent().getStringExtra("mobile"));
        edit_name.setText(getIntent().getStringExtra("name"));
        edit_email.setText(getIntent().getStringExtra("email"));
        edit_address.setText(getIntent().getStringExtra("address"));
        edit_pincode.setText(getIntent().getStringExtra("pincode"));


        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserRequiredDetails();

            }
        });

    }

    private void UserRequiredDetails() {

        String mobile=edit_phone.getText().toString().trim();
        String name=edit_name.getText().toString().trim();
        String pincode=edit_pincode.getText().toString().trim();
        String address=edit_address.getText().toString().trim();

        if (mobile.length() != 10){
            edit_phone.setError("enter valid number please");
            edit_phone.requestFocus();
            return;
        }
        if (name.isEmpty()){
            edit_name.setError("name is required");
            edit_name.requestFocus();
            return;
        }
        if (address.isEmpty()){
            edit_address.setError("enter address");
            edit_address.requestFocus();
            return;
        }
        if (pincode.isEmpty()||pincode.length()<6){
            edit_pincode.setError("enter valid pincode");
            edit_pincode.requestFocus();
            return;
        }
        else{

            Map<String, Object> map = new HashMap<>();
            map.put("mobile", edit_phone.getText().toString());
            map.put("name", edit_name.getText().toString());
            map.put("address", edit_address.getText().toString());
            map.put("pincode", edit_pincode.getText().toString());

            FirebaseFirestore.getInstance().collection("USERS")
                    .document("rahul@gmail.com").update(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Profile_User.this, "profile updated", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    });

        }
    }

    @Override
        public boolean onSupportNavigateUp () {
            finish();
            return super.onSupportNavigateUp();
        }
    }
