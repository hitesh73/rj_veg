package com.example.newdemo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newdemo.R;
import com.example.newdemo.activity.HomeActivity;
import com.example.newdemo.activity.MainActivity;
import com.example.newdemo.fragment.HomeFragment;
import com.example.newdemo.model.ProductItem;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.net.DatagramSocket;


public class ProductListAdapter extends FirestoreRecyclerAdapter<ProductItem, ProductListAdapter.ProductViewHolder> {

    Context context;

    public ProductListAdapter(Context context, @NonNull FirestoreRecyclerOptions<ProductItem> options) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final ProductViewHolder holder, int position, @NonNull final ProductItem model) {
        holder.textView1.setText(model.getProductName());
        holder.textView2.setText(model.getProductDescription());
        holder.textView3.setText(model.getProductPrice());
        holder.textView4.setText(model.getProductWeight());
        Glide.with(context).load(model.getProductImage()).into(holder.imageView);
//        holder.favoritebtn.setImageResource(R.drawable.ic_liked);


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
        checkFavoriteProducts(holder, model);

        holder.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProductToCart(model);
            }
        });
//
//        final Button b = new Button (MyClass.this);
//        b.setBackgroundDrawable(getResources().getDrawable(R.drawable.whatever));


        holder.favoritebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//                    if (holder.favoritebtn.getBackground() == context.getDrawable(R.drawable.ic_favorite))
//                    {
//                        holder.favoritebtn.setImageResource(R.drawable.ic_liked);
//                        Toast.makeText(context, "adding to favorite", Toast.LENGTH_SHORT).show();
//                        addProductToFavorite(model);
//                    }
////                    if (holder.favoritebtn.getBackground() == context.getDrawable(R.drawable.ic_liked))
//                    else{
//                        holder.favoritebtn.setImageResource(R.drawable.ic_favorite);
//                        removeProductFromFavorite(model);
//
                FirebaseFirestore.getInstance().collection("USERS").document("rahul@gmail.com")
                        .collection("FAVORITE").whereEqualTo("productId", model.getProductId())
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (value != null) {
                                    if (!value.isEmpty()) {
                                        if (value.getDocuments().get(0).exists()) {
                                            removeProductFromFavorite(model);
                                        } else
                                            addProductToFavorite(model);
                                    } else addProductToFavorite(model);
                                }
                                if (error != null) {
                                    Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                                }

                            }

                        });
            }
        });
//        if (1 == model) {
//            holder.favoritebtn.setImageResource(R.drawable.ic_favorite);
//        }
//        else
//            holder.favoritebtn.setImageResource(R.drawable.ic_favorite);
//
//
//        holder.favoritebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FavoriteList favoriteList=new FavoriteList();
//
//                int id=productList.getId();
//                String image=productList.getImage();
//                String name=productList.getMame();
//
//                favoriteList.setId(id);
//                favoriteList.setImage(image);
//                favoriteList.setName(name);
//
//                if (MainActivity.favoriteDatabase.favoriteDao().isFavorite(id)!=1){
//                    viewHolder.fav_btn.setImageResource(R.drawable.ic_favorite);
//                    MainActivity.favoriteDatabase.favoriteDao().addData(favoriteList);
//
//                }else {
//                    viewHolder.fav_btn.setImageResource(R.drawable.ic_favorite_border_black_24dp);
//                    MainActivity.favoriteDatabase.favoriteDao().delete(favoriteList);
//
//                }
//
//
//            }
//        });
//    }


    }

    private void removeProductFromFavorite(final ProductItem model) {

        FirebaseFirestore.getInstance().collection("USERS").document("rahul@gmail.com")
                .collection("FAVORITE").whereEqualTo("productId", model.getProductId())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                         @Override
                                         public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                                             if (value != null && !value.isEmpty()) {
                                                 if (value.getDocuments().get(0).exists()) {
                                                     value.getDocuments().get(0).getReference().delete();
                                                     Toast.makeText(context, "removing to favorite", Toast.LENGTH_SHORT).show();
                                                 }
                                             }
                                             if (error != null) {
                                                 Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                                             }
                                         }
                                     }
                );
    }

    private void checkFavoriteProducts(final ProductViewHolder holder, ProductItem model) {
        FirebaseFirestore.getInstance().collection("USERS").document("rahul@gmail.com")
                .collection("FAVORITE").whereEqualTo("productId", model.getProductId())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null && !value.isEmpty()) {
                            if (value.getDocuments().get(0).exists()) {
                                holder.favoritebtn.setBackgroundResource(R.drawable.ic_liked);
                            } else
                                holder.favoritebtn.setBackgroundResource(R.drawable.ic_favorite);
                        }
                        if (error != null) {
                            Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }

                });

    }

    private void addProductToCart(final ProductItem model) {
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

    private void addProductToFavorite(final ProductItem model) {
//        if (model.getProductId()!=null){
//            FirebaseFirestore.getInstance().collection("USERS")
//                    .document("rahul@gmail.com")
//                    .collection("FAVORITE").whereEqualTo("productId", model.getProductId())
//                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                        @Override
//                        public void onEvent(@Nullable final QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                            if (value != null) {
//                                if (!value.isEmpty()) {
//                                    if (value.getDocuments().get(0).exists())
//                                    value.getDocuments().get(0).getReference().delete();
//                                    Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }
//                    });
//        }
        FirebaseFirestore.getInstance().collection("USERS")
                .document("rahul@gmail.com")
                .collection("FAVORITE").whereEqualTo("productId", model.getProductId())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable final QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null) {
                            if (!value.isEmpty()) {
                                if (value.getDocuments().get(0).exists())
                                    Toast.makeText(context, "Already Added", Toast.LENGTH_SHORT).show();
                            } else
                                FirebaseFirestore.getInstance().collection("USERS")
                                        .document("rahul@gmail.com")
                                        .collection("FAVORITE").add(model)
                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(context, "add to favorite", Toast.LENGTH_SHORT).show();
                                                } else {
//                                                value.getDocuments().get(0).getReference().delete();
                                                    Toast.makeText(context, "" + task.getException(), Toast.LENGTH_SHORT).show();
                                                }

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
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items, parent, false);
        return new ProductViewHolder(view);
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView1, textView2, textView3, textView4, textView5;
        Button plusbtn, minusbtn, addbtn, buybtn;
        ImageView favoritebtn;

        public ProductViewHolder(@NonNull View itemView) {
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
            buybtn = itemView.findViewById(R.id.buybtn);

        }
    }
}
