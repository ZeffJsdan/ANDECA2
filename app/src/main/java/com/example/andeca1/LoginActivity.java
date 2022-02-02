package com.example.andeca1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener  {

    private EditText email,password;
    private Button loginBtn;
    private TextView loginQn;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        loginQn = findViewById(R.id.loginQn);

        mAuth = FirebaseAuth.getInstance();
        progressBar = new ProgressBar(this);
    }

    @Override
    public void onClick(View v){
        Intent intent = new Intent(LoginActivity.this,Registration.class);
        startActivity(intent);
    }

    public void onClickLogin(View v){
        String emailString = email.getText().toString();
        String passwordString = password.getText().toString();

        if(TextUtils.isEmpty(emailString)){
            email.setError("Email is Required");
        }
        if(TextUtils.isEmpty(passwordString)){
            password.setError("Password is Required");
        }
        else {

            mAuth.signInWithEmailAndPassword(emailString,passwordString).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}