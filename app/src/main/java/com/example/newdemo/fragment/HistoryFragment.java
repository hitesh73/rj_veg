package com.example.newdemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newdemo.R;
import com.example.newdemo.adapter.CartProductList;
import com.example.newdemo.adapter.OrderProductList;
import com.example.newdemo.model.CartModel;
import com.example.newdemo.model.OrderModel;
import com.example.newdemo.model.ProductItem;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {


    List<OrderModel> orderModels;
    List<CartModel> cartModels;
    RecyclerView rv_history;
    OrderProductList historyViewHolder;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_history,container,false);
        TextView textView=view.findViewById(R.id.tv);
//        textView.setText("history fragment");
//        FrameLayout frameLayout=view.findViewById(R.id.frame3);
//        //frameLayout.setBackgroundColor(getResources().getColor(R.color.green));

        rv_history = view.findViewById(R.id.rv_history);
        orderModels = new ArrayList<>();
        cartModels = new ArrayList<>();
        AddToHistory();
        return view;
    }

    private void AddToHistory() {
        Query query= FirebaseFirestore.getInstance().collection("USERS").document("rahul@gmail.com")
                .collection("ORDERS");

        FirestoreRecyclerOptions firestoreRecyclerOptions= new FirestoreRecyclerOptions.Builder<OrderModel>().setQuery(query, OrderModel.class).build();

        historyViewHolder= new OrderProductList(getActivity(),firestoreRecyclerOptions);
        rv_history.setAdapter(historyViewHolder);
        rv_history.setLayoutManager(new GridLayoutManager(getActivity(),1));
    }

    @Override
    public void onStart() {
        super.onStart();
        historyViewHolder.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        historyViewHolder.stopListening();
    }
}
