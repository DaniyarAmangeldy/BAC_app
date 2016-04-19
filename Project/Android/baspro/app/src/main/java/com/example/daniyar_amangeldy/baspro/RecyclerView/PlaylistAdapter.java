package com.example.daniyar_amangeldy.baspro.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.daniyar_amangeldy.baspro.R;
import com.example.daniyar_amangeldy.baspro.realm.PlaylistItems;
import com.squareup.picasso.Picasso;

import java.util.zip.Inflater;

import io.realm.RealmResults;

/**
 * Created by Daniyar_Amangeldy on 4/15/16.
 */
public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {
    Context context;
    private int selectedItem = 0;

    RealmResults<PlaylistItems> list;
    public PlaylistAdapter(Context context,RealmResults<PlaylistItems> list){
        this.context = context;
        this.list=list;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_view, parent, false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(context).load(list.get(position).getImg_url()).into(holder.iv);
        holder.playlistTime.setTextColor(Color.WHITE);
        holder.itemView.setSelected(selectedItem == position);
        holder.playlistDesc.setTextColor(Color.WHITE);
        holder.playlistDesc.setText(list.get(position).getName());
        holder.playlistTime.setText(list.get(position).getTime());


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView playlistTime;
        RelativeLayout rl;
        View parentView;
        TextView playlistDesc;
        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyItemChanged(selectedItem);
                    selectedItem = getLayoutPosition();
                    notifyItemChanged(selectedItem);
                }
            });

            this.parentView = itemView;
            itemView.setClickable(true);
            iv = (ImageView) itemView.findViewById(R.id.imagePlaylist);
            playlistTime = (TextView) itemView.findViewById(R.id.timePlaylist);
            playlistDesc = (TextView) itemView.findViewById(R.id.textPlaylist);
        }

    }
}
