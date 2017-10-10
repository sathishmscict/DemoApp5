package com.blacksmithyouthclub.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import com.blacksmithyouthclub.SelectLanguageActivity;
import com.blacksmithyouthclub.SignInActivity;

import java.util.HashMap;

/**
 * Created by Lincoln on 05/05/16.
 */
public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "offersapp";


    // Check For Activation
    public static final String KEY_CODE = "code",
            KEY_RECEIVECODE = "reccode";


    public static final String KEY_USER_AVATAR_URL = "UserAvatarURL";

    public static final String KEY_SELECTED_BUSINESS_SUBCATEGORY_ID="electedBusinessSubCategoryId",KEY_SELECTED_BUSINESS_SUBCATEGORY="SelectedBusinessSubCategory";






    public static final String KEY_SEARCH_USERID  = "SearchUserId" , KEY_SEARCH_USERNAME ="SeacrchUserName";
    public static final String KEY_SELECTED_SURNAME_ID ="SelectedSurnameId", KEY_SELECTED_SURNAME = "SelectedSurname";

    public static final String KEY_SELECTED_CASTE  = "SelectedCaste";
    public static final String  KEY_IS_ACTIVE = "IsActive";
    public static final String KEY_ENODEDED_STRING = "Encoded_string";
    public static final String KEY_USER_ID = "UserId", KEY_USER_VERIFICATION_STATUS = "VerificationStatus",KEY_USER_REFERAL_CODE = "ReferralCode", KEY_USER_DEVICE_TYPE = "DeviceType",  KEY_USER_IS_ACTIVE = "IsActive", KEY_USER_IS_REFERRED = "IsReferred", KEY_USER_MOBILE = "UserMobile";


    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void CheckSMSVerificationActivity(String reccode, String actstatus) {

        editor.putString(KEY_RECEIVECODE, reccode);
        editor.putString(KEY_USER_VERIFICATION_STATUS, actstatus);
        editor.commit();

    }






    public void setUserImageUrl(String imgURL) {

        editor.putString(KEY_USER_AVATAR_URL, imgURL);
        editor.commit();
    }




    /**
     * Get stored session data
     */
    public HashMap<String, String> getSessionDetails() {


        HashMap<String, String> user = new HashMap<String, String>();







        user.put(KEY_SEARCH_USERID, pref.getString(KEY_SEARCH_USERID, "0"));
        user.put(KEY_SEARCH_USERNAME, pref.getString(KEY_SEARCH_USERNAME, "0"));


        user.put(KEY_SELECTED_BUSINESS_SUBCATEGORY_ID, pref.getString(KEY_SELECTED_BUSINESS_SUBCATEGORY_ID, "0"));
        user.put(KEY_SELECTED_BUSINESS_SUBCATEGORY, pref.getString(KEY_SELECTED_BUSINESS_SUBCATEGORY, ""));


        user.put(KEY_SELECTED_SURNAME_ID, pref.getString(KEY_SELECTED_SURNAME_ID, "0"));
        user.put(KEY_SELECTED_SURNAME, pref.getString(KEY_SELECTED_SURNAME, ""));




        user.put(KEY_SELECTED_CASTE, pref.getString(KEY_SELECTED_CASTE, "0"));


        user.put(KEY_IS_ACTIVE, pref.getString(KEY_IS_ACTIVE, "0"));



        user.put(KEY_ENODEDED_STRING, pref.getString(KEY_ENODEDED_STRING, ""));
        // user.put(KEY_ORDERID, pref.getString(KEY_ORDERID, "0"));
        user.put(KEY_RECEIVECODE, pref.getString(KEY_RECEIVECODE, "0"));
        user.put(KEY_CODE, pref.getString(KEY_CODE, "0"));


        user.put(KEY_USER_VERIFICATION_STATUS, pref.getString(KEY_USER_VERIFICATION_STATUS, "0"));
        user.put(KEY_USER_AVATAR_URL, pref.getString(KEY_USER_AVATAR_URL, ""));


        user.put(KEY_USER_ID, pref.getString(KEY_USER_ID, "0"));

        user.put(KEY_USER_REFERAL_CODE, pref.getString(KEY_USER_REFERAL_CODE, ""));
        user.put(KEY_USER_DEVICE_TYPE, pref.getString(KEY_USER_DEVICE_TYPE, ""));

        user.put(KEY_USER_IS_ACTIVE, pref.getString(KEY_USER_IS_ACTIVE, "1"));
        user.put(KEY_USER_IS_REFERRED, pref.getString(KEY_USER_IS_REFERRED, "0"));

        user.put(KEY_USER_MOBILE, pref.getString(KEY_USER_MOBILE, "0"));


        return user;
    }

    public void setEncodedImage(String encodeo_image) {


        editor.putString(KEY_ENODEDED_STRING, encodeo_image);

        editor.commit();
    }


    public void createUserSendSmsUrl(String code) {

        editor.putString(KEY_CODE, code);

        editor.commit();

    }



    public void setReferalcode(String  referralCode)
    {

        editor.putString(KEY_USER_REFERAL_CODE , referralCode);
        editor.commit();

    }

    public void setUserDetails(String userid, String mobileno, boolean isactive) {





        editor.putString(KEY_USER_MOBILE, mobileno);



        editor.putString(KEY_USER_ID, userid);
        if(isactive)
        {
            editor.putString(KEY_IS_ACTIVE, "1");
        }
        else
        {
            editor.putString(KEY_IS_ACTIVE, "0");
        }






        editor.commit();


    }


    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, SelectLanguageActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    public  void setCaste(String strCaste)
    {
        editor.putString(KEY_SELECTED_CASTE , strCaste);

        editor.commit();

    }


    public  void setSelectedBusinessCategoryDetails(String businessSubCategoryId , String businessSubcategoryName)
    {


        editor.putString(KEY_SELECTED_BUSINESS_SUBCATEGORY_ID , businessSubCategoryId);
        editor.putString(KEY_SELECTED_BUSINESS_SUBCATEGORY , businessSubcategoryName);

        editor.commit();

    }





    public void setSelectedSurnameDetails(String surname_id, String surname) {


        editor.putString(KEY_SELECTED_SURNAME_ID , surname_id);
        editor.putString(KEY_SELECTED_SURNAME , surname);

        editor.commit();

    }

    public void setSearchUserDetails(String searchuserId, String searchUserName) {


        editor.putString(KEY_SEARCH_USERID , searchuserId);
        editor.putString(KEY_SEARCH_USERNAME , searchUserName);

        editor.commit();

    }

    public void setUserActivationStatus(Boolean isactive) {
        if(isactive)
        {
            editor.putString(KEY_IS_ACTIVE, "1");
        }
        else
        {
            editor.putString(KEY_IS_ACTIVE, "0");
        }


        editor.commit();
    }
}
