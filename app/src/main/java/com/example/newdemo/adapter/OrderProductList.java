package com.example.newdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newdemo.R;
import com.example.newdemo.model.OrderModel;
import com.example.newdemo.model.ProductItem;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class OrderProductList extends FirestoreRecyclerAdapter<OrderModel, OrderProductList.OrderViewHolder> {
    Context context;


    public OrderProductList(Context context, @NonNull FirestoreRecyclerOptions<OrderModel> options) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final OrderViewHolder holder, int position, @NonNull OrderModel orderModel) {
        holder.tvname.setText("Name : "+orderModel.getProductItem().getProductName());
        holder.tvdes.setText("Des : "+orderModel.getProductItem().getProductDescription());
        holder.tvprice.setText("price : "+"₹."+orderModel.getProductItem().getProductPrice());
        Glide.with(context).load(orderModel.getProductItem().getProductImage()).into(holder.orderimage);
        holder.tvstatus.setText("Status : "+orderModel.getOrderStatus());
        holder.tvqnty.setText("Quantity : "+orderModel.getProductQty());

        int price,qty;

        price = Integer.parseInt(orderModel.getProductItem().getProductPrice());

        qty = Integer.parseInt(orderModel.getProductQty());

        holder.tvtotal.setText(String.valueOf("Total : "+"₹."+price*qty));



    }


    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_details, parent, false);
        return new OrderViewHolder(view);
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        ImageView orderimage;
        TextView tvprice, tvdes, tvstatus, tvqnty, tvname,tvtotal;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvname = itemView.findViewById(R.id.order_tvname);
            tvdes = itemView.findViewById(R.id.order_tvdes);
            tvprice = itemView.findViewById(R.id.order_tvprice);
            tvqnty = itemView.findViewById(R.id.order_tvqnty);
            tvstatus = itemView.findViewById(R.id.order_tvstatus);
            orderimage = itemView.findViewById(R.id.order_image);
            tvtotal = itemView.findViewById(R.id.order_tvtotal);
        }
    }
}
