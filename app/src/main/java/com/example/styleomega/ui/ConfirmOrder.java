package com.example.styleomega.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;


import com.example.styleomega.Model.Cart;
import com.example.styleomega.Model.Customer;
import com.example.styleomega.Model.DefaultAddress;
import com.example.styleomega.Model.Product;
import com.example.styleomega.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ConfirmOrder extends AppCompatActivity {
    double totalAmount;
    String username;
    DefaultAddress address=null;
    Button cofirmButton;
    EditText appNotext,streetText,cityText,districtText,zipText;
    CheckBox checkbox;
    SharedPreferences shared;
    DatabaseReference orderListRef;
    DatabaseReference productRef;
    DatabaseReference addressRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        totalAmount=getIntent().getDoubleExtra("total",0.0);
        setContentView(R.layout.activity_confirm_order);
        appNotext=findViewById(R.id.appNo_input);
        streetText=findViewById(R.id.streetInput);
        cityText=findViewById(R.id.cityInput);
        districtText=findViewById(R.id.districtInput);
        zipText=findViewById(R.id.zipInput);
        checkbox=findViewById(R.id.address_checkbox);
        cofirmButton=findViewById(R.id.confirmOrder);
        productRef=FirebaseDatabase.getInstance().getReference().child("Products");
        addressRef=FirebaseDatabase.getInstance().getReference().child("Address");
        shared = getSharedPreferences("shared", Context.MODE_PRIVATE);
        username=shared.getString("uname","");
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(checkbox.isChecked()){
                  getDefaultAddress();
                }
            }
        });
        cofirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkbox.isChecked()){
                    saveAddress();
                }
                if(validateFields()){
                    conirmOrder();
                    Intent intent=new Intent(ConfirmOrder.this,Thankyou.class);
                    startActivity(intent);
                   // FancyToast.makeText(ConfirmOrder.this,"Your Order has been confirmed.",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                }else{
                    FancyToast.makeText(ConfirmOrder.this,"All fields must be filled.",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
                }
            }
        });
    }



    private void conirmOrder() {
        orderListRef=FirebaseDatabase.getInstance().getReference().child("Orders");
        final HashMap<String,Object> orderList=new HashMap<>();
        Random r=new Random();
        String orderID;
        orderID=username+r.nextInt(1000000000);
        for(Cart c:ShoppingCart.cartItems){
            orderList.put("productId",c.getProductId());
            orderList.put("name",c.getName());
            orderList.put("price",c.getPrice());
            orderList.put("quantity",c.getQuantity());
            orderListRef.child(username).child(c.getProductId()).updateChildren(orderList);
        }
        orderListRef=FirebaseDatabase.getInstance().getReference().child("Cart List").child("User View");
        orderListRef.child(username)
                .removeValue();
        updateDatabase();
        ShoppingCart.cartItems.clear();
        FancyToast.makeText(ConfirmOrder.this,"Your order will be delivered within 3 business days..",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();



    }

    private void updateDatabase() {
        for(Product p:ShoppingCart.orderedProducts){
            for(Cart c:ShoppingCart.cartItems){
                if(p.getId().equals(c.getProductId())){
                    p.setQuantity(p.getQuantity()-c.getQuantity());
                }
            }
        }
        for(Product p:ShoppingCart.orderedProducts){
            HashMap<String,Object> productData=new HashMap<>();
            productData.put("id",p.getId());
            productData.put("name",p.getName());
            productData.put("price",p.getPrice());
            productData.put("quantity",p.getQuantity());
            productData.put("description",p.getDescription());
            productData.put("imageUrl",p.getImageUrl());
            productData.put("category",p.getCategory());
            productData.put("rating",5);

            productRef.child(p.getId()).updateChildren(productData);

        }
        ShoppingCart.orderedProducts.clear();
        ShoppingCart.cartItems.clear();
    }

    private boolean validateFields() {
        if(TextUtils.isEmpty(appNotext.getText().toString())||TextUtils.isEmpty(streetText.getText().toString())||TextUtils.isEmpty(cityText.getText().toString())||TextUtils.isEmpty(districtText.getText().toString())
                ||TextUtils.isEmpty(zipText.getText().toString())){
                return false;
        }else{
            return true;
        }
    }

    private void getDefaultAddress() {
        addressRef.child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                address=dataSnapshot.getValue(DefaultAddress.class);
               if(address!=null){
                   appNotext.setText(address.getAppartmentNo());
                   streetText.setText(address.getStreet());
                   cityText.setText(address.getCity());
                   districtText.setText(address.getDistrict());
                   zipText.setText(address.getZipcode());
               }else{
                   FancyToast.makeText(ConfirmOrder.this,"This is your first order..",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();

               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void saveAddress() {
        HashMap<String,Object> dataset=new HashMap<>();
        dataset.put("usernme",username);
        dataset.put("appartmentNo",appNotext.getText().toString());
        dataset.put("street",streetText.getText().toString());
        dataset.put("city",cityText.getText().toString());
        dataset.put("district",districtText.getText().toString());
        dataset.put("zipcode",zipText.getText().toString());

        addressRef.child(username).updateChildren(dataset).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                FancyToast.makeText(ConfirmOrder.this,"Your address has been saved",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();

            }
        });

    }
}
