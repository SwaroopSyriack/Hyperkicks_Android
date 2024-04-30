package com.example.hyperkicks;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class brandAdapter extends RecyclerView.Adapter<MyVeiwholder> {

    Context context;
    ArrayList<brandModel> arrayList;

    public brandAdapter(Context context, ArrayList<brandModel> arraylist) {
        this.context = context;
        arrayList = arraylist;
    }

    @NonNull
    @Override
    public MyVeiwholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.brand_item,parent,false);
        return new MyVeiwholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVeiwholder holder, int position) {
        brandModel add = arrayList.get(position);
        Glide.with(context).load(arrayList.get(position).getImageUrl()).into(holder.brand_img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,view_brands.class);
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

class MyVeiwholder extends ViewHolder{
    ImageView brand_img;
    public MyVeiwholder(@NonNull View itemView) {
        super(itemView);
        brand_img = (ImageView) itemView.findViewById(R.id.brand_img);




    }
}
