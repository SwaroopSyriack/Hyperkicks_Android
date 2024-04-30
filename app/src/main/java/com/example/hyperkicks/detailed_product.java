package com.example.hyperkicks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hyperkicks.databinding.ActivityDetailedProductBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class detailed_product extends AppCompatActivity {
    ActivityDetailedProductBinding binding;

    productModel productmodel;

    Dialog dialog;

    TextView okay_text;

    String selected;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailedProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialog = new Dialog(detailed_product.this);

        //getting data from the intent
        Intent intent = getIntent();

        //setting the values to the views
        productmodel = (productModel) intent.getSerializableExtra("model");
        Glide.with(binding.getRoot()).load(productmodel.getImgUrl()).into(binding.productimage1);
        binding.textView.setText(productmodel.getName());
        binding.textView2.setText(productmodel.getBrand());
        binding.mrp2.setText(productmodel.getMrp());

        // size spinner
        binding.description.setText(productmodel.getDescription());
        String[] spinnerValues = {"Select Size","6","7","8","9", "10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerValues);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        binding.size.setAdapter(adapter);
        binding.size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected = spinnerValues[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(detailed_product.this, "No item Selected", Toast.LENGTH_SHORT).show();
            }
        });

        //setting the back button
        binding.backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(detailed_product.this,MainActivity.class));
            }
        });

        binding.cartbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(detailed_product.this,cartactivity.class));
            }
        });

        binding.cartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showbottomsheetdialog();
            }

            private void showbottomsheetdialog() {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(detailed_product.this);
                View view = LayoutInflater.from(detailed_product.this).inflate(R.layout.bottom_layout,(RelativeLayout)findViewById(R.id.linearLayoutbottom),false);
                bottomSheetDialog.setContentView(view);

                ImageView cimage = view.findViewById(R.id.cartitemImage1);
                TextView cproduct_name = view.findViewById(R.id.productName1);
                TextView cbrand_name = view.findViewById(R.id.cartbrandname1);
                TextView csize = view.findViewById(R.id.size1);
                TextView qty = view.findViewById(R.id.cartproductQuantity1);
                Button add_cart = view.findViewById(R.id.addtocartbtn);
                // setting the text
                Glide.with(view).load(productmodel.getImgUrl()).into(cimage);
                cproduct_name.setText(productmodel.getName());
                cbrand_name.setText(productmodel.getBrand());
                csize.setText(selected);
                qty.setText("1");
                bottomSheetDialog.show();


                add_cart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String quantity = qty.getText().toString();
                        bottomSheetDialog.dismiss();
                        add_to_cart("1",selected);

                    }
                });
            }
        });
    }

    private void add_to_cart(String quantity,String selected) {
        progress(); // Custom progress Bar
        String id = UUID.randomUUID().toString();
        CartModel cartModel = new CartModel(id,productmodel.getName(),productmodel.getImgUrl(),productmodel.getMrp(),quantity,selected, FirebaseAuth.getInstance().getUid(),null);
        firestore.collection("cart").document(id).set(cartModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                dialog.setContentView(R.layout.added_to_cart_dialog);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                okay_text = dialog.findViewById(R.id.okay_text);
                okay_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

    }
    private void progress() {
        dialog = new Dialog(detailed_product.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_progress_bar);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l)
            {
            }

            @Override
            public void onFinish()
            {
                dialog.dismiss();
            }
        }.start();
    }

}