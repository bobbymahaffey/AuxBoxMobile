package com.example.auxbox;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import android.media.MediaPlayer;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.firestore.DocumentReference;
        import com.google.firebase.firestore.DocumentSnapshot;
        import com.google.firebase.firestore.FirebaseFirestore;
        import java.io.IOException;

public class Host extends AppCompatActivity {

    private Button mPlayBtn;
    private Button mStopBtn;
    private Button mPauseBtn;
    private FirebaseDatabase fDatabase;
    private FirebaseFirestore fFirestore;
    final String TAG = "TAG";


    private MediaPlayer mediaPlayer;

    private boolean playing = false;
    private boolean prepared = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        mPlayBtn = findViewById(R.id.downloadPlaylistButton);
        mStopBtn = findViewById(R.id.stopButton);
        mPauseBtn = findViewById(R.id.pauseButton);
        fDatabase = FirebaseDatabase.getInstance();
        fFirestore = FirebaseFirestore.getInstance();

        mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prepared) {
                    playSong();
                }
                else {
                    prepare();
                }

            }
        });
        mStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopSong();
            }
        });
        mPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseSong();
            }
        });
    }

    public void playSong()
    {
            mediaPlayer.start();
            playing = true;
    }

    public void stopSong()
    {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            playing = false;
            prepared = false;
            mPlayBtn.setText(R.string.download_playlist);
    }

    public void pauseSong()
    {
        mediaPlayer.pause();
    }



    private void prepare()
    {
        mediaPlayer = new MediaPlayer();
        DocumentReference docRef = fFirestore.collection("playlist").document("demo");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        String source = document.getData().toString();
                        String temp[] = source.split("=", 2);
                        source = temp[1];
                        if (source.contains("}")) {
                            source = source.substring(0, source.length()-1);
                        }
                        try {
                                mediaPlayer.setDataSource(source);
                                mediaPlayer.prepare();
                                prepared = true;
                                mPlayBtn.setText(R.string.play_text);
                        } catch(IOException e)
                        {
                            e.printStackTrace();
                        }
                    } else {
                        Log.d(TAG, "No such document");
                        prepared = false;
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


    }
}

