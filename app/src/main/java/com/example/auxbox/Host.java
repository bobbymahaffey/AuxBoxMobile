package com.example.auxbox;

        import androidx.appcompat.app.AppCompatActivity;

        import android.media.MediaPlayer;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

        import java.io.IOException;

public class Host extends AppCompatActivity {

    Button mPlayBtn;
    Button mStopBtn;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        mPlayBtn = findViewById(R.id.playButton);
        mStopBtn = findViewById(R.id.stopButton);

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
    }

    public void playSong()
    {
        prepare();
        mediaPlayer.start();
    }

    public void stopSong()
    {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
    }

    private void prepare()
    {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource("https://firebasestorage.googleapis.com/v0/b/auxbox-29cc0.appspot.com/o/youtubnow.co%20-%20Maroon%205%20-%20Memories.mp3?alt=media&token=6d69241e-1da8-47e1-afe9-8439e966493a");
            mediaPlayer.prepare();
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}

