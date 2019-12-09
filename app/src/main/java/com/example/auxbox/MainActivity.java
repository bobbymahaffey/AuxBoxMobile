package com.example.auxbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Button hostButton;
    private Button userButton;
    private Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        userButton = (Button) findViewById(R.id.joinbtn);
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openJoinSession();
            }
        });

        hostButton = (Button) findViewById(R.id.createSessionBtn);
        hostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserSession();
            }
        });

    }

    public void openJoinSession(){
        Intent intent = new Intent(this, User.class);
        startActivity(intent);
    }

    public void openUserSession(){
        Intent intent = new Intent(this, Host.class);
        startActivity(intent);
    }

    public void logout(View view) {
        //once user clicks log out it will log them out of firebase
        FirebaseAuth.getInstance().signOut();
        //since user is logged out now we need to have them log back in
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
}
