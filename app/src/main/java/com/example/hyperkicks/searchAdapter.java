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

public class searchAdapter extends RecyclerView.Adapter<searchAdapter.Viewholder3> {

    ArrayList<productModel> arrayList;
    Context context;

    public searchAdapter(ArrayList<productModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public void setfilteredlist(ArrayList<productModel> filteredList){
        this.arrayList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Viewholder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item,parent,false);
        return new Viewholder3(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder3 holder, int position) {
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

     class Viewholder3 extends Viewholder{

        ImageView product_img;
        TextView product_name;
        TextView product_price;

        public Viewholder3(@NonNull View itemView) {
            super(itemView);

            product_img = itemView.findViewById(R.id.productImg);
            product_name = itemView.findViewById(R.id.productName);
            product_price = itemView.findViewById(R.id.product_price);
        }
    }

}
