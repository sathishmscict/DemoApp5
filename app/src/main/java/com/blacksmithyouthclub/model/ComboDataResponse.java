package com.blacksmithyouthclub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SATHISH on 07-Oct-17.
 */

public class ComboDataResponse {


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
    @SerializedName("SURNAME_DATA")
    @Expose
    private List<SURNAMEDATum> sURNAMEDATA = null;
    @SerializedName("MARITAL_DATA")
    @Expose
    private List<MARITALDATum> mARITALDATA = null;
    @SerializedName("STATE_DATA")
    @Expose
    private List<STATEDATum> sTATEDATA = null;
    @SerializedName("HEIGHT_DATA")
    @Expose
    private List<HEIGHTDATum> hEIGHTDATA = null;
    @SerializedName("BLOOD_GROUP_DATA")
    @Expose
    private List<BLOODGROUPDATum> bLOODGROUPDATA = null;

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

    public List<SURNAMEDATum> getSURNAMEDATA() {
        return sURNAMEDATA;
    }

    public void setSURNAMEDATA(List<SURNAMEDATum> sURNAMEDATA) {
        this.sURNAMEDATA = sURNAMEDATA;
    }

    public List<MARITALDATum> getMARITALDATA() {
        return mARITALDATA;
    }

    public void setMARITALDATA(List<MARITALDATum> mARITALDATA) {
        this.mARITALDATA = mARITALDATA;
    }

    public List<STATEDATum> getSTATEDATA() {
        return sTATEDATA;
    }

    public void setSTATEDATA(List<STATEDATum> sTATEDATA) {
        this.sTATEDATA = sTATEDATA;
    }

    public List<HEIGHTDATum> getHEIGHTDATA() {
        return hEIGHTDATA;
    }

    public void setHEIGHTDATA(List<HEIGHTDATum> hEIGHTDATA) {
        this.hEIGHTDATA = hEIGHTDATA;
    }

    public List<BLOODGROUPDATum> getBLOODGROUPDATA() {
        return bLOODGROUPDATA;
    }

    public void setBLOODGROUPDATA(List<BLOODGROUPDATum> bLOODGROUPDATA) {
        this.bLOODGROUPDATA = bLOODGROUPDATA;
    }


    public class BLOODGROUPDATum {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }


    public class HEIGHTDATum {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("heightname")
        @Expose
        private String heightname;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getHeightname() {
            return heightname;
        }

        public void setHeightname(String heightname) {
            this.heightname = heightname;
        }

    }


    public class MARITALDATum {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("maritalStatus")
        @Expose
        private String maritalStatus;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getMaritalStatus() {
            return maritalStatus;
        }

        public void setMaritalStatus(String maritalStatus) {
            this.maritalStatus = maritalStatus;
        }

    }


    public class STATEDATum {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("stateName")
        @Expose
        private String stateName;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

    }


    public class SURNAMEDATum {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("surname")
        @Expose
        private String surname;
        @SerializedName("count")
        @Expose
        private Object count;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public Object getCount() {
            return count;
        }

        public void setCount(Object count) {
            this.count = count;
        }

    }

}
