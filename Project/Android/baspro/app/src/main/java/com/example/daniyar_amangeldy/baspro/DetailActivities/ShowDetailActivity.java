package com.example.daniyar_amangeldy.baspro.DetailActivities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.daniyar_amangeldy.baspro.R;
import com.example.daniyar_amangeldy.baspro.Volley.AppController;
import com.example.daniyar_amangeldy.baspro.Youtube.youtubeActivity;
import com.example.daniyar_amangeldy.baspro.realm.PlaylistItems;
import com.github.fabtransitionactivity.SheetLayout;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class ShowDetailActivity extends AppCompatActivity implements SheetLayout.OnFabAnimationEndListener {
    SheetLayout shl;
    JSONObject urlJSONObject;
    JSONObject TextJsonObject;
    String urlforTime;
    JSONObject videoIdJSONObject;
    String TextStringYoutube;
    RealmResults<PlaylistItems> playlist;
    String ImageUrlStringYoutube;
    String url;
    String VideoUrlYoutube;
    String TimeStringYoutube;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        realm = Realm.getInstance(this);
        shl = (SheetLayout) findViewById(R.id.bottom_sheet);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.showDetailfab);
        ImageView iv = (ImageView) findViewById(R.id.showDetailBackdrop);
        TextView tv = (TextView) findViewById(R.id.showDetailText);
        url = "https://www.googleapis.com/youtube/v3/playlistItems?key=AIzaSyBawDQDFNHNE33OcXpUUqZGn2QSdZPv3pc&playlistId="+getIntent().getStringExtra("url")+"&part=id,snippet,contentDetails&order=date&maxResults=20";
        iv.setImageDrawable(getDrawable(getIntent().getIntExtra("photo", 0)));
        tv.setText("asdasdsad");


        loadPlaylist(realm,url);



        shl.setFab(fab);
        shl.setFabAnimationEndListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shl.expandFab();
            }
        });
    }

    public void onFabAnimationEnd() {
        Intent intent = new Intent(this, youtubeActivity.class);
        intent.putExtra("url",urlforTime);
        intent.putExtra("playlist", getIntent().getStringExtra("url"));
        startActivityForResult(intent, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            loadPlaylist(realm,url);
            shl.contractFab();
        }


    }
    public void loadPlaylist(final Realm realm, String url){
        urlforTime = "https://www.googleapis.com/youtube/v3/videos?key=AIzaSyBawDQDFNHNE33OcXpUUqZGn2QSdZPv3pc&part=contentDetails&id=";
        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        realm.beginTransaction();
                        realm.where(PlaylistItems.class).findAll().clear();

                        try {
                            for (int index = 0; index < response.getJSONArray("items").length(); index++) {

                                try {
                                    TextJsonObject = response.getJSONArray("items").getJSONObject(index).getJSONObject("snippet");
                                    urlJSONObject = response.getJSONArray("items").getJSONObject(index).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("medium");
                                    videoIdJSONObject = response.getJSONArray("items").getJSONObject(index).getJSONObject("contentDetails");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    TextStringYoutube = TextJsonObject.getString("title");
                                    ImageUrlStringYoutube = urlJSONObject.getString("url");
                                    VideoUrlYoutube = videoIdJSONObject.getString("videoId");
                                    PlaylistItems playlist = realm.createObject(PlaylistItems.class);
                                    playlist.setName(TextStringYoutube);
                                    playlist.setImg_url(ImageUrlStringYoutube);
                                    playlist.setVideo_url(VideoUrlYoutube);
                                    urlforTime=urlforTime+VideoUrlYoutube+",";




                                    Log.e("Downloading...", String.valueOf(response.length()));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        realm.commitTransaction();
                        realm.refresh();
                        urlforTime=urlforTime.substring(0, urlforTime.length() - 1);
                        Log.e("url",urlforTime);


                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                    }
                }
        ), "tag_json_obj");
    }
}


