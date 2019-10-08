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
import android.widget.TextView;

import com.example.styleomega.Model.Product;
import com.example.styleomega.R;
import com.example.styleomega.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class CategorySearch extends AppCompatActivity {
String input="";
RecyclerView searchList;
DatabaseReference reference;
TextView heading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_search);
        input=getIntent().getStringExtra("category");
        heading=findViewById(R.id.category_name);
        heading.setText(input);
        searchList=findViewById(R.id.searchCat_recycler);
        searchList.setLayoutManager(new LinearLayoutManager(CategorySearch.this));
        onStart();
    }

    @Override
    protected void onStart() {
        super.onStart();

        reference= FirebaseDatabase.getInstance().getReference().child("Products");
        FirebaseRecyclerOptions<Product> options=new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(reference.orderByChild("category").startAt(input).endAt(input),Product.class).build();
       FirebaseRecyclerAdapter<Product,ProductViewHolder> adapter=new FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {
           @Override
           protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Product model) {
               holder.productNameText.setText(model.getName());
               holder.descriptionText.setText(model.getDescription());
               holder.priceText.setText("Rs."+model.getPrice());
               holder.quantityText.setText("Quantity : "+model.getQuantity());
               holder.ratingText.setText("Rating : "+model.getRating());
               Picasso.get().load(model.getImageUrl()).into(holder.imageView);
               holder.itemView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       Intent intent=new Intent(CategorySearch.this,ProductDetailsActivity.class);
                       intent.putExtra("id",model.getId());
                       startActivity(intent);
                   }
               });
           }
           @NonNull
           @Override
           public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout,parent,false);
               ProductViewHolder holder=new ProductViewHolder(view);
               return holder;
                       //View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout,parent,false);
           }
       };
        searchList.setAdapter(adapter);
        adapter.startListening();
    }

}
