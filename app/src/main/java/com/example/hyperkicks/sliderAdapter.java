package com.example.hyperkicks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class sliderAdapter extends RecyclerView.Adapter<sliderAdapter.Veiwholder1> {

    ArrayList<sliderModel> arrayList;
    Context context;

    public sliderAdapter(ArrayList<sliderModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public Veiwholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.slider_item,parent,false);
        return new Veiwholder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Veiwholder1 holder, int position) {
        Glide.with(context).load(arrayList.get(position).getImageURL()).into(holder.imgview);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class Veiwholder1 extends Viewholder{

        ImageView imgview;
        public Veiwholder1(@NonNull View itemView) {
            super(itemView);
            imgview = itemView.findViewById(R.id.imageView4);
        }
    }
}


