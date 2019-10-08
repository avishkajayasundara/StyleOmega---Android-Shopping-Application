package com.example.styleomega.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.styleomega.Interfaces.ItemClickListener;
import com.example.styleomega.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView productNameText,priceTxt,quantityText;
    public Button removeButton;
    private ItemClickListener itemClickListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        productNameText=itemView.findViewById(R.id.cartItem_productName);
        priceTxt=itemView.findViewById(R.id.cartItem_ProductPrice);
        quantityText=itemView.findViewById(R.id.cartItem_quantity);
        removeButton=itemView.findViewById(R.id.cart_removeButton);

    }

    @Override
    public void onClick(View view) {
itemClickListener.onClick(view,getAdapterPosition(),false);

    }
    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }
}
