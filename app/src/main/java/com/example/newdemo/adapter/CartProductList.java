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
import com.example.newdemo.model.CartModel;
import com.example.newdemo.model.OrderModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class CartProductList extends FirestoreRecyclerAdapter<CartModel, CartProductList.CartViewHolder> {
    Context context;
    boolean isDeleted=false;

    public CartProductList(Context context,@NonNull FirestoreRecyclerOptions<CartModel> options) {
        super(options);
        this.context = context;
    }



    @Override
    protected void onBindViewHolder(@NonNull final CartViewHolder holder, int position, @NonNull final CartModel cartModel) {
        holder.textView1.setText(cartModel.getProductItem().getProductName());
        holder.textView2.setText(cartModel.getProductItem().getProductDescription());
        holder.textView3.setText(cartModel.getProductItem().getProductPrice());
        holder.textView4.setText(cartModel.getProductItem().getProductWeight());
        Glide.with(context).load(cartModel.getProductItem().getProductImage()).into(holder.imageView);



        holder.textView5.setText(cartModel.getProductQty());

        holder.plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(holder.textView5.getText().toString());
                count++;
                holder.textView5.setText(String.valueOf(count));
                updateCartHistory(cartModel,holder.textView5.getText().toString());

            }
        });
        holder.minusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(holder.textView5.getText().toString());

                if (count > 1) {
                    count--;
                    holder.textView5.setText(String.valueOf(count));
                    updateCartHistory(cartModel,holder.textView5.getText().toString());

                }
            }
        });

        holder.deletedimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDeleted=false;
                deleteProduct(cartModel);
            }
        });





//        holder.buybtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context,HistoryFragment.class);
//                context.startActivity(intent);
//                String qty=holder.textView5.getText().toString();
//                addtoHistory(orderModel,qty);
//            }
//        });

    }

    private void deleteProduct(CartModel cartModel) {
        if (!isDeleted) {
            FirebaseFirestore.getInstance().collection("USERS").document("rahul@gmail.com").collection("CART")
                    .whereEqualTo("cartProductId", cartModel.getCartProductId()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value != null && !value.isEmpty()) {
                        value.getDocuments().get(0).getReference().delete();
                        isDeleted=true;
                    }
                }
            });
        }
    }

    private void updateCartHistory(CartModel orderModel, String qty) {
        final Map<String,Object> map=new HashMap<>();
        map.put("productQty",qty);
        FirebaseFirestore.getInstance().collection("USERS").document("rahul@gmail.com").collection("CART")
                .whereEqualTo("productItem",orderModel.getProductItem())
//                .document("DNPaVOFfqhL8hhZBEyRN").update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()){
//                    Toast.makeText(context, "update cart", Toast.LENGTH_SHORT).show();
//                    map.clear();
//                }else
//                    Toast.makeText(context, ""+task.getException(), Toast.LENGTH_SHORT).show();
//            }
//        });
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value!=null &&!value.isEmpty()) {
                            value.getDocuments().get(0).getReference().update(map)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(context, "update cart", Toast.LENGTH_SHORT).show();
                                                map.clear();
                                            }else
                                                Toast.makeText(context, ""+task.getException(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }if (error!=null){
                            Toast.makeText(context, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void addtoHistory(final CartModel cartModel, final String qty) {
        FirebaseFirestore.getInstance().collection("USERS").document("RAHUL@GMAIL.COM")
                .collection("ORDERS").whereEqualTo("productId", cartModel.getProductItem().getProductId())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null) {
                            if (!value.isEmpty()) {
                                if (value.getDocuments().get(0).exists())
                                    Toast.makeText(context, "already adding", Toast.LENGTH_SHORT).show();
                            } else {
                                FirebaseFirestore.getInstance().collection("USERS")
                                        .document("rahul@gmail.com")
                                        .collection("ORDERS").add(cartModel)
                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(context, "history", Toast.LENGTH_SHORT).show();
                                                    FirebaseFirestore.getInstance().collection("USERS").document("rahul@gmail.com")
                                                            .collection("CART").document(cartModel.getCartProductId()).delete();
                                                } else
                                                    Toast.makeText(context, "" + task.getException(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
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
        ImageView imageView,deletedimage;
        TextView textView1, textView2, textView3, textView4, textView5,granfTotal,placeOrder;
        Button plusbtn, minusbtn;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv1);
            textView1 = itemView.findViewById(R.id.tv);
            textView2 = itemView.findViewById(R.id.tv2);
            textView3 = itemView.findViewById(R.id.tv3);
            textView4 = itemView.findViewById(R.id.tvweights);
            textView5 = itemView.findViewById(R.id.increament);
            plusbtn = itemView.findViewById(R.id.plusbtn);
            minusbtn = itemView.findViewById(R.id.minusbtn);
            deletedimage = itemView.findViewById(R.id.iv_deleting);
            granfTotal = itemView.findViewById(R.id.grandTotsl);
            placeOrder = itemView.findViewById(R.id.placeOrder);
        }

    }
}
