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

        mediaPlayer = new MediaPlayer();
        mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                playSong(v, mediaPlayer);
            }
        });
        mStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stopSong(v, mediaPlayer);
            }
        });
    }

    public void playSong(View v, MediaPlayer m)
    {
        try
        {
            m.setDataSource("https://firebasestorage.googleapis.com/v0/b/auxbox-29cc0.appspot.com/o/youtubnow.co%20-%20Maroon%205%20-%20Memories.mp3?alt=media&token=6d69241e-1da8-47e1-afe9-8439e966493a");
            m.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });

            m.prepare();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void stopSong(View v, MediaPlayer m)
    {
            m.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.stop();
                }
            });
    }
}

