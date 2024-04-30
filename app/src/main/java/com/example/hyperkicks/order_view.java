package com.example.hyperkicks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.hyperkicks.databinding.ActivityMainBinding;
import com.example.hyperkicks.databinding.ActivityOrderViewBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class order_view extends AppCompatActivity {

    ActivityOrderViewBinding binding;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    ArrayList<orderModel> orderList;

    orderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        orderList = new ArrayList<>();
        adapter = new orderAdapter(this,orderList);
        LinearLayoutManager HorizontalLayout = new LinearLayoutManager(order_view.this, LinearLayoutManager.VERTICAL, false);
        binding.orderrecycler.setLayoutManager(HorizontalLayout);
        binding.orderrecycler.setAdapter(adapter);
        import_order();

        binding.backarrow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(order_view.this,MainActivity.class));
            }
        });

        binding.cartbtn12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(order_view.this,cartactivity.class));
            }
        });

    }

    private void import_order() {
        firestore.collection("orders").whereEqualTo("userid",FirebaseAuth.getInstance().getUid()).addSnapshotListener((value, error) -> {
            if (error != null)
            {
                Log.e("Firebase error",error.getMessage());

            }
            for(DocumentChange dc : value.getDocumentChanges()){


                if (dc.getType() == DocumentChange.Type.ADDED)
                {
                    orderList.add(dc.getDocument().toObject(orderModel.class));
                }
                adapter.notifyDataSetChanged();
            }
            Log.e("msg","OnEvevt executed");


        });

    }
}