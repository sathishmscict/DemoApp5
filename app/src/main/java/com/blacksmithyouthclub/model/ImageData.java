package com.blacksmithyouthclub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SATHISH on 01-Oct-17.
 */

public class ImageData {


    @SerializedName("MESSAGE")
    @Expose
    private String mESSAGE;
    @SerializedName("ORIGINAL_MESSAGE")
    @Expose
    private Object oRIGINALMESSAGE;
    @SerializedName("ERROR_STATUS")
    @Expose
    private Boolean eRRORSTATUS;
    @SerializedName("RECORDS")
    @Expose
    private Boolean rECORDS;
    @SerializedName("ORIGINAL_ERROR")
    @Expose
    private String oRIGINALERROR;
    @SerializedName("DATA")
    @Expose
    private List<DATum> dATA = null;

    public String getMESSAGE() {
        return mESSAGE;
    }

    public void setMESSAGE(String mESSAGE) {
        this.mESSAGE = mESSAGE;
    }

    public Object getORIGINALMESSAGE() {
        return oRIGINALMESSAGE;
    }

    public void setORIGINALMESSAGE(Object oRIGINALMESSAGE) {
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

    public String getORIGINALERROR() {
        return oRIGINALERROR;
    }

    public void setORIGINALERROR(String oRIGINALERROR) {
        this.oRIGINALERROR = oRIGINALERROR;
    }

    public List<DATum> getDATA() {
        return dATA;
    }

    public void setDATA(List<DATum> dATA) {
        this.dATA = dATA;
    }

    public class DATum {

        @SerializedName("imageurl")
        @Expose
        private String imageurl;

        public String getImageurl() {
            return imageurl;
        }

        public void setImageurl(String imageurl) {
            this.imageurl = imageurl;
        }

    }

}
