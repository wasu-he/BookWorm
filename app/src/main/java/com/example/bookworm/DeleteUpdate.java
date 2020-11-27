package com.example.bookworm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class DeleteUpdate extends AppCompatActivity {

    EditText bookname,bookdiscription,bookprice;
    Button btnUpdate,btnDelete;
    DatabaseReference reference;
    String name,discrip,pric,userId ;
    Book_Details bookDetails;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_update);

        //reference = FirebaseDatabase.getInstance().getReference("Book_Details");

        bookname = findViewById(R.id.editBookName);
        bookdiscription = findViewById(R.id.editDiscription);
        bookprice = findViewById(R.id.editPrice);
        btnUpdate = (Button)findViewById(R.id.updateBtton);
        btnDelete = (Button)findViewById(R.id.deleteButton);
        //userId = bookDetails.getBname();

        Intent intent = getIntent();
        Book_Details book = (Book_Details) intent.getSerializableExtra("selectedBook");
         name = book.getBname();
        bookname.setText(name);
        discrip = book.getBdiscrip();
        bookdiscription.setText(discrip);
        pric = book.getBprice();
        bookprice.setText(pric);
        //System.out.println(book);

        btnDelete.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteUpdate.this);
                builder.setMessage("Do you want to delete - "+name+ " ?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        bookDetails = new Book_Details();
                        reference = FirebaseDatabase.getInstance().getReference();
                        Query query = reference.child("Book_Details").orderByChild("bname").equalTo(name);
                       query.addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot snapshot) {
                               for (DataSnapshot snapshot1 : snapshot.getChildren()){
                                   snapshot1.getRef().removeValue();

                               }
                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError error) {

                           }
                       });
                       /* reference.removeValue();
                        bookname.setText("");
                        bookdiscription.setText("");
                        bookprice.setText("");*/
                    }
                }).setNegativeButton("No",null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });










    }

    /*public void update (View view){
        if (isBookNameChanged()|| isDiscriptionChanged() || isPriceChanged()){
            Toast.makeText(this,"Data has been updated",Toast.LENGTH_LONG).show();

        }
        else Toast.makeText(this,"Data is same, can not updated ",Toast.LENGTH_LONG).show();
    }

    private boolean isPriceChanged() {
        if (!pric.equals(bookprice.getText().toString())){
            reference.child("bprice").setValue(bookprice.getText().toString());

            return true;

        }else {
            return false;
        }
    }


    private boolean isDiscriptionChanged() {
        if (!discrip.equals(bookdiscription.getText().toString())){
            reference.child("bdiscrip").setValue(bookdiscription.getText().toString());

            return true;

        }else {
            return false;
        }
    }

    private boolean isBookNameChanged() {

        if (!name.equals(bookname.getText().toString())){
            reference.child("bname").setValue(bookname.getText().toString());

            return true;

        }else {
            return false;
        }
    }*/



}