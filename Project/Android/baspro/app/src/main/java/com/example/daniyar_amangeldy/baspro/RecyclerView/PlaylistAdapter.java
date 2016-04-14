package com.example.daniyar_amangeldy.baspro.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daniyar_amangeldy.baspro.R;
import com.squareup.picasso.Picasso;

import java.util.zip.Inflater;

import io.realm.RealmResults;

/**
 * Created by Daniyar_Amangeldy on 4/15/16.
 */
public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {
    Context context;
    String  url;
    String time;
    String desc;
    public PlaylistAdapter(Context context,String url,String time, String desc){
        this.context = context;
        this.time = time;
        this.url = url;
        this.desc = desc;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_view, parent, false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context).load(url).into(holder.iv);

        holder.playlistTime.setTextColor(Color.WHITE);
        holder.playlistDesc.setText(desc);
        if(time.length()==8){
            time = time.substring(2,4)+":"+time.substring(6,8);
        }
        if(time.length()==6){
            time = time.substring(2,3)+":"+time.substring(5,6);
        }
        if(time.length()==7){
            if(time.charAt(3)!='M'){
                time=time.substring(2,4)+":"+time.substring(6,7);
            }else{
                time=time.substring(2,3)+":"+time.substring(5,7);
            }

        }
        holder.playlistTime.setText(time);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView playlistTime;
        TextView playlistDesc;
        ViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.imagePlaylist);
            playlistTime = (TextView) itemView.findViewById(R.id.timePlaylist);
            playlistDesc = (TextView) itemView.findViewById(R.id.textPlaylist);
        }

    }
}
