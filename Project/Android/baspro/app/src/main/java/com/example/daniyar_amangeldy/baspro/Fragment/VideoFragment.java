package com.example.daniyar_amangeldy.baspro.Fragment;


import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.daniyar_amangeldy.baspro.MainActivity;
import com.example.daniyar_amangeldy.baspro.R;
import com.example.daniyar_amangeldy.baspro.RecyclerView.RVAdapterHorizontal;
import com.example.daniyar_amangeldy.baspro.RecyclerView.RecyclerItemClickListener;
import com.example.daniyar_amangeldy.baspro.Youtube_player;
import com.example.daniyar_amangeldy.baspro.realm.RecentVideos;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment {
    Toolbar mToolbar;
    RecyclerView rv;

    public VideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_video, container, false);
        final Realm realm = Realm.getInstance(getContext());
        // Inflate the layout for this fragment
        mToolbar = (Toolbar) view.findViewById(R.id.toolbarVideo);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        rv = (RecyclerView) view.findViewById(R.id.horizontalVideoRv);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(llm);
        RealmResults recents = realm.where(RecentVideos.class).findAll();
        RVAdapterHorizontal adapter = new RVAdapterHorizontal(recents,getContext());
        rv.setAdapter(adapter);
        rv.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(getActivity(), Youtube_player.class);
                i.putExtra("url", realm.where(RecentVideos.class).findAll().get(position).getVideo_url());
                startActivity(i);
            }
        }));

        return view;
    }

}
