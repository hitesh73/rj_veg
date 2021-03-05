package com.example.newdemo.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.newdemo.activity.PlaceOrder;
import com.example.newdemo.activity.Profile_User;
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

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    TextView textView1, textView2, textView3, textView4,textView5;
    Button logout;
    CircleImageView circleImageView;
    ImageView editing;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        //final TextView textView = view.findViewById(R.id.tv);
        //textView.setText("profile fragment");
        //FrameLayout frameLayout=view.findViewById(R.id.frame5);
        //frameLayout.setBackgroundColor(getResources().getColor(R.color.yellow));
        textView1 = view.findViewById(R.id.number);
        textView2 = view.findViewById(R.id.adds);
        textView3 = view.findViewById(R.id.pincode);
        textView4 = view.findViewById(R.id.email);
        textView5 = view.findViewById(R.id.username);

        logout = view.findViewById(R.id.logoutbtn);

        circleImageView = view.findViewById(R.id.circleimage);

        editing = view.findViewById(R.id.iv_editing);


        editing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Profile_User.class);
                intent.putExtra("mobile",textView1.getText().toString());
                intent.putExtra("address",textView2.getText().toString());
                intent.putExtra("pincode",textView3.getText().toString());
                intent.putExtra("email",textView4.getText().toString());
                intent.putExtra("name",textView5.getText().toString());
                startActivity(intent);
            }
        });
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
                                    textView5.setText(user.name);
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
