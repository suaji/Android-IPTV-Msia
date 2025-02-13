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

public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();

        if (action == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_UP:
                    video1Icon.setVisibility(View.VISIBLE);
                    video2Icon.setVisibility(View.VISIBLE);
                    video3Icon.setVisibility(View.VISIBLE);
                    return true;
					
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    if (!isVideo1Selected) {
                        playVideo(video1Url);
                        selectVideo(video1Icon);
                        isVideo1Selected = true;
                    }
                    return true;
					
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    if (isVideo1Selected) {
                        playVideo(video2Url);
                        selectVideo(video2Icon);
                        isVideo1Selected = false;
                    } else {
                        playVideo(video3Url);
                        selectVideo(video3Icon);
                        isVideo1Selected = false;
                    }
                    return true;
					
                case KeyEvent.KEYCODE_DPAD_CENTER:
				
                case KeyEvent.KEYCODE_ENTER:
                    if (isVideo1Selected) {
                        playVideo(video1Url);
                    } else if (video2Icon.isSelected()) {
                        playVideo(video2Url);
                    } else {
                        playVideo(video3Url);
                    }
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }