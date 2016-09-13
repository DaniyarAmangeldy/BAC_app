package com.example.daniyar_amangeldy.baspro.DetailActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daniyar_amangeldy.baspro.R;

public class ResidentDetailActivity extends AppCompatActivity {
    ImageView img;
    TextView desc;
    Toolbar mToolbar;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_detail);
        mToolbar = (Toolbar) findViewById(R.id.residentDetailToolbar);
        intent = getIntent();
        mToolbar.setTitle(intent.getStringExtra("name"));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        desc = (TextView) findViewById(R.id.residentDetailText);
        desc.setText(intent.getStringExtra("desc"));
        img = (ImageView) findViewById(R.id.residentDetailBackdrop);
        img.setImageDrawable(getResources().getDrawable(intent.getIntExtra("photo", 0)));


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(0);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
