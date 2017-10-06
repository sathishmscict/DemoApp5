package com.blacksmithyouthclub.realm.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by SATHISH on 10-Aug-17.
 */

public class Notification extends RealmObject {


    @PrimaryKey
    long id;
    int notificationid;

    String title, descr, imageURL, date, type,intenttype;
    boolean isReaded;





    public Notification() {

    }

    public Notification(long id, int notificationid, String title, String descr, String imageURL, String date, String type, boolean isreaded) {
        this.id = id;
        this.notificationid = notificationid;

        this.title = title;
        this.descr = descr;
        this.imageURL = imageURL;
        this.date = date;
        this.type = type;
        this.isReaded  = isreaded;
    }

    public boolean isReaded() {
        return isReaded;
    }

    public void setReaded(boolean readed) {
        isReaded = readed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNotificationid() {
        return notificationid;
    }

    public void setNotificationid(int notificationid) {
        this.notificationid = notificationid;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIntenttype() {
        return intenttype;
    }

    public void setIntenttype(String intenttype) {
        this.intenttype = intenttype;
    }
}
