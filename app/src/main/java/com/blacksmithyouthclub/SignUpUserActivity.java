package com.blacksmithyouthclub;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.blacksmithyouthclub.Verification.VerificationActivity;
import com.blacksmithyouthclub.api.ApiClient;
import com.blacksmithyouthclub.api.ApiInterface;
import com.blacksmithyouthclub.helper.Camera;
import com.blacksmithyouthclub.helper.CommonMethods;
import com.blacksmithyouthclub.helper.ImageUtils;
import com.blacksmithyouthclub.helper.NetConnectivity;
import com.blacksmithyouthclub.material_spinner.MaterialSpinner;
import com.blacksmithyouthclub.model.CityData;
import com.blacksmithyouthclub.model.ImageData;
import com.blacksmithyouthclub.model.SpinnersData;
import com.blacksmithyouthclub.model.UserDataResponse;
import com.blacksmithyouthclub.realm.model.UserMaster;
import com.blacksmithyouthclub.session.SessionManager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import id.zelory.compressor.Compressor;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpUserActivity extends AppCompatActivity {


    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Bitmap bitmap;

    private Context context = this;
    private SpotsDialog spotsDialog;
    private SessionManager sessionManager;
    private HashMap<String, String> userDetails = new HashMap<String, String>();


    @BindView(R.id.spnSurname)
    MaterialSpinner spnSurname;

    @BindView(R.id.edtMobile)
    EditText edtMobile;

    @BindView(R.id.edtMobileWrapper)
    TextInputLayout edtMobileWrapper;

    @BindView(R.id.edtSurname)
    EditText edtSurname;

    @BindView(R.id.edtSurnameWrapper)
    TextInputLayout edtSurnameWrapper;


    @BindView(R.id.spnMaritalStatus)
    MaterialSpinner spnMaritalStatus;

    @BindView(R.id.spnState)
    MaterialSpinner spnState;

    @BindView(R.id.spnCity)
    MaterialSpinner spnCity;


    @BindView(R.id.edtFirstName)
    EditText edtFirstName;

    @BindView(R.id.edtFirstNameWrapper)
    TextInputLayout edtFirstNameWrapper;

    @BindView(R.id.edtArea)
    EditText edtArea;

    @BindView(R.id.edtAreaWrapper)
    TextInputLayout edtAreaWrapper;

    @BindView(R.id.edtFather)
    EditText edtFather;

    @BindView(R.id.edtFatherWrapper)
    TextInputLayout edtFatherWrapper;


    @BindView(R.id.edtMotherName)
    EditText edtMotherName;

    @BindView(R.id.edtMotherNameWrapper)
    TextInputLayout edtMotherNameWrapper;


    @BindView(R.id.edtVillageName)
    EditText edtVillageName;

    @BindView(R.id.edtVillageNameWrapper)
    TextInputLayout edtVillageNameWrapper;


    @BindView(R.id.rdGenderGroup)
    RadioGroup rdGenderGroup;

    @BindView(R.id.rdMale)
    RadioButton rdMale;

    @BindView(R.id.rdFemale)
    RadioButton rdFemale;

    @BindView(R.id.llDocument)
    LinearLayout llDocument;

    @BindView(R.id.tvDocument)
    TextView tvDocumnet;

    @BindView(R.id.btnBrowse)
    Button btnBrowse;


    @BindView(R.id.imgDocument)
    ImageView imgDocument;

    @BindView(R.id.edtReferalCodeWrapper)
    TextInputLayout edtReferalCodeWrapper;

    @BindView(R.id.edtReferalCode)
    EditText edtReferalCode;


    @BindView(R.id.btnRegister)
    Button btnRegister;

    private String TAG = SignUpUserActivity.class.getSimpleName();


    ArrayList<String> list_Surname = new ArrayList<String>();
    ArrayList<String> list_SurnameId = new ArrayList<String>();

    ArrayList<String> list_MaritalStatus = new ArrayList<String>();
    ArrayList<String> list_MaritalStatusId = new ArrayList<String>();

    ArrayList<String> list_State = new ArrayList<String>();
    ArrayList<String> list_StateId = new ArrayList<String>();

    ArrayList<String> list_City = new ArrayList<String>();
    ArrayList<String> list_CityId = new ArrayList<String>();

    private String userChoosenTask;
    private String BASE64STRING;
    private String DOCUMENT_IMAGE_URL = "";
    private Camera camera;
    private File actualImage;
    private String SELECTED_GENDER = "";

    private Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_user);

        ButterKnife.bind(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle(getString(R.string.activity_signup));

        this.realm = Realm.getDefaultInstance();

        //   MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinner);

        // Build the camera
        camera = new Camera.Builder()
                .resetToCorrectOrientation(true)// it will rotate the camera bitmap to the correct orientation from meta data
                .setTakePhotoRequestCode(Camera.REQUEST_TAKE_PHOTO)
                /*.setDirectory("BETA")*/
                .setName("BSYC" + System.currentTimeMillis())
                .setImageFormat(Camera.IMAGE_JPEG)
                .setCompression(50)
                .setImageHeight(1000)// it will try to achieve this height as close as possible maintaining the aspect ratio;
                .build(this);


        spnState.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

//                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();

                Log.d(TAG, "Selected State :" + item);
                if (position != 0) {

                    getAllCityDetailsFromServer();

                }
            }
        });
        spnState.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
//                Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
            }
        });


        edtSurnameWrapper.setVisibility(View.GONE);
        spnSurname.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                Log.d(TAG, "Selected Surname :" + item);


                if (!item.toLowerCase().equals("other") && !item.toLowerCase().equals("select surname")) {

                    edtSurnameWrapper.setVisibility(View.GONE);
                    edtSurname.setText(item);

                } else if (item.toLowerCase().equals("other")) {
                    edtSurnameWrapper.setVisibility(View.VISIBLE);

                    edtSurname.setText("");
                    //edtSurname.setVisibility(View.VISIBLE);
                }
            }
        });
        spnSurname.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
            }
        });


        if (Build.VERSION.SDK_INT > 9) {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }

        spotsDialog = new SpotsDialog(context);
        spotsDialog.setCancelable(false);

        sessionManager = new SessionManager(context);
        userDetails = sessionManager.getSessionDetails();

        rdGenderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                if (checkedId == R.id.rdMale) {
                    SELECTED_GENDER = "Male";
                } else if (checkedId == R.id.rdFemale) {
                    SELECTED_GENDER = "Female";

                }
                //Toast.makeText(context, "Seleted Gender : "+SELECTED_GENDER, Toast.LENGTH_SHORT).show();
            }
        });


        //  Toast.makeText(context, "Before Gender : "+SELECTED_GENDER, Toast.LENGTH_SHORT).show();

        rdMale.setChecked(true);
        //Toast.makeText(context, "After Gender : "+SELECTED_GENDER, Toast.LENGTH_SHORT).show();



      /*  edtFirstName.setFilters(new InputFilter[] {
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence cs, int start,
                                               int end, Spanned spanned, int dStart, int dEnd) {
                        // TODO Auto-generated method stub
                        if(cs.equals("")){ // for backspace
                            return cs;
                        }
                        if(cs.toString().matches("[a-zA-Z ]+")){
                            return cs;
                        }
                        return "";
                    }
                }
        });
*/


       /* edtFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length() > 0) {

                    CharSequence inputStr = charSequence;
                    Pattern pattern = Pattern.compile(new String("^[a-zA-Z\\s] "));
                    Matcher matcher = pattern.matcher(inputStr);
                    if (matcher.matches()) {
                        //if pattern matches
                        edtFirstNameWrapper.setErrorEnabled(false);
                        edtFirstNameWrapper.setError("");
                    } else {
                        //if pattern does not matches
                        edtFirstNameWrapper.setErrorEnabled(true);
                        edtFirstNameWrapper.setError("Special characters not allowed");

                    }


                } else {
                    edtFirstName.setText("");
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String error = "";


                if (edtFirstName.getText().toString().equals("")) {

                    error = error + "\nEnter First Name";
                    edtFirstNameWrapper.setErrorEnabled(true);
                    edtFirstNameWrapper.setError("Enter first name");
                } else {

                    if (edtFirstName.getText().toString().length() < 4) {
                        error = error + "\nFirst Name should more then 4 characters";
                        edtFirstNameWrapper.setErrorEnabled(true);
                        edtFirstNameWrapper.setError("First Name should be more then 4 characters");
                    } else {
                        edtFirstNameWrapper.setErrorEnabled(false);
                    }


                }


                if (edtMobile.getText().toString().equals("")) {

                    error = error + "\nEnter Mobile no,";
                    edtMobileWrapper.setErrorEnabled(true);
                    edtMobileWrapper.setError("Enter Mobile no");
                } else {

                    if (edtMobile.getText().toString().length() != 10) {
                        error = error + "\nInvalid mobile no,";
                        edtMobileWrapper.setErrorEnabled(true);
                        edtMobileWrapper.setError("Invalid mobile no");


                    } else {

                        edtMobileWrapper.setErrorEnabled(false);
                    }


                }


                if (edtArea.getText().toString().equals("")) {

                    error = error + "\nEnter Area,";
                    edtAreaWrapper.setErrorEnabled(true);
                    edtAreaWrapper.setError("Enter area");
                } else {


                    if (edtArea.getText().toString().length() < 3) {
                        error = error + "\nArea Name should be more then 3 characters";
                        edtAreaWrapper.setErrorEnabled(true);
                        edtAreaWrapper.setError("Area Name should more then 3 characters");
                    } else {
                        edtAreaWrapper.setErrorEnabled(false);
                    }


                }

                if (edtFather.getText().toString().equals("")) {

                    error = error + "\nEnter father name,";
                    edtFatherWrapper.setErrorEnabled(true);
                    edtFatherWrapper.setError("Enter father name");
                } else {

                    if (edtFather.getText().toString().length() < 4) {
                        error = error + "\nFather name should more then  4 characters,";
                        edtFatherWrapper.setErrorEnabled(true);
                        edtFatherWrapper.setError("Father name should more then  4 characters,");
                    } else {
                        edtFatherWrapper.setErrorEnabled(false);
                    }



                }

                if (edtMotherName.getText().toString().equals("")) {

                    error = error + "\nEnter mother name,";
                    edtMotherNameWrapper.setErrorEnabled(true);
                    edtMotherNameWrapper.setError("Enter mother name");
                } else {


                    if (edtMotherName.getText().toString().length() < 4) {
                        error = error + "\nMother name should more then  4 characters,";
                        edtMotherNameWrapper.setErrorEnabled(true);
                        edtMotherNameWrapper.setError("Mother name should more then  4 characters,");
                    } else {
                        edtMotherNameWrapper.setErrorEnabled(false);
                    }


                }

                if (edtVillageName.getText().toString().equals("")) {

                    error = error + "\nEnter village name,";
                    edtVillageNameWrapper.setErrorEnabled(true);
                    edtVillageNameWrapper.setError("Enter village name");
                } else {


                    if (edtVillageName.getText().toString().length() < 4) {
                        error = error + "\nVillage name should more then  4 characters,";
                        edtVillageNameWrapper.setErrorEnabled(true);
                        edtVillageNameWrapper.setError("Village name should more then  4 characters,");
                    } else {
                        edtVillageNameWrapper.setErrorEnabled(false);
                    }


                }

               /* if (DOCUMENT_IMAGE_URL.equals("")) {

                    error = error + "\nUpload any 1 KYC Document,";

                }*/

                if (spnSurname.getSelectedIndex() == 0) {

                    error = error + "\nSelect Surname,";
                } else {
                    if (edtSurname.getText().toString().equals("")) {
                        edtSurnameWrapper.setErrorEnabled(true);
                        edtSurnameWrapper.setError("Please enter surname");
                        error = error + "\nEnter Surname,";

                        edtSurname.requestFocus();

                    }
                    else
                    {
                        edtSurnameWrapper.setErrorEnabled(false);

                        if (edtSurname.getText().toString().length() < 4) {
                            error = error + "\nSurname  should more then  4 characters,";
                            edtSurnameWrapper.setErrorEnabled(true);
                            edtSurnameWrapper.setError("Surname  should more then  4 characters,");
                        } else {
                            edtSurnameWrapper.setErrorEnabled(false);
                        }

                    }
                }


                if (spnMaritalStatus.getSelectedIndex() == 0) {

                    error = error + "\nSelect Marital Status,";
                }


                if (spnState.getSelectedIndex() == 0) {

                    error = error + "\nSelect State,";
                }


                if (spnCity.getSelectedIndex() == 0) {

                    error = error + "\nSelect City,";
                }


                if (!error.equals("")) {
                    error = error.substring(0, error.lastIndexOf(","));
                }

                if (error.equals("")) {
                    // Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();


                    sendUserDetailToServer();

                } else {
                    // Toast.makeText(context, "Oops...", Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Error information");
                    builder.setMessage(error);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.cancel();
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }


            }
        });
        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                llDocument.performClick();
            }
        });

        tvDocumnet.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {

                                              llDocument.performClick();
                                          }
                                      }
        );
        llDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tvDocumnet.setText("Upload Documentt");
                DOCUMENT_IMAGE_URL = "";
                imgDocument.setVisibility(View.GONE);

                Dexter.withActivity(SignUpUserActivity.this)
                        .withPermissions(
                                Manifest.permission.CAMERA,
                                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE

                        ).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        selectImage();


                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                        Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show();
                        token.continuePermissionRequest();
                    }
                }).check();

            }
        });
        getAllComboDetailFromServer();


    }

    private void sendUserDetailToServer() {

        CommonMethods.showDialog(spotsDialog);


        String fcm_tokenid = "";
        try {
            MyFirebaseInstanceIDService mid = new MyFirebaseInstanceIDService();
            fcm_tokenid = String.valueOf(mid.onTokenRefreshNew(context));

        } catch (Exception e) {
            fcm_tokenid = "";
            e.printStackTrace();
        }


        DOCUMENT_IMAGE_URL = DOCUMENT_IMAGE_URL.replace("http://blacksmith.studyfield.com/", "");


        ApiInterface apiClient = ApiClient.getClient().create(ApiInterface.class);
        Log.d(TAG, "URL newUserRegistration : " + CommonMethods.WEBSITE + "newUserRegistration?type=register&fname=" + edtFirstName.getText().toString() + "&surname=" + edtSurname.getText().toString() + "&mobileno=" + edtMobile.getText().toString() + "&fathersName=" + edtFather.getText().toString() + "&mothersName=" + edtMotherName.getText().toString() + "&village=" + edtVillageName.getText().toString() + "&dob=&gender=" + SELECTED_GENDER + "&maritalStatus=" + list_MaritalStatusId.get(spnMaritalStatus.getSelectedIndex()) + "&area=" + edtArea.getText().toString() + "&city=" + list_CityId.get(spnCity.getSelectedIndex()) + "&state=" + list_StateId.get(spnState.getSelectedIndex()) + "&avatar=&referralcode=" + edtReferalCode.getText().toString() + "&fcmtoken=" + fcm_tokenid + "&devicetype=android&castename=" + userDetails.get(SessionManager.KEY_SELECTED_CASTE) + "&document_url=" + DOCUMENT_IMAGE_URL + "");


        apiClient.newUserRegistration("register", edtFirstName.getText().toString(), edtSurname.getText().toString(), edtMobile.getText().toString(), edtFather.getText().toString(), edtMotherName.getText().toString(), edtVillageName.getText().toString(), "", SELECTED_GENDER, Integer.parseInt(list_MaritalStatusId.get(spnMaritalStatus.getSelectedIndex())), edtArea.getText().toString(), Integer.parseInt(list_CityId.get(spnCity.getSelectedIndex())), Integer.parseInt(list_StateId.get(spnState.getSelectedIndex())), "", edtReferalCode.getText().toString(), fcm_tokenid, "android", userDetails.get(SessionManager.KEY_SELECTED_CASTE), DOCUMENT_IMAGE_URL).enqueue(new Callback<UserDataResponse>() {
            @Override
            public void onResponse(Call<UserDataResponse> call, Response<UserDataResponse> response) {


                Log.d(TAG, "newUserRegistration Response Code : " + response.code());

                if (response.code() == 200)
                {


                    String str_error = response.body().getMESSAGE();
                    String str_error_original = response.body().getORIGINALMESSAGE();
                    boolean error_status = response.body().getERRORSTATUS();
                    boolean record_status = response.body().getRECORDS();

                    if (error_status == false)
                    {
                        if (record_status == true) {

                            List<UserDataResponse.DATum> arr = response.body().getDATA();

                            for (int i = 0; i < arr.size(); i++) {

                                int userId = arr.get(i).getId();

                                String userMobile = arr.get(i).getMobile();


                                // setUserDetails(String str_userid, String str_username, String str_email, String str_mobile, String str_avatar) {
                                sessionManager.setUserDetails(String.valueOf(userId), userMobile, arr.get(i).getAppovalStatus());

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
                                    userMaster.setMaritalStatus(arr.get(i).getMaritalStatus());
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
                                    userMaster.setSurnameName(arr.get(i).getSurnameName());
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
                        CommonMethods.showAlertDialog(context,"Signup Information",str_error);

                        if(str_error.contains("Mobile"))
                        {
                            edtMobileWrapper.setErrorEnabled(true);
                            edtMobileWrapper.setError(str_error);
                            edtMobile.requestFocus();

                        }
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
//onCreate Completed

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_CANCELED) {
            if (resultCode == Activity.RESULT_OK) {


                if (requestCode == SELECT_FILE) {

                    onSelectFromGalleryResult(data);


                } else if (requestCode == Camera.REQUEST_TAKE_PHOTO) {


                    onCaptureImageResult();

                }

            }

        }

    }

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri contentUri) {
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String dd = cursor.getString(column_index);
            return cursor.getString(column_index);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return "sa";

    }

    private void onCaptureImageResult() {

        bitmap = camera.getCameraBitmap();

        int bitmapByteCount = BitmapCompat.getAllocationByteCount(bitmap);//15925248
        Log.d(TAG, "Before Bitmap Size Library :" + bitmapByteCount);


        if (bitmap != null) {
            // imgPreview.setImageBitmap(bitmap);
            Uri tempUri = getImageUri(bitmap);

            try {
                String realPath = getRealPathFromURI(tempUri);
                actualImage = new File(realPath);


                // Compress image in main thread using custom Compressor
                try {

                    // Compress image in main thread using custom Compressor
                    try {
                        File compressedImage = new Compressor(this)
                                .setMaxWidth(640)
                                .setMaxHeight(480)
                                .setQuality(75)
                                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                              /*  .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                        Environment.DIRECTORY_PICTURES).getAbsolutePath())*/
                                .compressToFile(actualImage);

                        bitmap = BitmapFactory.decodeFile(compressedImage.getAbsolutePath());

                        bitmapByteCount = BitmapCompat.getAllocationByteCount(bitmap);//3981312
                        Log.d(TAG, "After Bitmap Size Library :" + bitmapByteCount);


                    } catch (IOException e) {
                        e.printStackTrace();
                        CommonMethods.showError(context, e.getMessage().toString());
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    CommonMethods.showError(context, e.getMessage().toString());
                }


                //actualImageView.setImageBitmap(BitmapFactory.decodeFile(actualImage.getAbsolutePath()));
                //actualSizeTextView.setText(String.format("Size : %s", getReadableFileSize(actualImage.length())));
                // clearImage();
            } catch (Exception e) {
                // showError("Failed to read picture data!");
                Toast.makeText(context, "Failed to read picture data", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }


            String realPath = null;
            try {
                Log.d("C: Realpath URI : ", "" + tempUri.toString());
                realPath = getRealPathFromURI(tempUri);
                Log.d("C: Realpath : ", realPath);

                BASE64STRING = CommonMethods.getStringImage(bitmap);


            } catch (Exception e) {
                e.printStackTrace();
            }


            if (NetConnectivity.isOnline(context)) {


                SendDocumentToServer();
            } else {

                //   checkInternet();
                Toast.makeText(context, "Please enable internet", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Picture not taken!", Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {


        if (data != null) {
            try {


                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

                int bitmapByteCount = BitmapCompat.getAllocationByteCount(bitmap);
                Log.d("Before Bitmap Size : ", "" + bitmapByteCount);


                Uri tempUri = getImageUri(bitmap);


                String realPath = null;
                try {
                    Log.d("CC: Realpath URI : ", "" + tempUri.toString());
                    realPath = getRealPathFromURI(tempUri);
                    Log.d("CC: Realpath : ", realPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                bitmap = ImageUtils.getInstant().getCompressedBitmap(realPath);
                //imageView.setImageBitmap(bitmap);

                bitmapByteCount = BitmapCompat.getAllocationByteCount(bitmap);
                Log.d("After Bitmap Size : ", "" + bitmapByteCount);


                // getStringImage(bm);


            } catch (IOException e) {

                e.printStackTrace();
            }
        }

        try {
            if (NetConnectivity.isOnline(context)) {


                BASE64STRING = CommonMethods.getStringImage(bitmap);

                //imgProfilePic.setImageBitmap(bitmap);
                SendDocumentToServer();

                //   new SendUserProfilePictureToServer().execute();
            } else {
//                checkInternet();
                Toast.makeText(context, "Please enable internet", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void SendDocumentToServer() {
        CommonMethods.showDialog(spotsDialog);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Log.d(TAG, "URL GetImageUrl : " + CommonMethods.WEBSITE + "GetImageUrl?type=document&imagecode=" + BASE64STRING + "");
        apiInterface.getImageResponseFromServer("document", BASE64STRING).enqueue(new Callback<ImageData>() {
            @Override
            public void onResponse(Call<ImageData> call, Response<ImageData> response) {


                Log.d(TAG, "LoginEmployee Response Code : " + response.code());

                if (response.code() == 200) {


                    String str_error = response.body().getMESSAGE();
                    String str_error_original = response.body().getORIGINALERROR();
                    boolean error_status = response.body().getERRORSTATUS();
                    boolean record_status = response.body().getRECORDS();

                    if (error_status == false) {
                        if (record_status == true) {


                            DOCUMENT_IMAGE_URL = response.body().getDATA().get(0).getImageurl();

                            Log.d(TAG, "IMAGE URL : " + DOCUMENT_IMAGE_URL);

                            String filename = DOCUMENT_IMAGE_URL.substring(DOCUMENT_IMAGE_URL.lastIndexOf("/"), DOCUMENT_IMAGE_URL.length());
                            tvDocumnet.setText(filename);

                            Log.d(TAG, "Image URL : " + DOCUMENT_IMAGE_URL);

                            imgDocument.setVisibility(View.VISIBLE);

                            if (!DOCUMENT_IMAGE_URL.isEmpty()) {
                                try {
                                    //Glide.with(_context).load(list_categoty.get(position).getImageurl()).placeholder(R.drawable.app_logo).error(R.drawable.app_logo).crossFade(R.anim.fadein, 2000).override(500, 500).into(holder.ivCategory);

                                    imgDocument.setImageBitmap(bitmap);


                                    /*Picasso.with(context)
                                            .load(COMPLAINT_IMAGE_URL)
                                            .placeholder(R.drawable.app_logo)
                                            .error(R.drawable.app_logo)
                                            .into(imgDocument);*/


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }

                        } else {
                            imgDocument.setVisibility(View.GONE);
                            Toast.makeText(context, str_error, Toast.LENGTH_SHORT).show();
                        }
                    } else {


                    }


                } else {
                    CommonMethods.showErrorMessageWhenStatusNot200(context, response.code());
                }


                CommonMethods.hideDialog(spotsDialog);


            }

            @Override
            public void onFailure(Call<ImageData> call, Throwable t) {

                CommonMethods.onFailure(context, TAG, t);

                CommonMethods.hideDialog(spotsDialog);
            }
        });
    }


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select Profile Picture!");
        builder.setItems(items, new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";

                    try {
                        camera.takePicture();
                    } catch (IllegalAccessException e) {
                        Log.d(TAG, "Error in Capture image");
                        e.printStackTrace();
                    }

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";

                    galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {

        Dexter.withActivity(SignUpUserActivity.this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);//
                        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);


                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                        Toast.makeText(context, "Please grant read storage permission", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                }).check();


    }

    private void cameraIntent() {

        Dexter.withActivity(SignUpUserActivity.this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, REQUEST_CAMERA);

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(context, "Please grant camera access permisson", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }

                }).check();


    }


    private void getAllCityDetailsFromServer() {


        CommonMethods.showDialog(spotsDialog);
        CommonMethods.showDialog(spotsDialog);

        ApiInterface apiClient = ApiClient.getClient().create(ApiInterface.class);
        Log.d(TAG, "URL getAllCities : " + CommonMethods.WEBSITE + "getAllCities?type=city&stateid=" + list_StateId.get(spnState.getSelectedIndex()) + "");


        apiClient.getAllCities("city", list_StateId.get(spnState.getSelectedIndex())).enqueue(new Callback<CityData>() {
            @Override
            public void onResponse(Call<CityData> call, Response<CityData> response) {


                Log.d(TAG, "LoginEmployee Response Code : " + response.code());

                if (response.code() == 200) {


                    String str_error = response.body().getMESSAGE();
                    String str_error_original = response.body().getORIGINALMESSAGE();
                    boolean error_status = response.body().getERRORSTATUS();
                    boolean record_status = response.body().getRECORDS();

                    if (error_status == false) {
                        if (record_status == true) {

                            List<CityData.DATum> temp_list_CityData = response.body().getDATA();

                            //Set State SPinner Data
                            list_City.clear();
                            list_CityId.clear();
                            list_City.add("Select City");
                            list_CityId.add("0");
                            for (int i = 0; i < temp_list_CityData.size(); i++) {

                                list_City.add(temp_list_CityData.get(i).getCityName());
                                list_CityId.add(temp_list_CityData.get(i).getId().toString());

                            }
                            spnCity.setItems(list_City);


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
            public void onFailure(Call<CityData> call, Throwable t) {

                CommonMethods.onFailure(context, TAG, t);

                CommonMethods.hideDialog(spotsDialog);
            }
        });


    }

    private void getAllComboDetailFromServer() {


        CommonMethods.showDialog(spotsDialog);


        ApiInterface apiClient = ApiClient.getClient().create(ApiInterface.class);
        Log.d(TAG, "URL getserviceForSpinnersData : " + CommonMethods.WEBSITE + "getserviceForSpinnersData?type=spinner&countryid=1");
        apiClient.getserviceForSpinnersData("spinner", "1", userDetails.get(SessionManager.KEY_SELECTED_CASTE)).enqueue(new Callback<SpinnersData>() {
            @Override
            public void onResponse(Call<SpinnersData> call, Response<SpinnersData> response) {


                Log.d(TAG, "getserviceForSpinnersData Response Code : " + response.code());

                if (response.code() == 200) {


                    String str_error = response.body().getMESSAGE();
                    String str_error_original = response.body().getORIGINALMESSAGE();
                    boolean error_status = response.body().getERRORSTATUS();
                    boolean record_status = response.body().getRECORDS();

                    if (error_status == false) {
                        if (record_status == true) {

                            List<SpinnersData.STATEDATum> temp_list_StateData = response.body().getSTATEDATA();
                            List<SpinnersData.SURNAMEDATum> temp_list_SurnameData = response.body().getSURNAMEDATA();
                            List<SpinnersData.MARITALDATum> temp_list_MaritalStatusData = response.body().getMARITALDATA();

                            //Set State SPinner Data
                            list_State.clear();
                            list_StateId.clear();
                            list_State.add("Select State");
                            list_StateId.add("0");

                            for (int i = 0; i < temp_list_StateData.size(); i++) {

                                list_State.add(temp_list_StateData.get(i).getStateName());
                                list_StateId.add(temp_list_StateData.get(i).getId().toString());

                            }
                            spnState.setItems(list_State);


                            //Set Surname SPinner Data
                            list_Surname.clear();
                            list_SurnameId.clear();
                            list_Surname.add("Select Surname");
                            list_SurnameId.add("0");

                            for (int i = 0; i < temp_list_SurnameData.size(); i++) {
                                list_Surname.add(temp_list_SurnameData.get(i).getSurname());
                                list_SurnameId.add(temp_list_SurnameData.get(i).getId().toString());
                            }

                            list_Surname.add("Other");
                            list_SurnameId.add("Other");

                            spnSurname.setItems(list_Surname);


                            //Set Surname SPinner Data
                            list_MaritalStatus.clear();
                            list_MaritalStatusId.clear();
                            list_MaritalStatus.add("Select Marital Status");
                            list_MaritalStatusId.add("0");

                            for (int i = 0; i < temp_list_MaritalStatusData.size(); i++) {

                                list_MaritalStatus.add(temp_list_MaritalStatusData.get(i).getMaritalStatus());
                                list_MaritalStatusId.add(temp_list_MaritalStatusData.get(i).getId().toString());

                            }
                            spnMaritalStatus.setItems(list_MaritalStatus);

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
            public void onFailure(Call<SpinnersData> call, Throwable t) {

                CommonMethods.onFailure(context, TAG, t);

                CommonMethods.hideDialog(spotsDialog);
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {


            Intent intent = new Intent(context, SignInActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(context, SignInActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }


}
