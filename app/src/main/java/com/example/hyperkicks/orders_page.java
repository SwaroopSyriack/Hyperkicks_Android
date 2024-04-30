package com.example.hyperkicks;

import static com.example.hyperkicks.cartactivity.cartList;
import static com.example.hyperkicks.cartactivity.cartitems;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.hyperkicks.databinding.ActivityCartactivityBinding;
import com.example.hyperkicks.databinding.ActivityOrdersPageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.StringValue;

import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

public class orders_page extends AppCompatActivity {

    ActivityOrdersPageBinding binding;
    int minTotal = 0;

    private String fname_o,lname_o,address_o,state_o,email_o,phno_o;

    Dialog dialog;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrdersPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.checkoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname_o = binding.fNamee.getText().toString();
                lname_o = binding.lName2.getText().toString();
                address_o = binding.address.getText().toString();
                state_o = binding.state.getText().toString();
                email_o = binding.maile.getText().toString();
                phno_o = binding.phNo.getText().toString();
                place_order();


            }
        });

        binding.imagearraow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(orders_page.this,detailed_product.class));
            }
        });
    }

    private void place_order() {
        progress();
        String order_number = String.valueOf(random_number(100000,999999));
        orderModel model = new orderModel(order_number,fname_o,lname_o,state_o,address_o,String.valueOf(minTotal),"220",null,"Tcs",String.valueOf(Calendar.getInstance().getTimeInMillis()),"Pending",firebaseAuth.getUid());
        FirebaseFirestore.getInstance().collection("orders").document(order_number).set(model);
        for(int i=0;i<cartitems.size();i++){
            CartModel model1 = cartitems.get(i);
            model1.setOrdernumber(order_number);
            String id = UUID.randomUUID().toString();
            model1.setCartid(id);
            FirebaseFirestore.getInstance().collection("orderProducts").document(id).set(model1);
        }
        Toast.makeText(getApplicationContext(), "Order Placed", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(orders_page.this,MainActivity.class));

    }

    public static int random_number(int min,int max){
        return (new Random()).nextInt((max-min)+1) + min;
    }

    private void progress() {
        dialog = new Dialog(orders_page.this);
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


    @Override
    protected void onStart() {
        super.onStart();
        for(int i = 0;i<cartitems.size();i++){
            CartModel cartModel = cartitems.get(i);
            int price = Integer.parseInt(cartModel.getProductPrice());
            int qty = 1;
            int Total = price*qty;
            minTotal += Total;
        }
        binding.total1.setText(String.valueOf(minTotal));
        binding.delivery.setText("100");
        binding.mintotal.setText(String.valueOf(minTotal+100));
    }
}

