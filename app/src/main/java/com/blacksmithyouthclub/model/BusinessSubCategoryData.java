package com.blacksmithyouthclub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SATHISH on 02-Oct-17.
 */

public class BusinessSubCategoryData {


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

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("businessCategoryName")
        @Expose
        private String businessCategoryName;
        @SerializedName("count")
        @Expose
        private Integer count;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getBusinessCategoryName() {
            return businessCategoryName;
        }

        public void setBusinessCategoryName(String businessCategoryName) {
            this.businessCategoryName = businessCategoryName;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

    }


}
