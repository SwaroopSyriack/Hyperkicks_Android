package com.example.hyperkicks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;


import com.example.hyperkicks.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding binding;
    ArrayList<brandModel> brandList;
    ArrayList<productModel> productList;

    sliderAdapter adapter;

    brandAdapter adapter1;

    productAdapter adapter2;

    ArrayList<sliderModel> imageList;

    private  final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("slider");
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    FirebaseAuth mAuth = FirebaseAuth.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.navView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,binding.drawerLayout,binding.toolbar,R.string.OPEN,R.string.CLOSE);
        binding.drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        // setting navigation drawer header
        TextView navemail = binding.navView.getHeaderView(0).findViewById(R.id.headername);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            String email = currentUser.getEmail();
            navemail.setText(email);
        }

        imageList = new ArrayList<>();
        adapter = new sliderAdapter(imageList,getApplicationContext());
        binding.viewPager.setAdapter(adapter);
        import_images_from_slider();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 3000, 5000);


        brandList = new ArrayList<>();
        adapter1 = new brandAdapter(this,brandList);
        LinearLayoutManager HorizontalLayout = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        binding.brandrecycler.setLayoutManager(HorizontalLayout);
        binding.brandrecycler.setAdapter(adapter1);
        import_brand();

        productList  = new ArrayList<>();
        adapter2 = new productAdapter(this,productList);
        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this,2);
        binding.productRecycler.setLayoutManager(layoutManager);
        binding.productRecycler.setAdapter(adapter2);
        import_product();

        binding.searchbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,search.class));
            }
        });

        binding.shoppingcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,cartactivity.class));
            }
        });

        binding.HyperVR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://precious-fawn-giving.ngrok-free.app";

                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(ContextCompat.getColor(MainActivity.this, R.color.black)); // Customize toolbar color
                builder.addDefaultShareMenuItem(); // Enable the share action in the toolbar
                CustomTabsIntent customTabsIntent = builder.build();

                openInChromeCustomTab(Uri.parse(url), MainActivity.this,customTabsIntent);
            }
        });
    }

    private void openInChromeCustomTab(Uri url, Context context,CustomTabsIntent customTabs) {


        String packageName = "com.android.chrome";
        if (packageName != null) {

            // we are checking if the package name is not null
            // if package name is not null then we are calling
            // that custom chrome tab with intent by passing its
            // package name.
            customTabs.intent.setPackage(packageName);

            // in that custom tab intent we are passing
            // our url which we have to browse.
            customTabs.launchUrl(context, url);
        } else {
            // if the custom tabs fails to load then we are simply
            // redirecting our user to users device default browser.
            context.startActivity(new Intent(Intent.ACTION_VIEW, url));
        }
    }


    private void import_product() {
        firestore.collection("products").addSnapshotListener((value, error) -> {
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

    private void import_brand() {

        firestore.collection("brands").addSnapshotListener((value, error) -> {
            if (error != null)
            {
                Log.e("Firebase error",error.getMessage());

            }
            for(DocumentChange dc : value.getDocumentChanges()){


                if (dc.getType() == DocumentChange.Type.ADDED)
                {
                    brandList.add(dc.getDocument().toObject(brandModel.class));
                }
                adapter1.notifyDataSetChanged();
            }
            Log.e("msg","OnEvevt executed");


        });

    }

    private void import_images_from_slider()
    {

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    sliderModel sliderModel = dataSnapshot.getValue(com.example.hyperkicks.sliderModel.class);
                    imageList.add(sliderModel);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.shop) {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        } else if (id == R.id.orders) {
            startActivity(new Intent(getApplicationContext(),order_view.class));
            finish();

        } else if (id == R.id.customersupport) {
            startActivity(new Intent(getApplicationContext(), customer_support_page.class));
            finish();
            // Handle Settings click
        }
        else if (id == R.id.nav_logout) {
            mAuth.signOut();
            startActivity( new Intent(MainActivity.this,hyper_login.class));

        }


        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private class SliderTimer extends TimerTask {
        @Override
        public void run() {
            if (binding.viewPager.getCurrentItem() < imageList.size() - 1) {
                binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem() + 1);
            } else {
                binding.viewPager.setCurrentItem(0);
            }
        }
    }
}