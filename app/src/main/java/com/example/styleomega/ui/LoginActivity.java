package com.example.styleomega.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.styleomega.Model.Customer;
import com.example.styleomega.Prevalent.Prevalent;
import com.example.styleomega.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    EditText usernameText,passwordText;
    Button loginButton;
    ProgressDialog loadingBar;
    String username,password;
    private CheckBox checkBox;
    TextView admin;
    private SharedPreferences mPreferences;
    private  SharedPreferences.Editor mEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        admin=(TextView)findViewById(R.id.admin_link);
        loginButton=(Button)findViewById(R.id.login_button);
        usernameText=(EditText)findViewById(R.id.login_username_input);
        passwordText=(EditText)findViewById(R.id.login_password_input);
        checkBox=(CheckBox)findViewById(R.id.remmberme_checkbox);
        SharedPreferences sharedPreferences=this.getSharedPreferences("shared", Context.MODE_PRIVATE);

        mEditor=sharedPreferences.edit();

        Paper.init(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    login();

            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this, AdminLogin.class);
                startActivity(intent);
            }
        });
    }

    private void login() {


        username=usernameText.getText().toString();
        password=passwordText.getText().toString();

        AuthenticateAccount(username,password);
        






    }

    private void AuthenticateAccount(final String username, final String password) {
        List<Customer> customers=Customer.findWithQuery(Customer.class,"Select * from Customer where username =?",username);
        if(customers.isEmpty()){
            FancyToast.makeText(LoginActivity.this,"The username does not exist.",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
        }
        else{
            for(Customer c:customers){
                if(c.getUsername().equals(username)&&c.getPassword().equals(password)){
                    if(checkBox.isChecked()){
                        Paper.book().write(Prevalent.username,username);
                        Paper.book().write(Prevalent.password,password);
                    }
                    Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    mEditor.putString("uname",username);
                    mEditor.apply();
                    break;
                }else{
                    FancyToast.makeText(LoginActivity.this,"Invalid password.",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();

                }
            }

            }
        }




}
