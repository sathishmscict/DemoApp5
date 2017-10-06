package com.blacksmithyouthclub.model;

/**
 * Created by pc on 9/29/2017.
 */

public class Advertiesment_Pojo {

    private String AdverId,AdverName,AdverDesc,AdverImg;
    public Advertiesment_Pojo(String AdverId, String AdverName, String AdverDesc, String AdverImg) {
        this.AdverId=AdverId;
        this.AdverName=AdverName;

        this.AdverDesc=AdverDesc;
        this.AdverImg=AdverImg;
    }

    public String getAdverId() {
        return AdverId;
    }

    public void setAdverId(String adverId) {
        AdverId = adverId;
    }

    public String getAdverName() {
        return AdverName;
    }

    public void setAdverName(String adverName) {
        AdverName = adverName;
    }

    public String getAdverDesc() {
        return AdverDesc;
    }

    public void setAdverDesc(String adverDesc) {
        AdverDesc = adverDesc;
    }

    public String getAdverImg() {
        return AdverImg;
    }

    public void setAdverImg(String adverImg) {
        AdverImg = adverImg;
    }
}
