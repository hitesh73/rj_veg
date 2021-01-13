package com.example.newdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newdemo.R;
import com.example.newdemo.fragment.CartFragment;
import com.example.newdemo.fragment.HistoryFragment;
import com.example.newdemo.model.ProductItem;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class CartProductList extends FirestoreRecyclerAdapter<ProductItem, CartProductList.CartViewHolder> {
    Context context;
    public CartProductList(Context context, @NonNull FirestoreRecyclerOptions<ProductItem> options) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final CartViewHolder holder, int position, @NonNull final ProductItem model) {


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
        holder.buybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtocart(model);
                Intent intent = new Intent(context,HistoryFragment.class);
                v.getContext().startActivity(intent);

            }
        });

    }

    private void addtocart(final ProductItem model) {
        FirebaseFirestore.getInstance().collection("USERS").document("rahul@gmail.com")
                .collection("CART").whereEqualTo("productId", model.getProductId())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null) {
                            if (!value.isEmpty()) {
                                if (value.getDocuments().get(0).exists())
                                    Toast.makeText(context, "Already Added ", Toast.LENGTH_SHORT).show();
                            } else
                                FirebaseFirestore.getInstance().collection("USERS")
                                        .document("rahul@gmail.com")
                                        .collection("CART").add(model)
                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(context, "add to cart", Toast.LENGTH_SHORT).show();
                                                } else
                                                    Toast.makeText(context, "" + task.getException(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                        }
                        if (error != null)
                            Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_product_item,parent,false);
        return new CartViewHolder(view);
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1, textView2, textView3, textView4, textView5;
        Button plusbtn, minusbtn,buybtn;
        ImageView favoritebtn;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            favoritebtn = itemView.findViewById(R.id.iv_favorite);
            imageView = itemView.findViewById(R.id.iv1);
            textView1 = itemView.findViewById(R.id.tv);
            textView2 = itemView.findViewById(R.id.tv2);
            textView3 = itemView.findViewById(R.id.tv3);
            textView4 = itemView.findViewById(R.id.tvweights);
            textView5 = itemView.findViewById(R.id.increament);
            plusbtn = itemView.findViewById(R.id.plusbtn);
            minusbtn = itemView.findViewById(R.id.minusbtn);
            buybtn = itemView.findViewById(R.id.buybtn);
        }

    }
}
