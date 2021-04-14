package com.example.sellmybooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddBook extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference dbReference,bookDbReference;
    private String userID;

    private EditText ed_bookName, ed_bookDescription, ed_author, ed_publisher, ed_price;
    private Button btn_addBook;

    private TextView txt_userName;
    private TextView txt_userEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        user = FirebaseAuth.getInstance().getCurrentUser();
        dbReference = FirebaseDatabase.getInstance().getReference("Users");
        bookDbReference = FirebaseDatabase.getInstance().getReference("Books");
        userID = user.getUid();

        txt_userName = (TextView)findViewById(R.id.txt_abUsername);
        txt_userEmail = (TextView)findViewById(R.id.txt_abEmail);
        ed_bookName = (EditText)findViewById(R.id.ed_abBookName);
        ed_bookDescription = (EditText)findViewById(R.id.ed_abDescription);
        ed_author = (EditText)findViewById(R.id.ed_abAuthorName);
        ed_publisher = (EditText)findViewById(R.id.ed_abPublisher);
        ed_price = (EditText)findViewById(R.id.ed_abPrice);
        btn_addBook=(Button)findViewById(R.id.btn_addBook);


        dbReference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null)
                {
                    String userName = userProfile.name;
                    String userEmail = userProfile.email;
                    String userPhoneNo = userProfile.phoneNo;

                    txt_userName.setText(userName);
                    txt_userEmail.setText(userEmail);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddBook.this,"Something went wrong!",Toast.LENGTH_LONG).show();
            }
        });

        btn_addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertBookData();
            }
        });



    }

    private void insertBookData() {

        String username = txt_userName.getText().toString().trim();
        String userEmail = txt_userEmail.getText().toString().trim();
        String bookName = ed_bookName.getText().toString().trim();
        String bookDesc = ed_bookDescription.getText().toString().trim();
        String bookAuthor = ed_author.getText().toString().trim();
        String bookPublisher = ed_publisher.getText().toString().trim();
        String bookPrice = ed_price.getText().toString().trim();

        BooksDetails mybook = new BooksDetails(username,userEmail,bookName,bookDesc,bookAuthor,bookPublisher,bookPrice);

        bookDbReference.push().setValue(mybook);
        Toast.makeText(AddBook.this,"Data Inserted",Toast.LENGTH_SHORT).show();
    }
}