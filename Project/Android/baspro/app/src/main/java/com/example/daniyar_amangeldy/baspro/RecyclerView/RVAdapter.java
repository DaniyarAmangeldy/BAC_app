package com.example.daniyar_amangeldy.baspro.RecyclerView;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daniyar_amangeldy.baspro.R;
import com.example.daniyar_amangeldy.baspro.realm.Instagram;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import io.realm.RealmResults;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder>{
    Context context;
    RealmResults<Instagram> post;
    public RVAdapter(RealmResults<Instagram> post,Context context){
        this.post = post;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context).load(post.get(position).getUrl().toString()).into(holder.Photo);

        holder.Desc.setText(post.get(position).getText().toString());
        holder.TimeString.setText(getDateCurrentTimeZone(Long.valueOf(post.get(position).getTime().toString())));

    }
    public  String getDateCurrentTimeZone(long timestamp) {
        try{
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
            Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(currenTimeZone);
        }catch (Exception e) {
        }
        return "";
    }

    @Override
    public int getItemCount() {
        return post.size();
    }
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView TimeString;
        TextView Desc;
        ImageView Photo;
        ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            Desc = (TextView)itemView.findViewById(R.id.person_name);
            TimeString = (TextView)itemView.findViewById(R.id.person_time);
            Photo = (ImageView)itemView.findViewById(R.id.person_photo);
            Photo.setHorizontalScrollBarEnabled(false);
            Photo.setVerticalScrollBarEnabled(false);
        }
    }

}