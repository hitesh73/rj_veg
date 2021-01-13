package com.example.newdemo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

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
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.action_home);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.action_home){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();
                    return true;
                }
                if(item.getItemId()==R.id.action_favorite){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,new FavoriteFragment()).commit();
                    return true;
                }
                if(item.getItemId()==R.id.action_history){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,new HistoryFragment()).commit();
                    return true;
                }
                if(item.getItemId()==R.id.action_cart){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,new CartFragment()).commit();
                    return true;
                }
                if(item.getItemId()==R.id.action_profile){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,new ProfileFragment()).commit();
                    return true;
                }
                return false;
            }
        });
    }
}
