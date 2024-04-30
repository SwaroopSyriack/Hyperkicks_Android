package com.example.hyperkicks;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class productAdapter extends RecyclerView.Adapter<Viewholder> {

    Context context;
    ArrayList<productModel> arrayList;

    public productAdapter(Context context, ArrayList<productModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        productModel add = arrayList.get(position);
        Glide.with(context).load(arrayList.get(position).getImgUrl()).into(holder.product_img);
        holder.product_name.setText(add.getName());
        holder.product_price.setText(add.getMrp());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,detailed_product.class);
                intent.putExtra("model",add);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}

class Viewholder extends RecyclerView.ViewHolder{

    ImageView product_img;
    TextView product_name;
    TextView product_price;

    public Viewholder(@NonNull View itemView) {
        super(itemView);
        product_img = itemView.findViewById(R.id.productImg);
        product_name = itemView.findViewById(R.id.productName);
        product_price = itemView.findViewById(R.id.product_price);
    }
}
