package com.suajie.ajietv;

import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.video_view);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("https://raw.githubusercontent.com/username/repo/main/m3u8_link.json");
                    InputStream is = url.openStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                    String json = "";
                    String line;
                    while ((line = reader.readLine()) != null) {
                        json += line;
                    }

                    JSONObject jsonObject = new JSONObject(json);
                    String m3u8Url = jsonObject.getString("m3u8_url");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MediaController mediaController = new MediaController(MainActivity.this);
                            mediaController.setAnchorView(videoView);
                            videoView.setMediaController(mediaController);
                            videoView.setVideoURI(Uri.parse(m3u8Url));
                            videoView.start();
                        }
                    });

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}