package com.example.daniyar_amangeldy.baspro.DetailActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.daniyar_amangeldy.baspro.R;
import com.example.daniyar_amangeldy.baspro.RecyclerView.RVAdapterGrid;
import com.example.daniyar_amangeldy.baspro.RecyclerView.RecyclerItemClickListener;
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
    String ImageUrlStringYoutube;
    String url;
    String VideoUrlYoutube;
    RecyclerView rv;
    RVAdapterGrid adapter;
    Realm realm;
    RealmChangeListener changeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        realm = Realm.getInstance(this);
        rv = (RecyclerView) findViewById(R.id.rvShowDetail);

        url = "https://www.googleapis.com/youtube/v3/playlistItems?key=AIzaSyBawDQDFNHNE33OcXpUUqZGn2QSdZPv3pc&playlistId="+getIntent().getStringExtra("url")+"&part=id,snippet,contentDetails&order=date&maxResults=50";
        realm.beginTransaction();
        realm.where(PlaylistItems.class).findAll().clear();
        realm.commitTransaction();
        loadPlaylist(realm, url);
        final RealmResults<PlaylistItems> show = realm.where(PlaylistItems.class).findAllAsync();
        rv.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        adapter = new RVAdapterGrid(show,getApplicationContext());
        rv.setAdapter(adapter);
        rv.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(ShowDetailActivity.this, youtubeActivity.class);
                i.putExtra("position", position);
                i.putExtra("playlist", show.get(position).getVideo_url());
                i.putExtra("url",urlforTime);
                startActivityForResult(i,0);
            }
        }));


        changeListener = new RealmChangeListener() {
            @Override
            public void onChange() {

                adapter.notifyDataSetChanged();
            }
        };

        show.addChangeListener(changeListener);




    }

    public void onFabAnimationEnd() {
        Intent intent = new Intent(this, youtubeActivity.class);
        intent.putExtra("url",urlforTime);
        intent.putExtra("playlist", getIntent().getStringExtra("url"));
        intent.putExtra("type","playlistType");
        startActivityForResult(intent, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            loadPlaylist(realm,url);
        }


    }
    public void loadPlaylist(final Realm realm, String url){
        urlforTime = "https://www.googleapis.com/youtube/v3/videos?key=AIzaSyBawDQDFNHNE33OcXpUUqZGn2QSdZPv3pc&part=statistics,contentDetails&id=";
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
                                    urlJSONObject = response.getJSONArray("items").getJSONObject(index).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("high");
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
                                    urlforTime = urlforTime + VideoUrlYoutube + ",";


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
                        urlforTime = urlforTime.substring(0, urlforTime.length() - 1);
                        Log.e("url", urlforTime);


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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                setResult(0);

                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}


