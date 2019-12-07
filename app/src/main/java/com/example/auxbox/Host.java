package com.example.auxbox;

        import androidx.appcompat.app.AppCompatActivity;

        import android.media.MediaPlayer;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

        import java.io.IOException;

public class Host extends AppCompatActivity {

    Button mplaySongBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        mplaySongBtn = findViewById(R.id.playSOngbtn);
        mplaySongBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                playSong(v);

            }
        });

    }

    public void playSong(View v)
    {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try
        {
            mediaPlayer.setDataSource("https://firebasestorage.googleapis.com/v0/b/auxbox-29cc0.appspot.com/o/youtubnow.co%20-%20Maroon%205%20-%20Memories.mp3?alt=media&token=6d69241e-1da8-47e1-afe9-8439e966493a");
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });

            mediaPlayer.prepare();
        }catch(IOException e)
        {
            e.printStackTrace();
        }

    }
}

