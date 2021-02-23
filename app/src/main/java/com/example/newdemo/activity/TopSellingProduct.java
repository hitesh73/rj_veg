package com.example.newdemo.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newdemo.R;
import com.example.newdemo.adapter.ProductListAdapter;
import com.example.newdemo.adapter.RecyclerAdapterItem;
import com.example.newdemo.model.ProductItem;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TopSellingProduct extends AppCompatActivity {

    List<ProductItem> productItemList;
    RecyclerView recyclerView;
    ProductListAdapter productListAdapter;
    ProductItem productItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_selling_product);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView=findViewById(R.id.rv_top_sell_product);
        productItemList=new ArrayList<>();

        getTopSellingProducts();

    }

    private void getTopSellingProducts() {
        Query query = FirebaseFirestore.getInstance().collectionGroup("PRODUCTS")
                .whereEqualTo("topSelling", "YES");
        FirestoreRecyclerOptions rvOptions = new FirestoreRecyclerOptions.Builder<ProductItem>().setQuery(query, ProductItem.class).build();

        productListAdapter=new ProductListAdapter(this,rvOptions);
        recyclerView.setAdapter(productListAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}