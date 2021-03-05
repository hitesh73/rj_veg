package com.example.newdemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.newdemo.R;
import com.example.newdemo.adapter.ProductListAdapter;
import com.example.newdemo.model.ProductItem;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class DealOfTheDay extends AppCompatActivity {
    List<ProductItem> productItemList;
    RecyclerView recyclerView;
    ProductListAdapter productListAdapter;
    ProductItem productItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_of_the_day);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView=findViewById(R.id.rv_dealoftheDay);
        productItemList=new ArrayList<>();

        getDealOfTheDay();

    }

    private void getDealOfTheDay() {
        Query query = FirebaseFirestore.getInstance().collectionGroup("PRODUCTS")
                .whereEqualTo("dealOfTheDay","YES");
        FirestoreRecyclerOptions firestoreRecyclerOptions= new FirestoreRecyclerOptions.Builder<ProductItem>()
                .setQuery(query, ProductItem.class).build();

        productListAdapter=new ProductListAdapter(this,firestoreRecyclerOptions);
        recyclerView.setAdapter(productListAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    protected void onStart() {
        productListAdapter.startListening();
        super.onStart();
    }

    @Override
    protected void onStop() {
        productListAdapter.stopListening();
        super.onStop();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}