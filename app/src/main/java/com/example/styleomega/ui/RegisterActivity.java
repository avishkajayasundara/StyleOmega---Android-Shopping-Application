package com.example.styleomega.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.styleomega.Model.Customer;
import com.example.styleomega.R;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    Button nextButton;
    TextView firstNameText,lastNameText,contactText,emailText,usernameText,passwordText;

    String firstName,lastName,contact,email,username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firstNameText=(TextView)findViewById(R.id.fname_input);
        lastNameText=(TextView)findViewById(R.id.lname_input);
        contactText=(TextView)findViewById(R.id.mobile_input);
        emailText=(TextView)findViewById(R.id.email_input);
        usernameText=(TextView)findViewById(R.id.uname_input);
        passwordText=(TextView)findViewById(R.id.pwd_input);
        nextButton=(Button)findViewById(R.id.next_btn);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });


    }

    private void CreateAccount() {
        firstName=firstNameText.getText().toString();
        lastName=lastNameText.getText().toString();
        contact=contactText.getText().toString();
        email=emailText.getText().toString();
        username=usernameText.getText().toString();
        password=passwordText.getText().toString();
        ValidateUser(username,password);
    }

    private void ValidateUser(final String uname,final String _email) {

        List<Customer> customers=Customer.findWithQuery(Customer.class,"Select * from Customer where username =?",uname);
        if(!customers.isEmpty()){
            Toast.makeText(RegisterActivity.this,"The Username already exists...",Toast.LENGTH_SHORT).show();

        }else{
            customers=Customer.findWithQuery(Customer.class,"Select * from Customer where email =?",_email);
            if(!customers.isEmpty()){
                Toast.makeText(RegisterActivity.this,"You have already registered.Please Login...",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }else{
                Customer customer=new Customer( firstName,  lastName,  email,  contact,  username,  password);
                customer.save();
                Toast.makeText(RegisterActivity.this,"You have registered Successfully.Please Login...",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }
    }

}
