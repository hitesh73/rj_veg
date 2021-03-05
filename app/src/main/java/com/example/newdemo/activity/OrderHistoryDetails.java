package com.example.newdemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.newdemo.R;
import com.example.newdemo.adapter.CartProductList;
import com.example.newdemo.adapter.OrderListAdapter;
import com.example.newdemo.adapter.OrderProductList;
import com.example.newdemo.adapter.RecyclerAdapterItem;
import com.example.newdemo.model.CartModel;
import com.example.newdemo.model.OrderModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderHistoryDetails extends AppCompatActivity {
    TextView tv_mobile_no, tv_date, tv_address, tv_payment, tv_total_price;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String mobile_no;
    List<CartModel> cartModels;
    RecyclerView recyclerViewItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_details);

        tv_mobile_no = findViewById(R.id.mobile_no);
        tv_date = findViewById(R.id.date);
        tv_address = findViewById(R.id.address);
        tv_payment = findViewById(R.id.paymentmethod);
        tv_total_price = findViewById(R.id.total_price);
        recyclerViewItems = findViewById(R.id.orderItem);

        cartModels = new ArrayList<>();

        preferences = getSharedPreferences("user", MODE_PRIVATE);
        editor = preferences.edit();
        preferences.getString("address","");


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        OrderModel orderModel = (OrderModel) bundle.getSerializable("orderModel");
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy  hh:mm a");
        String date = format.format(orderModel.getTimestamp());
        tv_date.setText("Date "+date);
        tv_total_price.setText("Total Price : " + orderModel.getOrderTotal());

        orderModel.getCartModels();

        OrderListAdapter orderListAdapter = new OrderListAdapter(OrderHistoryDetails.this,orderModel.getCartModels());
        recyclerViewItems.setAdapter(orderListAdapter);
        recyclerViewItems.setLayoutManager(new GridLayoutManager(this,1));

    }
}