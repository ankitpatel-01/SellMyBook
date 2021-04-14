package com.example.sellmybooks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.view.View.*;

public class LoginActivity extends AppCompatActivity implements OnClickListener {

    private TextView register;
    private TextView resPass;

    private EditText etxt_emailInput;
    private EditText etxt_passwordInput;
    private Button btn_login;
    CheckBox remember;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = (TextView)findViewById(R.id.txt_createAcc);
        resPass = (TextView)findViewById(R.id.txt_forgot_pass);
        btn_login =(Button)findViewById(R.id.btn_login);
        etxt_emailInput = (EditText)findViewById(R.id.etxt_emailInput);
        etxt_passwordInput = (EditText)findViewById(R.id.etxt_PasswordInput);
        progressBar = (ProgressBar)findViewById(R.id.loginProgressBar);
        remember = (CheckBox)findViewById(R.id.chk_RememberMe);


        SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox = preferences.getString("remember","");

        assert checkbox != null;
        if(checkbox.equals("true"))
        {
            Intent intent = new Intent(LoginActivity.this,HomePage.class);
            startActivity(intent);
        }
        else if(checkbox.equals("false"))
        {
            Toast.makeText(this,"Please Sign In",Toast.LENGTH_SHORT).show();
        }


        mAuth = FirebaseAuth.getInstance();
        register.setOnClickListener(this);
        resPass.setOnClickListener(this);
        btn_login.setOnClickListener(this);

       remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked())
                {
                    SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","true");
                    editor.apply();
                    Toast.makeText(LoginActivity.this,"Checked",Toast.LENGTH_SHORT).show();
                }
                else if(!compoundButton.isChecked())
                {
                    SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","false");
                    editor.apply();
                    Toast.makeText(LoginActivity.this,"Unchecked",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.txt_createAcc:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.txt_forgot_pass:
                startActivity(new Intent(this,PasswordRest.class));
                break;
            case R.id.btn_login:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email = etxt_emailInput.getText().toString().trim();
        String password = etxt_passwordInput.getText().toString().trim();

        if(email.isEmpty())
        {
            etxt_emailInput.setError("Email is required!");
            etxt_emailInput.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            etxt_emailInput.setError("Please provide valid e-mail id!");
            etxt_emailInput.requestFocus();
            return;
        }

        if(password.isEmpty())
        {
            etxt_passwordInput.setError("Password is required!");
            etxt_passwordInput.requestFocus();
            return;
        }

        if(password.length()<6)
        {
            etxt_passwordInput.setError("Min password length is 6 character!");
            etxt_passwordInput.requestFocus();
            return;
        }

        progressBar.setVisibility(VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    //load home page
                    startActivity(new Intent(LoginActivity.this, HomePage.class));
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Failed to login! Please check your Credentials!",Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(GONE);
            }
        });


    }

}