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
import com.example.daniyar_amangeldy.baspro.realm.Resident;

import io.realm.RealmResults;

public class RVAdapterResident extends  RecyclerView.Adapter<RVAdapterResident.ViewHolder>{
    Context context;
    RealmResults<Resident> resident;
    public RVAdapterResident(RealmResults<Resident> resident,Context context){
        this.resident = resident;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_resident, parent, false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.residentText1.setText(resident.get(position).getName());
        holder.residentText2.setText(resident.get(position).getDesc());
        holder.residentText1.setTextColor(Color.WHITE);
        holder.residentText2.setTextColor(Color.WHITE);
        holder.residentPhoto.setImageDrawable(context.getResources().getDrawable(resident.get(position).getPhoto()));
    }

    @Override
    public int getItemCount() {
        return resident.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView residentText1;
        TextView residentText2;
        ImageView residentPhoto;
        ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            residentText1 = (TextView)itemView.findViewById(R.id.resident_text1);
            residentText2 = (TextView)itemView.findViewById(R.id.resident_text2);
            residentPhoto = (ImageView)itemView.findViewById(R.id.resident_image);
        }
    }
}

