package com.example.newdemo.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.newdemo.R;
import com.example.newdemo.activity.LoginScreen;
import com.example.newdemo.model.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    TextView textView1, textView2, textView3, textView4;
    Button logout;
    CircleImageView circleImageView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        //final TextView textView = view.findViewById(R.id.tv);
        //textView.setText("profile fragment");
        //FrameLayout frameLayout=view.findViewById(R.id.frame5);
        //frameLayout.setBackgroundColor(getResources().getColor(R.color.yellow));
        textView1 = view.findViewById(R.id.number);
        textView2 = view.findViewById(R.id.adds);
        textView3 = view.findViewById(R.id.pincode);
        textView4 = view.findViewById(R.id.email);

        logout = view.findViewById(R.id.logoutbtn);

        circleImageView = view.findViewById(R.id.circleimage);

        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        editor=preferences.edit();

        FirebaseFirestore.getInstance().collection("USERS")
                .whereEqualTo("email", preferences.getString("email", ""))
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null) {
                            value.getDocuments().get(0).getReference().get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Users user = documentSnapshot.toObject(Users.class);

                                    textView1.setText(user.mobile);
                                    textView2.setText(user.address);
                                    textView3.setText(user.pincode);
                                    textView4.setText(user.email);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }

                            });
                        }
                        if (error != null) {
                            Toast.makeText(getActivity(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                editor.clear();
                editor.commit();
                startActivity(new Intent(getActivity(), LoginScreen.class));
                getActivity().finish();
            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return view;


    }




}
