package com.example.hyperkicks;

import static com.example.hyperkicks.cartactivity.cartList;
import static com.example.hyperkicks.cartactivity.cartitems;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class orderAdapter extends RecyclerView.Adapter<Viewholder3> {


    Context context;
    ArrayList<orderModel> arrayList;

    public orderAdapter(Context context, ArrayList<orderModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Viewholder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_item,parent,false);
        return new Viewholder3(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder3 holder, int position) {
        orderModel add = arrayList.get(position);
        holder.name.setText(add.getCustomer_fname());
        holder.address.setText(add.getCustomer_address());
        holder.city.setText(add.getCustomer_place());
        holder.ordernumber.setText(add.getOrdernumber());
        holder.delivery.setText(add.getDeliverystatus());


        cartAdapter cartAdapter = new cartAdapter(context);
        holder.order_recycler.setAdapter(cartAdapter);
        holder.order_recycler.setLayoutManager(new LinearLayoutManager(context));
        FirebaseFirestore.getInstance().collection("orderProducts")
                .whereEqualTo("ordernumber",add.getOrdernumber()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> documentSnapshotList = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot documentSnapshot:documentSnapshotList){
                            CartModel cartModel = documentSnapshot.toObject(CartModel.class);
                            cartAdapter.add_product(cartModel);
                        }

                    }
                });




    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}

class Viewholder3 extends RecyclerView.ViewHolder{


    TextView name;
    TextView address;
    TextView city;
    TextView ordernumber;
    TextView delivery;
    RecyclerView order_recycler;


    public Viewholder3(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.ordername);
        address = itemView.findViewById(R.id.orderaddress);
        city = itemView.findViewById(R.id.ordercity);
        ordernumber = itemView.findViewById(R.id.orderno);
        delivery = itemView.findViewById(R.id.textView9);
        order_recycler = itemView.findViewById(R.id.recyclerView2);
    }
}
