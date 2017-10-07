package com.blacksmithyouthclub;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blacksmithyouthclub.Verification.VerificationActivity;
import com.blacksmithyouthclub.adapter.SurnameAdapterRecyclerView;
import com.blacksmithyouthclub.api.ApiClient;
import com.blacksmithyouthclub.api.ApiInterface;
import com.blacksmithyouthclub.helper.CommonMethods;
import com.blacksmithyouthclub.helper.Utils;
import com.blacksmithyouthclub.model.MembersDataBySurname;
import com.blacksmithyouthclub.model.SurnamesData;
import com.blacksmithyouthclub.model.UserDataResponse;
import com.blacksmithyouthclub.realm.model.Notification;
import com.blacksmithyouthclub.realm.model.UserMaster;
import com.blacksmithyouthclub.session.SessionManager;
import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.rvSurnames)
    RecyclerView rvSurnames;

    @BindView(R.id.fab)
    FloatingActionButton fab_share;

    private SessionManager sessionManager;
    private Context context = this;
    private HashMap<String, String> userDetails = new HashMap<String, String>();
    private SpotsDialog spotsDialog;
    private String TAG = DashBoardActivity.class.getSimpleName();
    private SurnameAdapterRecyclerView adapter;
    private MenuItem cart;
    private List<SurnamesData.DATum> list_Surnames = new ArrayList<SurnamesData.DATum>();

    private Realm realm;
    private UserMaster userData;
    UserMaster u = new UserMaster();
    private RealmResults<Notification> notificationsData;
    private TextView tvUserName;
    private TextView tvEmail;
    private CircularImageView imgProfilePic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_dash_board);

        ButterKnife.bind(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        setTitle(getString(R.string.app_name));


        sessionManager = new SessionManager(context);
        userDetails = sessionManager.getSessionDetails();


        spotsDialog = new SpotsDialog(context);
        spotsDialog.setCancelable(false);

        this.realm = Realm.getDefaultInstance();


        userData = u.getUserData(realm, Long.parseLong(userDetails.get(SessionManager.KEY_USER_ID)));

        //  Toast.makeText(context, "User Iddd : " + userData.getUserid(), Toast.LENGTH_SHORT).show();
        try {
            Log.d(TAG, " USer Id : " + userData.getUserid());
        } catch (Exception e) {
            Log.d(TAG, " USer Id Error : " + e.getMessage());
            e.printStackTrace();
        }

        LinearLayoutManager lManager = new LinearLayoutManager(context);
        rvSurnames.setLayoutManager(lManager);






        fab_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {


                    CommonMethods.showDialog(spotsDialog);




                    Dexter.withActivity(DashBoardActivity.this)
                            .withPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse response) {
                                    try {
                                        final String shareDescr = "Inviting you to connect with  Luhar Suthar Family  on BlackSmith Youth Club App.Use my referral code : "+ userDetails.get(sessionManager.KEY_USER_REFERAL_CODE) +" . Download app from https://goo.gl/gMVxym";


                              /*  Intent sendIntent = new Intent();
                                sendIntent.setAction(Intent.ACTION_SEND);
                                sendIntent.putExtra(Intent.EXTRA_TEXT,
                                        shareDescr);
                                sendIntent.setType("text/plain");
                                startActivity(sendIntent);
*/
                                        Picasso.with(getApplicationContext()).load("http://blacksmith.studyfield.com/Resources/app_logo.png").into(new Target()
                                        {
                                            @Override
                                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                                Intent i = new Intent(Intent.ACTION_SEND);
                                                i.setType("image/*");

                                                i.putExtra(Intent.EXTRA_TEXT,
                                                        shareDescr);
                                                i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
                                                startActivity(Intent.createChooser(i, "Share Image"));
                                                CommonMethods.hideDialog(spotsDialog);
                                            }

                                            @Override
                                            public void onBitmapFailed(Drawable errorDrawable) {
                                                CommonMethods.hideDialog(spotsDialog);
                                                Toast.makeText(context, "Try again...", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onPrepareLoad(Drawable placeHolderDrawable) {
                                                //Toast.makeText(context, "Try again...", Toast.LENGTH_SHORT).show();
                                                CommonMethods.showDialog(spotsDialog);
                                            }
                                        });
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        CommonMethods.hideDialog(spotsDialog);
                                    }


                                }

                                @Override
                                public void onPermissionDenied(PermissionDeniedResponse response) {/* ... */
                                    CommonMethods.hideDialog(spotsDialog);}

                                @Override
                                public void onPermissionRationaleShouldBeShown(PermissionRequest
                                                                                       permission, PermissionToken token) {/* ... */
                                    CommonMethods.hideDialog(spotsDialog);}
                            }).check();


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });



        rvSurnames.addOnItemTouchListener(new CommonMethods.RecyclerTouchListener(this, rvSurnames, new CommonMethods.ClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View view, int position) {


                sessionManager.setSelectedSurnameDetails(String.valueOf(list_Surnames.get(position).getId()), list_Surnames.get(position).getSurname());

                if(list_Surnames.get(position).getCount().equals("0"))
                {

                    CommonMethods.showAlertDialog(context , "Information","Members not joined yet");
                }
                else
                {
                    Intent intent = new Intent(context, MembersDataBySurnameActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

                }



            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }
        ));

/*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/



/**
 * Side Navigation Drawer
 */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setVisibility(View.VISIBLE);


        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_dash_board);

        tvUserName = (TextView) headerLayout.findViewById(R.id.tvName);
        imgProfilePic = (CircularImageView)headerLayout.findViewById(R.id.imgProfilePic);
        tvEmail = (TextView) headerLayout.findViewById(R.id.tvEmail);


        try {
            //Glide.with(context).load(userData.getAvatar()).error(R.mipmap.ic_launcher).into(imgProfilePic);
          /*  Picasso.with(context)
                    .load(userData.getAvatar())
                    .placeholder(R.drawable.app_logo)
                    .error(R.drawable.app_logo)
                    .into(imgProfilePic);*/

            //sessionManager.setEncodedImage(CommonMethods.getStringImage(myBitmapAgain));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            SetUserProfilePictireFromBase64EnodedString();
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG , "Error in setup Profile Picture");
        }

        imgProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProfileActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });


        if (userDetails.get(SessionManager.KEY_USER_MOBILE).length() == 10 && userDetails.get(SessionManager.KEY_USER_VERIFICATION_STATUS).equals("0")) {
            Intent intent = new Intent(context, VerificationActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        } else if (userDetails.get(SessionManager.KEY_USER_MOBILE).length() != 10 && !userDetails.get(SessionManager.KEY_USER_ID).equals("0")) {
            Intent intent = new Intent(context, SignInActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        } else if (userDetails.get(SessionManager.KEY_USER_ID).equals("0")) {


            Intent intent = new Intent(context, SelectLanguageActivity.class);
            startActivity(intent);

            finish();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        } else {

            try {
                tvUserName.setText(userData.getFirstName()+" "+userData.getSurname());
                tvEmail.setText(userData.getEmail());
            } catch (Exception e) {
                e.printStackTrace();
            }


            if (!userDetails.get(SessionManager.KEY_IS_ACTIVE).equals("0")) {

               getAllSurnameData();


            } else {


                getAllSurnameData();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(getString(R.string.str_approval_status));
                builder.setMessage(getString(R.string.str_approval_descr) + " " + userData.getCreatedDate());
                builder.setNeutralButton(getString(R.string.str_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.cancel();
                        dialogInterface.dismiss();

                        Intent intent = new Intent(context, DashBoardActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });
                builder.setCancelable(false);

               builder.show();


            }


        }






    }


    private void SetUserProfilePictireFromBase64EnodedString() {
        CommonMethods.showDialog(spotsDialog);

        //tvUserName.setText(userDetails.get(SessionManager.KEY_USER_NAME));
        //tvEmail.setText(userDetails.get(SessionManager.KEY_USER_EMAIL));

        try {
            userDetails = sessionManager.getSessionDetails();
            String myBase64Image = userDetails.get(SessionManager.KEY_ENODEDED_STRING);
            if (!myBase64Image.equals("")) {

                Bitmap myBitmapAgain = CommonMethods.decodeBase64(myBase64Image);


                imgProfilePic.setImageBitmap(myBitmapAgain);
            }
            else
            {


                try {


                    URL url = new URL(userData.getAvatar().toString());

                    Log.d("Image URL : ", "" + userData.getAvatar());
                    Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                    imgProfilePic.setImageBitmap(image);
                    String enc = CommonMethods.getStringImage(image);
                  //  Toast.makeText(context, "BASE64 : "+enc, Toast.LENGTH_SHORT).show();
                    sessionManager.setEncodedImage(enc);
                } catch (IOException e) {
                    e.printStackTrace();
                }



            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Decode Img Exception : ", e.getMessage());
            CommonMethods.hideDialog(spotsDialog);
        }
        CommonMethods.hideDialog(spotsDialog);
    }


    public Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }




    private void getAllSurnameData() {

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
        Log.d(TAG, "URL getAllSurname : " + CommonMethods.WEBSITE + "getAllSurname?type=surnameall&casteid=" + userDetails.get(SessionManager.KEY_SELECTED_CASTE) + "&userid=" + userDetails.get(SessionManager.KEY_USER_ID) + "");
        apiClient.getAllSurname("surnameall", Integer.parseInt(userDetails.get(SessionManager.KEY_SELECTED_CASTE)), userDetails.get(SessionManager.KEY_USER_ID)).enqueue(new Callback<SurnamesData>() {
            @Override
            public void onResponse(Call<SurnamesData> call, Response<SurnamesData> response) {


                Log.d(TAG, "getAllSurname Response Code : " + response.code());

                if (response.code() == 200) {


                    String str_error = response.body().getMESSAGE();
                    String str_error_original = response.body().getORIGINALMESSAGE();
                    boolean error_status = response.body().getERRORSTATUS();
                    boolean record_status = response.body().getRECORDS();
                    boolean approvalStatus = response.body().getAPPROVALSTATUS();

                    sessionManager.setUserDetails(userDetails.get(SessionManager.KEY_USER_ID),userDetails.get(SessionManager.KEY_USER_MOBILE), approvalStatus);

                    if (error_status == false) {
                        if (record_status == true) {

                            list_Surnames = response.body().getDATA();


                            adapter = new SurnameAdapterRecyclerView(context, list_Surnames);
                            rvSurnames.setAdapter(adapter);


                            List<SurnamesData.LOGINDATum> arr = response.body().getLOGINDATA();

                            for (int i = 0; i < arr.size(); i++) {

                                int userId = arr.get(i).getId();

                                String userMobile = arr.get(i).getMobile();




                                // setUserDetails(String str_userid, String str_username, String str_email, String str_mobile, String str_avatar) {
                                sessionManager.setUserDetails(String.valueOf(userId), userMobile, arr.get(i).getAppovalStatus());
                                sessionManager.setReferalcode(arr.get(i).getReferalCode());
                                sessionManager.setUserActivationStatus();

                                userDetails = sessionManager.getSessionDetails();



                                try {


                                    try {
                                        realm.beginTransaction();
                                        realm.delete(UserMaster.class);
                                        realm.commitTransaction();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                    //get realm instance
                                   // realm = Realm.getDefaultInstance();

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
            public void onFailure(Call<SurnamesData> call, Throwable t) {

                try {
                    CommonMethods.onFailure(context, TAG, t);

                    CommonMethods.hideDialog(spotsDialog);

                    if(t.getMessage().contains("Unable to resolve host"))
                    {

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("No Internet Connection");
                        builder.setMessage(getString(R.string.no_network3));
                        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialogInterface.dismiss();
                                dialogInterface.cancel();

                                Intent intent = new Intent(context, DashBoardActivity.class);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                            }
                        });
                        builder.show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.


        try {
            getMenuInflater().inflate(R.menu.dash_board, menu);
            cart = (MenuItem) menu.findItem(R.id.menu_notification);


            LinkedHashMap<String, String> lhm = new LinkedHashMap<String, String>();


//int orderid=db.getMaxOrderId();


            setAddToCartBadget();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return true;
    }

    private void setAddToCartBadget() {

        //    String query = "select * from " + db.TABLE_ORDER;


        try {
            LayerDrawable icon = (LayerDrawable) cart.getIcon();


            realm.refresh();

            realm.beginTransaction();

            //notificationsData = realm.where(Notification.class).findAllSorted("id", Sort.DESCENDING);
            notificationsData = realm.where(Notification.class).equalTo("isReaded", false).findAllSorted("id", Sort.DESCENDING);
            //personsData.deleteAllFromRealm();
            realm.commitTransaction();

            if (notificationsData.size() != 0) {
                Utils.setBadgeCount(context, icon, notificationsData.size());
            }


            // startCountAnimation();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_notification) {


            Intent intent = new Intent(context, NotificationActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

            return true;
        } else if (id == R.id.menu_search) {


            Intent intent = new Intent(context, SearchActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_directory) {

            Intent intent = new Intent(context, DashBoardActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

            // Handle the camera action
        } else if (id == R.id.nav_business) {


            Intent intent = new Intent(context, BusinessSubCategoryActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);


        } else if (id == R.id.nav_profile) {

            Intent intent = new Intent(context, ProfileActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);


        } else if (id == R.id.nav_events) {


            Intent intent = new Intent(context, EventsActivity.class);

            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);


        } else if (id == R.id.nav_advertisements) {

            Intent intent = new Intent(context, AdvertisementActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_videos) {


            Intent intent = new Intent(context, VideosActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);


        } else if (id == R.id.nav_contactus) {

            Intent intent = new Intent(context, ContactUsActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        } else if (id == R.id.nav_logout) {

            try {
                realm.beginTransaction();
                realm.deleteAll();
                realm.commitTransaction();
            } catch (Exception e) {
                e.printStackTrace();
            }


            sessionManager.logoutUser();


        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
