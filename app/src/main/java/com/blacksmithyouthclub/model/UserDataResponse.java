package com.blacksmithyouthclub.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by SATHISH on 11-Sep-17.
 */

public class UserDataResponse {








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
        @SerializedName("casteName")
        @Expose
        private String casteName;
        @SerializedName("firstName")
        @Expose
        private String firstName;
        @SerializedName("surname")
        @Expose
        private String surname;
        @SerializedName("originalSurname")
        @Expose
        private String originalSurname;
        @SerializedName("village")
        @Expose
        private String village;
        @SerializedName("maritalStatusId")
        @Expose
        private Integer maritalStatusId;
        @SerializedName("about")
        @Expose
        private String about;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("doa")
        @Expose
        private String doa;
        @SerializedName("religion")
        @Expose
        private String religion;
        @SerializedName("avatar")
        @Expose
        private String avatar;
        @SerializedName("bloodGrpId")
        @Expose
        private Integer bloodGrpId;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("heightId")
        @Expose
        private Integer heightId;
        @SerializedName("weight")
        @Expose
        private Integer weight;
        @SerializedName("fathersName")
        @Expose
        private String fathersName;
        @SerializedName("mothersName")
        @Expose
        private String mothersName;
        @SerializedName("fathersFathersName")
        @Expose
        private String fathersFathersName;
        @SerializedName("fathersMothersName")
        @Expose
        private String fathersMothersName;
        @SerializedName("mothersFathersName")
        @Expose
        private String mothersFathersName;
        @SerializedName("mothersMothersName")
        @Expose
        private String mothersMothersName;
        @SerializedName("mothersFathersSurname")
        @Expose
        private String mothersFathersSurname;

        @SerializedName("occupationspecialization")
        @Expose
        private String occupationspecialization;



        @SerializedName("mothersFathersVillage")
        @Expose
        private String mothersFathersVillage;
        @SerializedName("husbandsName")
        @Expose
        private String husbandsName;
        @SerializedName("husbandsFathersName")
        @Expose
        private String husbandsFathersName;
        @SerializedName("husbandsMothersName")
        @Expose
        private String husbandsMothersName;
        @SerializedName("wifesName")
        @Expose
        private String wifesName;
        @SerializedName("wifesFathersName")
        @Expose
        private String wifesFathersName;
        @SerializedName("wifesMothersName")
        @Expose
        private String wifesMothersName;
        @SerializedName("wifesFathersSurname")
        @Expose
        private String wifesFathersSurname;
        @SerializedName("wifesFathersVillage")
        @Expose
        private String wifesFathersVillage;
        @SerializedName("homePhone")
        @Expose
        private String homePhone;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("currentArea")
        @Expose
        private String currentArea;
        @SerializedName("cityId")
        @Expose
        private Integer cityId;
        @SerializedName("stateId")
        @Expose
        private Integer stateId;
        @SerializedName("countryId")
        @Expose
        private Integer countryId;
        @SerializedName("pincode")
        @Expose
        private Integer pincode;
        @SerializedName("businessId")
        @Expose
        private Integer businessId;

        @SerializedName("businesssubcategoryids")
        @Expose
        private String businesssubcategoryids;

        public String getBusinesssubcategoryids() {
            return businesssubcategoryids;
        }

        public void setBusinesssubcategoryids(String businesssubcategoryids) {
            this.businesssubcategoryids = businesssubcategoryids;
        }

        public String getOccupationspecialization() {
            return occupationspecialization;
        }

        public void setOccupationspecialization(String occupationspecialization) {
            this.occupationspecialization = occupationspecialization;
        }

        @SerializedName("businessName")
        @Expose
        private String businessName;
        @SerializedName("educationName")
        @Expose
        private String educationName;
        @SerializedName("educationSpecialization")
        @Expose
        private String educationSpecialization;
        @SerializedName("usertypeId")
        @Expose
        private Integer usertypeId;
        @SerializedName("createdDate")
        @Expose
        private String createdDate;
        @SerializedName("appovalStatus")
        @Expose
        private Boolean appovalStatus;
        @SerializedName("hobbyId")
        @Expose
        private String hobbyId;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("maritalStatus")
        @Expose
        private String maritalStatus;
        @SerializedName("bloodgrpName")
        @Expose
        private String bloodgrpName;
        @SerializedName("stateName")
        @Expose
        private String stateName;
        @SerializedName("cityName")
        @Expose
        private String cityName;
        @SerializedName("businessAddress")
        @Expose
        private String businessAddress;
        @SerializedName("businessLogo")
        @Expose
        private String businessLogo;
        @SerializedName("heightName")
        @Expose
        private String heightName;
        @SerializedName("surnameName")
        @Expose
        private String surnameName;
        @SerializedName("mob1")
        @Expose
        private String mob1;
        @SerializedName("mob2")
        @Expose
        private String mob2;
        @SerializedName("landLine1")
        @Expose
        private String landLine1;
        @SerializedName("landLine2")
        @Expose
        private String landLine2;
        @SerializedName("businessWebsite")
        @Expose
        private String businessWebsite;
        @SerializedName("workTitle")
        @Expose
        private String workTitle;
        @SerializedName("document_url")
        @Expose
        private String documentUrl;
        @SerializedName("referal_code")
        @Expose
        private String referalCode;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getCasteName() {
            return casteName;
        }

        public void setCasteName(String casteName) {
            this.casteName = casteName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
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

        public Integer getMaritalStatusId() {
            return maritalStatusId;
        }

        public void setMaritalStatusId(Integer maritalStatusId) {
            this.maritalStatusId = maritalStatusId;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getDoa() {
            return doa;
        }

        public void setDoa(String doa) {
            this.doa = doa;
        }

        public String getReligion() {
            return religion;
        }

        public void setReligion(String religion) {
            this.religion = religion;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public Integer getBloodGrpId() {
            return bloodGrpId;
        }

        public void setBloodGrpId(Integer bloodGrpId) {
            this.bloodGrpId = bloodGrpId;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public Integer getHeightId() {
            return heightId;
        }

        public void setHeightId(Integer heightId) {
            this.heightId = heightId;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public String getFathersName() {
            return fathersName;
        }

        public void setFathersName(String fathersName) {
            this.fathersName = fathersName;
        }

        public String getMothersName() {
            return mothersName;
        }

        public void setMothersName(String mothersName) {
            this.mothersName = mothersName;
        }

        public String getFathersFathersName() {
            return fathersFathersName;
        }

        public void setFathersFathersName(String fathersFathersName) {
            this.fathersFathersName = fathersFathersName;
        }

        public String getFathersMothersName() {
            return fathersMothersName;
        }

        public void setFathersMothersName(String fathersMothersName) {
            this.fathersMothersName = fathersMothersName;
        }

        public String getMothersFathersName() {
            return mothersFathersName;
        }

        public void setMothersFathersName(String mothersFathersName) {
            this.mothersFathersName = mothersFathersName;
        }

        public String getMothersMothersName() {
            return mothersMothersName;
        }

        public void setMothersMothersName(String mothersMothersName) {
            this.mothersMothersName = mothersMothersName;
        }

        public String getMothersFathersSurname() {
            return mothersFathersSurname;
        }

        public void setMothersFathersSurname(String mothersFathersSurname) {
            this.mothersFathersSurname = mothersFathersSurname;
        }

        public String getMothersFathersVillage() {
            return mothersFathersVillage;
        }

        public void setMothersFathersVillage(String mothersFathersVillage) {
            this.mothersFathersVillage = mothersFathersVillage;
        }

        public String getHusbandsName() {
            return husbandsName;
        }

        public void setHusbandsName(String husbandsName) {
            this.husbandsName = husbandsName;
        }

        public String getHusbandsFathersName() {
            return husbandsFathersName;
        }

        public void setHusbandsFathersName(String husbandsFathersName) {
            this.husbandsFathersName = husbandsFathersName;
        }

        public String getHusbandsMothersName() {
            return husbandsMothersName;
        }

        public void setHusbandsMothersName(String husbandsMothersName) {
            this.husbandsMothersName = husbandsMothersName;
        }

        public String getWifesName() {
            return wifesName;
        }

        public void setWifesName(String wifesName) {
            this.wifesName = wifesName;
        }

        public String getWifesFathersName() {
            return wifesFathersName;
        }

        public void setWifesFathersName(String wifesFathersName) {
            this.wifesFathersName = wifesFathersName;
        }

        public String getWifesMothersName() {
            return wifesMothersName;
        }

        public void setWifesMothersName(String wifesMothersName) {
            this.wifesMothersName = wifesMothersName;
        }

        public String getWifesFathersSurname() {
            return wifesFathersSurname;
        }

        public void setWifesFathersSurname(String wifesFathersSurname) {
            this.wifesFathersSurname = wifesFathersSurname;
        }

        public String getWifesFathersVillage() {
            return wifesFathersVillage;
        }

        public void setWifesFathersVillage(String wifesFathersVillage) {
            this.wifesFathersVillage = wifesFathersVillage;
        }

        public String getHomePhone() {
            return homePhone;
        }

        public void setHomePhone(String homePhone) {
            this.homePhone = homePhone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCurrentArea() {
            return currentArea;
        }

        public void setCurrentArea(String currentArea) {
            this.currentArea = currentArea;
        }

        public Integer getCityId() {
            return cityId;
        }

        public void setCityId(Integer cityId) {
            this.cityId = cityId;
        }

        public Integer getStateId() {
            return stateId;
        }

        public void setStateId(Integer stateId) {
            this.stateId = stateId;
        }

        public Integer getCountryId() {
            return countryId;
        }

        public void setCountryId(Integer countryId) {
            this.countryId = countryId;
        }

        public Integer getPincode() {
            return pincode;
        }

        public void setPincode(Integer pincode) {
            this.pincode = pincode;
        }

        public Integer getBusinessId() {
            return businessId;
        }

        public void setBusinessId(Integer businessId) {
            this.businessId = businessId;
        }

        public String getBusinessName() {
            return businessName;
        }

        public void setBusinessName(String businessName) {
            this.businessName = businessName;
        }

        public String getEducationName() {
            return educationName;
        }

        public void setEducationName(String educationName) {
            this.educationName = educationName;
        }

        public String getEducationSpecialization() {
            return educationSpecialization;
        }

        public void setEducationSpecialization(String educationSpecialization) {
            this.educationSpecialization = educationSpecialization;
        }

        public Integer getUsertypeId() {
            return usertypeId;
        }

        public void setUsertypeId(Integer usertypeId) {
            this.usertypeId = usertypeId;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public Boolean getAppovalStatus() {
            return appovalStatus;
        }

        public void setAppovalStatus(Boolean appovalStatus) {
            this.appovalStatus = appovalStatus;
        }

        public String getHobbyId() {
            return hobbyId;
        }

        public void setHobbyId(String hobbyId) {
            this.hobbyId = hobbyId;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getMaritalStatus() {
            return maritalStatus;
        }

        public void setMaritalStatus(String maritalStatus) {
            this.maritalStatus = maritalStatus;
        }

        public String getBloodgrpName() {
            return bloodgrpName;
        }

        public void setBloodgrpName(String bloodgrpName) {
            this.bloodgrpName = bloodgrpName;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getBusinessAddress() {
            return businessAddress;
        }

        public void setBusinessAddress(String businessAddress) {
            this.businessAddress = businessAddress;
        }

        public String getBusinessLogo() {
            return businessLogo;
        }

        public void setBusinessLogo(String businessLogo) {
            this.businessLogo = businessLogo;
        }

        public String getHeightName() {
            return heightName;
        }

        public void setHeightName(String heightName) {
            this.heightName = heightName;
        }

        public String getSurnameName() {
            return surnameName;
        }

        public void setSurnameName(String surnameName) {
            this.surnameName = surnameName;
        }

        public String getMob1() {
            return mob1;
        }

        public void setMob1(String mob1) {
            this.mob1 = mob1;
        }

        public String getMob2() {
            return mob2;
        }

        public void setMob2(String mob2) {
            this.mob2 = mob2;
        }

        public String getLandLine1() {
            return landLine1;
        }

        public void setLandLine1(String landLine1) {
            this.landLine1 = landLine1;
        }

        public String getLandLine2() {
            return landLine2;
        }

        public void setLandLine2(String landLine2) {
            this.landLine2 = landLine2;
        }

        public String getBusinessWebsite() {
            return businessWebsite;
        }

        public void setBusinessWebsite(String businessWebsite) {
            this.businessWebsite = businessWebsite;
        }

        public String getWorkTitle() {
            return workTitle;
        }

        public void setWorkTitle(String workTitle) {
            this.workTitle = workTitle;
        }

        public String getDocumentUrl() {
            return documentUrl;
        }

        public void setDocumentUrl(String documentUrl) {
            this.documentUrl = documentUrl;
        }

        public String getReferalCode() {
            return referalCode;
        }

        public void setReferalCode(String referalCode) {
            this.referalCode = referalCode;
        }

    }

}







