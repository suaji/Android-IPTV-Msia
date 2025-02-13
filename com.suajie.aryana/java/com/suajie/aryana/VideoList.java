package com.suajie.aryana;

import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import android.view.GestureDetector;

public class MainActivity extends AppCompatActivity {

    private PlayerView playerView;
    private ExoPlayer player;
    private ImageView video1Icon, video2Icon, video3Icon;
    private String video1Url = "https://playlist.m3u8?id=1";
    private String video2Url = "https://playlist.m3u8?id=2";
    private String video3Url = "https://playlist.m3u8?id=3";
    private GestureDetector gestureDetector;
    private boolean isVideo1Selected = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerView = findViewById(R.id.playerView);
        video1Icon = findViewById(R.id.video1Icon);
        video2Icon = findViewById(R.id.video2Icon);
        video3Icon = findViewById(R.id.video3Icon);
        video1 = findViewById(R.id.video1Icon);
        video2 = findViewById(R.id.video2Icon);
        video3 = findViewById(R.id.video3Icon);

        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);

        playVideo(video1Url);
        selectVideo(video1Icon);

        video1Icon.setOnClickListener(v -> {
            playVideo(video1Url);
            selectVideo(video1Icon);
            isVideo1Selected = true;
        });
        video2Icon.setOnClickListener(v -> {
            playVideo(video2Url);
            selectVideo(video2Icon);
            isVideo1Selected = false;
        });
        video3Icon.setOnClickListener(v -> {
            playVideo(video3Url);
            selectVideo(video3Icon);
            isVideo1Selected = false;
        });
	}