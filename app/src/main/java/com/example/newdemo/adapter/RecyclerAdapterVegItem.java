package com.example.newdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newdemo.R;
import com.example.newdemo.activity.VegetableActivity;
import com.example.newdemo.model.ProductItem;

import java.util.List;

public class RecyclerAdapterVegItem extends RecyclerView.Adapter<RecyclerAdapterVegItem.ItemViewHolder> {
    Context context;
    List<ProductItem> productItemList;

    public RecyclerAdapterVegItem(Context context, List<ProductItem> productItemList) {
        this.context=context;
        this.productItemList=productItemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, int position) {
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

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1,textView2,textView3,textView4,textView5;
        Button plusbtn,minusbtn,addbtn;
        public ItemViewHolder(@NonNull View itemView) {
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
        }
    }
}
