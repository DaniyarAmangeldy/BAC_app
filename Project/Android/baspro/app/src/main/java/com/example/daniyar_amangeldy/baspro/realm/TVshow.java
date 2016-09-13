package com.example.daniyar_amangeldy.baspro.realm;

import io.realm.RealmObject;

/**
 * Created by Daniyar_Amangeldy on 4/14/16.
 */
public class TVshow extends RealmObject {
    public String name;
    public String url;
    public int img_url;
    public String text;

    public void setName(String name){
        this.name = name;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public void setImg_url(int img_url) {
        this.img_url = img_url;
    }
    public String getName(){return name;}
    public String getUrl(){return url;}
    public int getImg_url(){return img_url;}
    public void setText(String text){
        this.text=text;
    }
    public String getText(){
        return this.text;
    }
}
