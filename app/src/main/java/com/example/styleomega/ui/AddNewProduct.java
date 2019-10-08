package com.example.styleomega.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.styleomega.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class AddNewProduct extends AppCompatActivity {
    private String name;
    private String id;

    private double price=0;
    private int quantity=0;
    private String description;
    private String category;
    EditText nameText,descriptionText,priceText,quantityText,categoryText,productIdText;
    Button addButton;
    ImageView productImage;
    private Uri ImageUri;
    private String url;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    private StorageReference productImageReference;
    private static final int galleryPick=1;

    private DatabaseReference productRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);
        category=getIntent().getExtras().get("category").toString();
        productImageReference=storage.getReferenceFromUrl("gs://styleomega-d4c99.appspot.com/");
       productImageReference= FirebaseStorage.getInstance().getReference().child("Product Images");
        productRef=FirebaseDatabase.getInstance().getReference().child("Products");


        nameText=findViewById(R.id.addproduct_name);
        descriptionText=findViewById(R.id.description);
        priceText=findViewById(R.id.price);
        quantityText=findViewById(R.id.quantity);
        categoryText=findViewById(R.id.category);
        categoryText.setText(category);
        addButton=findViewById(R.id.add_new_product_button);
        productImage=findViewById(R.id.select_product_image);
        productIdText=findViewById(R.id.product_id);

        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGalery();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 addProduct();
            }
        });




    }

    private void addProduct() {
            name=nameText.getText().toString();
            price=Double.parseDouble(priceText.getText().toString());
            quantity=Integer.parseInt(quantityText.getText().toString());
            description=descriptionText.getText().toString();
            id=productIdText.getText().toString();
            saveProduct();

        if(ImageUri==null){
            Toast.makeText(AddNewProduct.this,"Product Image is Required",Toast.LENGTH_SHORT).show();

        }
        else
        if(TextUtils.isEmpty(description)||TextUtils.isEmpty(name)||price==0||quantity==0){
            Toast.makeText(AddNewProduct.this,"Fields cannot be left blank",Toast.LENGTH_SHORT).show();

        }
        else{
            final StorageReference filePath=productImageReference.child(ImageUri.getLastPathSegment()+id+".jpg");
            final UploadTask uploadTask=filePath.putFile(ImageUri);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddNewProduct.this,""+e.toString(),Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AddNewProduct.this,"Product Image uploaded successfully",Toast.LENGTH_SHORT).show();

                    Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if(task.isSuccessful()){
                                url=filePath.getDownloadUrl().toString();
                            }

                            return filePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if(task.isSuccessful()){
                                url=task.getResult().toString();
                                Toast.makeText(AddNewProduct.this,"Image URL was retrieved",Toast.LENGTH_SHORT).show();
                                saveProduct();
                            }
                        }
                    });

                }
            });

        }

    }

    private void saveProduct() {
        HashMap<String,Object> productData=new HashMap<>();
        productData.put("id",id);
        productData.put("name",name);
        productData.put("price",price);
        productData.put("quantity",quantity);
        productData.put("description",description);
        productData.put("imageUrl",url);
        productData.put("category",category);
        productData.put("rating",0);

        productRef.child(id).updateChildren(productData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent intent=new Intent(AddNewProduct.this,AdminHome.class);
                    startActivity(intent);
                    Toast.makeText(AddNewProduct.this,"Product was successfully added",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(AddNewProduct.this,"Error : "+task.getException(),Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void openGalery() {
        Intent galleryIntent=new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,galleryPick);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==galleryPick&&resultCode==RESULT_OK&&data!=null){
            ImageUri=data.getData();
            productImage.setImageURI(ImageUri);
        }
    }
}
