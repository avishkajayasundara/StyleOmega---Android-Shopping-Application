package com.example.styleomega.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.example.styleomega.Model.Order;
import com.example.styleomega.R;
import com.example.styleomega.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyOrders extends AppCompatActivity {
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private SharedPreferences shared;
    private String username;
    FirebaseRecyclerAdapter<Order, CartViewHolder> adapter;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        recyclerView=findViewById(R.id.order_recycler);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        shared = getSharedPreferences("shared", Context.MODE_PRIVATE);
        username=shared.getString("uname","");

    }

    @Override
    protected void onStart() {
        super.onStart();

        reference= FirebaseDatabase.getInstance().getReference().child("Orders").child(username);
        FirebaseRecyclerOptions<Order> options=new FirebaseRecyclerOptions.Builder<Order>()
                .setQuery(reference,Order.class)
                .build();
        adapter=new FirebaseRecyclerAdapter<Order, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Order model) {
                holder.productNameText.setText(model.getName());
                holder.priceTxt.setText("Product Price : Rs."+model.getPrice());
                holder.quantityText.setText("Quantity : "+model.getQuantity());
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
                CartViewHolder holder=new CartViewHolder(view);
                return holder;            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
