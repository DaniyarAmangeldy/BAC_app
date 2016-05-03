package com.example.daniyar_amangeldy.baspro.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.daniyar_amangeldy.baspro.R;
import com.example.daniyar_amangeldy.baspro.realm.Instagram;
import com.squareup.picasso.Picasso;
import com.victor.loading.rotate.RotateLoading;
import io.realm.RealmResults;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder>{
    Context context;
    int lastPosition=-1;
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
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.progressBar.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition = position;
        holder.progressBar.start();
        Picasso.with(context).load(post.get(position).getUrl().toString())
                .into(holder.Photo, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        if (holder.progressBar != null) {
                            holder.progressBar.stop();
                            holder.progressBar.setVisibility(View.GONE);
                            holder.Photo.setBackgroundColor(Color.WHITE);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                            RelativeLayout.LayoutParams paramsPhoto = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
                            params.setMargins(0,35,0,20);
                            holder.cv.setLayoutParams(params);
                            holder.Photo.setLayoutParams(paramsPhoto);
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });



        holder.Desc.setText(post.get(position).getText().toString());

    }

   /* public  String getDateCurrentTimeZone(long timestamp) {
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
    */

    @Override
    public int getItemCount() {
        return post.size();
    }
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView Desc;
        ImageView Photo;
        RotateLoading progressBar;
        ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            Desc = (TextView)itemView.findViewById(R.id.person_name);
            Photo = (ImageView)itemView.findViewById(R.id.person_photo);
            Photo.setHorizontalScrollBarEnabled(false);
            Photo.setVerticalScrollBarEnabled(false);
            progressBar = (RotateLoading) itemView.findViewById(R.id.rotateloadingPhoto);
        }
    }

}
