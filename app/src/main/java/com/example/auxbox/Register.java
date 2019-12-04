package com.example.auxbox;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity{
    EditText mFullName,mEmail,mPassword,mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.fullname);
        mEmail    = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mPhone    = findViewById(R.id.phonenumber);
        mRegisterBtn = findViewById(R.id.registerBtn);
        mLoginBtn    = findViewById(R.id.loginRedirect);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        //user already logged in or return user
        if(fAuth.getCurrentUser() != null){
            //send to main activity
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }


        //wants register is click need to validate data
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                //check if empty
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Must provide a valid email");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError(" A valid password os required");
                    return;
                }

                if(password.length() < 5){
                    mPassword.setError(" Password must be at least 5 characters");
                    return;
                }

                //Display the progress Bar
                progressBar.setVisibility(View.VISIBLE);

                //Now we register the user with firebase
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //the process of registering the user is called a task
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "user was created", Toast.LENGTH_SHORT).show();
                            //redirect user to main activity
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else{
                            //error account not created
                            Toast.makeText(Register.this, "Error user not created!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            //make progress bar disappear after loading
                            progressBar.setVisibility(View.GONE);

                        }
                    }
                });

            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

    }
}

