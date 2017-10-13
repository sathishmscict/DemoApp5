package com.blacksmithyouthclub.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blacksmithyouthclub.Manifest;
import com.blacksmithyouthclub.R;
import com.blacksmithyouthclub.SignUpUserActivity;
import com.blacksmithyouthclub.api.ApiClient;
import com.blacksmithyouthclub.api.ApiInterface;
import com.blacksmithyouthclub.helper.Camera;
import com.blacksmithyouthclub.helper.CommonMethods;
import com.blacksmithyouthclub.helper.ImageUtils;
import com.blacksmithyouthclub.helper.NetConnectivity;
import com.blacksmithyouthclub.material_spinner.MaterialSpinner;
import com.blacksmithyouthclub.model.BusinessSubCategoryData;
import com.blacksmithyouthclub.model.BussinessCategoryData;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import id.zelory.compressor.Compressor;
import io.apptik.widget.multiselectspinner.BaseMultiSelectSpinner;
import io.apptik.widget.multiselectspinner.MultiSelectSpinner;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentBusinessDetails extends Fragment {


    @BindView(R.id.spnBusinesssSubcategory)
    MultiSelectSpinner spnBusinessSubCategory;

    @BindView(R.id.imgBusinessLogo)
    CircularImageView imgBusinessLogo;


    @BindView(R.id.edtOccupationSpecialization)
    EditText edtOccupationSpecialization;

    @BindView(R.id.edtOccupationSpecializationWrapper)
    TextInputLayout edtOccupationSpecializationWrapper;

    @BindView(R.id.edtBusinessAddress)
    EditText edtBusinessAddress;

    @BindView(R.id.edtBusinessAddressWrapper)
    TextInputLayout edtBusinessAddressWrapper;

    @BindView(R.id.edtOccupationArea)
    EditText edtOccupationArea;

    @BindView(R.id.edtOccupationAreaWrapper)
    TextInputLayout edtOccupationAreaWrapper;


    @BindView(R.id.edtWorkRoleTitle)
    EditText edtWorkRoleTitle;

    @BindView(R.id.edtWorkRoleTitleWrapper)
    TextInputLayout edtWorkRoleTitleWrapper;


    @BindView(R.id.edtOfficeno1)
    EditText edtOfficeno1;

    @BindView(R.id.edtOfficeno1Wrapper)
    TextInputLayout edtOfficeno1Wrapper;


    @BindView(R.id.edtOfficeno2)
    EditText edtOfficeno2;

    @BindView(R.id.edtOfficeno2Wrapper)
    TextInputLayout edtOfficeno2Wrapper;


    @BindView(R.id.edtWorkLandline1)
    EditText edtWorkLandline1;

    @BindView(R.id.edtWorkLandline1Wrapper)
    TextInputLayout edtWorkLandline1Wrapper;


    @BindView(R.id.edtWorkLandline2)
    EditText edtWorkLandline2;

    @BindView(R.id.edtWorkLandline2Wrapper)
    TextInputLayout edtWorkLandline2Wrapper;


    @BindView(R.id.edtBusinessWebsite)
    EditText edtBusinessWebsite;

    @BindView(R.id.edtBusinessWebsiteWrapper)
    TextInputLayout edtBusinessWebsiteWrapper;


    @BindView(R.id.edtBusinessCategory)
    EditText edtBusinessCategory;

    @BindView(R.id.edtBusinessCategoryWrapper)
    TextInputLayout edtBusinessCategoryWrapper;


    @BindView(R.id.edtBusinessSubCategory)
    EditText edtBusinessSubCategory;

    @BindView(R.id.edtBusinessSubCategoryWrapper)
    TextInputLayout edtBusinessSubCategoryCategoryWrapper;


    @BindView(R.id.spnBusiness)
    MaterialSpinner spnBusiness;

    private String userChoosenTask;
    private String BASE64STRING;

    private Camera camera;
    private File actualImage;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Bitmap bitmap;


    private ArrayList<StudentData> list_studentdata = new ArrayList<StudentData>();
    ArrayList<String> list_selected_student = new ArrayList<String>();

    ArrayList<String> list_BusinessCategory = new ArrayList<String>();
    ArrayList<String> list_BusinessCategoryId = new ArrayList<String>();

    ArrayList<String> list_BusinessSubCategory = new ArrayList<String>();
    ArrayList<String> list_BusinessSubCategoryId = new ArrayList<String>();


    private Context context = getActivity();
    private SpotsDialog spotsDialog;
    private View rootView;
    //private ImageView imgLogo;

    //  private EditText edtbusinessname, edtoccupationspecialization, edtbusinessaddress, edtoccupationarea, edtworkroletitle, edtofficeno1, edtofficeno2, edtworklandline1, edtworklandline2, edtbusinesswebsite, edtbusinesscategory;
    private MenuItem profile_edit, profile_update;
    private SessionManager sessionManager;
    private HashMap<String, String> userDetails = new HashMap<String, String>();

    private Realm realm;
    private UserMaster userData;
    UserMaster u = new UserMaster();
    private String TAG = FragmentBusinessDetails.class.getSimpleName();
    private String BUSINESS_LOGO_URL = "";
    private List<BusinessSubCategoryData.DATum> list_BusinessSubCategoryData = new ArrayList<BusinessSubCategoryData.DATum>();

    private ArrayList<String> getSelectedSubCategoryItems = new ArrayList<String>();

    public FragmentBusinessDetails() {
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
        //rootView = inflater.inflate(R.layout.fragment_business_details, container,false);

        rootView = inflater.inflate(R.layout.fragment_business_details, null);



   /* For this injection you have to specify the source of the views */
        ButterKnife.bind(this, rootView);

        if (Build.VERSION.SDK_INT > 9) {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }

        sessionManager = new SessionManager(getContext());
        userDetails = sessionManager.getSessionDetails();


        spotsDialog = new SpotsDialog(getActivity());
        spotsDialog.setCancelable(false);


        this.realm = Realm.getDefaultInstance();
        userData = u.getUserData(realm, Long.parseLong(userDetails.get(SessionManager.KEY_USER_ID)));


        try {
            //Glide.with(getActivity()).load(userData.getBusinessLogo()).error(R.mipmap.ic_launcher).into(imgBusinessLogo);
            Log.d(TAG, "Business Logo : " + userData.getBusinessLogo());
            BUSINESS_LOGO_URL = userData.getBusinessLogo();
            Picasso.with(getActivity())
                    .load(userData.getBusinessLogo())
                    .placeholder(R.drawable.app_logo)
                    .error(R.drawable.app_logo)
                    .into(imgBusinessLogo);

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


        imgBusinessLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BUSINESS_LOGO_URL = "";


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
        FillDataOnControls();


        spnBusinessSubCategory.setVisibility(View.GONE);
        spnBusinessSubCategory.setItems(getSelectedSubCategoryItems)

                .setListener(new MultiSelectSpinner.MultiSpinnerListener() {
                    @Override
                    public void onItemsSelected(boolean[] selected) {

               /*         Toast.makeText(context, "Selected ITems : "+multiSelectSpinner1.getSelectedItem(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(context, "Max Selected ITems : "+multiSelectSpinner1.getMaxSelectedItems(), Toast.LENGTH_SHORT).show();*/

                        /*try {
                            getSelectedSubCategoryItems = new ArrayList<String>(Arrays.asList(userData.getBusinesssubcategoryids().split(",")));

                            String str_selected_subcategories = "";
                            for(int i=0;i<getSelectedSubCategoryItems.size();i++)
                            {
                                int index = list_BusinessSubCategoryId.indexOf(getSelectedSubCategoryItems.get(i));
                                spnBusinessSubCategory.selectItem(index,true);
                                str_selected_subcategories = str_selected_subcategories+list_BusinessSubCategory.get(index)+",";
                            }
                            if(!str_selected_subcategories.isEmpty())
                            {
                                str_selected_subcategories = str_selected_subcategories.substring(0,str_selected_subcategories.lastIndexOf(","));
                                edtBusinessSubCategory.setText(str_selected_subcategories);
                            }*/

                        // Toast.makeText(getActivity(), "Selected ITems"+spnBusinessSubCategory.getSelectedItem(), Toast.LENGTH_SHORT).show();

                        String str = spnBusinessSubCategory.getSelectedItem().toString();
                        if(spnBusinessSubCategory.getSelectedItem().toString().toLowerCase().contains("select business subcategory"))
                        {
                            str = str.replace("Select Business SubCategory, ","");
                        }
                        else if(str.toLowerCase().contains("all types"))
                        {
                            str = list_BusinessSubCategory.toString();
                            if(str.toString().toLowerCase().contains("select business subcategory")) {
                                str = str.replace("Select Business SubCategory, ", "");

                            }

                            str = str.replace("[","");
                            str = str.replace("]","");


                        }
                        else
                        {
                            str = spnBusinessSubCategory.getSelectedItem().toString();
                        }
                        edtBusinessSubCategory.setText(str);
                        //Toast.makeText(getActivity(), "Total Count "+getSelectedSubCategoryItems.size(), Toast.LENGTH_SHORT).show();

/*
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d(TAG, "Error in Selected Items : "+e.getMessage());
                        }*/


                    }
                })

                .setAllCheckedText("All types")
                .setAllUncheckedText("none selected");

        edtBusinessSubCategory.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    spnBusinessSubCategory.performClick();

                }
                return false;
            }
        });


        edtBusinessCategory.setVisibility(View.GONE);
        spnBusiness.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                Log.d(TAG, "Selected Surname :" + item);


                if (!item.toLowerCase().equals("other") && !item.toLowerCase().equals("select surname")) {

                    edtBusinessCategory.setVisibility(View.GONE);
                    edtBusinessCategory.setText(item);

                } else if (item.toLowerCase().equals("other")) {

                    edtBusinessCategory.setText("");
                    edtBusinessCategory.setVisibility(View.VISIBLE);
                }
                if (position != 0) {

                    try {
                        getAllBusinessSubCategoryDetailsFromServer();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        spnBusiness.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
            }
        });


        getAllBusinessCategoryDataFromServer();
        HideControls();
        // Inflate the layout for this fragment
        return rootView;
    }

    private void getAllBusinessCategoryDataFromServer() {
        CommonMethods.showDialog(spotsDialog);


        ApiInterface apiClient = ApiClient.getClient().create(ApiInterface.class);
        Log.d(TAG, "URL getAllBusiness : " + CommonMethods.WEBSITE + "getAllBusiness?type=business");
        apiClient.getAllBusiness("business").enqueue(new Callback<BussinessCategoryData>() {
            @Override
            public void onResponse(Call<BussinessCategoryData> call, Response<BussinessCategoryData> response) {


                Log.d(TAG, "getserviceForSpinnersData Response Code : " + response.code());

                if (response.code() == 200) {


                    String str_error = response.body().getMESSAGE();
                    String str_error_original = response.body().getORIGINALMESSAGE();
                    boolean error_status = response.body().getERRORSTATUS();
                    boolean record_status = response.body().getRECORDS();

                    if (error_status == false) {
                        if (record_status == true) {

                            List<BussinessCategoryData.DATum> temp_list_BusniessData = response.body().getDATA();


                            //Set State SPinner Data
                            list_BusinessCategory.clear();
                            list_BusinessCategoryId.clear();
                            list_BusinessCategory.add("Select Business");
                            list_BusinessCategoryId.add("0");

                            for (int i = 0; i < temp_list_BusniessData.size(); i++) {

                                list_BusinessCategory.add(temp_list_BusniessData.get(i).getBusinessName());
                                list_BusinessCategoryId.add(temp_list_BusniessData.get(i).getId().toString());

                            }

                            //list_BusinessCategory.add("Other");
                            // list_BusinessCategoryId.add("Other");


                            spnBusiness.setItems(list_BusinessCategory);

                            try {
                                //String sur = userData.getSurname().toString();
                                int pos = list_BusinessCategoryId.indexOf(userData.getBusinessId().toString());

                                if (pos > 0) {
                                    spnBusiness.setSelectedIndex(pos);
                                    edtBusinessCategory.setText(list_BusinessCategory.get(pos));
                                    getAllBusinessSubCategoryDetailsFromServer();
                                }


                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }


                            CommonMethods.hideDialog(spotsDialog);

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
            public void onFailure(Call<BussinessCategoryData> call, Throwable t) {

                CommonMethods.onFailure(getActivity(), TAG, t);

                CommonMethods.hideDialog(spotsDialog);
            }
        });

    }

    private void getAllBusinessSubCategoryDetailsFromServer() {

        CommonMethods.showDialog(spotsDialog);


        ApiInterface apiClient = ApiClient.getClient().create(ApiInterface.class);
        Log.d(TAG, "URL getAllBusiness : " + CommonMethods.WEBSITE + "getAllBusiness?type=business");
        apiClient.getAllBusinessSubCategory("businesscategory", Integer.parseInt(list_BusinessCategoryId.get(spnBusiness.getSelectedIndex()))).enqueue(new Callback<BusinessSubCategoryData>() {
            @Override
            public void onResponse(Call<BusinessSubCategoryData> call, Response<BusinessSubCategoryData> response) {


                Log.d(TAG, "getserviceForSpinnersData Response Code : " + response.code());

                if (response.code() == 200) {


                    String str_error = response.body().getMESSAGE();
                    String str_error_original = response.body().getORIGINALMESSAGE();
                    boolean error_status = response.body().getERRORSTATUS();
                    boolean record_status = response.body().getRECORDS();

                    if (error_status == false) {
                        if (record_status == true) {
                            try {
                                List<BusinessSubCategoryData.DATum> temp_list_BusniessData = response.body().getDATA();


                                list_BusinessSubCategoryData = response.body().getDATA();
                                ;


                                // Dialog dialog = new Dial
                                //student_adapter = new StudentsDataAdapterRecyclerView(getActivity()/*, list_studentdata*/);
                                //rvb.setAdapter(student_adapter);

                                //Set State SPinner Data.clear();
                                list_BusinessSubCategory.clear();
                                list_BusinessSubCategoryId.clear();
                               // list_BusinessSubCategory.add("Select Business SubCategory");
                               // list_BusinessSubCategoryId.add("0");

                                for (int i = 0; i < temp_list_BusniessData.size(); i++) {

                                    list_BusinessSubCategory.add(temp_list_BusniessData.get(i).getBusinessCategoryName());
                                    list_BusinessSubCategoryId.add(temp_list_BusniessData.get(i).getId().toString());

                                }
                                //spnBusiness.setItems(list_BusinessCategory);


                                spnBusinessSubCategory.setItems(list_BusinessSubCategory);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            try {
                                getSelectedSubCategoryItems = new ArrayList<String>(Arrays.asList(userData.getBusinesssubcategoryids().split(",")));

                                String str_selected_subcategories = "";
                                for (int i = 0; i < getSelectedSubCategoryItems.size(); i++) {
                                    try {
                                        int index = list_BusinessSubCategoryId.indexOf(getSelectedSubCategoryItems.get(i));
                                        spnBusinessSubCategory.selectItem(index, true);
                                        str_selected_subcategories = str_selected_subcategories + list_BusinessSubCategory.get(index) + ",";
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (!str_selected_subcategories.isEmpty()) {
                                    str_selected_subcategories = str_selected_subcategories.substring(0, str_selected_subcategories.lastIndexOf(","));
                                    edtBusinessSubCategory.setText(str_selected_subcategories);
                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.d(TAG, "Error in Selected Items : " + e.getMessage());
                            }


                            try {
                                //String sur = userData.getSurname().toString();
/*
                                int pos = list_BusinessCategoryId.indexOf(userData.getBusinessId().toString());

                                if (pos > 0) {
                                    //spnBusiness.setSelectedIndex(pos);
                                    getAllBusinessSubCategoryDetailsFromServer();
                                }
*/


                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }


                            CommonMethods.hideDialog(spotsDialog);

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
            public void onFailure(Call<BusinessSubCategoryData> call, Throwable t) {

                CommonMethods.onFailure(getActivity(), TAG, t);

                CommonMethods.hideDialog(spotsDialog);
            }
        });


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_profile, menu);
        profile_edit = (MenuItem) menu.findItem(R.id.action_edit);

        profile_update = (MenuItem) menu.findItem(R.id.action_update);
        profile_update.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_edit) {
            //  IsUpdatePassword = false;
            profile_update.setVisible(true);
            profile_edit.setVisible(false);

            ShowAllControlsEditableAndVisible();
        } else if (item.getItemId() == R.id.action_update) {
            // profile_edit.setVisible(true);
            //profile_update.setVisible(false);
            //HideControls();

            if(edtBusinessSubCategory.getText().toString().length() == 0 || edtBusinessSubCategory.getText().toString().contains("none selected"))
            {
                CommonMethods.showAlertDialog(getActivity()  ,"Business details update info", "Please select business subcategory");
            }
            else
            {
                updateDetailsSendToServer();
            }


        }
        return super.onOptionsItemSelected(item);
    }

    private void ShowAllControlsEditableAndVisible() {

        edtBusinessSubCategory.setEnabled(true);
        edtBusinessCategory.setEnabled(true);
        edtOccupationSpecialization.setEnabled(true);
        edtBusinessAddress.setEnabled(true);
        edtOccupationArea.setEnabled(true);
        edtWorkRoleTitle.setEnabled(true);
        edtOfficeno1.setEnabled(true);
        edtOfficeno2.setEnabled(true);
        edtWorkLandline1.setEnabled(true);
        edtWorkLandline2.setEnabled(true);
        edtBusinessWebsite.setEnabled(true);
        edtBusinessCategory.setEnabled(true);
        imgBusinessLogo.setEnabled(true);

        spnBusiness.setEnabled(true);

        spnBusiness.setFocusableInTouchMode(true);
        edtBusinessSubCategory.setFocusableInTouchMode(true);
        imgBusinessLogo.setFocusableInTouchMode(true);

        edtBusinessCategory.setFocusableInTouchMode(true);
        edtOccupationSpecialization.setFocusableInTouchMode(true);
        edtBusinessAddress.setFocusableInTouchMode(true);
        edtOccupationArea.setFocusableInTouchMode(true);
        edtWorkRoleTitle.setFocusableInTouchMode(true);
        edtOfficeno1.setFocusableInTouchMode(true);
        edtOfficeno2.setFocusableInTouchMode(true);
        edtWorkLandline1.setFocusableInTouchMode(true);
        edtWorkLandline2.setFocusableInTouchMode(true);
        edtBusinessWebsite.setFocusableInTouchMode(true);
        edtBusinessCategory.setFocusableInTouchMode(true);

    }

    private void HideControls() {

        try {
            edtBusinessCategory.setEnabled(false);
            edtBusinessSubCategory.setEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        imgBusinessLogo.setEnabled(false);
        edtOccupationSpecialization.setEnabled(false);
        edtBusinessAddress.setEnabled(false);
        edtBusinessSubCategory.setEnabled(false);

        edtOccupationArea.setEnabled(false);
        edtWorkRoleTitle.setEnabled(false);
        edtOfficeno1.setEnabled(false);
        edtOfficeno2.setEnabled(false);
        edtWorkLandline1.setEnabled(false);
        edtWorkLandline2.setEnabled(false);
        edtBusinessWebsite.setEnabled(false);
        edtBusinessCategory.setEnabled(false);

        spnBusiness.setEnabled(false);


    }


    private void updateDetailsSendToServer() {

        CommonMethods.showDialog(spotsDialog);
        ApiInterface apiClient = ApiClient.getClient().create(ApiInterface.class);



        //getString  Business subcategory ids

        String subcategoryStringItems = "";


        List<String> myList = null;

        if(edtBusinessSubCategory.getText().toString().toLowerCase().contains("all types"))
        {
            myList = list_BusinessSubCategory;
        }
        else if(edtBusinessSubCategory.getText().toString().length() == 0)
        {
            CommonMethods.showAlertDialog(getActivity() ,"Business details update info","Please select business subcategory details");
            CommonMethods.hideDialog(spotsDialog);
            return;
        }
        else
        {
            myList = new ArrayList<String>(Arrays.asList(edtBusinessSubCategory.getText().toString().split(",")));
        }


        if (myList.size() != 0) {

            try {

                for (int i = 0; i < myList.size(); i++) {
                    try {
                        int indexOf = list_BusinessSubCategory.indexOf(myList.get(i).trim());
                        subcategoryStringItems = subcategoryStringItems + list_BusinessSubCategoryId.get(indexOf) + ",";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //subcategoryStringItems = myList.toString();
                //subcategoryStringItems = subcategoryStringItems.substring(0,subcategoryStringItems.lastIndexOf("]"));
                //subcategoryStringItems = subcategoryStringItems.substring(subcategoryStringItems.indexOf("["),subcategoryStringItems.length());
                subcategoryStringItems = subcategoryStringItems.substring(0, subcategoryStringItems.lastIndexOf(","));
                Log.d(TAG, "subcategoryStringItems : " + subcategoryStringItems);
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            subcategoryStringItems = "0";
        }


       /* for(int i=0;i<getSelectedSubCategoryItems.size();i++)
        {
           int indexOf = list_BusinessSubCategory.indexOf(getSelectedSubCategoryItems.get(i));
            subcategoryStringItems = subcategoryStringItems+list_BusinessSubCategoryId.get(indexOf)+",";
            subcategoryStringItems = subcategoryStringItems.substring(subcategoryStringItems.lastIndexOf(")"),subcategoryStringItems.length());

        }*/

        Log.d(TAG, "URL updateBusinessDetails : " + CommonMethods.WEBSITE + "updateBusinessDetails?type=businessdetails&contact=" + userDetails.get(SessionManager.KEY_USER_MOBILE) + "&businessname=" + edtBusinessCategory.getText().toString() + "&occupationspecialization=" + edtOccupationSpecialization.getText().toString() + "&businessaddress=" + edtBusinessAddress.getText().toString() + "&businesslogo=" + BUSINESS_LOGO_URL + "&occupationarea=" + edtOccupationArea.getText().toString() + "&workroletitle=" + edtWorkRoleTitle.getText().toString() + "&workmob1=" + edtOfficeno1.getText().toString() + "&workmob2=" + edtOfficeno2.getText().toString() + "&worklandline1=" + edtWorkLandline1.getText().toString() + "&worklandline2=" + edtWorkLandline2.getText().toString() + "&businesswebsite=" + edtBusinessWebsite.getText().toString() + "&businessubcategoryids=" + subcategoryStringItems + "&date=" + CommonMethods.getDateTime() + "");


///
//pdateBusinessDetails?type=businessdetails&contact=string&businessname=string&occupationspecialization=string&businessaddress=string&businesslogo=string&occupationarea=string&workroletitle=string&workmob1=string&workmob2=string&worklandline1=string&worklandline2=string&businesswebsite=string&businesscategory=string&date=string
        BUSINESS_LOGO_URL = BUSINESS_LOGO_URL.replace("http://blacksmith.studyfield.com/", "");

        apiClient.updateBusinessDetails("businessdetails", userDetails.get(SessionManager.KEY_USER_MOBILE), edtBusinessCategory.getText().toString(), edtOccupationSpecialization.getText().toString(), edtBusinessAddress.getText().toString(), BUSINESS_LOGO_URL, edtOccupationArea.getText().toString(), edtWorkRoleTitle.getText().toString(), edtOfficeno1.getText().toString(), edtOfficeno2.getText().toString(), edtWorkLandline1.getText().toString(), edtWorkLandline2.getText().toString(), edtBusinessWebsite.getText().toString(), subcategoryStringItems, CommonMethods.convertToJsonDateFormat(CommonMethods.getDateCurrentDate())).enqueue(new Callback<UserDataResponse>() {
            @Override
            public void onResponse(Call<UserDataResponse> call, Response<UserDataResponse> response) {


                Log.d(TAG, "updateAddressDetails Response Code : " + response.code());

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
                            //  sessionManager.setUserDetails(String.valueOf(userId), userMobile, arr.get(i).getAppovalStatus());


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

                                CommonMethods.showAlertDialog(getActivity(), "Business Details Update Info", "Your business details has been successfully updated.");


                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }


                        }

                        CommonMethods.hideDialog(spotsDialog);


                    } else {

                        CommonMethods.hideDialog(spotsDialog);
                        Toast.makeText(getActivity(), "" + getString(R.string.server_error), Toast.LENGTH_SHORT).show();
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

    private void FillDataOnControls() {
        try {
            edtBusinessCategory.setText(userData.getBusinessName().toString());
            edtOccupationSpecialization.setText(userData.getOccupationspecialization().toString());
            edtBusinessAddress.setText(userData.getBusinessAddress().toString());
            edtOccupationArea.setText(userData.getBusinessName().toString());
            edtWorkRoleTitle.setText(userData.getWorkTitle().toString());
            edtOfficeno1.setText(userData.getLandLine1().toString());
            edtOfficeno2.setText(userData.getLandLine2().toString());
            edtWorkLandline1.setText(userData.getLandLine1().toString());
            edtWorkLandline2.setText(userData.getLandLine2().toString());
            edtBusinessWebsite.setText(userData.getBusinessWebsite().toString());
            edtBusinessCategory.setText(userData.getBusinessAddress().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class StudentData {

        int id;
        String subcategoryname;
        boolean selectedStatus;


        public StudentData(int id, String subcategoryname, boolean selectedStatus) {
            this.id = id;
            this.subcategoryname = subcategoryname;
            this.selectedStatus = selectedStatus;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSubcategoryname() {
            return subcategoryname;
        }

        public void setSubcategoryname(String subcategoryname) {
            this.subcategoryname = subcategoryname;
        }

        public boolean isSelectedStatus() {
            return selectedStatus;
        }

        public void setSelectedStatus(boolean selectedStatus) {
            this.selectedStatus = selectedStatus;
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
        apiInterface.getImageResponseFromServer("business", BASE64STRING).enqueue(new Callback<ImageData>() {
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


                            BUSINESS_LOGO_URL = response.body().getDATA().get(0).getImageurl();

                            Log.d(TAG, "IMAGE URL : " + BUSINESS_LOGO_URL);

                            String filename = BUSINESS_LOGO_URL.substring(BUSINESS_LOGO_URL.lastIndexOf("/"), BUSINESS_LOGO_URL.length());


                            Log.d(TAG, "Image URL : " + BUSINESS_LOGO_URL);

                            //imgDocument.setVisibility(View.VISIBLE);

                            if (!BUSINESS_LOGO_URL.isEmpty()) {
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
                                        userMaster.setBusinessLogo(BUSINESS_LOGO_URL);


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


                                    imgBusinessLogo.setImageBitmap(bitmap);


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


    //Adapter for display business details

   /* public class StudentsDataAdapterRecyclerView extends RecyclerView.Adapter<StudentsDataAdapterRecyclerView.MyViewHolder> {


        private final Context context;
        private final LayoutInflater inflater;
        private final SessionManager sessionmanager;
        private HashMap<String, String> userDetails = new HashMap<String, String>();
       *//* private ArrayList<StudentData> list_studentData = new ArrayList<StudentData>();*//*


        private SQLiteDatabase sd;


        // boolean array for storing
        //the state of each CheckBox
        //ArrayList<Boolean> checkBoxState= new ArrayList<Boolean>();

        String[] texts;

        // Instance of a Custom edit text listener
        //  public CustomEtListener myCustomEditTextListener;
        private String TAG = StudentsDataAdapterRecyclerView.class.getSimpleName();

        public StudentsDataAdapterRecyclerView(Context context*//*, ArrayList<StudentData> allTestMarks*//*) {

            this.context = context;
            //this.list_studentData = allTestMarks;
            inflater = LayoutInflater.from(context);


            //  texts = new String[allTestMarks.size()];

            //this is the important step
            //for (int i = 0; i < allTestMarks.size(); i++) {

//      checkBoxState.add(i,allTestMarks.get(i).isSelected()); // initializes all items value with false
            //   texts[i] = allTestMarks.get(i).getObtainedMarks();
            // }


            sessionmanager = new SessionManager(context);

            userDetails = sessionmanager.getSessionDetails();


        }

        @Override
        public StudentsDataAdapterRecyclerView.MyViewHolder onCreateViewHolder(ViewGroup parent, final int position) {


            View view = inflater.inflate(R.layout.dialog_apptest_marks, parent, false);
            StudentsDataAdapterRecyclerView.MyViewHolder viewHolder = new StudentsDataAdapterRecyclerView.MyViewHolder(view);


            return viewHolder;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {


            private final CheckBox chkUID;
            private final EditText editText2;
            private final TextView tv, tv2;

            // Instance of a Custom edit text listener


            public MyViewHolder(View itemView) {
                super(itemView);


                chkUID = (CheckBox) itemView.findViewById(R.id.checkBox);
                editText2 = (EditText) itemView.findViewById(R.id.editText2);


                // chkUID.setWidth(LinearLayout.LayoutParams.FILL_PARENT);

                //chkUID.getLayoutParams().width = LinearLayout.LayoutParams.FILL_PARENT;
*//*
                RelativeLayout.LayoutParams chkBoxParams = new RelativeLayout.LayoutParams(
                        CoordinatorLayout.LayoutParams.FILL_PARENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);

                chkUID.setLayoutParams(chkBoxParams);*//*


                tv = (TextView) itemView.findViewById(R.id.editText);
                tv2 = (TextView) itemView.findViewById(R.id.textView8);


                tv.setVisibility(View.GONE);

                tv2.setVisibility(View.GONE);

                editText2.setVisibility(View.GONE);


                // assign a new instance of addTextChangedListener for each item


            }
        }


        @Override
        public void onBindViewHolder(final StudentsDataAdapterRecyclerView.MyViewHolder holder, final int position) {


            StudentData TM = list_studentdata.get(position);

            holder.chkUID.setText(TM.getSubcategoryname());


            holder.chkUID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        // String code, String name, String marks, boolean selected

                        if (((CheckBox) v).isChecked()) {
//                        checkBoxState.set(position , true);


                            //StudentData(String studentname, String rollno, String dob, String uid, String studentmobile, String fathermobile, String mothermobile, String classid) {
                            StudentData sd = new StudentData(list_studentdata.get(position).getId(),list_studentdata.get(position).getSubcategoryname(),true);
                            list_studentdata.set(position, sd);

                            notifyDataSetChanged();


                        } else {


                            StudentData sd = new StudentData(list_studentdata.get(position).getId(),list_studentdata.get(position).getSubcategoryname(),false);
                            list_studentdata.set(position, sd);

                            notifyDataSetChanged();


                        }


                        list_selected_student.clear();
                        String studentname = "";
                        for (int i = 0; i < list_studentdata.size(); i++) {
                            if (list_studentdata.get(i).isSelectedStatus() == true) {

                                studentname = studentname + "\n" + list_studentdata.get(i).getSubcategoryname();
                                list_selected_student.add(""+list_studentdata.get(i).getId());
                            }

                        }
                        // tvSelectedStudents.setText(studentname);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

            Log.d("Checked " + position, String.valueOf(list_studentdata.get(position).isSelectedStatus()));
            holder.chkUID.setChecked(list_studentdata.get(position).isSelectedStatus());


        }

        @Override
        public int getItemCount() {
            return list_studentdata.size();
        }


    }*/
}