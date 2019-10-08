package com.example.styleomega.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.styleomega.Model.Product;
import com.example.styleomega.R;
import com.example.styleomega.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements  OnNavigationItemSelectedListener {
String username;
Product p;
    String input="";
    SharedPreferences shared;
    TextView nameText;
    Toolbar toolbar;
    private AppBarConfiguration mAppBarConfiguration;
    FloatingActionButton fab;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    View headerView;
    EditText search;
    FloatingActionButton searchButton;
    private DatabaseReference productRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView=findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        search=findViewById(R.id.search_input);
        searchButton=findViewById(R.id.searchProducts);

        productRef= FirebaseDatabase.getInstance().getReference().child("Products");

        //  Toast.makeText(HomeActivity.this,username,Toast.LENGTH_SHORT).show();
         toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

         fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
               // personalProfile();
                Intent intent= new Intent(HomeActivity.this,ShoppingCart.class);
                startActivity(intent);
            }
        });
         drawer = findViewById(R.id.drawer_layout);
         toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
         navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(HomeActivity.this);



       headerView=navigationView.getHeaderView(0);
        nameText=headerView.findViewById(R.id.home_user_name);
        shared = getSharedPreferences("shared", Context.MODE_PRIVATE);
        username=shared.getString("uname","");
        nameText.setText(username);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input=search.getText().toString();
                Intent intent=new Intent(HomeActivity.this,SearchedProducts.class);
                intent.putExtra("input",input);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onStart() {

        super.onStart();

        FirebaseRecyclerOptions<Product> options=new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(productRef,Product.class)
                .build();
        FirebaseRecyclerAdapter<Product,ProductViewHolder> adapter=new FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ProductViewHolder holder, int position, @NonNull final Product model) {
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
                        Intent intent=new Intent(HomeActivity.this,ProductDetailsActivity.class);
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
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void personalProfile() {
        Intent intent=new Intent(HomeActivity.this,ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public void onBackPressed(){
       // DrawerLayout drawer=findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        item.setChecked(true);
        drawer.closeDrawers();
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
       switch(id){
           case R.id.nav_profile:{
               personalProfile();
               break;
           }
           case R.id.nav_cart:{
               Intent intent= new Intent(HomeActivity.this,ShoppingCart.class);
               startActivity(intent);
               break;
           }
           case R.id.nav_categories:{
               Intent intent=new Intent(HomeActivity.this, CategoriesMenu.class);
               startActivity(intent);
                break;
           }
           case R.id.nav_orders:{
               Intent intent=new Intent(HomeActivity.this,MyOrders.class);
               startActivity(intent);
               break;
           }
           case R.id.nav_settings:{
               Intent intent=new Intent(HomeActivity.this,ContactPage.class);
               startActivity(intent);
                break;
           }
           case R.id.nav_logout:{
               Paper.book().destroy();
               Intent intent=new Intent(HomeActivity.this,MainActivity.class);
               startActivity(intent);
               break;
           }
           case R.id.search:{
               Intent intent=new Intent(HomeActivity.this,SearchedProducts.class);
               intent.putExtra("input","");
               startActivity(intent);
               break;
           }
       }
     //  DrawerLayout drawer=findViewById(R.id.drawer_layout);
       drawer.closeDrawer(GravityCompat.START);
       return true;
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
