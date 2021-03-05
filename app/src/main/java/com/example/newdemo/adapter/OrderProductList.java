package com.example.newdemo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newdemo.R;
import com.example.newdemo.activity.OrderHistoryDetails;
import com.example.newdemo.model.CartModel;
import com.example.newdemo.model.OrderModel;
import com.example.newdemo.model.ProductItem;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderProductList extends FirestoreRecyclerAdapter<OrderModel, OrderProductList.OrderViewHolder> {
    Context context;
    String date;


    public OrderProductList(Context context, @NonNull FirestoreRecyclerOptions<OrderModel> options) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final OrderViewHolder holder, int position, @NonNull final OrderModel orderModel) {

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy  hh:mm a");
        String date = format.format(orderModel.getTimestamp());
        holder.tv_order_datetime.setText("Date : "+date);

        holder.tv_order_status.setText(orderModel.getOrderStatus());
        holder.tv_order_ID.setText("Order ID " + orderModel.getOrderId());


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderHistoryDetails.class);
                Bundle b = new Bundle();
                b.putSerializable("orderModel", orderModel);
                intent.putExtras(b);
                context.startActivity(intent);

            }
        });
//        holder.tvname.setText("Name : "+cartModel.getProductItem().getProductName());
//        holder.tvdes.setText("Des : "+cartModel.getProductItem().getProductDescription());
//        holder.tvprice.setText("price : "+"₹."+cartModel.getProductItem().getProductPrice());
//        Glide.with(context).load(cartModel.getProductItem().getProductImage()).into(holder.orderimage);
//        holder.tvqnty.setText("Quantity : "+cartModel.getProductQty());
//
//        int price,qty;
//
//        price = Integer.parseInt(cartModel.getProductItem().getProductPrice());
//
//        qty = Integer.parseInt(cartModel.getProductQty());
//
//        holder.tvtotal.setText(String.valueOf("Total : "+"₹."+price*qty));
//---------------------------------------------------------------------------------------------------------------------------------------------------
//        FirebaseFirestore.getInstance().collection("USERS")
//                .document("rahul@gmail.com")
//                .collection("ORDERS").add(cartModel)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Map<String, Object> map = new HashMap<>();
//                        map.put("cartProductId", documentReference.getId());
//                        documentReference.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });


    }


    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_details, parent, false);
        return new OrderViewHolder(view);
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        //        ImageView orderimage;
//        TextView tvprice, tvdes, tvstatus, tvqnty, tvname,tvtotal;
        TextView tv_orders, tv_order_ID, tv_order_status, tv_datetime, tv_order_datetime;
        CardView cardView;


        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
//            tvname = itemView.findViewById(R.id.order_tvname);
//            tvdes = itemView.findViewById(R.id.order_tvdes);
//            tvprice = itemView.findViewById(R.id.order_tvprice);
//            tvqnty = itemView.findViewById(R.id.order_tvqnty);
//            tvstatus = itemView.findViewById(R.id.order_tvstatus);
//            orderimage = itemView.findViewById(R.id.order_image);
//            tvtotal = itemView.findViewById(R.id.order_tvtotal);
//            tv_orders = itemView.findViewById(R.id.orders);
            tv_order_ID = itemView.findViewById(R.id.orderID);
            tv_order_status = itemView.findViewById(R.id.orderstatus);
//            tv_datetime = itemView.findViewById(R.id.date_time);
            tv_order_datetime = itemView.findViewById(R.id.order_datetime);
            cardView = itemView.findViewById(R.id.order_card);
        }
    }
}
