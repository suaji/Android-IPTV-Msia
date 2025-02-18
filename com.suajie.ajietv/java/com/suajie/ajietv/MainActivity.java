package com.suajie.ajietv;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity implements VideoAdapter.OnVideoClickListener {
    private PlayerView playerView;
    private ExoPlayer player;
    private RecyclerView recyclerView;
    private VideoAdapter adapter;
    private List<VideoItem> videoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerView = findViewById(R.id.player_view);
        recyclerView = findViewById(R.id.recyclerView);
        Button btnRefresh = findViewById(R.id.btnRefresh);

        initializePlayer();
        setupRecyclerView();
        loadVideosFromGithub();

        btnRefresh.setOnClickListener(v -> loadVideosFromGithub());
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VideoAdapter(videoList, this);
        recyclerView.setAdapter(adapter);
    }

    private void initializePlayer() {
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
    }

    private void loadVideosFromGithub() {
        String githubUrl = "https://raw.githubusercontent.com/suaji/Android-IPTV-Msia/refs/heads/main/videos.json";

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(githubUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    return result.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (result != null) {
                    try {
                        JSONArray jsonArray = new JSONArray(result);
                        List<VideoItem> newVideos = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject video = jsonArray.getJSONObject(i);
                            newVideos.add(new VideoItem(
                                    video.getString("title"),
                                    video.getString("url"),
                                    video.getString("type"),
                                    video.optString("thumbnail", "")
                            ));
                        }

                        videoList = newVideos;
                        adapter.updateVideos(videoList);

                        if (!videoList.isEmpty()) {
                            playVideo(videoList.get(0));
                        }
                    } catch (JSONException e) {
                        Toast.makeText(MainActivity.this,
                                "Ralat membaca data: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this,
                            "Tidak dapat memuat turun data",
                            Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    @Override
    public void onVideoClick(VideoItem video) {
        playVideo(video);
    }

    private void playVideo(VideoItem video) {
        MediaItem mediaItem = MediaItem.fromUri(video.getUrl());
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}