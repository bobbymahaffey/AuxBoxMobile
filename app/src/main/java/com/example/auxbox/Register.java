package com.example.auxbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity{
    public static final String TAG = "TAG";
    EditText mFullName,mEmail,mPassword,mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;


    String userID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.fullname);
        mEmail    = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mPhone    = findViewById(R.id.phoneNumber);
        mRegisterBtn = findViewById(R.id.registerBtn);
        mLoginBtn    = findViewById(R.id.loginRedirect);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
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
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                //retrieve the name and number for database
                final String fullName = mFullName.getText().toString();
                final String phone = mPhone.getText().toString();

                //check if empty
                if(TextUtils.isEmpty(email)){
                    mEmail.setError(getString(R.string.error_email));
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError(getString(R.string.error_password_blank));
                    return;
                }

                if(password.length() < 5){
                    mPassword.setError(getString(R.string.error_password_length));
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
                            Toast.makeText(Register.this, getString(R.string.register_successful1) + userID + getString(R.string.register_successful2), Toast.LENGTH_SHORT).show();

                            //save the ID of the current user
                            userID = fAuth.getCurrentUser().getUid();

                            //created a collect in the database called users
                            DocumentReference documentReference = fStore.collection("users").document(userID);

                            //store the data into the document using hash map
                            Map<String,Object> user = new HashMap<>();

                            //insert data into the hash map
                            // the key is the fname and email then the objects are the fullNmae, email
                            user.put("fName", fullName);
                            user.put("email", email);
                            user.put("phone", phone);

                            //insert the data to the database
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: Message for the logcat user Profile is created for "+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                            //redirect user to main activity
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else{
                            //error account not created
                            Toast.makeText(Register.this, getString(R.string.register_failed) + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
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

