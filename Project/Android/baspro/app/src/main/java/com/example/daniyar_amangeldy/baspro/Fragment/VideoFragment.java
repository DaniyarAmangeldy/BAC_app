package com.example.daniyar_amangeldy.baspro.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.daniyar_amangeldy.baspro.DetailActivities.ShowDetailActivity;
import com.example.daniyar_amangeldy.baspro.R;
import com.example.daniyar_amangeldy.baspro.RecyclerView.RVAdapterHorizontal;
import com.example.daniyar_amangeldy.baspro.RecyclerView.RecyclerItemClickListener;
import com.example.daniyar_amangeldy.baspro.Youtube.youtubeActivity;
import com.example.daniyar_amangeldy.baspro.realm.RecentVideos;
import com.example.daniyar_amangeldy.baspro.realm.TVshow;
import com.squareup.picasso.Picasso;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment {
    Toolbar mToolbar;
    RecyclerView rv;
    RecyclerView rvVert;


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
        rv = (RecyclerView) view.findViewById(R.id.VideoRv);
        RealmResults recents = realm.where(RecentVideos.class).findAll();
        RealmResults shows = realm.where(TVshow.class).findAll();
        RVAdapterHorizontal adapterHor = new RVAdapterHorizontal(recents,getContext());
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(llm);
        rv.setAdapter(adapterHor);
        rv.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(getActivity(), youtubeActivity.class);
                i.putExtra("url", realm.where(RecentVideos.class).findAll().get(position).getVideo_url());
                startActivity(i);
            }
        }));

        CardView cvBaseke = (CardView) view.findViewById(R.id.cvBaseke);
        CardView cvVuzery = (CardView) view.findViewById(R.id.cvVuzery);
        CardView cvKizikTimes = (CardView) view.findViewById(R.id.cvKizikTimes);
        CardView cvPatrul = (CardView) view.findViewById(R.id.cvPatrul);


        ImageView baseke = (ImageView) view.findViewById(R.id.imageBaseke);
        ImageView vuzery = (ImageView) view.findViewById(R.id.imageVuzery);
        ImageView patrul = (ImageView) view.findViewById(R.id.imagePatrul);
        ImageView kiziktimes = (ImageView) view.findViewById(R.id.imageKizikTimes);
        Picasso.with(getContext()).load(R.drawable.baseke).into(baseke);
        Picasso.with(getContext()).load(R.drawable.patrul).into(patrul);
        Picasso.with(getContext()).load(R.drawable.kiziktimes).into(kiziktimes);
        Picasso.with(getContext()).load(R.drawable.vuzery).into(vuzery);

      /*  LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(llm);
        rv.setAdapter(adapterHor);



        rvVert = (RecyclerView) view.findViewById(R.id.VideoRvVertical);
        rvVert.setLayoutManager(new LinearLayoutManager(getContext()));

        rvVert.setAdapter(adapterVertical);
        Log.e("showTest", String.valueOf(shows.size()));
        */



        cvBaseke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TVshow result = realm.where(TVshow.class).contains("name","Басеке").findFirst();
             Intent i = new Intent(getActivity(), ShowDetailActivity.class);
                i.putExtra("name",result.getName());
                i.putExtra("photo",result.getImg_url());
                i.putExtra("url",result.getUrl());
                startActivity(i);
            }
        });

        cvVuzery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TVshow result = realm.where(TVshow.class).contains("name","Вузеры").findFirst();
                Intent i = new Intent(getActivity(), ShowDetailActivity.class);
                i.putExtra("name",result.getName());
                i.putExtra("photo",result.getImg_url());
                i.putExtra("url",result.getUrl());
                startActivity(i);
            }
        });



        cvKizikTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TVshow result = realm.where(TVshow.class).contains("name","Кызык Times").findFirst();
                Intent i = new Intent(getActivity(), ShowDetailActivity.class);
                i.putExtra("name",result.getName());
                i.putExtra("photo",result.getImg_url());
                i.putExtra("url",result.getUrl());
                startActivity(i);
            }
        });



        cvPatrul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TVshow result = realm.where(TVshow.class).contains("name","Патруль").findFirst();
                Intent i = new Intent(getActivity(), ShowDetailActivity.class);
                i.putExtra("name",result.getName());
                i.putExtra("photo",result.getImg_url());
                i.putExtra("url",result.getUrl());
                startActivity(i);
            }
        });




        return view;
    }



}
