package com.example.daniyar_amangeldy.baspro.RecyclerView;

import android.content.Context;
import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.daniyar_amangeldy.baspro.R;
import com.example.daniyar_amangeldy.baspro.realm.TVshow;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import io.realm.RealmResults;

/**
 * Created by Daniyar_Amangeldy on 4/21/16.
 */
public class RVShowAdapter extends RecyclerView.Adapter<RVShowAdapter.ViewHolder> {
    RealmResults<TVshow> list;
    Context context;

    public RVShowAdapter(Context context,RealmResults<TVshow> list){
     this.context = context;
        this.list = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_shows,parent,false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.textShow.setText(list.get(position).getName());
        Picasso.with(context).load(list.get(position).getImg_url()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textShow;
        ImageView image;
        CardView cv;

        public ViewHolder(View itemView){
            super(itemView);

            textShow = (TextView)itemView.findViewById(R.id.textShow);
            image = (ImageView)itemView.findViewById(R.id.imageBaseke);
            cv  = (CardView)itemView.findViewById(R.id.cvShow);
        }


    }
}
