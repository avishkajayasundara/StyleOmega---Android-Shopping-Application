package com.example.styleomega.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.styleomega.R;
import com.rey.material.widget.ImageView;

public class CategoriesMenu extends AppCompatActivity {
    ImageView tshirts,dresses,sweaters,sunglasses,bags,shoes,trousers,childClothes,watches,
    tops,hats,sports;
    String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_menu);
        tshirts=findViewById(R.id.cat_shirts_img);
        dresses=findViewById(R.id.cat_female_dresses_img);
        sweaters=findViewById(R.id.cat_sweather_img);
        sunglasses=findViewById(R.id.cat_glasses_img);
        bags=findViewById(R.id.cat_purse_bag_img);
        shoes=findViewById(R.id.cat_shoes_img);
        trousers=findViewById(R.id.cat_trousers_img);
        childClothes=findViewById(R.id.cat_children_img);
        watches=findViewById(R.id.cat_watches_img);
        tops=findViewById(R.id.cat_tops_img);
        hats=findViewById(R.id.cat_hats_img);
        sports=findViewById(R.id.cat_sports_img);

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
        sunglasses.setOnClickListener(new View.OnClickListener() {
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
        childClothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category="baby clothes";
                startIntent(category);
            }
        });
        watches.setOnClickListener(new View.OnClickListener() {
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

    }

    private void startIntent(String category) {
        Intent intent=new Intent(CategoriesMenu.this,CategorySearch.class);
        intent.putExtra("category",category);
        startActivity(intent);
    }
}
