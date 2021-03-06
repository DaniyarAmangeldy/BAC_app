package com.example.daniyar_amangeldy.baspro.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Daniyar_Amangeldy on 4/15/16.
 */
public class PlaylistItems extends RealmObject {
    public String name;

    public String video_url;
    public String time;
    public String watch;
    public String token;
    public String img_url;

    public void setName(String name){
        this.name = name;
    }
    public void setImg_url(String img_url){
        this.img_url = img_url;
    }
    public void setVideo_url(String video_url){
        this.video_url = video_url;
    }
    public String getName(){
        return name;
    }
    public String getImg_url(){
        return img_url;
    }
    public String getVideo_url(){
        return video_url;
    }
    public void setTime(String time){this.time=time;}
    public String getTime(){return time;}
    public String getWatch(){return watch;}
    public void setWatch(String watch){this.watch = watch;}
    public void setToken(String token){this.token = token;}
    public String getToken(){return this.token;}
}
