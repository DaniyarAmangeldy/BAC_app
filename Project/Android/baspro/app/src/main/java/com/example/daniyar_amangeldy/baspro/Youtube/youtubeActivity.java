package com.example.daniyar_amangeldy.baspro.Youtube;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
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
import android.content.res.Configuration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class youtubeActivity extends YouTubeFailureRecoveryActivity implements YouTubePlayer.OnFullscreenListener  {

    YouTubePlayer player;
    RecyclerView rv;
    JSONObject TimeJSONObject;
    JSONObject WatchJSONObject;
    String WatchString;
    RealmChangeListener changeListener;
    RealmResults<PlaylistItems> playlist;
    String TimeStringYoutube;
    FloatingActionButton fab;
    private boolean fullscreen=false;
    public Realm realm;
    View decorView;
    RelativeLayout otherViews;
    String videoUrl;
    LinearLayout baseLayout;
    PlaylistAdapter adapter;
    YouTubePlayerView youTubeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_youtube);
        realm = Realm.getInstance(this);

        setTheme(R.style.AppTheme);
        decorView = getWindow().getDecorView();
        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_player);
        youTubeView.initialize(DeveloperKey.DEVELOPER_KEY, this);
        otherViews = (RelativeLayout) findViewById(R.id.otherViews);
        videoUrl = getIntent().getStringExtra("playlist");
        baseLayout = (LinearLayout) findViewById(R.id.baseLayout);
        playlist = realm.where(PlaylistItems.class).findAllAsync();

        doLayout();


        fab = (FloatingActionButton) findViewById(R.id.shareButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = null;
                    link = getResources().getString(R.string.sharevideo) + "\nhttp://youtu.be/" + videoUrl;

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
                                WatchJSONObject = response.getJSONArray("items").getJSONObject(index).getJSONObject("statistics");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                WatchString = WatchJSONObject.getString("viewCount");
                                TimeStringYoutube = TimeJSONObject.getString("duration");
                                //PT28M12S
                                //PT2M1S
                                //PT28M1S
                                //PT2M12S
                                //PT11S
                                //PT11M
                                //PT1M
                                //PT1S
                                if (TimeStringYoutube.length() == 4) {
                                    if (TimeStringYoutube.charAt(3) == 'M') {
                                        TimeStringYoutube = TimeStringYoutube.substring(2, 3) + ":00";
                                    } else {
                                        TimeStringYoutube = "00:" + TimeStringYoutube.substring(2, 3);
                                    }
                                }
                                if (TimeStringYoutube.length() == 5) {
                                    if (TimeStringYoutube.charAt(4) != 'M') {
                                        TimeStringYoutube = "00:" + TimeStringYoutube.substring(2, 4);
                                    } else {
                                        TimeStringYoutube = TimeStringYoutube.substring(2, 4) + ":00";
                                    }
                                }
                                if (TimeStringYoutube.length() == 8) {
                                    TimeStringYoutube = TimeStringYoutube.substring(2, 4) + ":" + TimeStringYoutube.substring(5, 7);

                                }
                                if (TimeStringYoutube.length() == 6) {
                                    TimeStringYoutube = "0" + TimeStringYoutube.substring(2, 3) + ":0" + TimeStringYoutube.substring(4, 5);
                                }
                                if (TimeStringYoutube.length() == 7) {
                                    if (TimeStringYoutube.charAt(3) != 'M') {
                                        TimeStringYoutube = TimeStringYoutube.substring(2, 4) + ":0" + TimeStringYoutube.substring(5, 6);
                                    } else {
                                        TimeStringYoutube = "0" + TimeStringYoutube.substring(2, 3) + ":" + TimeStringYoutube.substring(4, 6);
                                    }

                                }
                                playlist.get(index).setTime(TimeStringYoutube);
                                playlist.get(index).setWatch(WatchString);
                                Log.e("time", TimeStringYoutube);
                                Log.e("watch", WatchString);


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
        adapter = new PlaylistAdapter(getApplicationContext(), playlist,getIntent().getIntExtra("position",0));

        rv.setAdapter(adapter);

        changeListener = new RealmChangeListener() {
            @Override
            public void onChange() {
                adapter.notifyDataSetChanged();
            }
        };
        playlist.addChangeListener(changeListener);
        rv.setHasFixedSize(true);

        rv.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                view.setSelected(true);
                videoUrl = playlist.get(position).getVideo_url();
                    player.loadVideo(videoUrl);




            }
        }));


    }
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer player, boolean wasRestored) {
        this.player = player;
        player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
        player.setOnFullscreenListener(this);



        if (!wasRestored) {
                player.loadVideo(getIntent().getStringExtra("playlist"));
            }
    }

    @Override
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;

    }


    public void onBackPressed() {
        if (fullscreen){
            player.setFullscreen(false);
        } else{
            super.onBackPressed();
            finish();
            realm.beginTransaction();
            playlist.clear();
            realm.commitTransaction();
            realm.refresh();

        }
    }




    private void doLayout() {

        LinearLayout.LayoutParams playerParams =
                (LinearLayout.LayoutParams) youTubeView.getLayoutParams();
        if (fullscreen) {
            // When in fullscreen, the visibility of all other views than the player should be set to
            // GONE and the player should be laid out across the whole screen.
            playerParams.width = LayoutParams.MATCH_PARENT;
            playerParams.height = LayoutParams.MATCH_PARENT;
            playerParams.weight = 1;
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            otherViews.setVisibility(View.GONE);
            fab.hide();
        } else {
            otherViews.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams otherViewsParams = otherViews.getLayoutParams();
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                otherViews.setVisibility(View.GONE);
                playerParams.width = otherViewsParams.width = MATCH_PARENT;
                playerParams.height = WRAP_CONTENT;
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

                baseLayout.setOrientation(LinearLayout.HORIZONTAL);
            } else {
                playerParams.width = otherViewsParams.width = MATCH_PARENT;
                playerParams.height = WRAP_CONTENT;
                otherViewsParams.height = WRAP_CONTENT;
                otherViewsParams.width = MATCH_PARENT;
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                baseLayout.setOrientation(LinearLayout.VERTICAL);
            }
        }
    }
    @Override
    public void onFullscreen(boolean isFullscreen) {
        fullscreen = isFullscreen;
        doLayout();
    }
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        doLayout();
    }


}
