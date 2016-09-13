package com.example.daniyar_amangeldy.baspro.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.daniyar_amangeldy.baspro.MainActivity;
import com.example.daniyar_amangeldy.baspro.R;
import com.example.daniyar_amangeldy.baspro.RecyclerView.RVAdapterResident;
import com.example.daniyar_amangeldy.baspro.RecyclerView.RecyclerItemClickListener;
import com.example.daniyar_amangeldy.baspro.DetailActivities.ResidentDetailActivity;
import com.example.daniyar_amangeldy.baspro.realm.Resident;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResidentFragment extends Fragment {
    Realm realm;
    RecyclerView rv;
    RealmChangeListener changeListener;
    Toolbar mToolbar;
    public ResidentFragment() {
    }
    public static ResidentFragment newInstance(){
        return new ResidentFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resident, container, false);
        realm = Realm.getDefaultInstance();
        rv = (RecyclerView) view.findViewById(R.id.rvResident);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        RealmResults residents  = realm.where(Resident.class).findAllAsync();
        final RVAdapterResident adapter = new RVAdapterResident(residents, getContext());
        rv.setAdapter(adapter);

        changeListener = new RealmChangeListener() {
            @Override
            public void onChange() {

               adapter.notifyDataSetChanged();
            }
        };
        residents.addChangeListener(changeListener);

        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), ResidentDetailActivity.class);
                        intent.putExtra("name",realm.where(Resident.class).findAll().get(position).getName());
                        intent.putExtra("desc",realm.where(Resident.class).findAll().get(position).getDesc());
                        intent.putExtra("photo",realm.where(Resident.class).findAll().get(position).getPhoto());
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                getActivity(),
                                new Pair<View, String>(view.findViewById(R.id.resident_image),
                                        getString(R.string.transition_name_circle)),
                                new Pair<View, String>(view.findViewById(R.id.resident_text1),
                                        getString(R.string.transition_name_name))

                        );
                        ActivityCompat.startActivityForResult(getActivity(), intent,1, options.toBundle());

                    }
                })
        );
        return view;
    }



}
