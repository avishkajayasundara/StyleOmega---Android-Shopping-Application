package com.example.styleomega.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.styleomega.Interfaces.Api;
import com.example.styleomega.Model.Product;
import com.example.styleomega.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rey.material.widget.ImageView;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminHome extends AppCompatActivity {
ImageView tshirts,sports,dresses,sweaters,shades,bags,hats,shoes,
        trousers,children,watch,tops;
String category;
Button getProducts;
Gson gson=new Gson();
TextView logout;
List<Product> products;
Retrofit retrofit;
    private DatabaseReference productRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productRef= FirebaseDatabase.getInstance().getReference().child("Products");
        setContentView(R.layout.activity_admin_home);
        tshirts=(ImageView)findViewById(R.id.tshirts_img);
        sports=(ImageView)findViewById(R.id.sports_img);
        dresses=(ImageView)findViewById(R.id.female_dresses_img);
        sweaters=(ImageView)findViewById(R.id.sweather_img);
        shades=(ImageView)findViewById(R.id.glasses_img);
        bags=(ImageView)findViewById(R.id.purse_bag_img);
        hats=(ImageView)findViewById(R.id.hats_img);
        shoes=(ImageView)findViewById(R.id.shoes_img);
        trousers=(ImageView)findViewById(R.id.trousers_img);
        children=(ImageView)findViewById(R.id.children_img);
        watch=(ImageView)findViewById(R.id.hwatches_img);
        tops=(ImageView)findViewById(R.id.tops_img);
        getProducts=findViewById(R.id.syncButton);
        logout=findViewById(R.id.admin_logout);

        retrofit=new Retrofit.Builder().baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api=retrofit.create(Api.class);
        Call<List<Product>> call=api.getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                Toast.makeText(AdminHome.this,"CONNECTION SUCCESSFUL",Toast.LENGTH_LONG).show();
                products=response.body();
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(AdminHome.this,"CONNECTION ERROR",Toast.LENGTH_LONG).show();

            }
        });


        getProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                syncProducts();
            }
        });

        tshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category="tshirts";
                startIntent(category);
            }
        });
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category="sports";
                startIntent(category);
            }
        });
        dresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category="dresses";
                startIntent(category);
    }
});

        sweaters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category="sweaters";
                startIntent(category);
            }
        });
        shades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category="sunglasses";
                startIntent(category);
            }
        });
        bags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category="purses";
                startIntent(category);
            }
        });
        hats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category="hats";
                startIntent(category);
            }
        });
        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category="shoes";
                startIntent(category);
            }
        });
        trousers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category="pants";
                startIntent(category);
            }
        });
        children.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category="baby clothes";
                startIntent(category);
            }
        });
        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category="watches";
                startIntent(category);
            }
        });
        tops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category="ladies' tops";
                startIntent(category);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminHome.this,MainActivity.class);
                startActivity(intent);
                FancyToast.makeText(AdminHome.this,"Successfully Logged out",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();

            }
        });
        getProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                syncProducts();
            }
        });
    }

    private void syncProducts() {
       if(products.isEmpty()){
           FancyToast.makeText(AdminHome.this,"The List is empty",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();

       }else
           for(Product p : products){

               HashMap<String,Object> productData=new HashMap<>();
               productData.put("id",p.getId());
               productData.put("name",p.getName());
               productData.put("price",p.getPrice());
               productData.put("quantity",p.getQuantity());
               productData.put("description",p.getDescription());
               productData.put("category",p.getCategory());
               productData.put("rating",p.getRating());
               productRef.child(p.getId()).updateChildren(productData);
           }
    }




    private void startIntent(String category) {
        Intent intent=new Intent(AdminHome.this,AddNewProduct.class);
        intent.putExtra("category",category);
        startActivity(intent);
    }
}
