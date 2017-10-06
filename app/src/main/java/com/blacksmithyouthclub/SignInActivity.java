package com.blacksmithyouthclub;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.blacksmithyouthclub.Verification.VerificationActivity;
import com.blacksmithyouthclub.api.ApiClient;
import com.blacksmithyouthclub.api.ApiInterface;
import com.blacksmithyouthclub.helper.CommonMethods;
import com.blacksmithyouthclub.model.UserDataResponse;
import com.blacksmithyouthclub.realm.model.Notification;
import com.blacksmithyouthclub.realm.model.UserMaster;
import com.blacksmithyouthclub.session.SessionManager;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    private Context context = this;


    private Button btnLogin;
    private EditText edtMobile;
    private TextInputLayout edtMobileWrapper;
    private SpotsDialog spotsDialog;
    private String TAG = SignInActivity.class.getSimpleName();
    private CoordinatorLayout coordinatorLayout;
    private SessionManager sessionManager;
    private HashMap<String, String> userDetails = new HashMap<String, String>();

    @BindView(R.id.btnSignup)
    Button btnSignup;



    private Realm realm;
    private RealmResults<UserMaster> notificationsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ButterKnife.bind(this);
        this.realm = Realm.getDefaultInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("BSYC App Login");
        setTitle(getString(R.string.activity_signup));


        if (Build.VERSION.SDK_INT > 9) {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }

        spotsDialog = new SpotsDialog(context);
        spotsDialog.setCancelable(false);

        sessionManager = new SessionManager(context);
        userDetails = sessionManager.getSessionDetails();

        try {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getSupportActionBar().setHomeAsUpIndicator(back_button);
        } catch (Exception e) {
            e.printStackTrace();
        }


        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        edtMobile = (EditText) findViewById(R.id.edtMobile);
        edtMobileWrapper = (TextInputLayout) findViewById(R.id.edtMobileWrapper);


        btnLogin = (Button) findViewById(R.id.btnLogin);



        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, SignUpUserActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                boolean IsError = false;


                if (edtMobile.getText().toString().equals("")) {
                    IsError = true;
                    edtMobileWrapper.setError("Enter Mobile");
                    edtMobileWrapper.setErrorEnabled(true);


                } else {


                    if (edtMobile.getText().toString().length() == 10) {
                        edtMobileWrapper.setErrorEnabled(false);
                    } else {
                        IsError = true;
                        edtMobileWrapper.setErrorEnabled(true);
                        edtMobileWrapper.setError("Invalid Mobile");
                    }


                }

                if (IsError == false) {
                    getLoginDetailsFromServer();

                }
                /*
                Intent intent = new Intent(context, VerificationActivity.class);
                startActivity(intent);
                finish();*/
            }
        });


    }


    //onCreate Completed
    private void getLoginDetailsFromServer()
    {

        CommonMethods.showDialog(spotsDialog);


        String fcm_tokenid = "";
        try {
            MyFirebaseInstanceIDService mid = new MyFirebaseInstanceIDService();
            fcm_tokenid = String.valueOf(mid.onTokenRefreshNew(context));

        } catch (Exception e) {
            fcm_tokenid = "";
            e.printStackTrace();
        }



        ApiInterface apiClient = ApiClient.getClient().create(ApiInterface.class);
        Log.d(TAG, "URL getLoginDetails : " + CommonMethods.WEBSITE + "getLoginDetails?type=login&contact=" + edtMobile.getText().toString()+"&fcmtoken="+ fcm_tokenid +"");
        apiClient.getLoginDetails("login", edtMobile.getText().toString(),fcm_tokenid).enqueue(new Callback<UserDataResponse>() {
            @Override
            public void onResponse(Call<UserDataResponse> call, Response<UserDataResponse> response) {


                Log.d(TAG, "LoginEmployee Response Code : " + response.code());

                if (response.code() == 200)
                {


                    String str_error = response.body().getMESSAGE();
                    String str_error_original = response.body().getORIGINALMESSAGE();
                    boolean error_status = response.body().getERRORSTATUS();
                    boolean record_status = response.body().getRECORDS();

                    if (error_status == false) {
                        if (record_status == true)
                        {

                            List<UserDataResponse.DATum> arr = response.body().getDATA();

                            for (int i = 0; i < arr.size(); i++) {

                                int userId = arr.get(i).getId();

                                String userMobile = arr.get(i).getMobile();




                                // setUserDetails(String str_userid, String str_username, String str_email, String str_mobile, String str_avatar) {
                                sessionManager.setUserDetails(String.valueOf(userId), userMobile, arr.get(i).getAppovalStatus());



                                try {
                                    realm.beginTransaction();
                                    realm.deleteAll();
                                    realm.commitTransaction();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                try {
                                    //get realm instance
                                    realm = Realm.getDefaultInstance();

                                    //realm.refresh();

                                    realm.beginTransaction();

                                    UserMaster userMaster = new UserMaster();

                                    userMaster.setUserid(arr.get(i).getId());
                                    userMaster.setCasteName(arr.get(i).getCasteName());
                                    userMaster.setFirstName(arr.get(i).getFirstName());
                                    userMaster.setSurname(arr.get(i).getSurname());
                                    userMaster.setOriginalSurname(arr.get(i).getOriginalSurname());
                                    userMaster.setVillage(arr.get(i).getVillage());
                                    userMaster.setMaritalStatusId(arr.get(i).getMaritalStatusId());
                                    userMaster.setAbout(arr.get(i).getAbout());
                                    userMaster.setEmail(arr.get(i).getEmail());
                                    userMaster.setMobile(arr.get(i).getMobile());
                                    userMaster.setDoa(arr.get(i).getDoa());
                                    userMaster.setReligion(arr.get(i).getReligion());
                                    userMaster.setAvatar(arr.get(i).getAvatar());
                                    userMaster.setBloodGrpId(arr.get(i).getBloodGrpId());
                                    userMaster.setDob(arr.get(i).getDob());
                                    userMaster.setHeightId(arr.get(i).getHeightId());
                                    userMaster.setWeight(arr.get(i).getWeight());
                                    userMaster.setFathersName(arr.get(i).getFathersName());
                                    userMaster.setMothersName(arr.get(i).getMothersName());
                                    userMaster.setFathersFathersName(arr.get(i).getFathersFathersName());
                                    userMaster.setFathersMothersName(arr.get(i).getFathersMothersName());
                                    userMaster.setMothersFathersName(arr.get(i).getMothersFathersName());
                                    userMaster.setMothersMothersName(arr.get(i).getMothersMothersName());
                                    userMaster.setMothersFathersSurname(arr.get(i).getMothersFathersSurname());
                                    userMaster.setMothersFathersVillage(arr.get(i).getMothersFathersVillage());
                                    userMaster.setHusbandsName(arr.get(i).getHusbandsName());
                                    userMaster.setHusbandsFathersName(arr.get(i).getHusbandsFathersName());
                                    userMaster.setHusbandsMothersName(arr.get(i).getHusbandsMothersName());
                                    userMaster.setWifesName(arr.get(i).getWifesName());
                                    userMaster.setWifesFathersName(arr.get(i).getWifesFathersName());
                                    userMaster.setWifesMothersName(arr.get(i).getWifesMothersName());
                                    userMaster.setWifesFathersSurname(arr.get(i).getWifesFathersSurname());
                                    userMaster.setWifesFathersVillage(arr.get(i).getWifesFathersVillage());
                                    userMaster.setHomePhone(arr.get(i).getHomePhone());
                                    userMaster.setAddress(arr.get(i).getAddress());
                                    userMaster.setCurrentArea(arr.get(i).getCurrentArea());
                                    userMaster.setCityId(arr.get(i).getCityId());
                                    userMaster.setStateId(arr.get(i).getStateId());
                                    userMaster.setCountryId(arr.get(i).getCountryId());
                                    userMaster.setPincode(arr.get(i).getPincode());
                                    userMaster.setBusinessId(arr.get(i).getBusinessId());
                                    userMaster.setBusinesssubcategoryids(arr.get(i).getBusinesssubcategoryids());
                                    userMaster.setOccupationspecialization(arr.get(i).getOccupationspecialization());
                                    userMaster.setBusinessName(arr.get(i).getBusinessName());
                                    userMaster.setEducationName(arr.get(i).getEducationName());
                                    userMaster.setEducationSpecialization(arr.get(i).getEducationSpecialization());
                                    userMaster.setUsertypeId(arr.get(i).getUsertypeId());
                                    userMaster.setCreatedDate(arr.get(i).getCreatedDate());
                                    userMaster.setAppovalStatus(arr.get(i).getAppovalStatus());
                                    userMaster.setHobbyId(arr.get(i).getHobbyId());
                                    userMaster.setGender(arr.get(i).getGender());
                                    userMaster.setMaritalStatus(arr.get(i).getMaritalStatus());
                                    userMaster.setBloodgrpName(arr.get(i).getBloodgrpName());
                                    userMaster.setStateName(arr.get(i).getStateName());
                                    userMaster.setCityName(arr.get(i).getCityName());
                                    userMaster.setBusinessAddress(arr.get(i).getBusinessAddress());
                                    userMaster.setBusinessLogo(arr.get(i).getBusinessLogo());
                                    userMaster.setHeightName(arr.get(i).getHeightName());
                                    userMaster.setSurname(arr.get(i).getSurnameName());
                                    userMaster.setMob1(arr.get(i).getMob1());
                                    userMaster.setMob2(arr.get(i).getMob2());
                                    userMaster.setLandLine1(arr.get(i).getLandLine1());
                                    userMaster.setLandLine2(arr.get(i).getLandLine2());
                                    userMaster.setBusinessWebsite(arr.get(i).getBusinessWebsite());
                                    userMaster.setWorkTitle(arr.get(i).getWorkTitle());
                                    userMaster.setReferal_code(arr.get(i).getReferalCode());
                                    userMaster.setDocument_url(arr.get(i).getDocumentUrl());



                                    realm.copyToRealm(userMaster);
                                    realm.commitTransaction();
                                    Log.d(TAG, "Userdata has been added in database");
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }






                            }


                            CommonMethods.hideDialog(spotsDialog);

                            Intent intent = new Intent(context, VerificationActivity.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);


                        }
                    } else {

                        CommonMethods.hideDialog(spotsDialog);
                        Toast.makeText(context, "" + str_error, Toast.LENGTH_SHORT).show();
                    }


                } else {
                    CommonMethods.showErrorMessageWhenStatusNot200(context, response.code());
                }


                CommonMethods.hideDialog(spotsDialog);

            }

            @Override
            public void onFailure(Call<UserDataResponse> call, Throwable t) {

                CommonMethods.onFailure(context, TAG, t);

                CommonMethods.hideDialog(spotsDialog);
            }
        });




    }




   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_signup, menu);
        return super.onCreateOptionsMenu(menu);
    }*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
        {
            Intent intent=new Intent(context,SelectCasteActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(context,SelectCasteActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }



}
