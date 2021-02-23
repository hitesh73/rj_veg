package com.example.newdemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newdemo.R;
import com.example.newdemo.adapter.FavoritesProductList;
import com.example.newdemo.adapter.ProductListAdapter;
import com.example.newdemo.model.ProductItem;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {
    List<ProductItem> productItemList;
    RecyclerView favrecyclerView;
    FavoritesProductList favoritesProductList;
    ProductListAdapter productListAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_favorite,container,false);
//        TextView textView=view.findViewById(R.id.tv);
//        textView.setText("favorite fragment");
//        FrameLayout frameLayout=view.findViewById(R.id.frame2);
        //frameLayout.setBackgroundColor(getResources().getColor(R.color.blue));

        favrecyclerView=view.findViewById(R.id.rv_favorites);
        productItemList = new ArrayList<>();

        getFavoriteProduct();

        return view;
    }

    private void getFavoriteProduct() {

        Query query=FirebaseFirestore.getInstance().collection("USERS").document("rahul@gmail.com")
                .collection("FAVORITE");

        FirestoreRecyclerOptions firestoreRecyclerOptions= new FirestoreRecyclerOptions.Builder<ProductItem>().setQuery(query,ProductItem.class).build();

        favoritesProductList= new FavoritesProductList(getActivity(),firestoreRecyclerOptions);
        favrecyclerView.setAdapter(favoritesProductList);
        favrecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

    }

    @Override
    public void onStart() {
        super.onStart();
        favoritesProductList.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        favoritesProductList.stopListening();
    }
}
