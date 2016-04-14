package com.example.daniyar_amangeldy.baspro.Youtube;

import android.content.Intent;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.daniyar_amangeldy.baspro.RecyclerView.PlaylistAdapter;
import com.example.daniyar_amangeldy.baspro.RecyclerView.RecyclerItemClickListener;
import com.example.daniyar_amangeldy.baspro.Volley.AppController;
import com.example.daniyar_amangeldy.baspro.Youtube.Keys.DeveloperKey;
import com.example.daniyar_amangeldy.baspro.R;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;

public class youtubeActivity extends YouTubeFailureRecoveryActivity  {
boolean fullScreen;
    YouTubePlayer player;
    RecyclerView rv;
    JSONObject urlJSONObject;
    JSONObject TextJsonObject;
    JSONObject videoIdJSONObject;
    JSONObject TimeJSONObject;
    String TextStringYoutube;
    String ImageUrlStringYoutube;
    String VideoUrlYoutube;
    String TimeStringYoutube ="PT28M2S";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_youtube);
        String url = "https://www.googleapis.com/youtube/v3/playlistItems?key=AIzaSyBawDQDFNHNE33OcXpUUqZGn2QSdZPv3pc&playlistId="+getIntent().getStringExtra("playlist")+"&part=id,snippet,contentDetails&order=date";
        YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_player);
        youTubeView.initialize(DeveloperKey.DEVELOPER_KEY, this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.shareButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = null;
                if (getIntent().hasExtra("playlist")) {
                    link = getResources().getString(R.string.sharevideo) + "\nhttp://youtube.com/playlist?list=" + getIntent().getStringExtra("playlist");
                } else {
                    link = getResources().getString(R.string.sharevideo) + "\nhttp://youtu.be/" + getIntent().getStringExtra("url");
                }
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.putExtra(Intent.EXTRA_TEXT, link);
                share.setType("text/plain");
                startActivity(Intent.createChooser(share, getResources().getString(R.string.share)));
            }
        });

        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        for (int index = 0; index < response.length(); index++) {

                            try {
                                TextJsonObject = response.getJSONArray("items").getJSONObject(index).getJSONObject("snippet");
                                urlJSONObject = response.getJSONArray("items").getJSONObject(index).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("default");
                                videoIdJSONObject = response.getJSONArray("items").getJSONObject(index).getJSONObject("contentDetails");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                TextStringYoutube = TextJsonObject.getString("title");
                                ImageUrlStringYoutube = urlJSONObject.getString("url");
                                VideoUrlYoutube = videoIdJSONObject.getString("videoId");
                                Log.e("Downloading...", "Finished!");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                    }
                }
        ), "tag_json_obj");

        String urlforTime = "https://www.googleapis.com/youtube/v3/videos?key=AIzaSyBawDQDFNHNE33OcXpUUqZGn2QSdZPv3pc&id="+VideoUrlYoutube+"&part=contentDetails";

     /*   AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, urlforTime,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        for (int index = 0; index < response.length(); index++) {

                            try {
                                TimeJSONObject = response.getJSONArray("items").getJSONObject(index).getJSONObject("contentDetails");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                TimeStringYoutube = TimeJSONObject.getString("duration");


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                    }
                }
        ), "tag_json_obj");
        */



        rv = (RecyclerView) findViewById(R.id.playList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        PlaylistAdapter adapter = new PlaylistAdapter(getApplicationContext(),ImageUrlStringYoutube,TimeStringYoutube,TextStringYoutube);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        rv.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        }));
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_player);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            if(getIntent().hasExtra("playlist")){
                player.cuePlaylist(getIntent().getStringExtra("playlist"));
                player.play();
            }else {
                player.cueVideo(getIntent().getStringExtra("url"));
                player.play();
            }
            player.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {

                @Override
                public void onFullscreen(boolean _isFullScreen) {
                    fullScreen = _isFullScreen;
                }
            });




        }

    }
    public void onBackPressed() {
        if (fullScreen){
            player.setFullscreen(false);
        } else{
            super.onBackPressed();
        }
    }
}
