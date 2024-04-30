package com.example.hyperkicks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.hyperkicks.databinding.ActivityCustomerSupportPageBinding;
import com.example.hyperkicks.databinding.ActivityMainBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class customer_support_page extends AppCompatActivity {

    private ActivityCustomerSupportPageBinding binding;
    private String BOT_KEY = "bot";
    private  String USER_KEY = "user";
    ArrayList<chatModel> arrayList;
    private RVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerSupportPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        arrayList = new ArrayList<>();
        adapter = new RVAdapter(arrayList,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);

        binding.sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.inputtext.getText().toString().isEmpty()){
                    Toast.makeText(customer_support_page.this, "Please Enter your Message", Toast.LENGTH_SHORT).show();
                    return;
                }
                getResponse(binding.inputtext.getText().toString());
                binding.inputtext.setText("");

            }
        });

    }

    private void getResponse(String message) {
        arrayList.add(new chatModel(message,USER_KEY));
        adapter.notifyDataSetChanged();
        String url  = "http://api.brainshop.ai/get?bid=180253&key=uBOfFZCD20A95giq&uid=[uid]&msg="+message;
        String BASE_URL = "http://api.brainshop.ai";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi retrofitApi =  retrofit.create(RetrofitApi.class);
        Call<msgModel> call = retrofitApi.getMessage(url);
        call.enqueue(new Callback<msgModel>() {
            @Override
            public void onResponse(Call<msgModel> call, Response<msgModel> response) {
                if (response.isSuccessful()){
                    msgModel model = response.body();
                    arrayList.add(new chatModel(model.getCnt(),BOT_KEY));
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<msgModel> call, Throwable t) {
                arrayList.add(new chatModel("Revert your Question",BOT_KEY));
                Log.e("msg",t.getMessage());
                adapter.notifyDataSetChanged();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(customer_support_page.this,MainActivity.class));
    }
}