package com.example.styleomega.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.styleomega.R;

import io.paperdb.Paper;

public class AdminLogin extends AppCompatActivity {
EditText usernameText,passwordText;
Button loginButton;
String username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        usernameText=(EditText)findViewById(R.id.admin_staffID);
        passwordText=(EditText)findViewById(R.id.admin_password);
        loginButton=(Button)findViewById(R.id.admin_login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username=usernameText.getText().toString();
                password=passwordText.getText().toString();
                Paper.book().destroy();
                if(username.equals("admin")&&password.equals("admin")){
                    Intent intent=new Intent(AdminLogin.this, AdminHome.class);
                    startActivity(intent);
                }else
                    Toast.makeText(AdminLogin.this,"Invalid Credentials...",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
