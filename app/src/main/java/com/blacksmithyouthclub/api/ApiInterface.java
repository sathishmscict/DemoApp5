package com.blacksmithyouthclub.api;



import com.blacksmithyouthclub.model.BusinessSubCategoryData;
import com.blacksmithyouthclub.model.BussinessCategoryData;
import com.blacksmithyouthclub.model.CityData;
import com.blacksmithyouthclub.model.ComboDataResponse;
import com.blacksmithyouthclub.model.CommonReponse;
import com.blacksmithyouthclub.model.ImageData;
import com.blacksmithyouthclub.model.MembersDataBySurname;
import com.blacksmithyouthclub.model.SpinnersData;
import com.blacksmithyouthclub.model.SurnamesData;
import com.blacksmithyouthclub.model.UserDataResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ApiInterface {




    @POST("getLoginDetails")
    @FormUrlEncoded
    Call<UserDataResponse> getLoginDetails(@Field("type") String type, @Field("contact") String mobileNo,@Field("fcmtoken")String fcmToken);


    @POST("VerificationService")
    @FormUrlEncoded
    Call<CommonReponse> getVerificatonCode(@Field("type") String type, @Field("code") String verificationCode, @Field("mobile") String mobile);

    @POST("getAllStates")
    @FormUrlEncoded
    Call<ComboDataResponse> getAllStates(@Field("type") String type, @Field("countryid") String countryId);



    @POST("getserviceForSpinnersData")
    @FormUrlEncoded
    Call<SpinnersData> getserviceForSpinnersData(@Field("type") String type, @Field("countryid") String countryId, @Field("casteid") String casteid);


    @POST("getAllCities")
    @FormUrlEncoded
    Call<CityData> getAllCities(@Field("type") String type, @Field("stateid") String stateId);



    @POST("GetImageUrl")
    @FormUrlEncoded
    Call<ImageData> getImageResponseFromServer(@Field("type") String type, @Field("imagecode") String base64imageCode);

    @POST("newUserRegistration")
    @FormUrlEncoded
    Call<UserDataResponse> newUserRegistration(@Field("type") String type, @Field("fname") String firstName,@Field("surname") String surname,@Field("mobileno") String mobileNo,@Field("fathersName") String fathersName,@Field("mothersName") String mothersName,@Field("village") String village,@Field("dob") String dob,@Field("gender") String gender,@Field("maritalStatus") int maritalStatus,@Field("area") String area,@Field("city") int city,@Field("state") int state,@Field("avatar") String avatar,@Field("referralcode") String referralcode,@Field("fcmtoken") String fcmtoken,@Field("devicetype") String devicetype,@Field("castename") String castename,@Field("document_url") String document_url);


    @POST("getAllSurname")
    @FormUrlEncoded
    Call<SurnamesData> getAllSurname(@Field("type") String type, @Field("casteid") int casteid, @Field("userid") String userId, @Field("fcmtoken") String fcmtoken);






    @POST("getAllUsersBySurname")
    @FormUrlEncoded
    Call<MembersDataBySurname> getAllUsersBySurnameId(@Field("type") String type, @Field("surnameid") String surnameid);



    @POST("getAllUsersByBusinessSubCategoryId")
    @FormUrlEncoded
    Call<MembersDataBySurname> getAllUsersByBusinessSubCategoryId(@Field("type") String type, @Field("categoryid") String categoryid);



    @POST("getAllBusiness")
    @FormUrlEncoded
    Call<BussinessCategoryData> getAllBusiness(@Field("type") String type);


    @POST("getAllBusinessSubCategory")
    @FormUrlEncoded
    Call<BusinessSubCategoryData> getAllBusinessSubCategory(@Field("type") String type, @Field("businessid") int categoryid);


    @POST("getDetailsBySearch")
    @FormUrlEncoded
    Call<MembersDataBySurname> getDetailsBySearch(@Field("type") String type, @Field("search") String searchText);



    @POST("updatePersonalDetails")
    @FormUrlEncoded
    Call<UserDataResponse> updatePersonalDetails(@Field("type") String type, @Field("fname") String name, @Field("surname") String surname, @Field("originalsurname") String originalsurname, @Field("dob") String dob, @Field("village") String village, @Field("maritalStatus") int maritalStatus, @Field("bloodgrpid") String bloodgrpid, @Field("heightid") int heightid, @Field("contact") String contact, @Field("weight") int weight, @Field("avatar") String avatar, @Field("about") String about, @Field("religion") String religion, @Field("hobby") String hobby, @Field("casteid") int casteid, @Field("gender") String gender, @Field("updateddate") String updateddate,@Field("doa") String doa);


    @POST("updateAddressDetails")
    @FormUrlEncoded
    Call<UserDataResponse> updateAddressDetails(@Field("type") String type, @Field("homephone") String homephone, @Field("stateid") int stateid, @Field("cityid") int cityid, @Field("address") String address, @Field("pincode") String pincode, @Field("currentarea") String currentarea, @Field("contact") String contact, @Field("updateddate") String updateddate);



    @POST("updateBusinessDetails")
    @FormUrlEncoded
    Call<UserDataResponse> updateBusinessDetails(@Field("type") String type, @Field("contact") String contact, @Field("businessname") String businessname, @Field("occupationspecialization") String occupationspecialization, @Field("businessaddress") String businessaddress, @Field("businesslogo") String businesslogo, @Field("occupationarea") String occupationarea, @Field("workroletitle") String workroletitle, @Field("workmob1") String workmob1, @Field("workmob2") String workmob2, @Field("worklandline1") String worklandline1, @Field("worklandline2") String worklandline2, @Field("businesswebsite") String businesswebsite, @Field("businessubcategoryids") String businessubcategoryids, @Field("date") String date);


    @POST("updateFamilyDetails")
    @FormUrlEncoded
    Call<UserDataResponse> updateFamilyDetails(@Field("type") String type, @Field("fathersname") String fathersname, @Field("mothersname") String mothersname, @Field("fathersfathername") String fathersfathername, @Field("fathersmothername") String fathersmothername, @Field("mothersfathername") String mothersfathername, @Field("mothersmothername") String mothersmothername, @Field("casteid") String casteid,@Field("mothersfathersurname") String mothersfathersurname, @Field("mothersfathervillage") String mothersfathervillage, @Field("husbandname") String husbandname, @Field("husbandsfathername") String husbandsfathername, @Field("husbandsmothername") String husbandsmothername, @Field("wifesname") String wifesname, @Field("wifesfathername") String wifesfathername, @Field("wifesmothername") String wifesmothername, @Field("wifesfathersurname") String wifesfathersurname, @Field("wifesfathervllage") String wifesfathervllage, @Field("updateddate") String updateddate, @Field("contact") String contact);
































}
