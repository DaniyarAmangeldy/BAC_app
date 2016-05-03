package com.example.daniyar_amangeldy.baspro;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import io.realm.Realm;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.daniyar_amangeldy.baspro.Fragment.AccauntFragment;
import com.example.daniyar_amangeldy.baspro.Volley.AppController;
import com.example.daniyar_amangeldy.baspro.Fragment.NewsFragment;
import com.example.daniyar_amangeldy.baspro.Fragment.ResidentFragment;
import com.example.daniyar_amangeldy.baspro.Fragment.VideoFragment;
import com.example.daniyar_amangeldy.baspro.realm.Instagram;
import com.example.daniyar_amangeldy.baspro.realm.Resident;
import com.example.daniyar_amangeldy.baspro.realm.TVshow;

import org.json.JSONException;
import org.json.JSONObject;



public class MainActivity extends AppCompatActivity {

    Realm realm;
    private String imageUrlString;
    private String Textstring;
    private String TimeString;
    private JSONObject mainTextJsonObject;
    private JSONObject mainImageJsonObject;
    TabLayout tabLayout;
    ViewPager viewPager;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getInstance(getApplicationContext());
        Toolbar toolbar =(Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

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
        tv1.setText(getResources().getString(R.string.VuzeryDesc));
        tv1.setUrl("PLIzJtYvkW5jIzsf5bOSXCGze9ouqqMfQr");
        tv1.setImg_url(R.drawable.vuzery);

        TVshow tv2 = realm.createObject(TVshow.class);
        tv2.setName("Басеке");
        tv2.setUrl("PLIzJtYvkW5jIH43etTz_9w9gYCd02_e3h");
        tv2.setText(getResources().getString(R.string.BasekeDesc));
        tv2.setImg_url(R.drawable.baseke);

        TVshow tv3 = realm.createObject(TVshow.class);
        tv3.setName("Патруль");
        tv3.setUrl("PLIzJtYvkW5jKcyyBvYn7DTwBRKLKrcWQa");
        tv3.setText(getResources().getString(R.string.PatrulDesc));
        tv3.setImg_url(R.drawable.patrul);

        TVshow tv4 = realm.createObject(TVshow.class);
        tv4.setName("Кызык Times");
        tv4.setUrl("PLIzJtYvkW5jJmdnz3Lk4igSSktbqCZU3Q");
        tv4.setText(getResources().getString(R.string.KizikTimesDesc));
        tv4.setImg_url(R.drawable.kiziktimes);


        realm.commitTransaction();
        realm.refresh();


        Log.e("Request JSON", "Sending a request to https://www.googleapis.com/youtube/v3/");



//275855784 || 482993112
        AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, "https://api.instagram.com/v1/users/2274030128/media/recent/?access_token=2274030128.54c83de.869c11553138464d905bf0057e4da6ee&scount=20",
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        realm.beginTransaction();
                        realm.where(Instagram.class).findAll().clear();
                        for (int index = 0; index < 20; index++) {

                            try {
                                mainImageJsonObject = response.getJSONArray("data").getJSONObject(index).getJSONObject("images").getJSONObject("standard_resolution");
                                mainTextJsonObject = response.getJSONArray("data").getJSONObject(index).getJSONObject("caption");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                imageUrlString = mainImageJsonObject.getString("url");

                                if (response.getJSONArray("data").getJSONObject(index).isNull("caption")) {
                                    Textstring = " ";
                                    TimeString = response.getJSONArray("data").getJSONObject(index).getString("created_time");
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


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(4);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        tabLayout.setTabsFromPagerAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.white);
                        tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);
                    }
                });
        setupTabIcons();
        viewPager.setPageMargin(40);
        viewPager.setPageMarginDrawable(R.color.colorBackground);
    }

    private void setupTabIcons() {
        int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.white);
        int tabSelectedIconColor = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_48dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_play_circle_filled_black_48dp);
        tabLayout.getTabAt(2).setIcon(getResources().getDrawable(R.drawable.ic_stars_black_48dp));
        tabLayout.getTabAt(3).setIcon(getResources().getDrawable(R.drawable.ic_account_circle_black_24dp));
        tabLayout.getTabAt(0).getIcon().setColorFilter(tabSelectedIconColor, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(3).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
    }



    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public ViewPagerAdapter(FragmentManager fm,int numTabs) {
            super(fm);
            this.mNumOfTabs = numTabs;
        }

        @Override
        public Fragment getItem(int position) {
            Log.e("position",String.valueOf(position));
            switch (position) {
                case 0:
                   NewsFragment news = new NewsFragment();
                    return news;
                case 1:
                    VideoFragment video = new VideoFragment();
                    return video;
                case 2:
                    ResidentFragment resident = new ResidentFragment();
                    return resident;
                case 3:
                    AccauntFragment accaunt = new AccauntFragment();
                    return accaunt;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }
}














