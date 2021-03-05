package com.example.newdemo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newdemo.R;
import com.example.newdemo.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextInputEditText mobiletext,UserName,emailtext,passtext,confirmtext,addresstext,pincodetext;
    CheckBox checkBox1;
    Button accountbutton;
    TextView textView1,textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mobiletext=findViewById(R.id.textmobile);
        UserName=findViewById(R.id.textname);
        emailtext=findViewById(R.id.textemail);
        passtext=findViewById(R.id.textpassword);
        confirmtext=findViewById(R.id.textconpass);
        addresstext=findViewById(R.id.textaddress);
        pincodetext=findViewById(R.id.textpincode);

        checkBox1=findViewById(R.id.check);

        textView1=findViewById(R.id.Acc);
        textView2=findViewById(R.id.clicklog);

        mAuth = FirebaseAuth.getInstance();

        accountbutton = findViewById(R.id.createacc);
        accountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.createacc:
                        registerUser();
                        break;
                }
            }
        });


    }
    private void registerUser() {

        String mobile=mobiletext.getText().toString().trim();
        String name=UserName.getText().toString().trim();
        String email=emailtext.getText().toString().trim();
        String password=passtext.getText().toString().trim();
        String confirmation=confirmtext.getText().toString().trim();
        String address=addresstext.getText().toString().trim();
        String checked;

        if (mobile.isEmpty()||mobile.length()<10){
            mobiletext.setError("enter valid number please", Drawable.createFromPath("ic_phone.xml"));
            mobiletext.requestFocus();
            return;
        }
        if (name.isEmpty()){
            UserName.setError("name is required");
            UserName.requestFocus();
            return;
        }
        if (email.isEmpty()){
            emailtext.setError("Email is required");
            emailtext.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailtext.setError("Please provide valid email");
            emailtext.requestFocus();
            return;
        }

        if (password.isEmpty()){
            passtext.setError("Password is required");
            passtext.requestFocus();
            return;
        }
        if (password.length() < 6){
            passtext.setError("Should be 6 Characters is required");
            passtext.requestFocus();
            return;
        }
        if (!confirmation.equals(password)){
            confirmtext.setError("please check ur password");
            confirmtext.requestFocus();
            return;
        }
        if (address.isEmpty()){
            addresstext.setError("enter address");
            addresstext.requestFocus();
            return;
        }
        if (!checkBox1.isChecked()){
            Toast.makeText(this, "Please Accept terms & conditions", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Users user=new Users(mobiletext.getText().toString(),
                                    UserName.getText().toString(),
                                    emailtext.getText().toString(),
                                    passtext.getText().toString(),
                                    addresstext.getText().toString(),
                                    pincodetext.getText().toString());

                            FirebaseFirestore.getInstance().collection("USERS").document(user.email).set(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){

                                                Intent intent=new Intent(MainActivity.this, LoginScreen.class);
                                                startActivity(intent);
                                            }
                                            else
                                                Toast.makeText(MainActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }else{
                            Toast.makeText(MainActivity.this,"failed to register"+task.getException(),Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }

}
