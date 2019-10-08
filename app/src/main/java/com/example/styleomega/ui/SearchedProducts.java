package com.example.styleomega.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.styleomega.Model.Product;
import com.example.styleomega.R;
import com.example.styleomega.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

public class SearchedProducts extends AppCompatActivity {

    EditText searchInput;
    FloatingActionButton searchButton;
    RecyclerView searchList;
    String input="";
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_products);
        searchButton=findViewById(R.id.search_searchProducts);
        searchInput=findViewById(R.id.search_search_input);
        input=getIntent().getStringExtra("input");
        searchList=findViewById(R.id.searchList_recycler);
        searchList.setLayoutManager(new LinearLayoutManager(SearchedProducts.this));
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input=searchInput.getText().toString();
                onStart();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(input.equals("")){
            FancyToast.makeText(SearchedProducts.this,"Search For Products...",FancyToast.LENGTH_LONG,FancyToast.CONFUSING,true).show();

        }else{

            reference= FirebaseDatabase.getInstance().getReference().child("Products");
            FirebaseRecyclerOptions<Product> options=new FirebaseRecyclerOptions
                    .Builder<Product>().setQuery(reference.orderByChild("name").startAt(input),Product.class).
                    build();
            FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter=new FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Product model) {
                    holder.productNameText.setText(model.getName());
                    holder.descriptionText.setText(model.getDescription());
                    holder.priceText.setText("Rs."+model.getPrice());
                    holder.quantityText.setText("Quantity : "+model.getQuantity());
                    holder.ratingText.setText("Rating : "+model.getRating());
                    Picasso.get().load(model.getImageUrl()).into(holder.imageView);
                    // final Product finalModel = model;
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent(SearchedProducts.this,ProductDetailsActivity.class);
                            intent.putExtra("id",model.getId());
                            startActivity(intent);
                        }
                    });
                }
                @NonNull
                @Override
                public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout,parent,false);
                    ProductViewHolder holder=new ProductViewHolder(view);
                    return holder;
                }
            };
            searchList.setAdapter(adapter);
            adapter.startListening();
        }

    }
}
