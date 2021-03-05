package com.example.newdemo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.service.carrier.CarrierMessagingClientService;
import android.util.Log;
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
import com.example.newdemo.model.CartModel;
import com.example.newdemo.model.OrderModel;
import com.example.newdemo.model.ProductItem;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;


public class ProductListAdapter extends FirestoreRecyclerAdapter<ProductItem, ProductListAdapter.ProductViewHolder> {

    Context context;
    private boolean newItem, isFavorite = false;
    static OnItemListCountListener listener;

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
                String qty = holder.textView5.getText().toString();

                addProductToCart(model, qty);
            }
        });


        holder.favoritebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                newItem=true;
//                if (newItem) {
//                    FirebaseFirestore.getInstance().collection("USERS").document("rahul@gmail.com")
//                            .collection("FAVORITE").whereEqualTo("productId", model.getProductId())
//                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                                @Override
//                                public void onEvent(@Nullable final QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                                    if (value == null || value.isEmpty()) {
//                                        FirebaseFirestore.getInstance().collection("USERS").document("rahul@gmail.com")
//                                                .collection("FAVORITE").add(model)
//                                                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                                                    @Override
//                                                    public void onComplete(@NonNull Task<DocumentReference> task) {
//                                                        if (task.isComplete()) {
//                                                            Toast.makeText(context, "add to favorite", Toast.LENGTH_SHORT).show();
//                                                            newItem=false;
//                                                        }
//                                                    }
//                                                });
//                                    } else {
//                                        Toast.makeText(context, "Already Added", Toast.LENGTH_SHORT).show();
//
//                                    }
//                                }
//                            });
//                }
//            }
//        });
////
////                    if (holder.favoritebtn.getBackground() == context.getDrawable(R.drawable.ic_favorite))
////                    {
////                        holder.favoritebtn.setImageResource(R.drawable.ic_liked);
////                        Toast.makeText(context, "adding to favorite", Toast.LENGTH_SHORT).show();
////                        addProductToFavorite(model);
////                    }
////                    if (holder.favoritebtn.getBackground() == context.getDrawable(R.drawable.ic_liked))
////                    else{
////                        holder.favoritebtn.setImageResource(R.drawable.ic_favorite);
////                        removeProductFromFavorite(model);
////
                isFavorite = false;
                addFavoriteProduct(holder, model);
                holder.favoritebtn.setBackgroundResource(R.drawable.ic_liked);
//                FirebaseFirestore.getInstance().collection("USERS").document("rahul@gmail.com")
//                        .collection("FAVORITE").whereEqualTo("productId", model.getProductId())
//                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                            @Override
//                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                                if (value != null) {
//                                    if (!value.isEmpty()) {
//                                        if (value.getDocuments().get(0).exists()) {
//                                            removeProductFromFavorite(holder,model);
//                                            holder.favoritebtn.setBackgroundResource(R.drawable.ic_favorite);
//                                        }
//                                    } else {
//                                        holder.favoritebtn.setBackgroundResource(R.drawable.ic_liked);
//                                        addProductToFavorite(holder,model);
//                                    }
//                                }
//                                if (error != null) {
//                                    Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_LONG).show();
//                                }
//
//                            }
//
//                        });
            }
        });
    }

    private void addFavoriteProduct(final ProductViewHolder holder, final ProductItem model) {
//        FirebaseFirestore.getInstance().collection("USERS").document("rahul@gmail.com")
//                .collection("FAVORITE").whereEqualTo("productId", model.getProductId())
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable final QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        if (value == null || value.isEmpty()) {
//                            if (!isFavorite) {
//                                FirebaseFirestore.getInstance().collection("USERS").document("rahul@gmail.com")
//                                        .collection("FAVORITE").add(model)
//                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<DocumentReference> task) {
//                                                if (task.isComplete()) {
//                                                    holder.favoritebtn.setBackgroundResource(R.drawable.ic_liked);
//                                                    Toast.makeText(context, "add to favorite", Toast.LENGTH_SHORT).show();
//                                                    isFavorite = true;
//                                                }
//                                            }
//                                        });
//                            }
//                        } else {
//                            Toast.makeText(context, "Already Added", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                });
        FirebaseFirestore.getInstance().collection("USERS").document("rahul@gmail.com").collection("FAVORITE")
                .whereEqualTo("productId", model.getProductId())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable final QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (!isFavorite) {
                            if (value != null) {
                                if (!value.isEmpty()) {
                                    if (value.getDocuments().get(0).exists()) {
                                        Toast.makeText(context, "Already added", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    FirebaseFirestore.getInstance().collection("USERS")
                                            .document("rahul@gmail.com")
                                            .collection("FAVORITE").add(model)
                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                @Override
                                                public void onSuccess(DocumentReference documentReference) {
                                                    Map<String, Object> map = new HashMap<>();
                                                    map.put("favProductId", documentReference.getId());
                                                    documentReference.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            Toast.makeText(context, "Added to favorite", Toast.LENGTH_SHORT).show();
                                                            isFavorite = true;
                                                        }
                                                    });
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        }
                        if (error != null) {
                            Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void addProductToCart(final ProductItem model, final String qty) {

        final CartModel cartModel = new CartModel();
//        final OrderModel order = new OrderModel();
        FirebaseFirestore.getInstance().collection("USERS").document("rahul@gmail.com")
                .collection("CART").whereEqualTo("productId", model.getProductId())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null) {
                            if (!value.isEmpty()) {
                                if (value.getDocuments().get(0).exists())
                                    Toast.makeText(context, "Already Added ", Toast.LENGTH_SHORT).show();
                            } else {
                                cartModel.setProductItem(model);
                                cartModel.setProductQty(qty);
                                FirebaseFirestore.getInstance().collection("USERS")
                                        .document("rahul@gmail.com")
                                        .collection("CART").add(cartModel)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Map<String, Object> map = new HashMap<>();
                                                map.put("cartProductId", documentReference.getId());
                                                documentReference.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                        if (error != null)
                            Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void removeProductFromFavorite(final ProductViewHolder holder, final ProductItem model) {
        FirebaseFirestore.getInstance().collection("USERS").document("rahul@gmail.com")
                .collection("FAVORITE").whereEqualTo("productId", model.getProductId())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null && !value.isEmpty()) {
                            if (value.getDocuments().get(0).exists()) {
                                value.getDocuments().get(0).getReference().delete();
                                holder.favoritebtn.setBackgroundResource(R.drawable.ic_favorite);
                                Toast.makeText(context, "removing to favorite", Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (error != null) {
                            Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
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
                            } else if (value.getDocuments() != null) {
                                holder.favoritebtn.setBackgroundResource(R.drawable.ic_favorite);

                            }
                        }
                        if (error != null) {
                            Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_LONG).show();
                        }
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
        Button plusbtn, minusbtn, addbtn;
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


        }
    }
}
