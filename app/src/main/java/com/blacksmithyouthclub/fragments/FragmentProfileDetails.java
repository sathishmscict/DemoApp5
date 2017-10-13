package com.blacksmithyouthclub.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.support.v4.app.Fragment;
import android.support.v4.graphics.BitmapCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.blacksmithyouthclub.R;
import com.blacksmithyouthclub.Verification.VerificationActivity;
import com.blacksmithyouthclub.api.ApiClient;
import com.blacksmithyouthclub.api.ApiInterface;
import com.blacksmithyouthclub.helper.Camera;
import com.blacksmithyouthclub.helper.CommonMethods;
import com.blacksmithyouthclub.helper.ImageUtils;
import com.blacksmithyouthclub.helper.NetConnectivity;
import com.blacksmithyouthclub.material_spinner.MaterialSpinner;
import com.blacksmithyouthclub.model.CommonReponse;
import com.blacksmithyouthclub.model.ImageData;
import com.blacksmithyouthclub.model.SpinnersData;
import com.blacksmithyouthclub.model.UserDataResponse;
import com.blacksmithyouthclub.realm.model.UserMaster;
import com.blacksmithyouthclub.session.SessionManager;
import com.bumptech.glide.Glide;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import id.zelory.compressor.Compressor;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentProfileDetails extends Fragment {


    private Context context = getActivity();
    private SpotsDialog spotsDialog;
    private MenuItem profile_edit, profile_update;
    private View rootView;

    @BindView(R.id.imgProfilePic)
    CircularImageView imgProfile;

    @BindView(R.id.spnSurname)
    MaterialSpinner spnSurname;

    @BindView(R.id.spnBloodGroup)
    MaterialSpinner spnBloodGroup;

    @BindView(R.id.spnHeight)
    MaterialSpinner spnHeight;
    /*@BindView(R.id.spnHeight)
    Spinner spnHeight;*/


    @BindView(R.id.spnMaritalStatus)
    MaterialSpinner spnMaritalStatus;


    @BindView(R.id.edtName)
    EditText edtName;

    @BindView(R.id.edtNameWrapper)
    TextInputLayout edtNameWrapper;

    @BindView(R.id.edtSurname)
    EditText edtSurname;

    @BindView(R.id.edtSurnameWrapper)
    TextInputLayout edtSurnameWrapper;


    @BindView(R.id.edtVillageName)
    EditText edtVillageName;

    @BindView(R.id.edtVillageNameWrapper)
    TextInputLayout edtVillageNameWrapper;


    @BindView(R.id.edtReligion)
    EditText edtReligion;

    @BindView(R.id.edtReligionWrapper)
    TextInputLayout edtReligionWrapper;


    @BindView(R.id.edtDOB)
    EditText edtDOB;

    @BindView(R.id.edtDOBWrapper)
    TextInputLayout edtDOBWrapper;


    @BindView(R.id.edtDOA)
    EditText edtDOA;

    @BindView(R.id.edtDOAWrapper)
    TextInputLayout edtDOAWrapper;


    @BindView(R.id.edtWeight)
    EditText edtWeight;

    @BindView(R.id.edtWeightWrapper)
    TextInputLayout edtWeightWrapper;


    @BindView(R.id.edtAbout)
    EditText edtAbout;

    @BindView(R.id.edtAboutWrapper)
    TextInputLayout edtAboutWrapper;


    @BindView(R.id.edtHobby)
    EditText edtHobby;

    @BindView(R.id.edtHobbyWrapper)
    TextInputLayout edtHobbyWrapper;


    @BindView(R.id.rdGenderGroup)
    RadioGroup rdGenderGroup;

    @BindView(R.id.rdMale)
    RadioButton rdMale;

    @BindView(R.id.rdFemale)
    RadioButton rdFemale;


    private File actualImage;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Bitmap bitmap;


    private int mYear, mMonth, mDay;
    int day, month, Year;

    /*@BindView(R.id.rvEmployee)
    RecyclerView rvEmployee;

    @BindView(R.id.tvNodata)
    TextView tvNodata;*/


    private Realm realm;
    private UserMaster userData;
    UserMaster u = new UserMaster();


    private SessionManager sessionManager;
    private HashMap<String, String> userDetails = new HashMap<String, String>();
    private String TAG = FragmentProfileDetails.class.getSimpleName();


    ArrayList<String> list_Surname = new ArrayList<String>();
    ArrayList<String> list_SurnameId = new ArrayList<String>();

    ArrayList<String> list_MaritalStatus = new ArrayList<String>();
    ArrayList<String> list_MaritalStatusId = new ArrayList<String>();

    ArrayList<String> list_HeightMaster = new ArrayList<String>();
    ArrayList<String> list_HeightMasterId = new ArrayList<String>();


    ArrayList<String> list_BloodGroup = new ArrayList<String>();
    ArrayList<String> list_BloodGroupId = new ArrayList<String>();


    ArrayList<String> list_State = new ArrayList<String>();
    ArrayList<String> list_StateId = new ArrayList<String>();

    ArrayList<String> list_City = new ArrayList<String>();
    ArrayList<String> list_CityId = new ArrayList<String>();
    private String SELECTED_GENDER = "";
    private String PROFILE_PICTURE_URL = "";
    private Camera camera;
    private String userChoosenTask;
    private String BASE64STRING;
    //private String PROFILE_LOGO_URL="";


    public FragmentProfileDetails() {
        // Required empty public constructor
    }

	/*
     * @Override public void onCreate(Bundle savedInstanceState) {
	 * super.onCreate(savedInstanceState); setHasOptionsMenu(true);
	 * 
	 * 
	 * }
	 */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //rootView = inflater.inflate(R.layout.fragment_profile_details, container,false);

        rootView = inflater.inflate(R.layout.fragment_profile_details, null);

        if (Build.VERSION.SDK_INT > 9) {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }

         /* For this injection you have to specify the source of the views */
        ButterKnife.bind(this, rootView);

        sessionManager = new SessionManager(getContext());
        userDetails = sessionManager.getSessionDetails();


        spotsDialog = new SpotsDialog(getActivity());
        spotsDialog.setCancelable(false);

        this.realm = Realm.getDefaultInstance();
        userData = u.getUserData(realm, Long.parseLong(userDetails.get(SessionManager.KEY_USER_ID)));


        try {
            //Glide.with(getActivity()).load(userData.getAvatar()).error(R.mipmap.ic_launcher).into(imgProfile);
            PROFILE_PICTURE_URL = userData.getAvatar();
            Picasso.with(getActivity())
                    .load(userData.getAvatar())
                    .placeholder(R.drawable.app_logo)
                    .error(R.drawable.app_logo)
                    .into(imgProfile);

        } catch (Exception e) {
            e.printStackTrace();
        }
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


        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Dexter.withActivity(getActivity())
                        .withPermissions(
                                android.Manifest.permission.CAMERA,
                                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE

                        ).withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        selectImage();


                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                        Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                }).check();
            }
        });

        // Initilization();
        HideControls();
        // Inflate the layout for this fragment


        FillDataOnControls();


        edtDOB.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ShowDate(edtDOB);
                }
                return false;
            }
        });

        edtDOA.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ShowDate(edtDOA);
                }
                return false;
            }
        });

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

        try {
            if (userData.getGender().toLowerCase().equals("female")) {
                rdFemale.setChecked(true);
            } else {
                rdMale.setChecked(true);

            }
        } catch (Exception e) {
            rdMale.setChecked(true);
            e.printStackTrace();
        }

        //  edtSurnameWrapper.setVisibility(View.GONE);
        spnSurname.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                Log.d(TAG, "Selected Surname :" + item);


                if (!item.toLowerCase().equals("other") && !item.toLowerCase().equals("select surname")) {

                    edtSurnameWrapper.setVisibility(View.GONE);
                    edtSurname.setText(item);

                } else if (item.toLowerCase().equals("other")) {

                    edtSurname.setText("");
                    edtSurnameWrapper.setVisibility(View.VISIBLE);
                } else {
                    //edtSurnameWrapper.setVisibility(View.GONE);
                }
            }
        });
        spnSurname.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
            }
        });


        spnMaritalStatus.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                Log.d(TAG, "Selected MaritalStatus :" + item);


                if (item.toLowerCase().contains("widowed") || item.toLowerCase().equals("married")) {

                    edtDOAWrapper.setVisibility(View.VISIBLE);
                    edtDOA.setFocusableInTouchMode(true);
                } else  {

                    edtDOAWrapper.setVisibility(View.GONE);


                }
            }
        });
       /* spnMaritalStatus.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
            }
        });*/


        getAllComboDetailFromServer();

        return rootView;
    }

    private void FillDataOnControls() {
        try {
            edtName.setText(userData.getFirstName().toString());
            //edtSurname.setText(userData.getSurname().toString());
            edtVillageName.setText(userData.getVillage().toString());
            edtReligion.setText(userData.getReligion().toString());
            edtDOB.setText(userData.getDob().toString());
            edtDOA.setText(userData.getDoa().toString());
            edtWeight.setText(userData.getWeight().toString());
            edtAbout.setText(userData.getAbout().toString());
            edtHobby.setText(userData.getHobbyId().toString());
            edtSurname.setText(userData.getSurnameName().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//onCreate completed


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
                            List<SpinnersData.BLOODGROUPDATum> temp_list_BloodGroupData = response.body().getBLOODGROUPDATA();
                            List<SpinnersData.HEIGHTDATum> temp_list_HeightData = response.body().getHEIGHTDATA();

                            //Set State SPinner Data
                            /*list_State.clear();
                            list_StateId.clea
                            r();
                            list_State.add("Select State");
                            list_StateId.add("0");

                            for (int i = 0; i < temp_list_StateData.size(); i++) {

                                list_State.add(temp_list_StateData.get(i).getStateName());
                                list_StateId.add(temp_list_StateData.get(i).getId().toString());

                            }
                            spnState.setItems(list_State);*/


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


                            try {
                                //String sur = userData.getSurname().toString();
                                int pos = list_Surname.indexOf(userData.getSurnameName().toString());

                                if (pos > 0) {
                                    spnSurname.setSelectedIndex(pos);
                                }


                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }


                            //Set MArital Status SPinner Data
                            list_MaritalStatus.clear();
                            list_MaritalStatusId.clear();
                            list_MaritalStatus.add("Select Marital Status");
                            list_MaritalStatusId.add("0");

                            for (int i = 0; i < temp_list_MaritalStatusData.size(); i++) {

                                list_MaritalStatus.add(temp_list_MaritalStatusData.get(i).getMaritalStatus());
                                list_MaritalStatusId.add(temp_list_MaritalStatusData.get(i).getId().toString());

                            }
                            spnMaritalStatus.setItems(list_MaritalStatus);

                            try {
                                //String sur = userData.getSurname().toString();
                                int pos = list_MaritalStatusId.indexOf(userData.getMaritalStatusId().toString());

                                if (pos > 0) {
                                    spnMaritalStatus.setSelectedIndex(pos);
                                }


                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }


                            //Set Bloodgroup SPinner Data
                            list_BloodGroup.clear();
                            list_BloodGroupId.clear();
                            list_BloodGroup.add(getString(R.string.str_select_blood_group));
                            list_BloodGroupId.add("0");

                            for (int i = 0; i < temp_list_BloodGroupData.size(); i++) {

                                list_BloodGroup.add(temp_list_BloodGroupData.get(i).getName().toString());
                                list_BloodGroupId.add(temp_list_BloodGroupData.get(i).getId().toString());

                            }
                            spnBloodGroup.setItems(list_BloodGroup);
                            try {
                                int bldgrpid = userData.getBloodGrpId();
                                int pos = list_BloodGroupId.indexOf(userData.getBloodGrpId().toString());

                                if (pos > 0) {
                                    spnBloodGroup.setSelectedIndex(pos);
                                }


                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }


                            //Set Surname SPinner Data
                            list_HeightMaster.clear();
                            list_HeightMasterId.clear();
                            list_HeightMaster.add(getString(R.string.str_select_heigth));
                            list_HeightMasterId.add("0");

                            for (int i = 0; i < temp_list_HeightData.size(); i++) {

                                list_HeightMaster.add(temp_list_HeightData.get(i).getHeightname().toString());
                                list_HeightMasterId.add(temp_list_HeightData.get(i).getId().toString());

                            }
                            //ArrayAdapter<String> heightAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,list_HeightMaster);
                            // spnHeight.setAdapter(heightAdapter);


                            spnHeight.setItems(list_HeightMaster);

                            try {
                                int heigthpid = userData.getHeightId();
                                int pos = list_HeightMasterId.indexOf(userData.getHeightId().toString());

                                if (pos > 0) {
                                    spnHeight.setSelectedIndex(pos);
                                    //    spnHeight.setSelection(pos);
                                }


                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }


                            CommonMethods.hideDialog(spotsDialog);

                            // FillDataOnControls();

                        }
                    } else {

                        CommonMethods.hideDialog(spotsDialog);
                        Toast.makeText(getActivity(), "" + str_error, Toast.LENGTH_SHORT).show();
                    }


                } else {
                    CommonMethods.showErrorMessageWhenStatusNot200(getActivity(), response.code());
                }


                CommonMethods.hideDialog(spotsDialog);

            }

            @Override
            public void onFailure(Call<SpinnersData> call, Throwable t) {

                CommonMethods.onFailure(getActivity(), TAG, t);

                CommonMethods.hideDialog(spotsDialog);
            }
        });


    }


 /*   private void Initilization() {
        // imgProfile=(ImageView)rootView.findViewById(R.id.imgProfilePic);

        //edtname=(EditText)rootView.findViewById(R.id.edtname);
        // edtsurename=(EditText)rootView.findViewById(R.id.edtsurename);
        // edtsurenameother=(EditText)rootView.findViewById(R.id.edtsurenameother);
        //  edtvillagename=(EditText)rootView.findViewById(R.id.edtvillagename);
        //  edtreligion=(EditText)rootView.findViewById(R.id.edtreligion);
        //  edtdob=(EditText)rootView.findViewById(R.id.edtdob);
        //   edtweight=(EditText)rootView.findViewById(R.id.edtweight);
        //   edtabout=(EditText)rootView.findViewById(R.id.edtabout);
        //   edthobby=(EditText)rootView.findViewById(R.id.edthobby);

        //spnBloodGroup=(MaterialSpinner)rootView.findViewById(R.id.spnBloodGroup);
        //spnHeight=(MaterialSpinner)rootView.findViewById(R.id.spnHeight);
        //spMaritalStatus=(MaterialSpinner)rootView.findViewById(R.id.spnMaritalStatus);
        //spgender=(MaterialSpinner)rootView.findViewById(R.id.spgender);
    }*/

    private void ShowDate(final EditText edt) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String txtdate = (year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);


                        txtdate = (year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        txtdate = CommonMethods.setCurrentDate(year, monthOfYear, dayOfMonth);

                        Year = year;
                        month = monthOfYear;
                        day = dayOfMonth;
                        edt.setText(txtdate);
                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_profile, menu);
        profile_edit = (MenuItem) menu.findItem(R.id.action_edit);

        profile_update = (MenuItem) menu.findItem(R.id.action_update);
        profile_update.setVisible(false);
        profile_edit.setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_edit) {
            //  IsUpdatePassword = false;
            profile_update.setVisible(true);
            profile_edit.setVisible(false);

            ShowAllControlsEditableAndVisible();
        } else if (item.getItemId() == R.id.action_update) {
            //profile_edit.setVisible(true);
            //profile_update.setVisible(false);
            //HideControls();


            if (spnSurname.getSelectedIndex() == 0) {

                CommonMethods.showAlertDialog(getActivity(), "Profile update info", "Please select Surname");

            } else if (edtSurname.getText().toString().equals("")) {
                edtSurnameWrapper.setErrorEnabled(true);
                edtSurnameWrapper.setError("Please enter surname");
                CommonMethods.showAlertDialog(getActivity(), "Profile update info", "Please enter surname");


                edtSurname.requestFocus();

            } else {
                updateDetailsSendToServer();
            }


        }
        return super.onOptionsItemSelected(item);
    }

    private void updateDetailsSendToServer() {

        //updatePersonalDetails?type=string&fname=string&surname=string&originalsurname=string&dob=string&village=string&maritalStatus=string&bloodgrpid=string&heightid=string&contact=string&weight=string&avatar=string&about=string&religion=string&hobby=string&casteid=string&gender=string&updateddate=string


        if (edtWeight.getText().toString().equals("")) {
            edtWeight.setText("0");

        }
        CommonMethods.showDialog(spotsDialog);
        ApiInterface apiClient = null;
        try {
            apiClient = ApiClient.getClient().create(ApiInterface.class);
            Log.d(TAG, "URL updatePersonalDetails : " + CommonMethods.WEBSITE + "?updatePersonalDetails?type=personaldetails&fname=" + edtName.getText().toString() + "&surname=" + edtSurname.getText().toString() + "&originalsurname=" + edtSurname.getText().toString() + "&dob=" + CommonMethods.convertToJsonDateFormat(edtDOB.getText().toString()) + "&village=" + edtVillageName.getText().toString() + "&maritalStatus=" + list_MaritalStatusId.get(spnMaritalStatus.getSelectedIndex()) + "&bloodgrpid=" + list_BloodGroupId.get(spnBloodGroup.getSelectedIndex()) + "&heightid=" + list_HeightMasterId.get(spnHeight.getSelectedIndex()) + "&contact=" + userDetails.get(SessionManager.KEY_USER_MOBILE) + "&weight=" + Integer.parseInt(edtWeight.getText().toString()) + "&avatar=" + PROFILE_PICTURE_URL + "&about=" + edtAbout.getText().toString() + "&religion=" + edtReligion.getText().toString() + "&hobby=" + edtHobby.getText().toString() + "&casteid=" + Integer.parseInt(userDetails.get(SessionManager.KEY_SELECTED_CASTE)) + "&gender=" + SELECTED_GENDER + "&updateddate=" + CommonMethods.getDateTime() + "&doa=" + CommonMethods.convertToJsonDateFormat(edtDOA.getText().toString()) + "");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        PROFILE_PICTURE_URL = PROFILE_PICTURE_URL.replace("http://blacksmith.studyfield.com/", "");

        apiClient.updatePersonalDetails("personaldetails", edtName.getText().toString(), edtSurname.getText().toString(), edtSurname.getText().toString(), CommonMethods.convertToJsonDateFormat(edtDOB.getText().toString()), edtVillageName.getText().toString(), Integer.parseInt(list_MaritalStatusId.get(spnMaritalStatus.getSelectedIndex())), list_BloodGroupId.get(spnBloodGroup.getSelectedIndex()), Integer.parseInt(list_HeightMasterId.get(spnHeight.getSelectedIndex())), userDetails.get(SessionManager.KEY_USER_MOBILE), Integer.parseInt(edtWeight.getText().toString()), PROFILE_PICTURE_URL, edtAbout.getText().toString(), edtReligion.getText().toString(), edtHobby.getText().toString(), Integer.parseInt(userDetails.get(SessionManager.KEY_SELECTED_CASTE)), SELECTED_GENDER, CommonMethods.getDateTime(), CommonMethods.convertToJsonDateFormat(edtDOA.getText().toString())).enqueue(new Callback<UserDataResponse>() {
            @Override
            public void onResponse(Call<UserDataResponse> call, Response<UserDataResponse> response) {


                Log.d(TAG, "updatePersonalDetails Response Code : " + response.code());

                if (response.code() == 200) {


                    String str_error = response.body().getMESSAGE();
                    String str_error_original = response.body().getORIGINALMESSAGE();
                    boolean error_status = response.body().getERRORSTATUS();
                    boolean record_status = response.body().getRECORDS();

                    if (error_status == false) {
                        //If details has been updated successfully then option menu title has been changed
                        profile_edit.setVisible(true);
                        profile_update.setVisible(false);

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
                                userMaster = userData;


                                //userMaster.setUserid(arr.get(i).getId());
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
                                //realm.insertOrUpdate(userMaster);
                                realm.commitTransaction();
                                Log.d(TAG, "Userdata has been added in database");
                                FillDataOnControls();
                                HideControls();


                                CommonMethods.showAlertDialog(getActivity(), "Profile Update Info", "Your profile details has been successfully updated.");
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }


                        }


                        CommonMethods.hideDialog(spotsDialog);


                    } else {

                        CommonMethods.hideDialog(spotsDialog);
                        Toast.makeText(getActivity(), "" + str_error, Toast.LENGTH_SHORT).show();
                    }


                } else {
                    CommonMethods.showErrorMessageWhenStatusNot200(getActivity(), response.code());
                }


                CommonMethods.hideDialog(spotsDialog);

            }

            @Override
            public void onFailure(Call<UserDataResponse> call, Throwable t) {

                CommonMethods.onFailure(getActivity(), TAG, t);

                CommonMethods.hideDialog(spotsDialog);
            }
        });


    }

    private void ShowAllControlsEditableAndVisible() {

        imgProfile.setEnabled(true);
        edtName.setEnabled(true);
        edtSurname.setEnabled(true);
        spnSurname.setEnabled(true);

        //spnSurname.setVisibility(View.GONE);
        edtSurnameWrapper.setVisibility(View.GONE);
        spnSurname.setVisibility(View.VISIBLE);

        //edtsurenameother.setEnabled(true);
        edtVillageName.setEnabled(true);
        edtReligion.setEnabled(true);
        edtDOB.setEnabled(true);
        edtWeight.setEnabled(true);
        edtAbout.setEnabled(true);
        edtHobby.setEnabled(true);

        spnBloodGroup.setEnabled(true);
        spnHeight.setEnabled(true);
        spnMaritalStatus.setEnabled(true);
        //spgender.setEnabled(true);
        rdGenderGroup.setEnabled(true);

        spnSurname.setFocusableInTouchMode(true);
        edtSurname.setFocusableInTouchMode(true);


        edtName.setFocusableInTouchMode(true);
        edtVillageName.setFocusableInTouchMode(true);
        edtReligion.setFocusableInTouchMode(true);
        edtDOB.setFocusableInTouchMode(true);
        edtWeight.setFocusableInTouchMode(true);
        edtAbout.setFocusableInTouchMode(true);
        edtHobby.setFocusableInTouchMode(true);

        spnBloodGroup.setFocusableInTouchMode(true);
        spnHeight.setFocusableInTouchMode(true);
        spnMaritalStatus.setFocusableInTouchMode(true);
        //spgender.setEnabled(true);
        rdGenderGroup.setFocusableInTouchMode(true);


        if (userData.getMaritalStatusId().toString().toLowerCase().equals("3") || userData.getMaritalStatusId().toString().toLowerCase().equals("4")) {

            edtDOAWrapper.setVisibility(View.VISIBLE);
            edtDOA.setFocusableInTouchMode(true);
            edtDOA.setEnabled(true);
        } else {
            edtDOAWrapper.setVisibility(View.GONE);
        }


    }

    private void HideControls() {

        edtName.setEnabled(false);
        imgProfile.setEnabled(false);

//        edtName.setCursorVisible(false);
        //       edtName.setClickable(false);


        spnSurname.setEnabled(false);

        spnSurname.setVisibility(View.GONE);
        edtSurnameWrapper.setVisibility(View.VISIBLE);


        edtSurnameWrapper.setEnabled(false);

//        edtsurenameother.setEnabled(false);
        edtVillageName.setEnabled(false);

        edtReligion.setEnabled(false);
        edtDOB.setEnabled(false);
        edtWeight.setEnabled(false);
        edtAbout.setEnabled(false);
        edtHobby.setEnabled(false);

        spnBloodGroup.setEnabled(false);
        spnHeight.setEnabled(false);
        spnMaritalStatus.setEnabled(false);
        //spgender.setEnabled(false);
        rdGenderGroup.setEnabled(false);

        edtDOA.setEnabled(false);

        try {
            if (userData.getMaritalStatusId().toString().toLowerCase().equals("3") || userData.getMaritalStatusId().toString().toLowerCase().equals("4")) {

                edtDOAWrapper.setVisibility(View.VISIBLE);
                edtDOA.setEnabled(false);
            } else {
                edtDOAWrapper.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            edtDOAWrapper.setVisibility(View.GONE);
            e.printStackTrace();
        }
    }


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

        Dexter.withActivity(getActivity())
                .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
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

                        Toast.makeText(getActivity(), "Please grant read storage permission", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                }).check();


    }

    private void cameraIntent() {

        Dexter.withActivity(getActivity())
                .withPermission(android.Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, REQUEST_CAMERA);

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(getActivity(), "Please grant camera access permisson", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }

                }).check();


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {


            if (requestCode == SELECT_FILE) {

                onSelectFromGalleryResult(data);


            } else if (requestCode == Camera.REQUEST_TAKE_PHOTO) {


                onCaptureImageResult();

            }

        }
    }

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri contentUri) {
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().managedQuery(contentUri, proj, null, null, null);
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
                        File compressedImage = new Compressor(getActivity())
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
                        CommonMethods.showError(getActivity(), e.getMessage().toString());
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    CommonMethods.showError(getActivity(), e.getMessage().toString());
                }


                //actualImageView.setImageBitmap(BitmapFactory.decodeFile(actualImage.getAbsolutePath()));
                //actualSizeTextView.setText(String.format("Size : %s", getReadableFileSize(actualImage.length())));
                // clearImage();
            } catch (Exception e) {
                // showError("Failed to read picture data!");
                Toast.makeText(getActivity(), "Failed to read picture data", Toast.LENGTH_SHORT).show();
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


            if (NetConnectivity.isOnline(getActivity())) {


                SendImageToServer();
            } else {

                //   checkInternet();
                Toast.makeText(getActivity(), "Please enable internet", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), "Picture not taken!", Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {


        if (data != null) {
            try {


                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());

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
            if (NetConnectivity.isOnline(getActivity())) {


                BASE64STRING = CommonMethods.getStringImage(bitmap);

                //imgProfilePic.setImageBitmap(bitmap);
                SendImageToServer();

                //   new SendUserProfilePictureToServer().execute();
            } else {
//                checkInternet();
                Toast.makeText(getActivity(), "Please enable internet", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void SendImageToServer() {
        CommonMethods.showDialog(spotsDialog);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Log.d(TAG, "URL GetImageUrl : " + CommonMethods.WEBSITE + "GetImageUrl?type=business&imagecode=" + BASE64STRING + "");
        apiInterface.getImageResponseFromServer("user", BASE64STRING).enqueue(new Callback<ImageData>() {
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

                            sessionManager.setEncodedImage(BASE64STRING);
                            PROFILE_PICTURE_URL = response.body().getDATA().get(0).getImageurl();

                            Log.d(TAG, "IMAGE URL : " + PROFILE_PICTURE_URL);

                            String filename = PROFILE_PICTURE_URL.substring(PROFILE_PICTURE_URL.lastIndexOf("/"), PROFILE_PICTURE_URL.length());


                            Log.d(TAG, "Image URL : " + PROFILE_PICTURE_URL);

                            //imgDocument.setVisibility(View.VISIBLE);

                            if (!PROFILE_PICTURE_URL.isEmpty()) {
                                try {
                                    //Glide.with(_context).load(list_categoty.get(position).getImageurl()).placeholder(R.drawable.app_logo).error(R.drawable.app_logo).crossFade(R.anim.fadein, 2000).override(500, 500).into(holder.ivCategory);


                                    try {
                                        //get realm instance
                                        realm = Realm.getDefaultInstance();

                                        //realm.refresh();

                                        realm.beginTransaction();

                                        UserMaster userMaster = new UserMaster();
                                        userMaster = userData;


                                        //userMaster.setUserid(arr.get(i).getId());
                                        userMaster.setBusinessLogo(PROFILE_PICTURE_URL);


                                        realm.copyToRealm(userMaster);
                                        //realm.insertOrUpdate(userMaster);
                                        realm.commitTransaction();
                                        Log.d(TAG, "Userdata has been added in database");
                                        // FillDataOnControls();

                                        //HideControls();

                                        // CommonMethods.showAlertDialog(getActivity(),"Contact Details Update Info","Your contact details has been successfully updated.");


                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }


                                    imgProfile.setImageBitmap(bitmap);


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

                            Toast.makeText(getActivity(), str_error, Toast.LENGTH_SHORT).show();
                        }
                    } else {


                    }


                } else {
                    CommonMethods.showErrorMessageWhenStatusNot200(getActivity(), response.code());
                }


                CommonMethods.hideDialog(spotsDialog);


            }

            @Override
            public void onFailure(Call<ImageData> call, Throwable t) {

                CommonMethods.onFailure(getActivity(), TAG, t);

                CommonMethods.hideDialog(spotsDialog);
            }
        });
    }

}