package com.example.daniyar_amangeldy.baspro.Fragment;



import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.daniyar_amangeldy.baspro.R;
import com.google.android.gms.common.SignInButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccauntFragment extends Fragment{



    private static final int REQUEST_CODE = 1;
    public static AccauntFragment newInstance(){
        return new AccauntFragment();
    }
SignInButton btn;
    public AccauntFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accaunt, container, false);
        return view;
        }
    }



