package com.example.daniyar_amangeldy.baspro.Fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.daniyar_amangeldy.baspro.Volley.AppController;
import com.example.daniyar_amangeldy.baspro.R;
import com.example.daniyar_amangeldy.baspro.RecyclerView.RVAdapter;
import com.example.daniyar_amangeldy.baspro.realm.Instagram;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {
    Realm realm;
    RealmChangeListener changeListener;
    Toolbar mToolbar;
    SwipeRefreshLayout swipeRefreshLayout;
    private String imageUrlString;
    private String Textstring;
    private String TimeString;
    private JSONObject mainTextJsonObject;
    private JsonObjectRequest request;
    private JSONObject mainImageJsonObject;
    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_news, container, false);
        realm = Realm.getInstance(getContext());
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));


        RecyclerView rv = (RecyclerView)view.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        RealmResults insta = realm.where(Instagram.class).findAllAsync();
        final RVAdapter adapter = new RVAdapter(insta,view.getContext());
        rv.setAdapter(adapter);

        changeListener = new RealmChangeListener() {
            @Override
            public void onChange() {
                // This is called anytime the Realm database changes on any thread.
                // Please note, change listeners only work on Looper threads.
                // For non-looper threads, you manually have to use Realm.refresh() instead.
                adapter.notifyDataSetChanged(); // Update the UI
            }
        };
        // Tell Realm to notify our listener when the customers results
        // have changed (items added, removed, updated, anything of the sort).
        insta.addChangeListener(changeListener);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.green, R.color.red, R.color.yellow);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, "https://api.instagram.com/v1/users/482993112/media/recent/?access_token=2253563781.137bf98.bd1c3693d2b84f80a7ab8d661f641437&scount=20",
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                realm.beginTransaction();
                                Log.e("RefreshStatus", "Refreshing");
                                realm.where(Instagram.class).findAll().clear();
                                ;
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
                                Log.e("Refresh Status","Complete!");
                                realm.commitTransaction();
                                realm.refresh();
                            }
                        },

                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getContext(), getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                            }
                        }
                ), "tag_json_obj");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);





            }
        });



        return view;
}




}

