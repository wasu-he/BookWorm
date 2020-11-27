package com.example.bookworm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PublisherHomeNavi extends AppCompatActivity {


    ListView listview;
    Button button;
    DatabaseReference reference;
    ArrayList<Book_Details> bookArray = new ArrayList<>();


    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publisher_home_navi);
        listview = findViewById(R.id.bookList);



        Intent intent = getIntent();




        reference = FirebaseDatabase.getInstance().getReference().child("Book_Details");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                ArrayList<String> bnames = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()){
                    String name = ds.child("bname").getValue().toString();
                    bnames.add(name);

                    Book_Details booklist = ds.getValue(Book_Details.class);
                    bookArray.add(booklist);

                }
                adapter = new ArrayAdapter<String>(PublisherHomeNavi.this,android.R.layout.simple_list_item_1, bnames);
                listview.setAdapter(adapter);
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedItem = (String) parent.getItemAtPosition(position);
                        Intent nextIntent = new Intent(PublisherHomeNavi.this,DeleteUpdate.class);
                        for (Book_Details bookDetails : bookArray) {
                            if (bookDetails.getBname().equals(selectedItem)){
                                nextIntent.putExtra("selectedBook", bookDetails);
                                break;
                            }
                        }
                        startActivity(nextIntent);
                        // Here we have to write the redirection to the next page
                        System.out.println(selectedItem);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.post:
                        startActivity(new Intent(getApplicationContext(),PostingBook.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        return true;

                }
                return false;
            }
        });
    }


}