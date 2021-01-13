package com.example.newdemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.newdemo.R;

public class HistoryFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_history,container,false);
        TextView textView=view.findViewById(R.id.tv);
//        textView.setText("history fragment");
//        FrameLayout frameLayout=view.findViewById(R.id.frame3);
//        //frameLayout.setBackgroundColor(getResources().getColor(R.color.green));
        return view;
    }
}
