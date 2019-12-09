package com.example.auxbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class User extends AppCompatActivity {
    Button mSaveSong;
    Button mLoadSong;
    TextView mSongTitleDisplay;
    EditText mUserInputSongTitle;
    ProgressBar mProgressBar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        final String TAG = "TAG";
        mSaveSong = findViewById(R.id.saveSongTitleButton);
        mLoadSong = findViewById((R.id.saveSongToDatabaseButton));
        mSongTitleDisplay = findViewById(R.id.songTitleTextView);
        mUserInputSongTitle = findViewById(R.id.songTitleEditText);
        mProgressBar = findViewById(R.id.uploadProgressBar);
        mProgressBar.setVisibility(View.GONE);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

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
                final String songTitle = mSongTitleDisplay.getText().toString();
                final String songLink;
                songTitle.trim(); //remove accidental leading spaces
                final String quickParse[] = songTitle.split(" ", 2); //grab the artist for demo

                switch (quickParse[0]) {
                    //reusing the second element of quickParse to not create yet another variable
                    case "Soilwork": {
                        songLink = "https://firebasestorage.googleapis.com/v0/b/auxbox-29cc0.appspot.com/o/Soilwork%20-%20The%20Akuma%20Afterglow.mp3?alt=media&token=c2e939ea-b6d6-4ab9-bc36-1367ba15cfb1";
                        break;
                    }
                    case "Coldplay": {
                        songLink = "https://firebasestorage.googleapis.com/v0/b/auxbox-29cc0.appspot.com/o/Coldplay%20-%20Orphans.mp3?alt=media&token=505fe083-1df8-4b5c-8c5f-db25ca540c71";
                        break;
                    }
                    case "Maroon": {
                        songLink = "https://firebasestorage.googleapis.com/v0/b/auxbox-29cc0.appspot.com/o/youtubnow.co%20-%20Maroon%205%20-%20Memories.mp3?alt=media&token=6d69241e-1da8-47e1-afe9-8439e966493a";
                        break;
                    }
                    case "Daft": {
                        songLink = "https://firebasestorage.googleapis.com/v0/b/auxbox-29cc0.appspot.com/o/Daft%20Punk%20-%20Voyager.mp3?alt=media&token=1d5fd624-50eb-4cee-80bb-48586d1d4a9c";
                        break;
                    }
                    case "Aviators": {
                        songLink = "https://firebasestorage.googleapis.com/v0/b/auxbox-29cc0.appspot.com/o/Aviators%20-%20Dystopian%20Fiction.mp3?alt=media&token=7f2a4d0d-f56b-4c28-808b-1338066ce4df";
                        break;
                    }
                    default: {
                        songLink = "https://firebasestorage.googleapis.com/v0/b/auxbox-29cc0.appspot.com/o/Rick%20Astley%20-%20Never%20Gonna%20Give%20You%20Up.mp3?alt=media&token=1b4f70a3-97ec-477b-b17b-96b8bdb6b0e7";
                        break;
                    }

                }
                try {
                    String userID = fAuth.getCurrentUser().getUid();

                    //created a collect in the database called users
                    DocumentReference documentReference = fStore.collection("playlist").document("demo");

                    //store the data into the document using hash map
                    Map<String,Object> song = new HashMap<>();

                    //insert data into the hash map
                    // the key is the firebase field and the obj is the thing going in
                    //song.put("requestingUser", userID);
                    song.put("databaseLink", songLink);

                    //insert the data to the database
                    documentReference.set(song).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "onSuccess: Message for the logcat Song was added to Firebase: "+ songTitle);
                            Toast.makeText(User.this, getText(R.string.song_committed_to_db), Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: " + e.toString());
                            Toast.makeText(User.this, getText(R.string.song_failed_commit_to_db), Toast.LENGTH_LONG).show();
                        }
                    });
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                mProgressBar.setVisibility(View.GONE);

            }
        });
    }
}