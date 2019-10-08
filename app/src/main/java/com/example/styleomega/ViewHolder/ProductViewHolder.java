package com.example.styleomega.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.styleomega.Interfaces.ItemClickListener;
import com.example.styleomega.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView productNameText,descriptionText,priceText;
    public  TextView quantityText,ratingText;
    public ImageView imageView;
    public ItemClickListener listener;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        productNameText=(TextView)itemView.findViewById(R.id.product_name);
        descriptionText=(TextView)itemView.findViewById(R.id.product_Description);
        priceText=(TextView)itemView.findViewById(R.id.product_Price);
        quantityText=(TextView)itemView.findViewById(R.id.product_Quantity);
        ratingText=(TextView)itemView.findViewById(R.id.product_Rating);
        imageView=(ImageView) itemView.findViewById(R.id.product_image);

    }

    public void setItemClickListener(ItemClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view,getAdapterPosition(),false);
    }
}
