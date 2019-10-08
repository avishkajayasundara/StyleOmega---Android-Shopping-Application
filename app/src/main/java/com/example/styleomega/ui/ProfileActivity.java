package com.example.styleomega.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.styleomega.Model.Customer;
import com.example.styleomega.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class ProfileActivity extends AppCompatActivity {
     EditText nameText;
     EditText emailText;
     EditText contactText;
     EditText usernameText;
     EditText passwordText;
     TextView close,update;
     String newEmail,newContact,username,newPassword;
     SharedPreferences shared;
     boolean updateClicked=false;
     Customer currentCustomer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        nameText = findViewById(R.id.profile_full_name);
        emailText = findViewById(R.id.profile_email);
        contactText = findViewById(R.id.profile_contact);
        usernameText = findViewById(R.id.profile_uname);
        passwordText = findViewById(R.id.profile_password);
        close = findViewById(R.id.close_profile);
        update = findViewById(R.id.update_profile);

        shared = getSharedPreferences("shared", Context.MODE_PRIVATE);
        username = shared.getString("uname", "");
        setTextFields();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!updateClicked){
                    enableUpdate();
                    updateClicked=true;
                }else {
                    currentCustomer.setContact(contactText.getText().toString());
                    currentCustomer.setEmail(emailText.getText().toString());
                    currentCustomer.setPassword(passwordText.getText().toString());
                    currentCustomer.save();
                    updateClicked=false;
                    Intent intent=new Intent(ProfileActivity.this,HomeActivity.class);
                    startActivity(intent);


                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfileActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
        }

    private void enableUpdate() {
        emailText.setInputType(InputType.TYPE_CLASS_TEXT);
        passwordText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        contactText.setInputType(InputType.TYPE_CLASS_PHONE);
        passwordText.setEnabled(true);


    }

    private void setTextFields() {

        List<Customer> customers = Customer.findWithQuery(Customer.class, "Select * from Customer where username =?", username);
        for (Customer c : customers) {
            nameText.setText(c.getFname() + " " + c.getLname());
            emailText.setText(c.getEmail());
            contactText.setText(c.getContact());
            usernameText.setText(c.getUsername());
            passwordText.setText(c.getPassword());
            nameText.setInputType(InputType.TYPE_NULL);
            emailText.setInputType(InputType.TYPE_NULL);
            contactText.setInputType(InputType.TYPE_NULL);
            usernameText.setInputType(InputType.TYPE_NULL);

            passwordText.setEnabled(false);
            currentCustomer=c;

    }


}
}
