package com.example.newdemo.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.newdemo.R;
import com.example.newdemo.fragment.CartFragment;
import com.example.newdemo.fragment.FavoriteFragment;
import com.example.newdemo.fragment.HistoryFragment;
import com.example.newdemo.fragment.HomeFragment;
import com.example.newdemo.fragment.ProfileFragment;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    BadgeDrawable badgeDrawable;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        getSupportActionBar().setElevation(0);


        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.action_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();


        getCartCount();
        getFavoriteCount();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_home) {
//                    getSupportActionBar().setElevation(0);
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
                    return true;
                }
                if (item.getItemId() == R.id.action_favorite) {
                    getSupportActionBar().setElevation(0);
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new FavoriteFragment()).commit();
                    return true;
                }
                if (item.getItemId() == R.id.action_history) {
                    getSupportActionBar().setElevation(0);
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new HistoryFragment()).commit();
                    return true;
                }
                if (item.getItemId() == R.id.action_cart) {
//                    getSupportActionBar().setElevation(0);
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new CartFragment()).commit();
                    return true;
                }
                if (item.getItemId() == R.id.action_profile) {
                    getSupportActionBar().setElevation(0);
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProfileFragment()).commit();
                    return true;
                }
                return false;
            }
        });

    }

    private void getFavoriteCount() {
        FirebaseFirestore.getInstance().collection("USERS").document("rahul@gmail.com")
                .collection("FAVORITE")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value.size()==0){
                            bottomNavigationView.removeBadge(R.id.action_favorite);
                            if (value==null){
                                badgeDrawable.notify();
                            }
                        }else{
                            badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.action_favorite);
                            badgeDrawable.setNumber(value.size());
                            badgeDrawable.setBackgroundColor(getResources().getColor(R.color.blue));
                            badgeDrawable.setBadgeTextColor(getResources().getColor(R.color.white));
                        }
                    }
                });
    }


    private void getCartCount() {
//        badgeDrawable.setBackgroundColor(getResources().getColor(R.color.blue));
        FirebaseFirestore.getInstance().collection("USERS").document("rahul@gmail.com")
                .collection("CART")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        badgeDrawable.setNumber(value.getDocuments().size());
                        if (value.size()==0){
                            bottomNavigationView.removeBadge(R.id.action_cart);
                            if (value==null){
                                badgeDrawable.notify();
                            }
                        }else{
                            badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.action_cart);
                            badgeDrawable.setNumber(value.size());
                            badgeDrawable.setBackgroundColor(getResources().getColor(R.color.blue));
                            badgeDrawable.setBadgeTextColor(getResources().getColor(R.color.white));
                        }
                    }
                });
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                HomeActivity.super.onBackPressed();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button nbutton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setBackgroundColor(Color.TRANSPARENT);
        nbutton.setTextColor(getResources().getColor(R.color.blue));
        Button pbutton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setBackgroundColor(Color.TRANSPARENT);
        pbutton.setTextColor(getResources().getColor(R.color.blue));
    }

}

