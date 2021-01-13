package com.example.newdemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newdemo.R;
import com.example.newdemo.adapter.CartProductList;
import com.example.newdemo.model.ProductItem;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    List<ProductItem> productItemList;
    RecyclerView rv_cart;
    CartProductList cartViewHolder;
    Button buybtn;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_cart,container,false);
//        TextView textView=view.findViewById(R.id.tv);
//        textView.setText("shopping Cart");
//        FrameLayout frameLayout=view.findViewById(R.id.frame4);
        //frameLayout.setBackgroundColor(getResources().getColor(R.color.purple));

        rv_cart = view.findViewById(R.id.rv_addcart);
        productItemList = new ArrayList<>();

        getAddToCart();
        return view;
    }

    private void getAddToCart() {


        Query query= FirebaseFirestore.getInstance().collection("USERS").document("rahul@gmail.com")
                .collection("CART");

        FirestoreRecyclerOptions firestoreRecyclerOptions= new FirestoreRecyclerOptions.Builder<ProductItem>().setQuery(query,ProductItem.class).build();

        cartViewHolder= new CartProductList(getActivity(),firestoreRecyclerOptions);
        rv_cart.setAdapter(cartViewHolder);
        rv_cart.setLayoutManager(new GridLayoutManager(getActivity(),2));
    }

    @Override
    public void onStart() {
        super.onStart();
        cartViewHolder.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        cartViewHolder.stopListening();
    }
}
