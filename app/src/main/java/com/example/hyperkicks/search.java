package com.example.hyperkicks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.hyperkicks.databinding.ActivityMainBinding;
import com.example.hyperkicks.databinding.ActivitySearchBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class search extends AppCompatActivity {

    ActivitySearchBinding binding;

    searchAdapter adapter;

    ArrayList<productModel> arrayList1;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        arrayList1 = new ArrayList<>();
        adapter = new searchAdapter(arrayList1,this);

        binding.searchrecycler.setLayoutManager(new GridLayoutManager(this,2));
        binding.searchrecycler.setAdapter(adapter);
        searchFirestore();

        binding.searchitem.clearFocus();
        binding.searchitem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                fiterlist(s);
                return true;
            }
        });

    }

    private void fiterlist(String query) {
        ArrayList<productModel> filteredList = new ArrayList<>();
        for(productModel product : arrayList1){
            if (product.getName().toLowerCase().contains(query.toLowerCase())){
                filteredList.add(product);
            }
            else if (product.getBrand().toLowerCase().contains(query.toLowerCase())){
                filteredList.add(product);
            }

        }

        if (filteredList.isEmpty()){
            Toast.makeText(this, "No product Found", Toast.LENGTH_SHORT).show();

        }
        else{
            adapter.setfilteredlist(filteredList);
        }


    }

    private void searchFirestore() {
        firestore.collection("products")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        arrayList1.clear();
                        for (DocumentSnapshot dc : queryDocumentSnapshots){
                            productModel item = dc.toObject(productModel.class);
                            arrayList1.add(item);
                        }
                        adapter.notifyDataSetChanged();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("FirestoreSearch", "Error searching Firestore", e);
                    }
                });


    }
}