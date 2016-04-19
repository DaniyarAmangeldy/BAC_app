package com.example.daniyar_amangeldy.baspro.Youtube;

import android.content.Intent;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.daniyar_amangeldy.baspro.RecyclerView.PlaylistAdapter;
import com.example.daniyar_amangeldy.baspro.RecyclerView.RecyclerItemClickListener;
import com.example.daniyar_amangeldy.baspro.Volley.AppController;
import com.example.daniyar_amangeldy.baspro.Youtube.Keys.DeveloperKey;
import com.example.daniyar_amangeldy.baspro.R;
import com.example.daniyar_amangeldy.baspro.realm.PlaylistItems;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class youtubeActivity extends YouTubeFailureRecoveryActivity  {
boolean fullScreen;
    YouTubePlayer player;
    RecyclerView rv;
    JSONObject TimeJSONObject;
    RealmChangeListener changeListener;
    RealmResults<PlaylistItems> playlist;
    String TimeStringYoutube;
    FloatingActionButton fab;
    public Realm realm;
    View decorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_youtube);
        realm = Realm.getInstance(this);
        setTheme(R.style.AppTheme2);
        decorView = getWindow().getDecorView();
        YouTubePlayerView youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_player);
        youTubeView.initialize(DeveloperKey.DEVELOPER_KEY, this);
        playlist = realm.where(PlaylistItems.class).findAllAsync();




        fab = (FloatingActionButton) findViewById(R.id.shareButton);
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





        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, getIntent().getStringExtra("url"),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        realm.beginTransaction();
                        try {
                            JSONArray result = response.getJSONArray("items");

                            for (int index = 0; index < result.length(); index++) {

                                try {
                                    TimeJSONObject = response.getJSONArray("items").getJSONObject(index).getJSONObject("contentDetails");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    TimeStringYoutube = TimeJSONObject.getString("duration");
                                    //PT28M12S
                                    //PT2M1S
                                    //PT28M1S
                                    //PT2M12S
                                    //PT11S
                                    //PT11M
                                    //PT1M
                                    //PT1S
                                    if(TimeStringYoutube.length()==4){
                                        if(TimeStringYoutube.charAt(3)=='M'){
                                            TimeStringYoutube=TimeStringYoutube.substring(2,3)+":00";
                                        }else{
                                            TimeStringYoutube="00:"+TimeStringYoutube.substring(2,3);
                                        }
                                    }
                                    if (TimeStringYoutube.length() == 5) {
                                        if (TimeStringYoutube.charAt(4) != 'M') {
                                            TimeStringYoutube = "00:" +  TimeStringYoutube.substring(2, 4);
                                        } else {
                                            TimeStringYoutube = TimeStringYoutube.substring(2, 4)+":00";
                                        }
                                    }
                                    if (TimeStringYoutube.length() == 8) {
                                        TimeStringYoutube = TimeStringYoutube.substring(2, 4) + ":" + TimeStringYoutube.substring(5, 7);

                                    }
                                    if (TimeStringYoutube.length() == 6) {
                                        TimeStringYoutube = "0"+TimeStringYoutube.substring(2, 3) + ":0" + TimeStringYoutube.substring(4, 5);
                                    }
                                    if (TimeStringYoutube.length() == 7) {
                                        if (TimeStringYoutube.charAt(3) != 'M') {
                                            TimeStringYoutube = TimeStringYoutube.substring(2, 4) + ":0" +  TimeStringYoutube.substring(5, 6);
                                        } else {
                                            TimeStringYoutube = "0"+TimeStringYoutube.substring(2, 3) + ":" + TimeStringYoutube.substring(4, 6);
                                        }

                                    }
                                    playlist.get(index).setTime(TimeStringYoutube);
                                    Log.e("time", TimeStringYoutube);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                            realm.commitTransaction();
                            realm.refresh();

                        } catch (JSONException e) {
                            e.printStackTrace();
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




        rv = (RecyclerView) findViewById(R.id.playList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        final PlaylistAdapter adapter = new PlaylistAdapter(getApplicationContext(), playlist);
        rv.setAdapter(adapter);

        changeListener = new RealmChangeListener() {
            @Override
            public void onChange() {
                adapter.notifyDataSetChanged();
            }
        };
        playlist.addChangeListener(changeListener);
        rv.setHasFixedSize(true);
        rv.setSelected(true);
        rv.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                view.setSelected(true);

                player.loadVideo(playlist.get(position).getVideo_url());


            }
        }));
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_player);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer player, boolean wasRestored) {
        this.player = player;
        player.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {

            @Override
            public void onFullscreen(boolean _isFullScreen) {

                fullScreen = _isFullScreen;
                fab.hide();
                player.play();

                Log.e("fullscreen", String.valueOf(_isFullScreen));
                if (_isFullScreen == false) fab.show();
            }
        });
        if (!wasRestored) {
            if(getIntent().hasExtra("playlist")){
                player.cuePlaylist(getIntent().getStringExtra("playlist"));
                player.play();
            }else {
                player.loadVideo(getIntent().getStringExtra("url"));
                player.play();
            }





        }

    }
    public void onBackPressed() {
        if (fullScreen){
            player.setFullscreen(false);
        } else{
            super.onBackPressed();
            realm.beginTransaction();
            playlist.clear();
            realm.commitTransaction();
            realm.refresh();

        }
    }
    public void onResume(){
        super.onResume();
    }
}
