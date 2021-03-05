package com.example.newdemo.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newdemo.R;
import com.example.newdemo.model.OrderModel;
import com.example.newdemo.model.ProductItem;
import com.example.newdemo.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class PlaceOrder extends AppCompatActivity {
    TextView addtotal, addDate;
    EditText user_address, user_name, user_mobile_no;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    RadioButton rb_cod, rb_ONLINE;
    RadioGroup radioGroup_paymnt;
    OrderModel orderModel;
    Button paymentbtn;
    private FirebaseAuth mAuth;
    String radio_payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        addtotal = findViewById(R.id.add_Prdct_price);
        addDate = findViewById(R.id.date_time);
        user_address = findViewById(R.id.user_address);
        user_mobile_no = findViewById(R.id.user_mobile);
        user_name = findViewById(R.id.user_name);
        paymentbtn = findViewById(R.id.paymentbtn);
        radioGroup_paymnt = findViewById(R.id.radiogrp);

        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        OrderModel orderModel = (OrderModel) bundle.getSerializable("order");
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy  hh:mm a");
        String date = format.format(orderModel.getTimestamp());
        addDate.setText("Date : " + date);
//        addtotal.setText("₹." + orderModel.getOrderTotal());

        addtotal.setText("₹." + getIntent().getStringExtra("total"));

        String UserAddress = intent.getStringExtra("user_address");
        String UserName = intent.getStringExtra("user_name");
        String UserMobile = intent.getStringExtra("user_mobile");

        user_address.setText(UserAddress,TextView.BufferType.EDITABLE);
        user_name.setText(UserName);
        user_mobile_no.setText(UserMobile);


        paymentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb_cod = findViewById(radioGroup_paymnt.getCheckedRadioButtonId());
                String paymentMethod = rb_cod.getText().toString();
                checkUserDetails();
            }
        });
    }


    private void checkUserDetails() {
        String name = user_name.getText().toString();
        String mobile = user_mobile_no.getText().toString().trim();
        String address = user_address.getText().toString().trim();


        if (mobile.isEmpty() || mobile.length() < 10) {
            user_mobile_no.setError("enter valid number please");
            user_mobile_no.requestFocus();
            return;
        }
        if (name.isEmpty()) {
            user_name.setError("name is required");
            user_name.requestFocus();
            return;
        }
        if (address.isEmpty()) {
            user_address.setError("address is required");
            user_address.requestFocus();
        }
    }
}