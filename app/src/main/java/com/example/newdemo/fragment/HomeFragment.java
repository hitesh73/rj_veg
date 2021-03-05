package com.example.newdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newdemo.R;
import com.example.newdemo.activity.DealOfTheDay;
import com.example.newdemo.activity.FruitsActivity;
import com.example.newdemo.activity.TopSellingProduct;
import com.example.newdemo.activity.VegetableActivity;
import com.example.newdemo.adapter.ProductListAdapter;
import com.example.newdemo.adapter.RecyclerAdapterItem;
import com.example.newdemo.adapter.SliderAdapter;
import com.example.newdemo.model.ProductItem;
import com.example.newdemo.model.SliderItem;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    SliderView sliderView;
    SliderAdapter adapter;
    List<SliderItem> imageList;
    List<ProductItem> productItemList;
    ImageView iv, iv2;
    TextView tview, tview2;
    CardView cardView1, cardView2;
    RecyclerView recyclerView,recyclerViewdeal;
    Button topsellingbtn, deallingbtn;
    ProductListAdapter productAdapter,productListAdapterDeal;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        sliderView = view.findViewById(R.id.imageSlider);

        iv = view.findViewById(R.id.imagevw);
        iv2 = view.findViewById(R.id.imagevw2);

        tview = view.findViewById(R.id.textview1);
        tview2 = view.findViewById(R.id.textview2);

        recyclerView = view.findViewById(R.id.rv_sellingproduct);
        recyclerViewdeal = view.findViewById(R.id.rv_dealoftheday);
        productItemList = new ArrayList<>();

        cardView1 = view.findViewById(R.id.cardview);
        cardView2 = view.findViewById(R.id.cardview2);

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FruitsActivity.class);
                startActivity(intent);

            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VegetableActivity.class);
                startActivity(intent);

            }
        });

        topsellingbtn = view.findViewById(R.id.btn_sellingitem);
        deallingbtn = view.findViewById(R.id.btn_dealoftheday);

        topsellingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TopSellingProduct.class);
                startActivity(intent);
            }
        });

        deallingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DealOfTheDay.class);
                startActivity(intent);
            }
        });

        sliderView.startAutoCycle();
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        imageList = new ArrayList<>();
        getImageUrl();
        getVegetableImage();
        getFruitsImage();
        getTopSellingProducts();
        getDealOfTheDay();


        //FrameLayout frameLayout = view.findViewById(R.id.frame1);
        //frameLayout.setBackgroundColor(getResources().getColor(R.color.red));


//        FirebaseFirestore.getInstance().collectionGroup("PRODUCTS")
//                .limit(4)
//                .whereEqualTo("topSelling", "YES")
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                        if (value != null && !value.isEmpty()) {
//                            for (int i = 0; i < value.size(); i++) {
//                                ProductItem productItem = value.getDocuments().get(i).toObject(ProductItem.class);
//                                productItemList.add(productItem);
//                            }
//
//                            RecyclerAdapterItem recyclerAdapterItem = new RecyclerAdapterItem(getActivity(), productItemList);
//                            recyclerView.setAdapter(recyclerAdapterItem);
//                            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//                        }
//                        if (error != null) {
//                            Toast.makeText(getActivity(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
//                            Log.e(TAG, "onEvent: ", error);
//                        }
//                    }
//                });


        return view;
    }

    private void getDealOfTheDay() {
        Query query = FirebaseFirestore.getInstance().collectionGroup("PRODUCTS")
                .whereEqualTo("dealOfTheDay","YES").limit(2);
        FirestoreRecyclerOptions firestoreRecyclerOptions= new FirestoreRecyclerOptions.Builder<ProductItem>()
                .setQuery(query, ProductItem.class).build();

        productListAdapterDeal=new ProductListAdapter(getActivity(),firestoreRecyclerOptions);
        recyclerViewdeal.setAdapter(productListAdapterDeal);
        recyclerViewdeal.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }

    private void getTopSellingProducts() {
        Query query = FirebaseFirestore.getInstance().collectionGroup("PRODUCTS")
                .whereEqualTo("topSelling", "YES").limit(2);
        FirestoreRecyclerOptions rvOptions = new FirestoreRecyclerOptions.Builder<ProductItem>().setQuery(query, ProductItem.class).build();
        productAdapter=new ProductListAdapter(getActivity(),rvOptions);
        recyclerView.setAdapter(productAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

    }


    private void getFruitsImage() {
        FirebaseFirestore.getInstance().collection("FRUITS").document("fruits")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null && value.exists()) {
                            String imageUrl = value.get("imageUrl").toString();
                            Glide.with(getActivity()).load(imageUrl).into(iv);
                            String title = value.get("title").toString();
                            tview.setText(title);
                        }
                    }
                });
    }

    private void getVegetableImage() {
        FirebaseFirestore.getInstance().collection("VEGETABLES").document("vegetable")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null && value.exists()) {
                            String imageUrl = value.get("imageUrl").toString();
                            Glide.with(getActivity()).load(imageUrl).into(iv2);
                            String title = value.get("title").toString();
                            tview2.setText(title);
                        }
                    }
                });
    }

    private void getImageUrl() {
        FirebaseFirestore.getInstance().collection("BANNER").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (int i = 0; i < queryDocumentSnapshots.size(); i++) {
                            SliderItem sliderItem = queryDocumentSnapshots.getDocuments().get(i).toObject(SliderItem.class);
                            imageList.add(sliderItem);
                        }

                        adapter = new SliderAdapter(getContext(), imageList);
                        sliderView.setSliderAdapter(adapter);

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        productAdapter.startListening();
        productListAdapterDeal.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        productAdapter.stopListening();
        productListAdapterDeal.stopListening();
    }


}
