package com.example.daniyar_amangeldy.baspro.DetailActivities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
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
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class ShowDetailActivity extends AppCompatActivity {
    JSONObject urlJSONObject;
    JSONObject TextJsonObject;
    String urlforTime;
    JSONObject videoIdJSONObject;
    static String lastToken;
    String pageToken;
    String TextStringYoutube;
    String ImageUrlStringYoutube;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    String url;
     int pos=0;
    MaterialSearchView searchView;
    String VideoUrlYoutube;
    RecyclerView rv;
    RealmResults<PlaylistItems> show;
    RVAdapterGrid adapter;
    Realm realm;
    GridLayoutManager mLayoutManager;
    RealmChangeListener changeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        realm = Realm.getDefaultInstance();
        Toolbar toolbar =(Toolbar) findViewById(R.id.toolbarShowDetail);

        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        toolbar.setTitleTextColor(Color.WHITE);
        realm.setAutoRefresh(true);

        searchView = (MaterialSearchView) findViewById(R.id.search);
        rv = (RecyclerView) findViewById(R.id.rvShowDetail);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);

        url = "https://www.googleapis.com/youtube/v3/playlistItems?key=AIzaSyBawDQDFNHNE33OcXpUUqZGn2QSdZPv3pc&playlistId="+getIntent().getStringExtra("url")+"&part=id,snippet,contentDetails&order=date&maxResults=50";
        if(!realm.where(PlaylistItems.class).findAll().isEmpty()) {
            realm.beginTransaction();
            realm.where(PlaylistItems.class).findAll().clear();
            realm.commitTransaction();

        }
        loadPlaylist(realm,url,null,null);
        show = realm.where(PlaylistItems.class).findAllAsync();
        mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        rv.setLayoutManager(mLayoutManager);
        adapter = new RVAdapterGrid(show,getApplicationContext(),new ContextCompat());
        rv.setAdapter(adapter);
        rv.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                for(int j=0;j<show.size();j++){
                    if(show.get(j).getVideo_url().equals(adapter.getItem(position))){
                        pos = j;
                    }
                }
                Intent i = new Intent(ShowDetailActivity.this, youtubeActivity.class);
                i.putExtra("position",pos);
                searchView.setActivated(false);
                i.putExtra("playlist", realm.where(PlaylistItems.class).equalTo("video_url", adapter.getItem(position)).findFirst().getVideo_url());
                i.putExtra("url", urlforTime);
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
        rv.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading)
                    {
                        if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                        {
                            loading = false;
                            if(!show.last().getToken().isEmpty()) {
                                loadPlaylist(realm, url, show.last().getToken(), adapter);
                            }



                        }
                    }
                }
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                RealmQuery<PlaylistItems> result = realm.where(PlaylistItems.class).contains("name",newText.toString());
                adapter.updateRealmResults(result.findAll());
                return true;
            }
        });




    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
        }


    }
    public  void loadPlaylist(final Realm realm, String url, final String token, final RecyclerView.Adapter adapter){
        Log.e("volley",url);
        if(token!=null){
            url = url+"&pageToken="+token;
        }
        urlforTime = "https://www.googleapis.com/youtube/v3/videos?key=AIzaSyBawDQDFNHNE33OcXpUUqZGn2QSdZPv3pc&part=statistics,contentDetails&id=";
        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {

                        realm.beginTransaction();
                      // realm.where(PlaylistItems.class).findAll().clear();

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
                                    if(response.has("nextPageToken")) {
                                        pageToken = response.getString("nextPageToken");
                                        lastToken = pageToken;
                                    }else{pageToken = "";}


                                    PlaylistItems playlist = realm.createObject(PlaylistItems.class);
                                    playlist.setName(TextStringYoutube);
                                    playlist.setImg_url(ImageUrlStringYoutube);
                                    playlist.setVideo_url(VideoUrlYoutube);
                                    playlist.setToken(pageToken);
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
        if(adapter!=null) {
            adapter.notifyDataSetChanged();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                setResult(0);
                Log.d("","");

                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBackPressed() {
        if(searchView.isSearchOpen()){
            searchView.closeSearch();
        }else {
            super.onBackPressed();
            realm.beginTransaction();
            realm.where(PlaylistItems.class).findAll().clear();
            realm.commitTransaction();
            overridePendingTransition(R.anim.left_in, R.anim.right_out);
            finish();
        }

    }
}


