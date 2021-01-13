package com.example.newdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newdemo.R;
import com.example.newdemo.activity.HomeActivity;
import com.example.newdemo.model.ProductItem;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapterItem extends RecyclerView.Adapter<RecyclerAdapterItem.ProductViewHolder> {
    List<ProductItem> productItemList;
    Context context;
    public RecyclerAdapterItem(Context context, List<ProductItem> productItemList) {
        this.productItemList=productItemList;
        this.context=context;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder holder, final int position) {
        holder.textView1.setText(productItemList.get(position).getProductName());
        holder.textView2.setText(productItemList.get(position).getProductDescription());
        holder.textView3.setText(productItemList.get(position).getProductPrice());
        holder.textView4.setText(productItemList.get(position).getProductWeight());
        Glide.with(context).load(productItemList.get(position).getProductImage()).into(holder.imageView);



        holder.plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(holder.textView5.getText().toString());
                count++;
                holder.textView5.setText(String.valueOf(count));
            }
        });
        holder.minusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(holder.textView5.getText().toString());

                if (count > 1) {
                    count--;
                    holder.textView5.setText(String.valueOf(count));
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return productItemList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView,iv_favorite;
        TextView textView1,textView2,textView3,textView4,textView5;
        Button plusbtn,minusbtn,addbtn;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.iv1);
            textView1=itemView.findViewById(R.id.tv);
            textView2=itemView.findViewById(R.id.tv2);
            textView3=itemView.findViewById(R.id.tv3);
            textView4=itemView.findViewById(R.id.tvweights);
            textView5=itemView.findViewById(R.id.increament);
            plusbtn=itemView.findViewById(R.id.plusbtn);
            minusbtn=itemView.findViewById(R.id.minusbtn);
            addbtn=itemView.findViewById(R.id.addbtn);
            iv_favorite=itemView.findViewById(R.id.iv_favorite);


        }
    }
}
