package com.example.daniyar_amangeldy.baspro.realm;

import io.realm.RealmObject;

/**
 * Created by Daniyar_Amangeldy on 3/29/16.
 */
public class Instagram extends RealmObject {
    public String url;
    public String text;
    public String time;

    public void setText(String text){
        this.text=text;
    }
    public String getText(){
        return this.text;
    }
    public void setTime(String time){
        this.time=time;
    }

    public String getTime() {
        return this.time;
    }
    public void setUrl(String url){
        this.url=url;
    }

    public String getUrl() {
        return this.url;
    }
}
