package com.example.styleomega.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.styleomega.Model.Product;
import com.example.styleomega.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.Button;
import com.rey.material.widget.FloatingActionButton;
import com.rey.material.widget.ImageView;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {
    boolean canOrder=true;
    android.widget.Button add;
    private FloatingActionButton share;
    Product product;
    ImageView image;
   // private ElegantNumberButton increment;
    private TextView nameText,descriptionText,priceText,quantityText,ratingText;
    String id=null;
    Button addQuantity,reduceQuantity;
    int maxQuantity;
    int currentquantity=1;
    EditText selectedQuantity,totalPrice;
    double price, total;
    SharedPreferences shared;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        add=findViewById(R.id.addToCart);
        id=getIntent().getStringExtra("id");
        share=findViewById(R.id.shareLink);
        image=(ImageView) findViewById(R.id.productDetails_image);
    // increment=findViewById(R.id.selected_quantity);
        nameText=findViewById(R.id.details_name);
        descriptionText=findViewById(R.id.details_description);
        priceText=findViewById(R.id.details_price);
        quantityText=findViewById(R.id.details_quantity);
        ratingText=findViewById(R.id.details_rating);
        addQuantity=findViewById(R.id.addQuantity);
        reduceQuantity=findViewById(R.id.reduceQuantity);
        selectedQuantity=findViewById(R.id.selectedQuantity);
        totalPrice=findViewById(R.id.displayTotal);
        shared = getSharedPreferences("shared", Context.MODE_PRIVATE);
        username=shared.getString("uname","");
        getProductDetails();
        add=findViewById(R.id.addToCart);
        addQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentquantity<maxQuantity){
                    currentquantity++;
                    selectedQuantity.setText(""+currentquantity);
                    total=currentquantity*price;
                    totalPrice.setText(" Rs."+total);
                }
            }
        });
        reduceQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentquantity>1){
                    currentquantity--;
                    selectedQuantity.setText(""+currentquantity);
                    total=currentquantity*price;
                    totalPrice.setText(" Rs."+total);
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(canOrder){
                    addToCart();
                    ShoppingCart.orderedProducts.add(product);
                    FancyToast.makeText(ProductDetailsActivity.this,"Item was added to the cart",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();

                }else{
                    FancyToast.makeText(ProductDetailsActivity.this,"This item is out of stock",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();

                }

            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,"MY NEW APP");
                intent.putExtra(Intent.EXTRA_TEXT,nameText.getText().toString());
                startActivity(Intent.createChooser(intent,"Share via"));
            }
        });
    }

    private void addToCart() {
       final DatabaseReference cartListRef=FirebaseDatabase.getInstance().getReference().child("Cart List");
        final HashMap<String,Object> cartMap=new HashMap<>();
        cartMap.put("productId",id);
        cartMap.put("name",product.getName());
        cartMap.put("price",product.getPrice());
        cartMap.put("quantity",currentquantity);

        cartListRef.child("User View").child(username).child("Products").
                child(id).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
           if(task.isSuccessful()){
                //Toast.makeText(ProductDetailsActivity.this,"Item was added to the cart",Toast.LENGTH_SHORT).show();
               Intent intent=new Intent(ProductDetailsActivity.this,HomeActivity.class);
               startActivity(intent);
           }
            }
        });



    }

    private void getProductDetails() {
        DatabaseReference productRef= FirebaseDatabase.getInstance().getReference().child("Products");
        productRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 product= dataSnapshot.getValue(Product.class);
                nameText.setText(product.getName());
                descriptionText.setText(product.getDescription());
                quantityText.setText("Quantity ; "+product.getQuantity());
                priceText.setText("Rs."+product.getPrice());
                ratingText.setText("Rating ; "+product.getRating());
                Picasso.get().load(product.getImageUrl()).into(image);
                maxQuantity=product.getQuantity();
                total=product.getPrice();
                price=product.getPrice();
                totalPrice.setText(" Rs."+total);
                if(product.getQuantity()==0){
                    canOrder=false;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
}
