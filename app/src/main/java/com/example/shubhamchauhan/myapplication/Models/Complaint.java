package com.example.shubhamchauhan.myapplication.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by shubhamchauhan on 30/11/17.
 */

@Entity
public class Complaint {


    @PrimaryKey(autoGenerate = true)
    private int complaintID;

    @ColumnInfo(name = "category")
    private String category ;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "time")
    private String time;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "latitude")
    private String latitude;

    @ColumnInfo(name = "longitude")
    private String longitude;

    @ColumnInfo(name = "acknowledgeBool")
    private Boolean acknowledgeBool;


    public Complaint() {

    }



    public Complaint(int complaintID, String category, String title, String date, String time, String description, byte[] image, String location, String latitude, String longitude, Boolean acknowledgeBool) {
        this.complaintID = complaintID;
        this.category = category;
        this.title = title;
        this.date = date;
        this.time = time;
        this.description = description;
        this.image = image;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.acknowledgeBool = acknowledgeBool;
    }


    public int getComplaintID() {
        return complaintID;
    }

    public void setComplaintID(int complaintID) {
        this.complaintID = complaintID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Boolean getAcknowledgeBool() {
        return acknowledgeBool;
    }

    public void setAcknowledgeBool(Boolean acknowledgeBool) {
        this.acknowledgeBool = acknowledgeBool;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
