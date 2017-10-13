package com.blacksmithyouthclub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SATHISH on 07-Apr-17.
 */

public class SearchData {


    String userId, firstName, originalSurname, village, mobile, address, avatar, fatherName, surnamename, businesssubcategoryname, membercount, businesssubcategorytitle, businesssubcategoryid, businesscategoryid, businesscategorytitle;


    public SearchData(String userId, String firstName, String originalSurname, String village, String mobile, String address, String avatar, String fatherName, String surnamename, String businesssubcategoryname, String membercount, String businesssubcategorytitle, String businesssubcategoryid, String businesscategoryid, String businesscategorytitle) {
        this.userId = userId;
        this.firstName = firstName;
        this.originalSurname = originalSurname;
        this.village = village;
        this.mobile = mobile;
        this.address = address;
        this.avatar = avatar;
        this.fatherName = fatherName;
        this.surnamename = surnamename;
        this.businesssubcategoryname = businesssubcategoryname;
        this.membercount = membercount;
        this.businesssubcategorytitle = businesssubcategorytitle;

        this.businesssubcategoryid = businesssubcategoryid;

        this.businesscategoryid = businesscategoryid;
        this.businesscategorytitle = businesscategorytitle;


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

    public String getSurnamename() {
        return surnamename;
    }

    public void setSurnamename(String surnamename) {
        this.surnamename = surnamename;
    }

    public String getBusinesssubcategoryname() {
        return businesssubcategoryname;
    }

    public void setBusinesssubcategoryname(String businesssubcategoryname) {
        this.businesssubcategoryname = businesssubcategoryname;
    }

    public String getMembercount() {
        return membercount;
    }

    public void setMembercount(String membercount) {
        this.membercount = membercount;
    }

    public String getBusinesssubcategorytitle() {
        return businesssubcategorytitle;
    }

    public void setBusinesssubcategorytitle(String businesssubcategorytitle) {
        this.businesssubcategorytitle = businesssubcategorytitle;
    }

    public String getBusinesssubcategoryid() {
        return businesssubcategoryid;
    }

    public void setBusinesssubcategoryid(String businesssubcategoryid) {
        this.businesssubcategoryid = businesssubcategoryid;
    }

    public String getBusinesscategoryid() {
        return businesscategoryid;
    }

    public void setBusinesscategoryid(String businesscategoryid) {
        this.businesscategoryid = businesscategoryid;
    }

    public String getBusinesscategorytitle() {
        return businesscategorytitle;
    }

    public void setBusinesscategorytitle(String businesscategorytitle) {
        this.businesscategorytitle = businesscategorytitle;
    }
}
