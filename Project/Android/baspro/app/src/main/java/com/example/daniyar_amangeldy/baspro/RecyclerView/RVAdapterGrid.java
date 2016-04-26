package com.example.daniyar_amangeldy.baspro.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.daniyar_amangeldy.baspro.R;
import com.example.daniyar_amangeldy.baspro.realm.PlaylistItems;
import com.squareup.picasso.Picasso;
import com.victor.loading.rotate.RotateLoading;

import io.realm.RealmResults;

/**
 * Created by Daniyar_Amangeldy on 4/11/16.
 */
public class RVAdapterGrid extends  RecyclerView.Adapter<RVAdapterGrid.ViewHolder>{
    Context context;
    RealmResults<PlaylistItems> show;
    public RVAdapterGrid(RealmResults<PlaylistItems> show, Context context){
        this.show = show;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_recent_videos, parent, false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.progressBar.setVisibility(View.VISIBLE);
        holder.RecentDesc.setText(show.get(position).getName());
        holder.RecentDesc.setTextColor(context.getColor(R.color.white));
        holder.progressBar.start();
        holder.RecentPhoto.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        Picasso.with(context).load(show.get(position).getImg_url().toString())
                .into(holder.RecentPhoto, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        holder.RecentPhoto.setBackgroundColor(Color.WHITE);
                        holder.playIcon.setVisibility(View.VISIBLE);
                        holder.progressBar.setVisibility(View.GONE);

                    }


                    @Override
                    public void onError() {

                    }
                });

        //Picasso.with(context).load("recent.get(position).getImg_url().toString()").into(holder.RecentPhoto);


    }

    @Override
    public int getItemCount() {
        return show.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView RecentDesc;
        RotateLoading progressBar;
        ImageView RecentPhoto;
        ImageView playIcon;
        ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cvRecent);
            RecentDesc = (TextView)itemView.findViewById(R.id.descRecent);
            RecentPhoto = (ImageView)itemView.findViewById(R.id.imageRecent);
            playIcon  = (ImageView)itemView.findViewById(R.id.playIcon);
            progressBar = (RotateLoading) itemView.findViewById(R.id.rotateloadingPhoto1);
        }
    }

}
