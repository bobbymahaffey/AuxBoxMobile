package com.example.auxbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void logout(View view) {
        //wants user clicks log out it will log them out of firebase
        FirebaseAuth.getInstance().signOut();
        //since user is logged our now we need to have them log back in
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
}
