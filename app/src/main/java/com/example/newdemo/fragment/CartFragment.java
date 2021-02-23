package com.example.newdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newdemo.R;
import com.example.newdemo.activity.PaymentActivity;
import com.example.newdemo.adapter.CartProductList;
import com.example.newdemo.model.CartModel;
import com.example.newdemo.model.OrderModel;
import com.example.newdemo.model.ProductItem;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    List<ProductItem> productItemList;
    List<CartModel> cartList;
    RecyclerView rv_cart;
    TextView tvTotal, placeOrder;
    CartProductList cartViewHolder;
    Button buybtn;
    String cartTotal;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
//        TextView textView=view.findViewById(R.id.tv);
//        textView.setText("shopping Cart");
//        FrameLayout frameLayout=view.findViewById(R.id.frame4);
        //frameLayout.setBackgroundColor(getResources().getColor(R.color.purple));

        rv_cart = view.findViewById(R.id.rv_addcart);
        tvTotal = view.findViewById(R.id.grandTotsl);
        placeOrder = view.findViewById(R.id.placeOrder);
        cartList = new ArrayList<>();

        getAddToCart();
        getCartTotal();


        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PaymentActivity.class);

                intent.putExtra("total",cartTotal);
                startActivity(intent);
            }
        });
//        buybtn = view.findViewById(R.id.buybtn);
//        buybtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(),HistoryFragment.class);
//                startActivity(intent);
//            }
//        });
        return view;
    }

    private void getCartTotal() {
        FirebaseFirestore.getInstance().collection("USERS").document("rahul@gmail.com")
                .collection("CART").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && !value.isEmpty()) {
                    int price = 0;
                    int qty = 0;
                    double total = 0;
                    cartList = value.toObjects(CartModel.class);
                    for (int i = 0; i < cartList.size(); i++) {
                        price = Integer.parseInt(cartList.get(i).getProductItem().getProductPrice());
                        qty = Integer.parseInt(cartList.get(i).getProductQty());
                        total += price * qty;
                        tvTotal.setText(String.valueOf("₹." + total));
                        cartTotal=String.valueOf(total);
                    }

                }else
                    tvTotal.setText(String.valueOf("₹." + "0.0"));

            }
        });
    }

    private void getAddToCart() {


        Query query = FirebaseFirestore.getInstance().collection("USERS").document("rahul@gmail.com")
                .collection("CART");

        FirestoreRecyclerOptions firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<CartModel>().setQuery(query, CartModel.class).build();

        cartViewHolder = new CartProductList(getActivity(), firestoreRecyclerOptions);
        rv_cart.setAdapter(cartViewHolder);
        rv_cart.setLayoutManager(new GridLayoutManager(getActivity(), 2));
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
