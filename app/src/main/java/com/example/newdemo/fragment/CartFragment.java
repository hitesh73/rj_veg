package com.example.newdemo.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newdemo.R;
import com.example.newdemo.activity.OrderHistoryDetails;
import com.example.newdemo.activity.PaymentActivity;
import com.example.newdemo.activity.PlaceOrder;
import com.example.newdemo.adapter.CartProductList;
import com.example.newdemo.model.CartModel;
import com.example.newdemo.model.OrderModel;
import com.example.newdemo.model.ProductItem;
import com.example.newdemo.model.Users;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CartFragment extends Fragment {

    List<ProductItem> productItemList=new ArrayList<>();
    List<CartModel> cartList;
    RecyclerView rv_cart;
    TextView tvTotal, placeOrder;
    CartProductList cartViewHolder;
//    Button buybtn;
    String cartTotal,UserAddress,UserName,UserMobile;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

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

        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = preferences.edit();



        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OrderModel orderModel=new OrderModel();
                orderModel.setOrderStatus("Pending");
                orderModel.setCartModels(cartList);
                orderModel.setOrderId("");
                Date date = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                orderModel.setTimestamp(date);


//                Intent intent = new Intent(getActivity(), PaymentActivity.class);
                Intent intent = new Intent(getActivity(), PlaceOrder.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("order",orderModel);
                intent.putExtras(bundle);
                intent.putExtra("total",cartTotal);
                intent.putExtra("user_address",UserAddress);
                intent.putExtra("user_name",UserName);
                intent.putExtra("user_mobile",UserMobile);
//                intent.putExtra("order",orderModel);

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

        FirebaseFirestore.getInstance().collection("USERS")
                .whereEqualTo("email", preferences.getString("email", ""))
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable final FirebaseFirestoreException error) {
                        if (value != null) {
                            value.getDocuments().get(0).getReference().get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Users users = documentSnapshot.toObject(Users.class);

                                    UserAddress = users.address;
                                    UserName = users.name;
                                    UserMobile = users.mobile;

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                        }
                        if (error != null) {
                            Toast.makeText(getActivity(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

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
                        productItemList.add(cartList.get(i).getProductItem());
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
