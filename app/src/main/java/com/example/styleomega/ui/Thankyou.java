package com.example.styleomega.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.styleomega.R;
import com.shashank.sony.fancytoastlib.FancyToast;

import io.paperdb.Paper;

public class Thankyou extends AppCompatActivity {
Button continueButton,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Paper.init(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyou);
        continueButton=findViewById(R.id.continueShopping);
        logout=findViewById(R.id.logoutButton);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Thankyou.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().destroy();
                Intent intent=new Intent(Thankyou.this,MainActivity.class);
                startActivity(intent);
                FancyToast.makeText(Thankyou.this,"Successfully logged out",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();

            }
        });
    }
}
