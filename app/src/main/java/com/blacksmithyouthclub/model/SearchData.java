package com.blacksmithyouthclub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SATHISH on 07-Apr-17.
 */

public class SearchData {


    String userId, firstName, originalSurname, village, mobile, address, avatar, fatherName;


    public SearchData(String userId, String firstName, String originalSurname, String village, String mobile, String address, String avatar, String fatherName) {
        this.userId = userId;
        this.firstName = firstName;
        this.originalSurname = originalSurname;
        this.village = village;
        this.mobile = mobile;
        this.address = address;
        this.avatar = avatar;
        this.fatherName = fatherName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getOriginalSurname() {
        return originalSurname;
    }

    public void setOriginalSurname(String originalSurname) {
        this.originalSurname = originalSurname;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }
}
