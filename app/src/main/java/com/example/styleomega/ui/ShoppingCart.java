package com.example.styleomega.ui;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.styleomega.Model.Cart;
import com.example.styleomega.Model.Product;
import com.example.styleomega.R;
import com.example.styleomega.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shashank.sony.fancytoastlib.FancyToast;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart extends AppCompatActivity {
    int count;
    private SharedPreferences shared;
    private String username;
    public static List<Cart> cartItems=new ArrayList<Cart>();
    public static List<Product> orderedProducts=new ArrayList<Product>();
    private double shipping=0,totalOfItems=0,total=0;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button placeOrder;
    private TextView itemPriceText,shippingText,subtotalText,topTotalText;
    FirebaseRecyclerAdapter<Cart,CartViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        count=0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        recyclerView=findViewById(R.id.cart_recycler);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        placeOrder=findViewById(R.id.cart_placeOrder);
        itemPriceText=findViewById(R.id.itemTotal);
        shippingText=findViewById(R.id.shipping_price);
        subtotalText=findViewById(R.id.subTotal);
        topTotalText=findViewById(R.id.cartTotal);
        shared = getSharedPreferences("shared", Context.MODE_PRIVATE);
        username=shared.getString("uname","");
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(cartItems.isEmpty()){
                   FancyToast.makeText(ShoppingCart.this,"Your cart is empty.",FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
               }else{
                   Intent intent=new Intent(ShoppingCart.this,ConfirmOrder.class);
                   intent.putExtra("total",total);
                   startActivity(intent);
               }
            }
        });


    }
    @Override
    protected void onStart() {

        super.onStart();

        final DatabaseReference cartListRef= FirebaseDatabase.getInstance().getReference().child("Cart List");
         FirebaseRecyclerOptions<Cart> options=new FirebaseRecyclerOptions.Builder<Cart>().setQuery(cartListRef.child("User View").child(username)
                 .child("Products"),Cart.class).build();
          adapter=new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
             @Override
             protected void onBindViewHolder(@NonNull CartViewHolder holder, final int position, @NonNull final Cart model) {
                 count++;
                holder.productNameText.setText(model.getName());
                holder.priceTxt.setText("Product Price : Rs."+model.getPrice());
                holder.quantityText.setText("Quantity : "+model.getQuantity());
                cartItems.add(model);
                calcTotal();
                 itemPriceText.setText("Items : Rs."+totalOfItems );
                 shippingText.setText("Shipping : Rs."+shipping);
                 subtotalText.setText("Total : Rs."+total);
                 topTotalText.setText("Total : Rs."+total);

                 holder.removeButton.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         CharSequence options[]=new CharSequence[]
                         {
                             "Edit",
                                     "Remove"
                         };
                         AlertDialog.Builder builder=new AlertDialog.Builder(ShoppingCart.this);
                         builder.setTitle("Options");
                         builder.setItems(options, new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialogInterface, int i) {
                                 if(i==0){
                                     Intent intent=new Intent(ShoppingCart.this,ProductDetailsActivity.class);
                                     intent.putExtra("id",model.getProductId());
                                     startActivity(intent);
                                 }else{
                                     cartListRef.child("User View").child(username).child("Products")
                                             .child(model.getProductId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                         @Override
                                         public void onComplete(@NonNull Task<Void> task) {
                                             if(task.isSuccessful()){
                                                 Toast.makeText(ShoppingCart.this,"Item was removed",Toast.LENGTH_SHORT).show();
                                                 for(Cart c: cartItems){
                                                     if(model.getProductId().equals(c.getProductId())){
                                                         cartItems.remove(c);
                                                         break;
                                                     }
                                                 }
                                                 }
                                                 calcTotal();
                                                 itemPriceText.setText("Items : Rs."+totalOfItems );
                                                 shippingText.setText("Shipping : Rs."+shipping);
                                                 subtotalText.setText("Total : Rs."+total);
                                                 topTotalText.setText("Total : Rs."+total);
                                             }

                                     });
                                 }
                                 for(Product p:orderedProducts){
                                     if(model.getProductId().equals(p.getId()))
                                         orderedProducts.remove(p);
                                     break;
                                 }
                             }
                         });
                         builder.show();
                     }
                 });
           }
             @NonNull
             @Override
             public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                 View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
                 CartViewHolder holder=new CartViewHolder(view);
                 return holder;
             }
         };
         recyclerView.setAdapter(adapter);
        adapter.startListening();
        if (count == 0) {
            FancyToast.makeText(ShoppingCart.this,"Cart is empty",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
        }
    }
    public void calcTotal(){
        totalOfItems=0;
        for(Cart c:cartItems){
            totalOfItems=totalOfItems+c.getQuantity()*c.getPrice();
        }
        shipping=totalOfItems*0.1;
        total=totalOfItems+shipping;
    }
}
