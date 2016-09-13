package com.example.daniyar_amangeldy.baspro.Fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    public static NewsFragment newInstance(){
        return new NewsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_news, container, false);
        realm = Realm.getDefaultInstance();


        RecyclerView rv = (RecyclerView)view.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        RealmResults insta = realm.where(Instagram.class).findAllAsync();
        final RVAdapter adapter = new RVAdapter(insta,view.getContext(),new ContextCompat());
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
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimary,R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                AppController.getInstance().addToRequestQueue(new JsonObjectRequest(Request.Method.GET, "https://api.instagram.com/v1/users/2274030128/media/recent/?access_token=2274030128.54c83de.869c11553138464d905bf0057e4da6ee&scount=20",
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                swipeRefreshLayout.setRefreshing(false);
                                realm.beginTransaction();
                                Log.e("Request JSON", "Making a request...");
                                realm.where(Instagram.class).findAll().clear();
                                ;
                                for (int index = 0; index < 20; index++) {

                                    try {
                                        mainImageJsonObject = response.getJSONArray("data").getJSONObject(index).getJSONObject("images").getJSONObject("standard_resolution");
                                        mainTextJsonObject = response.getJSONArray("data").getJSONObject(index).optJSONObject("caption");

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Log.e("Refresh Status", "Complete!");
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
                                Toast.makeText(getContext(), getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
                                Log.e("ConnectionTimeOut", "Offline mode");
                            }
                        }
                ), "tag_json_obj");
//





            }
        });



        return view;
}




}

