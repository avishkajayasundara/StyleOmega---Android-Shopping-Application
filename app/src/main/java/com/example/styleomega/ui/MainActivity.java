package com.example.styleomega.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.styleomega.Model.Customer;
import com.example.styleomega.Prevalent.Prevalent;
import com.example.styleomega.R;


import java.util.List;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences mPreferences;
    private  SharedPreferences.Editor mEditor;
    private Button joinNowButton,loginButton;
    TextView contactUs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactUs=findViewById(R.id.contactUsMain);

        joinNowButton=(Button)findViewById(R.id.main_join_now_btn);
        loginButton=(Button)findViewById(R.id.main_login_btn);
        SharedPreferences sharedPreferences=this.getSharedPreferences("shared", Context.MODE_PRIVATE);

        mEditor=sharedPreferences.edit();

        Paper.init(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent=new Intent(MainActivity.this, LoginActivity.class);
              startActivity(intent);


            }
        });
        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        String uname=Paper.book().read(Prevalent.username);
        String pwd=Paper.book().read(Prevalent.password);
        if(uname!=""&&pwd!=""){
            if(!TextUtils.isEmpty(uname)&&!TextUtils.isEmpty(pwd)){
                AllowAccess(uname,pwd);
                Toast.makeText(MainActivity.this,"Already Logged in",Toast.LENGTH_SHORT).show();

            }
        }
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ContactPage.class);
                startActivity(intent);
            }
        });
    }

    private void AllowAccess(final String username,final String password) {
        List<Customer> customers=Customer.findWithQuery(Customer.class,"Select * from Customer where username =? and password=?",username,password);
        if(!customers.isEmpty()){
            Toast.makeText(MainActivity.this,"Welcome Back...",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(MainActivity.this, HomeActivity.class);
            mEditor.putString("uname",username);
            mEditor.apply();
            startActivity(intent);
        }




    }


}
