package com.blacksmithyouthclub;

import android.annotation.SuppressLint;

import android.app.AlertDialog;


import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.blacksmithyouthclub.adapter.SurnameAdapterRecyclerView;
import com.blacksmithyouthclub.api.ApiClient;
import com.blacksmithyouthclub.api.ApiInterface;
import com.blacksmithyouthclub.helper.CommonMethods;
import com.blacksmithyouthclub.model.SurnamesData;
import com.blacksmithyouthclub.realm.model.UserMaster;
import com.blacksmithyouthclub.session.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurnamesActivity extends AppCompatActivity {

    @BindView(R.id.rvSurnames)
    RecyclerView rvSurnames;

    private SessionManager sessionManager;
    private Context context = this;
    private HashMap<String, String> userDetails = new HashMap<String, String>();
    private SpotsDialog spotsDialog;

    private String TAG = SurnamesActivity.class.getSimpleName();
    private SurnameAdapterRecyclerView adapter;

    private List<SurnamesData.DATum> list_Surnames = new ArrayList<SurnamesData.DATum>();
    private MenuItem searchMenuItem;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surnames);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        fab.setVisibility(View.GONE);


        setTitle(getString(R.string.title_activity_surnames));

        try {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        } catch (Exception e) {
            e.printStackTrace();
        }


        sessionManager = new SessionManager(context);
        userDetails = sessionManager.getSessionDetails();


        spotsDialog = new SpotsDialog(context);
        spotsDialog.setCancelable(false);

        LinearLayoutManager lManager = new LinearLayoutManager(context);
        rvSurnames.setLayoutManager(lManager);


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



        getAllSurnameData();



    }
    //onCreate completed

    private void getAllSurnameData()
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
        Log.d(TAG, "URL getAllSurname : " + CommonMethods.WEBSITE + "getAllSurname?type=surnameall&casteid=" + userDetails.get(SessionManager.KEY_SELECTED_CASTE) + "&userid=" + userDetails.get(SessionManager.KEY_USER_ID) + "&fcmtoken="+fcm_tokenid  +"");
        apiClient.getAllSurname("surnameall", Integer.parseInt(userDetails.get(SessionManager.KEY_SELECTED_CASTE)), userDetails.get(SessionManager.KEY_USER_ID),fcm_tokenid).enqueue(new Callback<SurnamesData>() {
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
                    CommonMethods.hideDialog(spotsDialog);
                    CommonMethods.onFailure(context, TAG, t);


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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_surnamedata, menu);

        // Associate searchable configuration with the SearchView



        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_search) {

            //sessionManager.setSearchType("surname");
            sessionManager.setSearchType(CommonMethods.SEARCH_BUSINESS_SURNAME);
            Intent intent = new Intent(context, SearchActivity.class);
            intent.putExtra(CommonMethods.ACTIVITY_NAME , TAG);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);


        }
        else if (item.getItemId()==android.R.id.home)
        {
            Intent intent=new Intent(context,DashBoardActivity.class);
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
        Intent intent=new Intent(context,DashBoardActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

}
