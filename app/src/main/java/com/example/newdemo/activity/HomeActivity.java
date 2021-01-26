package com.example.newdemo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.example.newdemo.R;
import com.example.newdemo.fragment.CartFragment;
import com.example.newdemo.fragment.FavoriteFragment;
import com.example.newdemo.fragment.HistoryFragment;
import com.example.newdemo.fragment.HomeFragment;
import com.example.newdemo.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setElevation(0);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.action_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

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
//                    getSupportActionBar().setElevation(0);
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new FavoriteFragment()).commit();
                    return true;
                }
                if (item.getItemId() == R.id.action_history) {
//                    getSupportActionBar().setElevation(0);
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
//                    getSupportActionBar().setElevation(0);
                    getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blue)));
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProfileFragment()).commit();
                    return true;
                }
                return false;
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

