package com.example.auxbox;

        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

        import android.media.MediaPlayer;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;

        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.google.firebase.firestore.DocumentReference;
        import com.google.firebase.firestore.FirebaseFirestore;
        import java.io.IOException;
        import java.util.HashMap;
        import java.util.Map;

public class Host extends AppCompatActivity {

    private Button mPlayBtn;
    private Button mStopBtn;
    private Button mPauseBtn;
    private FirebaseDatabase fDatabase;
    final String TAG = "TAG";
    private String source = "";


    private MediaPlayer mediaPlayer;

    private boolean playing = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        mPlayBtn = findViewById(R.id.playButton);
        mStopBtn = findViewById(R.id.stopButton);
        mPauseBtn = findViewById(R.id.pauseButton);
        fDatabase = FirebaseDatabase.getInstance();

        mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSong();
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
        if(!playing) {
            prepare();
        }
        mediaPlayer.start();
        playing = true;
    }

    public void stopSong()
    {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            playing = false;
    }

    public void pauseSong()
    {
        mediaPlayer.pause();
    }

    private void prepare()
    {
        mediaPlayer = new MediaPlayer();
        DatabaseReference fRef = fDatabase.getReference("playlist/demo");
        try {

            if (source.length() > 20) {
                mediaPlayer.setDataSource(source);
                mediaPlayer.prepare();
            }
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}

