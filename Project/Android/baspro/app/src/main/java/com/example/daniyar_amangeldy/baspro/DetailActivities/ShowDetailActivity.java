package com.example.daniyar_amangeldy.baspro.DetailActivities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daniyar_amangeldy.baspro.R;
import com.example.daniyar_amangeldy.baspro.Youtube.youtubeActivity;
import com.github.fabtransitionactivity.SheetLayout;

import io.realm.Realm;

public class ShowDetailActivity extends AppCompatActivity implements SheetLayout.OnFabAnimationEndListener {
    SheetLayout shl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        Realm realm = Realm.getInstance(this);
        shl = (SheetLayout) findViewById(R.id.bottom_sheet);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.showDetailfab);
        ImageView iv = (ImageView) findViewById(R.id.showDetailBackdrop);
        TextView tv = (TextView) findViewById(R.id.showDetailText);
        iv.setImageDrawable(getDrawable(getIntent().getIntExtra("photo", 0)));
        tv.setText("asdasdsad");
        shl.setFab(fab);
        shl.setFabAnimationEndListener(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shl.expandFab();
            }
        });
    }

    public void onFabAnimationEnd() {
        Intent intent = new Intent(this, youtubeActivity.class);
        intent.putExtra("playlist", getIntent().getStringExtra("url"));
        startActivityForResult(intent, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            shl.contractFab();
        }


    }
}

