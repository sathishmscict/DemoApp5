package com.blacksmithyouthclub.parcel;

import android.os.Parcel;
import android.os.Parcelable;

import com.blacksmithyouthclub.model.MembersDataBySurname;
import com.blacksmithyouthclub.model.SearchData;

/**
 * Created by SATHISH on 11-Oct-17.
 */

public class UserData implements Parcelable {


    String userId, firstName, originalSurname, village, mobile, address, avatar, fatherName, businesssubcategoryname, surnamename, businesssubcategorytitle, membercount, businesssubcategoryid, businesscategoryid, businesscategorytitle;


    public UserData(String userId, String firstName, String originalSurname, String village, String mobile, String address, String avatar, String fatherName, String surnamename, String businesssubcategoryname, String businesssubcategorytitle, String membercount, String businesssubcategoryid, String businesscategoryid, String businesscategorytitle) {
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
        this.businesssubcategorytitle = businesssubcategorytitle;
        this.membercount = membercount;
        this.businesssubcategoryid = businesssubcategoryid;


        this.businesscategoryid = businesscategoryid;
        this.businesscategorytitle = businesscategorytitle;


    }

    /**
     * Use when reconstructing User object from parcel
     * This will be used only by the 'CREATOR'
     *
     * @param in a parcel to read this object
     */
    public UserData(Parcel in) {
        this.userId = in.readString();
        this.firstName = in.readString();
        this.originalSurname = in.readString();
        this.village = in.readString();
        this.mobile = in.readString();
        this.address = in.readString();
        this.avatar = in.readString();
        this.fatherName = in.readString();

        this.businesssubcategoryname = in.readString();
        this.businesssubcategoryid = in.readString();

        this.businesssubcategorytitle = in.readString();
        this.membercount = in.readString();
        this.surnamename = in.readString();

        this.businesscategoryid = in.readString();
        this.businesscategorytitle = in.readString();


    }

    //Parcel from searchActivity
    public UserData(SearchData searchData, String type) {

        this.userId = searchData.getUserId();
        this.firstName = searchData.getFirstName();
        this.originalSurname = searchData.getOriginalSurname();
        this.village = searchData.getVillage();
        this.mobile = searchData.getMobile();
        this.address = searchData.getAddress();
        this.avatar = searchData.getAvatar();
        this.fatherName = searchData.getFatherName();

        this.surnamename = searchData.getSurnamename();
        this.businesssubcategoryname = searchData.getBusinesssubcategoryname();
        this.businesssubcategorytitle = searchData.getBusinesssubcategorytitle();
        this.membercount = searchData.getMembercount();
        this.businesssubcategoryid = searchData.getBusinesssubcategoryid();

        this.businesscategoryid = searchData.getBusinesssubcategoryid();
        this.businesscategorytitle = searchData.getBusinesscategorytitle();


    }

    //Parcel from member data
    public UserData(MembersDataBySurname.DATum memberData) {

        this.userId = memberData.getUserId().toString();
        this.firstName = memberData.getFirstName();
        this.originalSurname = memberData.getOriginalSurname();
        this.village = memberData.getVillage();
        this.mobile = memberData.getMobile();
        this.address = memberData.getAddress();
        this.avatar = memberData.getAvatar();
        this.fatherName = memberData.getFatherName();
        this.surnamename = memberData.getSurnamename();
        this.businesssubcategoryname = memberData.getBusinesssubcategoryname();
        this.businesssubcategorytitle = memberData.getBusinesssubcategorytitle();
        this.membercount = memberData.getMembercount();
        this.businesssubcategoryid = memberData.getBusinesssubcategoryid();

        this.businesscategoryid = memberData.getBusinesscategoryid();
        this.businesscategorytitle = memberData.getBusinesscategorytitle();


    }


    /**
     * Define the kind of object that you gonna parcel,
     * You can use hashCode() here
     */

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Actual object serialization happens here, Write object content
     * to parcel one by one, reading should be done according to this write order
     *
     * @param dest  parcel
     * @param flags Additional flags about how the object should be written
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(firstName);
        dest.writeString(originalSurname);
        dest.writeString(village);
        dest.writeString(mobile);
        dest.writeString(address);
        dest.writeString(avatar);
        dest.writeString(fatherName);
        dest.writeString(businesssubcategoryname);
        dest.writeString(businesssubcategoryid);
        dest.writeString(businesssubcategorytitle);
        dest.writeString(membercount);
        dest.writeString(surnamename);
        dest.writeString(businesscategoryid);
        dest.writeString(businesscategorytitle);


    }

    /**
     * This field is needed for Android to be able to
     * create new objects, individually or as arrays
     * <p>
     * If you don’t do that, Android framework will through exception
     * Parcelable protocol requires a Parcelable.Creator object called CREATOR
     */
    public static final Parcelable.Creator<UserData> CREATOR = new Parcelable.Creator<UserData>() {

        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };


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

    public String getBusinesssubcategoryname() {
        return businesssubcategoryname;
    }

    public void setBusinesssubcategoryname(String businesssubcategoryname) {
        this.businesssubcategoryname = businesssubcategoryname;
    }

    public String getSurnamename() {
        return surnamename;
    }

    public void setSurnamename(String surnamename) {
        this.surnamename = surnamename;
    }

    public String getBusinesssubcategorytitle() {
        return businesssubcategorytitle;
    }

    public void setBusinesssubcategorytitle(String businesssubcategorytitle) {
        this.businesssubcategorytitle = businesssubcategorytitle;
    }

    public String getMembercount() {
        return membercount;
    }

    public void setMembercount(String membercount) {
        this.membercount = membercount;
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UserData) {
            UserData toCompare = (UserData) obj;
            return (this.firstName.equalsIgnoreCase(toCompare.getFirstName()));
        }

        return false;
    }

    @Override
    public int hashCode() {
        return (this.getFirstName()).hashCode();
    }


}
