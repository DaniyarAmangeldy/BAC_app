package com.example.daniyar_amangeldy.baspro;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.daniyar_amangeldy.baspro.realm.Resident;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    Realm realm;
    private String imageUrlString;
    private String Textstring;
    private String TimeString;
    private JSONObject mainTextJsonObject;
    private JSONObject mainImageJsonObject;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getInstance(getApplicationContext());



        realm.beginTransaction();
        realm.where(Resident.class).findAll().clear();
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
        realm.commitTransaction();
        realm.refresh();





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
                                    TimeString = mainTextJsonObject.getString("created_time");
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
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                    }
                }
        ), "tag_json_obj");




        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, new NewsFragment()).commit();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        BottomNavigationItem newsTab = new BottomNavigationItem
                (getResources().getString(R.string.main_tab_1), getResources().getColor(R.color.colorPrimary), R.drawable.ic_home_black_24dp);
        BottomNavigationItem videosTab = new BottomNavigationItem
                (getResources().getString(R.string.main_tab_2), getResources().getColor(R.color.colorPrimary), R.drawable.ic_play_circle_filled_black_24dp);
        BottomNavigationItem residentsTab = new BottomNavigationItem
                (getResources().getString(R.string.main_tab_3), getResources().getColor(R.color.colorPrimary), R.drawable.ic_stars_black_24dp);
        BottomNavigationItem accauntTab = new BottomNavigationItem
                (getResources().getString(R.string.main_tab_4), getResources().getColor(R.color.colorPrimary), R.drawable.ic_account_circle_black_24dp);

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


