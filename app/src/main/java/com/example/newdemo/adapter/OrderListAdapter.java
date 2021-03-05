package com.example.newdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newdemo.R;
import com.example.newdemo.activity.OrderHistoryDetails;
import com.example.newdemo.model.CartModel;
import com.example.newdemo.model.OrderModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderViewHolder>{
    Context context;
    List<CartModel> cartModels;


    public OrderListAdapter(Context context, List<CartModel> cartModels) {
        this.context = context;
        this.cartModels = cartModels;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.orderitemslist,parent,false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.tv_productName.setText(cartModels.get(position).getProductItem().getProductName());
        holder.tv_productPrice.setText("₹."+cartModels.get(position).getProductItem().getProductPrice());
        holder.tv_productKgs.setText(cartModels.get(position).getProductItem().getProductWeight());
        holder.tv_productQty.setText(cartModels.get(position).getProductQty());
    }

    @Override
    public int getItemCount() {
        return cartModels.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tv_productName, tv_productPrice, tv_productKgs, tv_productQty;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_productName = itemView.findViewById(R.id.product_name);
            tv_productPrice = itemView.findViewById(R.id.product_price);
            tv_productKgs = itemView.findViewById(R.id.product_kgs);
            tv_productQty = itemView.findViewById(R.id.product_qty);
        }
    }
}
