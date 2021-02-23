package com.example.newdemo.adapter;

import android.content.Context;
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

import com.bumptech.glide.Glide;
import com.example.newdemo.R;
import com.example.newdemo.model.OrderModel;
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

public class FavoritesProductList extends FirestoreRecyclerAdapter<ProductItem, FavoritesProductList.FavoriteViewHolder> {
    Context context;
    boolean newItem = true;
    boolean isDeleted=false;

    public FavoritesProductList(Context context, @NonNull FirestoreRecyclerOptions<ProductItem> options) {
        super(options);
        this.context = context;

    }

    @Override
    protected void onBindViewHolder(@NonNull final FavoriteViewHolder holder, int position, @NonNull final ProductItem model) {
        holder.textView1.setText(model.getProductName());
        holder.textView2.setText(model.getProductDescription());
        holder.textView3.setText(model.getProductPrice());
        holder.textView4.setText(model.getProductWeight());
        Glide.with(context).load(model.getProductImage()).into(holder.imageView);
        holder.favoritebtn.setBackgroundResource(R.drawable.ic_liked);

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

//        checkFavoriteProducts(holder,model);

        holder.favoritebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDeleted=false;
                removeFavoriteProduct(holder,model);
//                addFavoriteProduct(model);
//                deletedFavoritePtoduct(holder, model);
//                FirebaseFirestore.getInstance().collection("USERS").document("rahul@gmail.com").collection("FAVORITE")
//                        .whereEqualTo("productId", model.getProductId())
//                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                            @Override
//                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                                if (value != null) {
//                                    if (!value.isEmpty()) {
//                                        if (value.getDocuments().get(0).exists()) {
//                                            removeFavoriteProduct(model);
//                                        }
//                                    } else {
//                                        addFavoriteProduct(model);
//
//                                    }
//                                }
//                                if (error != null) {
//                                    Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
            }
        });

    }

    private void deletedFavoritePtoduct(final FavoriteViewHolder holder, final ProductItem model) {
        final OrderModel orderModel=new OrderModel();

            FirebaseFirestore.getInstance().collection("USERS").document("rahul@gmail.com").collection("FAVORITE")
                    .whereEqualTo("productId", model.getProductId()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value == null && value.isEmpty()) {
//                        addFavoriteProduct(model);
//                        holder.favoritebtn.setBackgroundResource(R.drawable.ic_liked);
                    } else {
                        isDeleted=false;
                        removeFavoriteProduct(holder, model);
//                        holder.favoritebtn.setBackgroundResource(R.drawable.ic_favorite);
                    }
                }
            });

    }

//    private void addFavoriteProduct(final ProductItem model) {
//        if (newItem)
//            FirebaseFirestore.getInstance().collection("USERS").document("rahul@gmail.com").collection("FAVORITE")
//                    .whereEqualTo("productId", model.getProductId())
//                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                        @Override
//                        public void onEvent(@Nullable final QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                            if (value != null) {
//                                if (!value.isEmpty()) {
//                                    if (value.getDocuments().get(0).exists()) {
//                                        Toast.makeText(context, "Already added", Toast.LENGTH_SHORT).show();
//                                    }
//                                } else
//                                    FirebaseFirestore.getInstance().collection("USERS").document("rahul@gmail.com")
//                                            .collection("FAVORITE").add(model)
//                                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<DocumentReference> task) {
//                                                    if (task.isSuccessful()) {
//
//                                                        Toast.makeText(context, "add to favorite", Toast.LENGTH_SHORT).show();
//                                                        newItem = false;
//                                                    } else {
//                                                        Toast.makeText(context, "" + task.getException(), Toast.LENGTH_SHORT).show();
//                                                    }
//                                                }
//                                            });
//                            }
//                            if (error != null) {
//                                Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//    }

    private void checkFavoriteProducts(final FavoriteViewHolder holder, ProductItem model) {
        FirebaseFirestore.getInstance().collection("USERS").document("rahul@gmail.com")
                .collection("FAVORITE").whereEqualTo("productId", model.getProductId())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value == null && value.isEmpty()) {
                            if (value.getDocuments().get(0).exists()) {
                                holder.favoritebtn.setBackgroundResource(R.drawable.ic_liked);
                                Toast.makeText(context, "add favorite item", Toast.LENGTH_SHORT).show();
                            } else {
                                holder.favoritebtn.setBackgroundResource(R.drawable.ic_favorite);
                                value.getDocuments().get(0).getReference().delete();
                                Toast.makeText(context, "remove favorite item", Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (error != null) {
                            Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                });
    }

    private void removeFavoriteProduct(final FavoriteViewHolder holder, ProductItem model) {
        final OrderModel orderModel=new OrderModel();
        if (!isDeleted) {
            FirebaseFirestore.getInstance().collection("USERS").document("rahul@gmail.com")
                    .collection("FAVORITE").whereEqualTo("favProductId", model.getFavProductId())
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                            if (value != null && !value.isEmpty()) {
                                if (value.getDocuments().get(0).exists()) {
                                    value.getDocuments().get(0).getReference().delete();
                                    holder.favoritebtn.setBackgroundResource(R.drawable.ic_favorite);
                                    Toast.makeText(context, "removing to favorite", Toast.LENGTH_SHORT).show();
                                    isDeleted=true;
                                }
                            }
                            if (error != null) {
                                Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items, parent, false);
        return new FavoriteViewHolder(view);
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1, textView2, textView3, textView4, textView5;
        Button plusbtn, minusbtn, addbtn;
        ImageView favoritebtn;

        public FavoriteViewHolder(@NonNull View itemView) {
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
            addbtn = itemView.findViewById(R.id.addbtn);
        }
    }
}
