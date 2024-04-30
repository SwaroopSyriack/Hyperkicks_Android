package com.example.hyperkicks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hyperkicks.databinding.ActivityCartactivityBinding;
import com.example.hyperkicks.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class cartactivity extends AppCompatActivity {

    private ActivityCartactivityBinding binding;

    public static ArrayList<CartModel> cartList;

    public static List<CartModel> cartitems;

    private cartAdapter adapter;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        cartList = new ArrayList<>();
        adapter = new cartAdapter(this);
        LinearLayoutManager Layout = new LinearLayoutManager(cartactivity.this);
        binding.cartrecycler.setLayoutManager(Layout);
        binding.cartrecycler.setAdapter(adapter);
        import_cart_item();

        binding.backcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(cartactivity.this,MainActivity.class));
            }
        });

        binding.checkoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartitems = adapter.getselecteditems();
                Toast.makeText(cartactivity.this, ""+cartitems.size(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(cartactivity.this,orders_page.class));

            }
        });
    }

    private void import_cart_item() {
        firestore.collection("cart").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> documentSnapshots = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot documentSnapshot:documentSnapshots){
                    CartModel cartModel = documentSnapshot.toObject(CartModel.class);
                    adapter.add_product(cartModel);
                }

            }
        });
    }
}