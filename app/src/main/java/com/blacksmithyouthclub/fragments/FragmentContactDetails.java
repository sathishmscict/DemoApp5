package com.blacksmithyouthclub.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
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
import com.blacksmithyouthclub.model.CityData;
import com.blacksmithyouthclub.model.CommonReponse;
import com.blacksmithyouthclub.model.SpinnersData;
import com.blacksmithyouthclub.model.UserDataResponse;
import com.blacksmithyouthclub.realm.model.UserMaster;
import com.blacksmithyouthclub.session.SessionManager;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentContactDetails extends Fragment {


    //private EditText edtcontactno1,edtcontactno2,edtcurrentarea,edtaddress,edtpincode;
    //private MaterialSpinner spstate,spcity;
    //private TextInputLayout edtcontactno1_wrapper,edtcontactno2_wrapper,edtcurrentarea_wrapper,edtaddress_wrapper,edtpincode_wrapper;


    @BindView(R.id.edtHomePhone)
    EditText edtHomePhone;

    @BindView(R.id.edtHomePhoneWrapper)
    TextInputLayout edtHomePhoneWrapper;


    @BindView(R.id.edtContactno2)
    EditText edtContactno2;

    @BindView(R.id.edtContactno2Wrapper)
    TextInputLayout edtContactno2Wrapper;


    @BindView(R.id.edtCurrentArea)
    EditText edtCurrentArea;

    @BindView(R.id.edtCurrentAreaWrapper)
    TextInputLayout edtCurrentAreaWrapper;

    @BindView(R.id.edtAddress)
    EditText edtAddress;

    @BindView(R.id.edtAddressWrapper)
    TextInputLayout edtAddressWrapper;

    @BindView(R.id.edtPincode)
    EditText edtPincode;

    @BindView(R.id.edtPincodeWrapper)
    TextInputLayout edtPincodeWrapper;


    @BindView(R.id.spnState)
    MaterialSpinner spnState;

    @BindView(R.id.spnCity)
    MaterialSpinner spnCity;


    ArrayList<String> list_State = new ArrayList<String>();
    ArrayList<String> list_StateId = new ArrayList<String>();

    ArrayList<String> list_City = new ArrayList<String>();
    ArrayList<String> list_CityId = new ArrayList<String>();


    private Context context = getActivity();
    private SpotsDialog spotsDialog;
    private MenuItem profile_edit, profile_update;
    private View rootView;
    private String TAG = FragmentContactDetails.class.getSimpleName();
    private SessionManager sessionManager;
    private HashMap<String, String> userDetails = new HashMap<String, String>();


    private Realm realm;
    private UserMaster userData;
    UserMaster u = new UserMaster();

    public FragmentContactDetails() {
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
        //rootView = inflater.inflate(R.layout.fragment_contact_details, container,false);

        rootView = inflater.inflate(R.layout.fragment_contact_details, null);

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


        HideControls();
        // Inflate the layout for this fragment


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


        FillDataOnControls();


        getAllComboDetailFromServer();

        return rootView;
    }


    private void FillDataOnControls () {
        edtHomePhone.setText(userData.getHomePhone().toString());
        edtContactno2.setText(userData.getMob2().toString());
        edtAddress.setText(userData.getAddress().toString());
        edtCurrentArea.setText(userData.getCurrentArea().toString());
        edtPincode.setText(userData.getPincode().toString());
    }
    private void HideControls() {
        edtHomePhone.setEnabled(false);
        edtContactno2.setEnabled(false);
        edtCurrentArea.setEnabled(false);
        edtAddress.setEnabled(false);
        edtPincode.setEnabled(false);
        spnState.setEnabled(false);
        spnCity.setEnabled(false);


        edtHomePhone.setFocusableInTouchMode(true);
        edtContactno2.setFocusableInTouchMode(true);
        edtCurrentArea.setFocusableInTouchMode(true);
        edtAddress.setFocusableInTouchMode(true);
        edtPincode.setFocusableInTouchMode(true);
        spnState.setFocusableInTouchMode(true);
        spnCity.setFocusableInTouchMode(true);


    }

    private void ShowAllControlsEditableAndVisible() {
        edtHomePhone.setEnabled(true);
        edtContactno2.setEnabled(true);
        edtCurrentArea.setEnabled(true);
        edtAddress.setEnabled(true);
        edtPincode.setEnabled(true);
        spnState.setEnabled(true);
        spnCity.setEnabled(true);


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

                            try {
                                //String sur = userData.getSurname().toString();
                                int pos = list_StateId.indexOf(userData.getStateId().toString());

                                if (pos > 0) {
                                    spnState.setSelectedIndex(pos);
                                    getAllCityDetailsFromServer();
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
            public void onFailure(Call<SpinnersData> call, Throwable t) {

                CommonMethods.onFailure(getActivity(), TAG, t);

                CommonMethods.hideDialog(spotsDialog);
            }
        });


    }


    /**
     * get All city names based on State selection
     */

    private void getAllCityDetailsFromServer() {


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


                            try {
                                //String sur = userData.getSurname().toString();
                                int pos = list_CityId.indexOf(userData.getCityId().toString());

                                if (pos > 0) {
                                    spnCity.setSelectedIndex(pos);
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
            public void onFailure(Call<CityData> call, Throwable t) {

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
            profile_edit.setVisible(true);
            profile_update.setVisible(false);


            updateDetailsSendToServer();
            // HideControls();
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateDetailsSendToServer() {


        CommonMethods.showDialog(spotsDialog);

        ApiInterface apiClient = ApiClient.getClient().create(ApiInterface.class);
        Log.d(TAG, "URL updateAddressDetails : " + CommonMethods.WEBSITE + "updateAddressDetails?type=addressdetails&homephone=" + edtHomePhone.getText().toString() + "&stateid=" + Integer.parseInt(list_StateId.get(spnState.getSelectedIndex())) + "&cityid=" + Integer.parseInt(list_CityId.get(spnCity.getSelectedIndex())) + "&address=" + edtAddress.getText().toString() + "&pincode=" + edtPincode.getText().toString() + "&currentarea=" + edtCurrentArea.getText().toString() + "&contact=" + userDetails.get(SessionManager.KEY_USER_MOBILE) + "&updateddate=" + CommonMethods.getDateTime() + "");


///
//        updateAddressDetails?type=string&homephone=string&stateid=string&cityid=string&address=string&pincode=string&currentarea=string&contact=string&updateddate=string


        apiClient.updateAddressDetails("addressdetails", edtHomePhone.getText().toString(), Integer.parseInt(list_StateId.get(spnState.getSelectedIndex())), Integer.parseInt(list_CityId.get(spnCity.getSelectedIndex())), edtAddress.getText().toString(), edtPincode.getText().toString(), edtCurrentArea.getText().toString(), userDetails.get(SessionManager.KEY_USER_MOBILE), CommonMethods.convertToJsonDateFormat(CommonMethods.getDateCurrentDate())).enqueue(new Callback<UserDataResponse>() {
            @Override
            public void onResponse(Call<UserDataResponse> call, Response<UserDataResponse> response) {


                Log.d(TAG, "updateAddressDetails Response Code : " + response.code());

                if (response.code() == 200) {


                    String str_error = response.body().getMESSAGE();
                    String str_error_original = response.body().getORIGINALMESSAGE();
                    boolean error_status = response.body().getERRORSTATUS();
                    boolean record_status = response.body().getRECORDS();

                    if (error_status == false)
                    {

                        List<UserDataResponse.DATum> arr = response.body().getDATA();

                        for (int i = 0; i < arr.size(); i++)
                        {

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
                                CommonMethods.hideDialog(spotsDialog);
                                CommonMethods.showAlertDialog(getActivity(),"Contact Details Update Info","Your contact details has been successfully updated.");



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