package com.example.sellmybooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    private ImageView backImg;
    private EditText username,email_id,phone_no,password,conf_password;
    private Button btn_createAcc;
    private TextView loginActivity;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Variables Declaration

        backImg = (ImageView)findViewById(R.id.ibtn_back);
        username = (EditText)findViewById(R.id.ed_username);
        email_id = (EditText)findViewById(R.id.ed_emailar);
        phone_no = (EditText)findViewById(R.id.ed_Phone);
        password = (EditText)findViewById(R.id.ed_pass);
        conf_password = (EditText)findViewById(R.id.ed_confpass);
        btn_createAcc = (Button)findViewById(R.id.btn_createAcc);
        loginActivity = (TextView)findViewById(R.id.txt_login);
        progressBar =(ProgressBar)findViewById(R.id.regProgressBar);

        loginActivity.setOnClickListener(this);
        backImg.setOnClickListener(this);
        btn_createAcc.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.txt_login:
            case R.id.ibtn_back:
                startActivity(new Intent(this,LoginActivity.class));
                break;

            case R.id.btn_createAcc:
                registerUser();
                break;
        }
    }

    private void registerUser()
    {
        final String name = username.getText().toString().trim();
        final String email = email_id.getText().toString().trim();
        final String phoneNo = phone_no.getText().toString().trim();
        String str_password = password.getText().toString().trim();
        String str_c_password = conf_password.getText().toString().trim();

        if(name.isEmpty())
        {
            username.setError("User name is required!");
            username.requestFocus();
            return;
        }

        if(email.isEmpty())
        {
            email_id.setError("E-mail id is required!");
            email_id.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            email_id.setError("Please provide valid email!");
            email_id.requestFocus();
            return;
        }

        if(phoneNo.isEmpty())
        {
            phone_no.setError("Phone Number is required!");
            phone_no.requestFocus();
            return;
        }

        if(phoneNo.length()<10)
        {
            phone_no.setError("Please provide valid phone No. !");
            phone_no.requestFocus();
            return;
        }

        if(str_password.isEmpty())
        {
            password.setError("Password is required!");
            password.requestFocus();
            return;
        }

        if(str_password.length()<6)
        {
            password.setError("Min Password length should be 6 character");
            password.requestFocus();
            return;
        }

        if(str_c_password.isEmpty())
        {
            conf_password.setError("Confirm Password is required!");
            conf_password.requestFocus();
            return;
        }

        if(!str_c_password.equals(str_password))
        {
            conf_password.setError("Confirm Password should be equal to password");
            conf_password.requestFocus();
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,str_c_password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            User user = new User(name,phoneNo,email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterActivity.this,"User has been registered successfully",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                    }
                                    else
                                    {
                                        Toast.makeText(RegisterActivity.this,"Failed to register! Try again!",Toast.LENGTH_LONG).show();

                                    }
                                    progressBar.setVisibility(View.GONE);
                                }
                            });

                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this,"Failed to register! Try again!",Toast.LENGTH_LONG).show();

                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

    }
}