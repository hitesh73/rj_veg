package com.example.newdemo.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.example.newdemo.R;
import com.example.newdemo.adapter.ProductListAdapter;
import com.example.newdemo.adapter.RecyclerAdapterItem;
import com.example.newdemo.model.ProductItem;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FruitsActivity extends AppCompatActivity {
    List<ProductItem> productItemList;
    RecyclerView recyclerView;
    ProductListAdapter productListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruits);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView=findViewById(R.id.recyclerview);
        productItemList=new ArrayList<>();

   FirebaseFirestore.getInstance().collection("FRUITS").document("fruits").collection("PRODUCTS")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null && !value.isEmpty()) {
                            for (int i = 0; i < value.size(); i++) {
                                ProductItem productItem = value.getDocuments().get(i).toObject(ProductItem.class);
                                productItemList.add(productItem);
                            }

                            RecyclerAdapterItem recyclerAdapterItem = new RecyclerAdapterItem(FruitsActivity.this,productItemList);
                            recyclerView.setAdapter(recyclerAdapterItem);
                            recyclerView.setLayoutManager(new GridLayoutManager(FruitsActivity.this,2));

                        }
                        if (error != null) {
                            Toast.makeText(FruitsActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

//    @Override
//    protected void onStart() {
//        productListAdapter.startListening();
//        super.onStart();
//    }
//
//    @Override
//    protected void onStop() {
//        productListAdapter.stopListening();
//        super.onStop();
//    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
