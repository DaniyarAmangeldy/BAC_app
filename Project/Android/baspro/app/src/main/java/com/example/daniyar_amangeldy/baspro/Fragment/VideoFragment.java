package com.example.daniyar_amangeldy.baspro.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.daniyar_amangeldy.baspro.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment {
    Toolbar mToolbar;

    public VideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_video, container, false);
        // Inflate the layout for this fragment
        mToolbar = (Toolbar) view.findViewById(R.id.toolbarVideo);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        return view;
    }

}
