package com.example.hyperkicks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.MyVeiwholder2> {

    Context context;
    ArrayList<CartModel> arrayList;

    public cartAdapter(Context context) {
        this.context = context;
        arrayList = new ArrayList<>();
    }

    public void add_product(CartModel cartModel){
        arrayList.add(cartModel);
        notifyDataSetChanged();
    }

    public List<CartModel> getselecteditems(){
        List<CartModel> cartitems = new ArrayList<>();
        for(int i = 0;i<arrayList.size();i++){
            if(arrayList.get(i).is_selected){
                cartitems.add(arrayList.get(i));
            }
        }
        return cartitems;

    }

    @NonNull
    @Override
    public MyVeiwholder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item,parent,false);
        return new MyVeiwholder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVeiwholder2 holder, int position) {
        CartModel add = arrayList.get(position);
        Glide.with(context).load(arrayList.get(position).getProductImageUrl()).into(holder.productimg);
        holder.productname.setText(add.getProductName());
        holder.qty.setText(add.getProductQty());
        holder.size.setText(add.getProductsize());
        holder.price.setText(add.getProductPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(add.is_selected){
                    add.is_selected = false;
                    holder.main.setBackgroundColor(context.getResources().getColor(R.color.white));
                }
                else{
                    holder.main.setBackgroundColor(context.getResources().getColor(R.color.teal_700));
                    add.is_selected = true;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyVeiwholder2 extends RecyclerView.ViewHolder {

        ImageView productimg;
        TextView productname,price,qty,size;
        private final LinearLayout main;

        public MyVeiwholder2(@NonNull View itemView) {
            super(itemView);
            productimg = itemView.findViewById(R.id.cartitemImage);
            productname = itemView.findViewById(R.id.productName);
            price = itemView.findViewById(R.id.cartproductPrice);
            qty =itemView.findViewById(R.id.cartproductQuantity);
            size = itemView.findViewById(R.id.size);
            main = itemView.findViewById(R.id.thelayout1);
        }
    }
}
