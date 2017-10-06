package com.blacksmithyouthclub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SATHISH on 02-Oct-17.
 */

public class MembersDataBySurname {


    @SerializedName("MESSAGE")
    @Expose
    private String mESSAGE;
    @SerializedName("ORIGINAL_MESSAGE")
    @Expose
    private String oRIGINALMESSAGE;
    @SerializedName("ERROR_STATUS")
    @Expose
    private Boolean eRRORSTATUS;
    @SerializedName("RECORDS")
    @Expose
    private Boolean rECORDS;
    @SerializedName("DATA")
    @Expose
    private List<DATum> dATA = null;

    public String getMESSAGE() {
        return mESSAGE;
    }

    public void setMESSAGE(String mESSAGE) {
        this.mESSAGE = mESSAGE;
    }

    public String getORIGINALMESSAGE() {
        return oRIGINALMESSAGE;
    }

    public void setORIGINALMESSAGE(String oRIGINALMESSAGE) {
        this.oRIGINALMESSAGE = oRIGINALMESSAGE;
    }

    public Boolean getERRORSTATUS() {
        return eRRORSTATUS;
    }

    public void setERRORSTATUS(Boolean eRRORSTATUS) {
        this.eRRORSTATUS = eRRORSTATUS;
    }

    public Boolean getRECORDS() {
        return rECORDS;
    }

    public void setRECORDS(Boolean rECORDS) {
        this.rECORDS = rECORDS;
    }

    public List<DATum> getDATA() {
        return dATA;
    }

    public void setDATA(List<DATum> dATA) {
        this.dATA = dATA;
    }


    public class DATum {

        @SerializedName("userId")
        @Expose
        private Integer userId;
        @SerializedName("firstName")
        @Expose
        private String firstName;
        @SerializedName("originalSurname")
        @Expose
        private String originalSurname;
        @SerializedName("village")
        @Expose
        private String village;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("avatar")
        @Expose
        private String avatar;
        @SerializedName("fatherName")
        @Expose
        private String fatherName;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
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


}
