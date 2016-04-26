package com.example.daniyar_amangeldy.baspro.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.daniyar_amangeldy.baspro.R;
import com.example.daniyar_amangeldy.baspro.realm.PlaylistItems;
import com.squareup.picasso.Picasso;

import io.realm.RealmResults;

/**
 * Created by Daniyar_Amangeldy on 4/15/16.
 */
public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {
    Context context;
    private int selectedItem;
    RealmResults<PlaylistItems> List;

    public PlaylistAdapter(Context context,RealmResults<PlaylistItems> List,int pos){
        this.context=context;
        this.List=List;
        this.selectedItem= pos;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_view, parent, false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.playlistDesc.setTextColor(Color.WHITE);
        holder.playlistTime.setTextColor(Color.WHITE);
        holder.watch.setTextColor(Color.WHITE);
        holder.itemView.setSelected(selectedItem == position);
        Picasso.with(context).load(List.get(position).getImg_url()).into(holder.iv);
        holder.playlistDesc.setText(List.get(position).getName());
        holder.playlistTime.setText(List.get(position).getTime());
        holder.watch.setText(List.get(position).getWatch());



    }


    @Override
    public int getItemCount() {
        return List.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView playlistTime;
        View parentView;
        TextView playlistDesc;
        RelativeLayout layout;
        TextView watch;
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
            layout = (RelativeLayout) itemView.findViewById(R.id.playlist_item);
            iv = (ImageView) itemView.findViewById(R.id.imagePlaylist);
            playlistTime = (TextView) itemView.findViewById(R.id.timePlaylist);
            playlistDesc = (TextView) itemView.findViewById(R.id.textPlaylist);
            watch = (TextView) itemView.findViewById(R.id.watchCount);
        }

    }
}
