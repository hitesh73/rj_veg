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
import com.example.newdemo.model.CartModel;
import com.example.newdemo.model.OrderModel;
import com.example.newdemo.model.ProductItem;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class OrderProductList extends FirestoreRecyclerAdapter<CartModel, OrderProductList.OrderViewHolder> {
    Context context;


    public OrderProductList(Context context, @NonNull FirestoreRecyclerOptions<CartModel> options) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final OrderViewHolder holder, int position, @NonNull CartModel cartModel) {
        holder.tvname.setText("Name : "+cartModel.getProductItem().getProductName());
        holder.tvdes.setText("Des : "+cartModel.getProductItem().getProductDescription());
        holder.tvprice.setText("price : "+"₹."+cartModel.getProductItem().getProductPrice());
        Glide.with(context).load(cartModel.getProductItem().getProductImage()).into(holder.orderimage);
        holder.tvstatus.setText("Status : "+cartModel.getOrderStatus());
        holder.tvqnty.setText("Quantity : "+cartModel.getProductQty());

        int price,qty;

        price = Integer.parseInt(cartModel.getProductItem().getProductPrice());

        qty = Integer.parseInt(cartModel.getProductQty());

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
