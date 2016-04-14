package com.example.daniyar_amangeldy.baspro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import io.realm.Realm;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.daniyar_amangeldy.baspro.Volley.AppController;
import com.example.daniyar_amangeldy.baspro.Fragment.AccauntFragment;
import com.example.daniyar_amangeldy.baspro.Fragment.NewsFragment;
import com.example.daniyar_amangeldy.baspro.Fragment.ResidentFragment;
import com.example.daniyar_amangeldy.baspro.Fragment.VideoFragment;
import com.example.daniyar_amangeldy.baspro.realm.Instagram;
import com.example.daniyar_amangeldy.baspro.realm.RecentVideos;
import com.example.daniyar_amangeldy.baspro.realm.Resident;
import com.example.daniyar_amangeldy.baspro.realm.TVshow;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    Realm realm;
    private String imageUrlString;
    private String Textstring;
    private String ImageUrlStringYoutube;
    private String VideoUrlYoutube;
    private String TextStringYoutube;
    private String TimeString;
    private JSONObject urlJSONObject;
    private JSONObject TitleJsonObject;
    private JSONObject videoJSONObject;
    private JSONObject mainTextJsonObject;
    private JSONObject mainImageJsonObject;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new NewsFragment()).commit();

        realm = Realm.getInstance(getApplicationContext());



        realm.beginTransaction();
        realm.where(Resident.class).findAll().clear();
        realm.where(TVshow.class).findAll().clear();
        Resident resident = realm.createObject(Resident.class);
        resident.setDesc("I'm Amirkhaaan");
        resident.setName("Amirkhan");
        resident.setPhoto(R.drawable.example);

        Resident resident1 = realm.createObject(Resident.class);
        resident1.setDesc("I'm Vkontante!");
        resident1.setName("VK");
        resident1.setPhoto(R.drawable.vk);

        Resident resident2 = realm.createObject(Resident.class);
        resident2.setDesc("I'm FacebooK!!");
        resident2.setName("Faceboook!");
        resident2.setPhoto(R.drawable.fb);

        TVshow tv = realm.createObject(TVshow.class);
        tv.setName("Пәленшеевы");
        tv.setUrl("PLIzJtYvkW5jK9z8JB7N08J1FEpyylw_-r");
        tv.setImg_url(R.drawable.palensheev);

        TVshow tv1 = realm.createObject(TVshow.class);
        tv1.setName("Вузеры");
        tv1.setUrl("PLIzJtYvkW5jIzsf5bOSXCGze9ouqqMfQr");
        tv1.setImg_url(R.drawable.vuzery);

        TVshow tv2 = realm.createObject(TVshow.class);
        tv2.setName("Басеке");
        tv2.setUrl("PLIzJtYvkW5jIH43etTz_9w9gYCd02_e3h");
        tv2.setImg_url(R.drawable.baseke);

        TVshow tv3 = realm.createObject(TVshow.class);
        tv3.setName("Патруль");
        tv3.setUrl("PLIzJtYvkW5jKcyyBvYn7DTwBRKLKrcWQa");
        tv3.setImg_url(R.drawable.patrul);

        TVshow tv4 = realm.createObject(TVshow.class);
        tv4.setName("Кызык Times");
        tv4.setUrl("PLIzJtYvkW5jJmdnz3Lk4igSSktbqCZU3Q");
        tv4.setImg_url(R.drawable.kiziktimes);

        realm.commitTransaction();
        realm.refresh();

       AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, "https://www.googleapis.com/youtube/v3/search?key=AIzaSyBawDQDFNHNE33OcXpUUqZGn2QSdZPv3pc&channelId=UCmh_2Vwnh4Ae8MkzFn2uPAA&part=id,snippet&order=date&maxResults=10",
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        realm.beginTransaction();
                        realm.where(RecentVideos.class).findAll().clear();
                        for (int index = 0; index < 10; index++) {

                            try {
                                TitleJsonObject = response.getJSONArray("items").getJSONObject(index).getJSONObject("snippet");
                                urlJSONObject = response.getJSONArray("items").getJSONObject(index).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("high");
                                videoJSONObject = response.getJSONArray("items").getJSONObject(index).getJSONObject("id");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                TextStringYoutube = TitleJsonObject.getString("title");
                                ImageUrlStringYoutube = urlJSONObject.getString("url");
                                VideoUrlYoutube = videoJSONObject.getString("videoId");
                                Log.e("url",ImageUrlStringYoutube);



                                RecentVideos recent = realm.createObject(RecentVideos.class);
                                recent.setName(TextStringYoutube);
                                recent.setImg_url(ImageUrlStringYoutube);
                                recent.setVideo_url(VideoUrlYoutube);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        Log.e("Youtube", "OK");
                        realm.commitTransaction();
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                    }
                }
        ), "tag_json_obj");




//275855784 || 482993112
        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, "https://api.instagram.com/v1/users/482993112/media/recent/?access_token=2253563781.137bf98.bd1c3693d2b84f80a7ab8d661f641437&scount=20",
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        realm.beginTransaction();
                        realm.where(Instagram.class).findAll().clear();
                        for (int index = 0; index < 20; index++) {

                            try {
                                mainImageJsonObject = response.getJSONArray("data").getJSONObject(index).getJSONObject("images").getJSONObject("standard_resolution");
                                mainTextJsonObject = response.getJSONArray("data").getJSONObject(index).optJSONObject("caption");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                imageUrlString = mainImageJsonObject.getString("url");
                                if (response.getJSONArray("data").getJSONObject(index).isNull("caption")) {
                                    Textstring = " ";
                                    TimeString = " ";
                                } else {
                                    Textstring = mainTextJsonObject.getString("text");
                                    TimeString = mainTextJsonObject.getString("created_time");
                                }


                                Instagram insta = realm.createObject(Instagram.class);
                                insta.setText(Textstring);
                                insta.setUrl(imageUrlString);
                                insta.setTime(TimeString);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        realm.commitTransaction();
                        realm.refresh();
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                    }
                }
        ), "tag_json_obj");






        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        BottomNavigationItem newsTab = new BottomNavigationItem
                (getResources().getString(R.string.main_tab_1), getResources().getColor(R.color.colorPrimaryDark), R.drawable.ic_home_black_24dp);
        BottomNavigationItem videosTab = new BottomNavigationItem
                (getResources().getString(R.string.main_tab_2), getResources().getColor(R.color.colorPrimaryDark), R.drawable.ic_play_circle_filled_black_24dp);
        BottomNavigationItem residentsTab = new BottomNavigationItem
                (getResources().getString(R.string.main_tab_3), getResources().getColor(R.color.colorPrimaryDark), R.drawable.ic_stars_black_24dp);
        BottomNavigationItem accauntTab = new BottomNavigationItem
                (getResources().getString(R.string.main_tab_4), getResources().getColor(R.color.colorPrimaryDark), R.drawable.ic_account_circle_black_24dp);

        bottomNavigationView.setOnBottomNavigationItemClickListener(new OnBottomNavigationItemClickListener() {
            @Override
            public void onNavigationItemClick(int index) {
                switch (index) {
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new NewsFragment()).commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new VideoFragment()).commit();
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new ResidentFragment()).commit();
                        break;
                    case 3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new AccauntFragment()).commit();
                        break;

                    default:
                        break;
                }
            }
        });


        bottomNavigationView.setTextInactiveSize(35);
        bottomNavigationView.setTextActiveSize(37);
        bottomNavigationView.addTab(newsTab);
        bottomNavigationView.addTab(videosTab);
        bottomNavigationView.addTab(residentsTab);
        bottomNavigationView.addTab(accauntTab);


    }
}


