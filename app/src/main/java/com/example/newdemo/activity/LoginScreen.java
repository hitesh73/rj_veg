package com.example.newdemo.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

public class LoginScreen extends AppCompatActivity {

    ImageView imgview;
    TextInputEditText mobiletext, passwordtext;
    TextView forgotpassword, clicktext, helptext;
    Button loginbutton, facebookbutton, googlebutton;
    private FirebaseAuth mAuth;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        imgview = findViewById(R.id.imageview);

        mobiletext = findViewById(R.id.textmobile);
        passwordtext = findViewById(R.id.textpassword);

        forgotpassword = findViewById(R.id.forgotpassword);
        clicktext = findViewById(R.id.textcheck);
        helptext = findViewById(R.id.help_it);

        loginbutton = findViewById(R.id.loginbutton);
        facebookbutton = findViewById(R.id.facebook);
        googlebutton = findViewById(R.id.google);

        preferences = getSharedPreferences("user", MODE_PRIVATE);
        mAuth = FirebaseAuth.getInstance();

        clicktext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.textcheck:
                        startActivity(new Intent(LoginScreen.this, MainActivity.class));
                        break;
                }
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this, ForgotPassword.class);
                startActivity(intent);
            }
        });

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.loginbutton:
                        loginUser();
                        break;
                }

            }

        });

    }

    private void loginUser() {
        String email = mobiletext.getText().toString();
        String password = passwordtext.getText().toString().trim();

        if (email.isEmpty()) {
            mobiletext.setError("number must needed");
            mobiletext.requestApplyInsets();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mobiletext.setError("valid number enter please");
            mobiletext.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passwordtext.setError("Password is required");
            passwordtext.requestFocus();
            return;
        }
        if (password.length() < 6) {
            passwordtext.setError("Should be 6 Characters is required");
            passwordtext.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(mobiletext.getText().toString(), passwordtext.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginScreen.this, "Successfully SignIn", Toast.LENGTH_LONG).show();

                            FirebaseFirestore.getInstance().collection("USERS")
                                    .whereEqualTo("email", mobiletext.getText().toString())
                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                            if (error != null) {
                                                Toast.makeText(LoginScreen.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                            if (value != null && !value.isEmpty()) {
                                                value.getDocuments().get(0).getReference().get()
                                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onSuccess(DocumentSnapshot snapshot) {

                                                                editor = preferences.edit();
                                                                editor.putString("email", snapshot.get("email").toString());
                                                                editor.commit();

                                                                Intent intent = new Intent(LoginScreen.this, HomeActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(LoginScreen.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            }
                                        }
                                    });

                        } else {

                            Toast.makeText(LoginScreen.this, "Authentication failed."+task.getException(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }
}
