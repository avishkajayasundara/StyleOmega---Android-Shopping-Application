package com.example.styleomega.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.styleomega.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.HashMap;
import java.util.Random;

public class ContactPage extends AppCompatActivity {

    CardView card1,card2;
    EditText nameText,subjectText,emailText,messageText;
    Button contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_page);
        card1=findViewById(R.id.card_call);
        card2=findViewById(R.id.card_email);
        nameText=findViewById(R.id.contact_nameInput);
        emailText=findViewById(R.id.contact_emailInput);
        subjectText=findViewById(R.id.contact_subjectInput);
        messageText=findViewById(R.id.contact_messageInput);
        contact=findViewById(R.id.makeInquiry);

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:+94770323035"));
                startActivity(Intent.createChooser(intent, "Choose Application"));
               // startActivity(intent);
            }
        });
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{"styleO@gmail.com"});
                startActivity(emailIntent);
            }
        });
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Random r=new Random();
                int x=r.nextInt(999999999);
                HashMap<String,Object> dataset=new HashMap<>();
                dataset.put("id",x);
                dataset.put("name",nameText.getText().toString());
                dataset.put("subject",subjectText.getText().toString());
                dataset.put("email",emailText.getText().toString());
                dataset.put("messaage",messageText.getText().toString());

                DatabaseReference inquiryRef= FirebaseDatabase.getInstance().getReference().child("Inquiry").child(x+"");
                inquiryRef.child(""+x).updateChildren(dataset).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        FancyToast.makeText(ContactPage.this,"Our team will get back to you as soon as possible.",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                        Intent intent=new Intent(ContactPage.this,HomeActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });

    }
}
