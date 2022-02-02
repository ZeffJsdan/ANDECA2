package com.example.andeca1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity implements View.OnClickListener{

    private EditText email,password;
    private Button registerBtn;
    private TextView registerQn;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registerBtn = findViewById(R.id.registerBtn);
        registerQn = findViewById(R.id.registerQn);

        mAuth = FirebaseAuth.getInstance();
        progressBar = new ProgressBar(this);
    }

    @Override
    public void onClick(View v){
        Intent intent = new Intent(Registration.this,LoginActivity.class);
        startActivity(intent);
    }

    public void onCLickRegister(View v){
        String emailString = email.getText().toString();
        String passwordString = password.getText().toString();

        if(TextUtils.isEmpty(emailString)){
            email.setError("Email is Required");
        }
        if(TextUtils.isEmpty(passwordString)){
            password.setError("Password is Required");
        }else {

            mAuth.createUserWithEmailAndPassword(emailString,passwordString).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    Intent intent = new Intent(Registration.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(Registration.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}