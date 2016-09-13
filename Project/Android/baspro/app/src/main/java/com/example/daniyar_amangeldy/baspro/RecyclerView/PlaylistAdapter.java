package com.example.daniyar_amangeldy.baspro.RecyclerView;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.daniyar_amangeldy.baspro.R;
import com.example.daniyar_amangeldy.baspro.realm.PlaylistItems;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import io.realm.RealmResults;

/**
 * Created by Daniyar_Amangeldy on 4/15/16.
 */
public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {
    Context context;
    private int selectedItem;
    RealmResults<PlaylistItems> List;
    ContextCompat compat;

    public PlaylistAdapter(Context context,RealmResults<PlaylistItems> List,int pos,ContextCompat compat){
        this.context=context;
        this.List=List;
        this.selectedItem= pos;
        this.compat = compat;
    }



    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_view, parent, false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }
//
//    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//
//        // Handle key up and key down and attempt to move selection
//        recyclerView.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
//
//                // Return false if scrolled to the bounds and allow focus to move off the list
//                if (event.getAction() == KeyEvent.ACTION_DOWN) {
//                    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
//                        return tryMoveSelection(lm, 1);
//                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
//                        return tryMoveSelection(lm, -1);
//                    }
//                }
//
//                return false;
//            }
//        });
//    }
//
//    private boolean tryMoveSelection(RecyclerView.LayoutManager lm, int direction) {
//        int tryFocusItem = selectedItem + direction;
//
//        // If still within valid bounds, move the selection, notify to redraw, and scroll
//        if (tryFocusItem >= 0 && tryFocusItem < getItemCount()) {
//            notifyItemChanged(selectedItem);
//            selectedItem = tryFocusItem;
//            notifyItemChanged(selectedItem);
//            lm.scrollToPosition(selectedItem);
//            return true;
//        }
//
//        return false;
//    }



    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.playlistDesc.setTextColor(compat.getColor(context,android.R.color.black));
        holder.channelName.setText("BAS TV");
        holder.channelName.setTextColor(compat.getColor(context,R.color.colorText));
        holder.playlistDesc.setTextSize(15);
        holder.playlistTime.setTextColor(Color.WHITE);
        holder.playlistTime.setTextSize(13);
        holder.watch.setTextColor(compat.getColor(context,R.color.colorText));
        holder.itemView.setSelected(selectedItem == position);
        Picasso.with(context).load(List.get(position).getImg_url()).fit().centerCrop().into(holder.iv);
        holder.playlistDesc.setText(List.get(position).getName());
        holder.playlistTime.setText(List.get(position).getTime());
        holder.eye.setImageResource(R.drawable.visible);
        holder.frame.setBackgroundColor(Color.TRANSPARENT);
        holder.watch.setText(List.get(position).getWatch());
        if(holder.itemView.isSelected()){
            holder.playlistDesc.setTextColor(Color.WHITE);
            holder.watch.setTextColor(Color.WHITE);
            holder.channelName.setTextColor(Color.WHITE);
            holder.frame.setBackgroundColor(Color.WHITE);
            holder.eye.setImageResource(R.drawable.visible_white);




        }



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
        FrameLayout colorTime;
        TextView watch;
        TextView channelName;
       FrameLayout frame;
        ImageView eye;
        ViewHolder(final View itemView) {
            super(itemView);
           itemView.setClickable(true);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return true;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyItemChanged(selectedItem);
                    selectedItem=getLayoutPosition();
                    notifyItemChanged(selectedItem);



                }

            });



            this.parentView = itemView;
            layout = (RelativeLayout) itemView.findViewById(R.id.playlist_item);
            iv = (ImageView) itemView.findViewById(R.id.imagePlaylist);
            playlistTime = (TextView) itemView.findViewById(R.id.timePlaylist);
            playlistDesc = (TextView) itemView.findViewById(R.id.textPlaylist);
            colorTime = (FrameLayout) itemView.findViewById(R.id.colorTime);
            frame = (FrameLayout) itemView.findViewById(R.id.imageFrame);
            watch = (TextView) itemView.findViewById(R.id.watchCount);
            eye = (ImageView) itemView.findViewById(R.id.eyeIcon);
            channelName = (TextView) itemView.findViewById(R.id.channelName);
        }

    }
}
