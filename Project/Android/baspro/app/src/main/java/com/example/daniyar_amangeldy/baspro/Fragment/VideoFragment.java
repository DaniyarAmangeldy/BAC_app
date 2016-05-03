package com.example.daniyar_amangeldy.baspro.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.daniyar_amangeldy.baspro.DetailActivities.ShowDetailActivity;
import com.example.daniyar_amangeldy.baspro.R;
import com.example.daniyar_amangeldy.baspro.RecyclerView.RVShowAdapter;
import com.example.daniyar_amangeldy.baspro.RecyclerView.RecyclerItemClickListener;
import com.example.daniyar_amangeldy.baspro.realm.TVshow;
import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment {
    Realm realm;

    public VideoFragment() {
    }
    public static VideoFragment newInstance(){
        return new VideoFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_video, container, false);
        realm = Realm.getInstance(getContext());
        RealmResults shows = realm.where(TVshow.class).findAllAsync();
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rvShow);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        RVShowAdapter adapter = new RVShowAdapter(getContext(),shows);
        rv.setAdapter(adapter);
        Log.e("check", realm.where(TVshow.class).findAll().get(0).getName());
        rv.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TVshow result = realm.where(TVshow.class).findAll().get(position);
                Intent i = new Intent(getActivity(), ShowDetailActivity.class);
                i.putExtra("name",result.getName());
                i.putExtra("photo",result.getImg_url());
                i.putExtra("url",result.getUrl());
                i.putExtra("text",result.getText());
                startActivity(i);
            }
        }));
        return view;
    }




}
