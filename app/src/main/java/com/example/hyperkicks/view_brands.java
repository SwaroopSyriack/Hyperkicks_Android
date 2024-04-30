package com.example.hyperkicks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.hyperkicks.databinding.ActivityDetailedProductBinding;
import com.example.hyperkicks.databinding.ActivityViewBrandsBinding;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class view_brands extends AppCompatActivity {
    ActivityViewBrandsBinding binding;
    brandModel brandModelmodel;

    ArrayList<productModel> productList;

    productAdapter adapter2;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewBrandsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        brandModelmodel = (brandModel) intent.getSerializableExtra("model");

        productList  = new ArrayList<>();
        adapter2 = new productAdapter(this,productList);
        GridLayoutManager layoutManager = new GridLayoutManager(view_brands.this,2);
        binding.viewbrecycler.setLayoutManager(layoutManager);
        binding.viewbrecycler.setAdapter(adapter2);
        import_product();





    }

    private void import_product() {
        firestore.collection("products").whereEqualTo("brand",brandModelmodel.getBrandname()).addSnapshotListener((value, error) -> {
            if (error != null)
            {
                Log.e("Firebase error",error.getMessage());

            }
            for(DocumentChange dc : value.getDocumentChanges()){


                if (dc.getType() == DocumentChange.Type.ADDED)
                {
                    productList.add(dc.getDocument().toObject(productModel.class));
                }
                adapter2.notifyDataSetChanged();
            }


        });

    }
}