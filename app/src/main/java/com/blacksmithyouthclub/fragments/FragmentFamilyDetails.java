package com.blacksmithyouthclub.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.blacksmithyouthclub.R;
import com.blacksmithyouthclub.api.ApiClient;
import com.blacksmithyouthclub.api.ApiInterface;
import com.blacksmithyouthclub.helper.CommonMethods;
import com.blacksmithyouthclub.material_spinner.MaterialSpinner;
import com.blacksmithyouthclub.model.SpinnersData;
import com.blacksmithyouthclub.model.UserDataResponse;
import com.blacksmithyouthclub.realm.model.UserMaster;
import com.blacksmithyouthclub.session.SessionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentFamilyDetails extends Fragment {


    private Context context = getActivity();
    private SpotsDialog spotsDialog;
    private MenuItem profile_edit, profile_update;
    private View rootView;


    @BindView(R.id.edtFathersName)
    EditText edtFathersName;

    @BindView(R.id.edtFathersNameWrapper)
    TextInputLayout edtFathersNameWrapper;


    @BindView(R.id.edtMotherName)
    EditText edtMotherName;

    @BindView(R.id.edtMotherNameWrapper)
    TextInputLayout edtMotherNameWrapper;


    @BindView(R.id.edtFathersFathername)
    EditText edtFathersFathername;

    @BindView(R.id.edtFathersFathernameWrapper)
    TextInputLayout edtFathersFathernameWrapper;

    @BindView(R.id.edtFathersMothername)
    EditText edtFathersMothername;

    @BindView(R.id.edtFathersMothernameWrapper)
    TextInputLayout edtFathersMothernameWrapper;


    @BindView(R.id.edtMothersFathername)
    EditText edtMothersFathername;

    @BindView(R.id.edtMothersFathernameWrapper)
    TextInputLayout edtMothersFathernameWrapper;

    @BindView(R.id.edtMothersMotherName)
    EditText edtMothersMotherName;

    @BindView(R.id.edtMothersMotherNameWrapper)
    TextInputLayout edtMothersMotherNameWrapper;

    @BindView(R.id.edtMothersFatherVillage)
    EditText edtMothersFatherVillage;

    @BindView(R.id.edtMothersFatherVillageWrapper)
    TextInputLayout edtMothersFatherVillageWrapper;

    @BindView(R.id.spnMothersFatherSurname)
    MaterialSpinner spnMothersFatherSurname;

    @BindView(R.id.edtMothersFatherSurname)
    EditText edtMothersFatherSurname;

    @BindView(R.id.edtMothersFatherSurnameWrapper)
    TextInputLayout edtMothersFatherSurnameWrapper;

    @BindView(R.id.spnWifesFatherSurname)
    MaterialSpinner spnWifesFatherSurname;


    @BindView(R.id.edtWifesFatherSurname)
    EditText edtWifesFatherSurname;

    @BindView(R.id.edtWifesFatherSurnameWrapper)
    TextInputLayout edtWifesFatherSurnameWrapper;




    @BindView(R.id.edtWifesName)
    EditText edtWifesName;

    @BindView(R.id.edtWifesNameWrapper)
    TextInputLayout edtWifesNameWrapper;


    @BindView(R.id.edtWifesFatherName)
    EditText edtWifesFatherName;

    @BindView(R.id.edtWifesFatherNameWrapper)
    TextInputLayout edtWifesFatherNameWrapper;


    @BindView(R.id.edtWifesMotherName)
    EditText edtWifesMotherName;

    @BindView(R.id.edtWifesMotherNameWrapper)
    TextInputLayout edtWifesMotherNameWrapper;

    @BindView(R.id.edtWifesFatherVllage)
    EditText edtWifesFatherVllage;

    @BindView(R.id.edtWifesFatherVllageWrapper)
    TextInputLayout edtWifesFatherVllageWrapper;


    @BindView(R.id.edtHusbandName)
    EditText edtHusbandName;

    @BindView(R.id.edtHusbandNameWrapper)
    TextInputLayout edtHusbandNameWrapper;


    @BindView(R.id.edtHusbandsFatherName)
    EditText edtHusbandsFatherName;

    @BindView(R.id.edtHusbandsFatherNameWrapper)
    TextInputLayout edtHusbandsFatherNameWrapper;


    @BindView(R.id.edtHusbandsMothername)
    EditText edtHusbandsMothername;

    @BindView(R.id.edtHusbandsMothernameWrapper)
    TextInputLayout edtHusbandsMothernameWrapper;


    //private EditText edtfathername,edtmothersname;
    // private EditText edtfathersfathername,edtfathersmothername;
    //private EditText edtmothersfathername,edtmothersmothername,edtmothersfathervillage;
    //private EditText edthusbandname,edthusbandsfathername,edthusbandsmothername;
    // private EditText edtwifesname,edtwifesfathername,edtwifesmothername,edtwifesfathervllage;
    //private MaterialSpinner spmothersfathersurname;
    //private MaterialSpinner spwifesfathersurname;

    private String MaritalStatusId = "3", gender = "Male";
    // private TextInputLayout edtfathername_wrapper,edtmothersname_wrapper;
    // private TextInputLayout edtfathersfathername_wrapper,edtfathersmothername_wrapper;
    // private TextInputLayout edtmothersfathername_wrapper,edtmothersmothername_wrapper,edtmothersfathervillage_wrapper;
    // private TextInputLayout edthusbandname_wrapper,edthusbandsfathername_wrapper,edthusbandsmothername_wrapper;
    //private TextInputLayout edtwifesname_wrapper,edtwifesfathername_wrapper,edtwifesmothername_wrapper,edtwifesfathervllage_wrapper;


    private Realm realm;
    private UserMaster userData;
    UserMaster u = new UserMaster();

    ArrayList<String> list_Surname = new ArrayList<String>();
    ArrayList<String> list_SurnameId = new ArrayList<String>();

    ArrayList<String> list_MothersFatherSurname = new ArrayList<String>();
    ArrayList<String> list_MothersFatherSurnameId = new ArrayList<String>();


    private String TAG = FragmentFamilyDetails.class.getSimpleName();
    private SessionManager sessionManager;
    private HashMap<String, String> userDetails = new HashMap<String, String>();


    public FragmentFamilyDetails() {
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
        //rootView = inflater.inflate(R.layout.fragment_family_details, container,false);

        rootView = inflater.inflate(R.layout.fragment_family_details, null);

        //unbinder = ButterKnife.bind(this, rootView);

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
            MaritalStatusId =userData.getMaritalStatusId().toString();
            gender = userData.getGender().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }


        edtWifesFatherSurname.setVisibility(View.GONE);
        spnWifesFatherSurname.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                Log.d(TAG , "Selected Surname :"+item);


                if(!item.toLowerCase().equals("other") && !item.toLowerCase().equals("select surname"))
                {

                    edtWifesFatherSurname.setVisibility(View.GONE);
                    edtWifesFatherSurname.setText(item);

                }
                else if(item.toLowerCase().equals("other"))
                {

                    edtWifesFatherSurname.setText("");
                    edtWifesFatherSurname.setVisibility(View.VISIBLE);
                }
            }
        });
        spnWifesFatherSurname.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
            }
        });


        edtMothersFatherSurname.setVisibility(View.GONE);

        spnMothersFatherSurname.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                Log.d(TAG , "Selected Surname :"+item);


                if(!item.toLowerCase().equals("other") && !item.toLowerCase().equals("select surname"))
                {

                    edtMothersFatherSurname.setVisibility(View.GONE);
                    edtMothersFatherSurname.setText(item);

                }
                else if(item.toLowerCase().equals("other"))
                {

                    edtMothersFatherSurname.setText("");
                    edtMothersFatherSurname.setVisibility(View.VISIBLE);
                }
            }
        });
        spnWifesFatherSurname.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
            }
        });





        HideControls();
        if (MaritalStatusId.toString().equals("3") || MaritalStatusId.toString().equals("4")) {
            if (gender.toString().toLowerCase().equals("male")) {

                edtWifesNameWrapper.setVisibility(View.VISIBLE);
                edtWifesFatherNameWrapper.setVisibility(View.VISIBLE);
                edtWifesMotherNameWrapper.setVisibility(View.VISIBLE);
                spnWifesFatherSurname.setVisibility(View.VISIBLE);
                edtWifesFatherVllageWrapper.setVisibility(View.VISIBLE);
            } else if (gender.toString().toLowerCase().equals("female")) {

                /*edthusbandname_wrapper.setVisibility(View.VISIBLE);
                edthusbandsfathername_wrapper.setVisibility(View.VISIBLE);
                edthusbandsmothername_wrapper.setVisibility(View.VISIBLE);*/

                edtHusbandNameWrapper.setVisibility(View.VISIBLE);
                edtHusbandsFatherNameWrapper.setVisibility(View.VISIBLE);
                edtHusbandsFatherNameWrapper.setVisibility(View.VISIBLE);

                edtHusbandNameWrapper.setVisibility(View.VISIBLE);
                edtHusbandsFatherNameWrapper.setVisibility(View.VISIBLE);
                edtHusbandsMothernameWrapper.setVisibility(View.VISIBLE);
                //spn.setVisibility(View.VISIBLE);
                //edtHus.setVisibility(View.VISIBLE);



            }
        }
        FillDataOnControls();
        getAllComboDetailFromServer();
        // Inflate the layout for this fragment
        return rootView;
    }


    private void FillDataOnControls() {



        edtFathersName.setText(userData.getFathersName().toString());
        edtMotherName.setText(userData.getMothersName().toString());

        edtFathersFathername.setText(userData.getFathersFathersName().toString());
        edtFathersMothername.setText(userData.getFathersMothersName().toString());

        edtMothersFathername.setText(userData.getMothersFathersName().toString());

        edtMothersMotherName.setText(userData.getMothersMothersName().toString());
        edtMothersFatherVillage.setText(userData.getMothersFathersVillage().toString());

        edtHusbandName.setText(userData.getHusbandsName().toString());

        edtWifesName.setText(userData.getWifesName().toString());
        edtWifesFatherName.setText(userData.getWifesFathersName().toString());
        edtWifesMotherName.setText(userData.getWifesMothersName().toString());
        edtWifesFatherVllage.setText(userData.getWifesFathersVillage().toString());

        edtFathersFathername.setText(userData.getFathersFathersName().toString());
        edtFathersMothername.setText(userData.getFathersMothersName().toString());
        edtMothersFathername.setText(userData.getMothersFathersName().toString());
        edtMothersMotherName.setText(userData.getMothersMothersName().toString());

        edtMothersFatherVillage.setText(userData.getMothersFathersVillage().toString());
        edtHusbandName.setText(userData.getHusbandsName().toString());
        edtHusbandsFatherName.setText(userData.getHusbandsFathersName().toString());
        edtHusbandsMothername.setText(userData.getHusbandsMothersName().toString());




 /*       edtHusbandsFatherName.setEnabled(false);
        edtHusbandsMothername.setEnabled(false);
        edtWifesName.setEnabled(false);
        edtWifesFatherName.setEnabled(false);
        edtWifesMotherName.setEnabled(false);
        edtWifesFatherVllage.setEnabled(false);*/




    }




    private void HideControls() {
        edtFathersName.setEnabled(false);
        edtMotherName.setEnabled(false);

        edtFathersFathername.setEnabled(false);
        edtFathersMothername.setEnabled(false);

        edtMothersFathername.setEnabled(false);

        edtMothersMotherName.setEnabled(true);
        edtMothersFatherVillage.setEnabled(true);

        edtMothersFatherVillage.setEnabled(false);

        edtHusbandName.setEnabled(false);
        edtHusbandsFatherName.setEnabled(false);
        edtHusbandsMothername.setEnabled(false);

        edtWifesName.setEnabled(false);
        edtWifesFatherName.setEnabled(false);
        edtWifesMotherName.setEnabled(false);
        edtWifesFatherVllage.setEnabled(false);
    }

    private void ShowAllControlsEditableAndVisible() {
        edtFathersName.setEnabled(true);
        edtMotherName.setEnabled(true);

        edtFathersFathername.setEnabled(true);
        edtFathersMothername.setEnabled(true);


        edtMothersFathername.setEnabled(true);

        edtMothersMotherName.setEnabled(true);
        edtMothersFatherVillage.setEnabled(true);

        edtMothersFatherVillage.setEnabled(true);

        edtHusbandName.setEnabled(true);
        edtHusbandsFatherName.setEnabled(true);
        edtHusbandsMothername.setEnabled(true);

        edtWifesName.setEnabled(true);
        edtWifesFatherName.setEnabled(true);
        edtWifesMotherName.setEnabled(true);
        edtWifesFatherVllage.setEnabled(true);


        edtFathersName.setFocusableInTouchMode(true);
        edtMotherName.setFocusableInTouchMode(true);

        edtFathersFathername.setFocusableInTouchMode(true);
        edtFathersMothername.setFocusableInTouchMode(true);

        edtMothersFathername.setFocusableInTouchMode(true);

        edtMothersMotherName.setFocusableInTouchMode(true);
        edtMothersFatherVillage.setFocusableInTouchMode(true);

        edtMothersFatherVillage.setFocusableInTouchMode(true);

        edtHusbandName.setFocusableInTouchMode(true);
        edtHusbandsFatherName.setFocusableInTouchMode(true);
        edtHusbandsMothername.setFocusableInTouchMode(true);

        edtWifesName.setFocusableInTouchMode(true);
        edtWifesFatherName.setFocusableInTouchMode(true);
        edtWifesMotherName.setFocusableInTouchMode(true);
        edtWifesFatherVllage.setFocusableInTouchMode(true);




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
            profile_edit.setVisible(true);
            profile_update.setVisible(false);
            //HideControls();

            updateDetailsSendToServer();
        }
        return super.onOptionsItemSelected(item);
    }


    private void getAllComboDetailFromServer() {


        CommonMethods.showDialog(spotsDialog);


        ApiInterface apiClient = ApiClient.getClient().create(ApiInterface.class);
        Log.d(TAG, "URL getserviceForSpinnersData : " + CommonMethods.WEBSITE + "getserviceForSpinnersData?type=spinner&countryid=1");
        apiClient.getserviceForSpinnersData("spinner", "1",userDetails.get(SessionManager.KEY_SELECTED_CASTE)).enqueue(new Callback<SpinnersData>() {
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
                            list_StateId.clear();
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
                            list_Surname.add("Select Wifes Father Surname");
                            list_SurnameId.add("0");


                            list_MothersFatherSurname.clear();
                            list_MothersFatherSurnameId.clear();
                            list_MothersFatherSurname.add("Select Mother Father Surname");
                            list_MothersFatherSurnameId.add("0");

                            for (int i = 0; i < temp_list_SurnameData.size(); i++) {
                                list_Surname.add(temp_list_SurnameData.get(i).getSurname());
                                list_SurnameId.add(temp_list_SurnameData.get(i).getId().toString());

                                list_MothersFatherSurname.add(temp_list_SurnameData.get(i).getSurname());
                                list_MothersFatherSurnameId.add(temp_list_SurnameData.get(i).getId().toString());

                            }


                            list_Surname.add("Other");
                            list_SurnameId.add("Other");
                            list_MothersFatherSurname.add("Other");
                            list_MothersFatherSurnameId.add("Other");

                            spnMothersFatherSurname.setItems(list_MothersFatherSurname);
                            spnWifesFatherSurname.setItems(list_Surname);




                            try {
                                //String sur = userData.getMothersFathersSurname().toString();
                                if(userData.getMothersFathersSurname()!= null)
                                {
                                    int pos = list_MothersFatherSurnameId.indexOf(userData.getMothersFathersSurname().toString());

                                    if (pos > 0) {

                                        spnMothersFatherSurname.setSelectedIndex(pos);
                                        edtMothersFatherSurname.setText(list_MothersFatherSurname.get(pos));
                                    }


                                }



                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }

                            try {
                                //String sur = userData.getMothersFathersSurname().toString();
                                if( userData.getWifesFathersSurname()!= null)
                                {
                                    int pos = list_SurnameId.indexOf(userData.getWifesFathersSurname().toString());

                                    if (pos > 0) {

                                        spnWifesFatherSurname.setSelectedIndex(pos);
                                        edtWifesFatherSurname.setText(list_Surname.get(pos));
                                    }


                                }



                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }




                            //Set MArital Status SPinner Data
                           /* list_MaritalStatus.clear();
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
                                int pos =  list_MaritalStatusId.indexOf(userData.getMaritalStatusId());

                                if(pos > 0)
                                {
                                    spnMaritalStatus.setSelectedIndex(pos);
                                }



                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }*/


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

    private void updateDetailsSendToServer()
    {

        CommonMethods.showDialog(spotsDialog);
        ApiInterface apiClient = ApiClient.getClient().create(ApiInterface.class);


        //getString  Business subcategory ids


        Log.d(TAG, "URL updateFamilyDetails : " + CommonMethods.WEBSITE + "updateFamilyDetails?type=familydetails&fathersname="+ edtFathersName.getText().toString() +"&mothersname="+ edtMotherName.getText().toString()+"&fathersfathername="+ edtFathersFathername.getText().toString() +"&fathersmothername="+ edtFathersMothername.getText().toString() +"&mothersfathername="+  edtMothersFathername.getText().toString()+"&mothersmothername="+ edtMothersMotherName.getText().toString() +"&casteid="+ userDetails.get(SessionManager.KEY_SELECTED_CASTE) +"&mothersfathersurname="+ list_Surname.get(spnMothersFatherSurname.getSelectedIndex()) +"&mothersfathervillage="+ edtMothersFatherVillage.getText().toString() +"&husbandname="+ edtHusbandName.getText().toString() +"&husbandsfathername="+ edtHusbandsFatherName.getText().toString() +"&husbandsmothername="+ edtHusbandsMothername.getText().toString()  +"&wifesname="+ edtWifesName.getText().toString() +"&wifesfathername="+ edtWifesFatherName.getText().toString() +"&wifesmothername="+ edtWifesMotherName.getText().toString() +"&wifesfathersurname="+ list_Surname.get(spnWifesFatherSurname.getSelectedIndex()) +"&wifesfathervllage="+ edtWifesFatherVllage.getText().toString() +"&updateddate="+ CommonMethods.convertToJsonDateFormat(CommonMethods.getDateCurrentDate()) +"&contact="+ userDetails.get(SessionManager.KEY_USER_MOBILE) +"");





///
//updateFamilyDetails?type=string&fathersname=string&mothersname=string&fathersfathername=string&fathersmothername=string&mothersfathername=string&mothersmothername=string&mothersfathersurname=string&mothersfathervillage=string&husbandname=string&husbandsfathername=string&husbandsmothername=string&wifesname=string&wifesfathername=string&wifesmothername=string&wifesfathersurname=string&wifesfathervllage=string&updateddate=string&contact=string
       // BUSINESS_LOGO_URL = BUSINESS_LOGO_URL.replace("http://blacksmith.studyfield.com/","");

        apiClient.updateFamilyDetails("familydetails", edtFathersName.getText().toString() , edtMotherName.getText().toString(),edtFathersFathername.getText().toString() ,edtFathersMothername.getText().toString() ,  edtMothersFathername.getText().toString(), edtMothersMotherName.getText().toString() ,userDetails.get(SessionManager.KEY_SELECTED_CASTE),edtMothersFatherSurname.getText().toString() ,edtMothersFatherVillage.getText().toString() , edtHusbandName.getText().toString() , edtHusbandsFatherName.getText().toString(),edtHusbandsMothername.getText().toString()  , edtWifesName.getText().toString() ,edtWifesFatherName.getText().toString() , edtWifesMotherName.getText().toString() , edtWifesFatherSurname.getText().toString() ,edtWifesFatherVllage.getText().toString(), CommonMethods.convertToJsonDateFormat(CommonMethods.getDateCurrentDate()) , userDetails.get(SessionManager.KEY_USER_MOBILE)).enqueue(new Callback<UserDataResponse>() {
            @Override
            public void onResponse(Call<UserDataResponse> call, Response<UserDataResponse> response) {


                Log.d(TAG, "updateFamilyDetails Response Code : " + response.code());

                if (response.code() == 200) {


                    String str_error = response.body().getMESSAGE();
                    String str_error_original = response.body().getORIGINALMESSAGE();
                    boolean error_status = response.body().getERRORSTATUS();
                    boolean record_status = response.body().getRECORDS();

                    if (error_status == false) {

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
                                //realm.insertOrUpdate(userMaster);
                                realm.commitTransaction();
                                Log.d(TAG, "Userdata has been added in database");
                                FillDataOnControls();

                                HideControls();

                                CommonMethods.showAlertDialog(getActivity(), "Family Details Update Info", "Your family details has been successfully updated.");


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

  }