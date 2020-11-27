package com.example.bookworm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PostingBook extends AppCompatActivity  {

    EditText bookName,discrip,pri;
    Button btnPost;

    DatabaseReference reference;
    Book_Details bookdetails;
    long maxid =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting_book);

        bookName = (EditText)findViewById(R.id.bookname);
        discrip = (EditText)findViewById(R.id.discription);
        pri = (EditText)findViewById(R.id.price);
        btnPost = (Button)findViewById(R.id.buttonPost);

        bookdetails =new Book_Details();
        reference= FirebaseDatabase.getInstance().getReference().child("Book_Details");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    maxid =(snapshot.getChildrenCount());}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = bookName.getText().toString().trim();
                String pric= pri.getText().toString().trim();

                bookdetails.setBdiscrip(discrip.getText().toString().trim());
                bookdetails.setBname(name);
                bookdetails.setBprice(pric);


                reference.child(String.valueOf(maxid+1)).setValue(bookdetails);
                Toast.makeText(PostingBook.this,"Book Details Inserted Successfully",Toast.LENGTH_LONG).show();



            }
        });











        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.post);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.post:
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),PublisherHomeNavi.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });



    }
}