package com.example.auxbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


public class User extends AppCompatActivity {
    Button mSaveSong;
    Button mLoadSong;
    TextView mSongTitleDisplay;
    EditText mUserInputSongTitle;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mSaveSong = findViewById(R.id.fileUploadBtn);
        mLoadSong = findViewById((R.id.dataBaseUploadBtn));
        mSongTitleDisplay = findViewById(R.id.SongTitle);
        mUserInputSongTitle = findViewById(R.id.songTitleEditText);
        mProgressBar = findViewById(R.id.uploadProgressBar);

        mSaveSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSongTitleDisplay.setText(mUserInputSongTitle.getText());
            }
        });

        mLoadSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBar.setVisibility(View.VISIBLE);
                try {

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                mProgressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(User.this, getText(R.string.song_committed_to_db), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
