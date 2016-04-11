package com.example.daniyar_amangeldy.baspro.realm;

import io.realm.RealmObject;

/**
 * Created by Daniyar_Amangeldy on 4/7/16.
 */
public class Resident extends RealmObject {
    public String Name;
    public int Photo;
    public String Desc;

    public String getName(){return Name;}
    public int getPhoto(){return Photo;}
    public String getDesc(){return Desc;}
    public void setName(String Name){this.Name=Name;}
    public void setPhoto(int Photo){this.Photo=Photo;}
    public void setDesc(String Desc){this.Desc=Desc;}
}
