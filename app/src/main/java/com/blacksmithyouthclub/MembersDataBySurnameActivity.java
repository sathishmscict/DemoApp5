package com.blacksmithyouthclub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.blacksmithyouthclub.adapter.SurnameAdapterRecyclerView;
import com.blacksmithyouthclub.adapter.SurnameWiseMemberDataAdapterRecyclerView;
import com.blacksmithyouthclub.api.ApiClient;
import com.blacksmithyouthclub.api.ApiInterface;
import com.blacksmithyouthclub.helper.CommonMethods;
import com.blacksmithyouthclub.model.MembersDataBySurname;
import com.blacksmithyouthclub.model.SurnamesData;
import com.blacksmithyouthclub.session.SessionManager;
import com.google.android.gms.common.SignInButton;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MembersDataBySurnameActivity extends AppCompatActivity {


    @BindView(R.id.rvMemebers)
    RecyclerView rvMemebers;
    private Context context = this;
    private SessionManager sessionManager;
    private HashMap<String, String> userDetails = new HashMap<String, String>();
    private SpotsDialog spotsDialog;
    private String TAG = MembersDataBySurnameActivity.class.getSimpleName();
    private SurnameWiseMemberDataAdapterRecyclerView adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_data_by_surname);

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



        LinearLayoutManager lManager = new LinearLayoutManager(context);
        rvMemebers.setLayoutManager(lManager);


        sessionManager = new SessionManager(context);
        userDetails = sessionManager.getSessionDetails();


        spotsDialog = new SpotsDialog(context);
        spotsDialog.setCancelable(false);


        getMemebersDataBySurname();


    }

    private void getMemebersDataBySurname() {
        CommonMethods.showDialog(spotsDialog);


        ApiInterface apiClient = ApiClient.getClient().create(ApiInterface.class);
        Log.d(TAG, "URL getAllUsersBySurname : " + CommonMethods.WEBSITE + "getAllUsersBySurname?type=user&surnameid=" + userDetails.get(SessionManager.KEY_SELECTED_SURNAME_ID) + "");
        apiClient.getAllUsersBySurnameId("user", userDetails.get(SessionManager.KEY_SELECTED_SURNAME_ID)).enqueue(new Callback<MembersDataBySurname>() {
            @Override
            public void onResponse(Call<MembersDataBySurname> call, Response<MembersDataBySurname> response) {


                Log.d(TAG, "getAllUsersBySurname Response Code : " + response.code());

                if (response.code() == 200) {


                    String str_error = response.body().getMESSAGE();
                    String str_error_original = response.body().getORIGINALMESSAGE();
                    boolean error_status = response.body().getERRORSTATUS();
                    boolean record_status = response.body().getRECORDS();

                    if (error_status == false) {
                        if (record_status == true) {

                            List<MembersDataBySurname.DATum> arr = response.body().getDATA();


                            adapter = new SurnameWiseMemberDataAdapterRecyclerView(context, arr);
                            rvMemebers.setAdapter(adapter);

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
            public void onFailure(Call<MembersDataBySurname> call, Throwable t) {

                CommonMethods.onFailure(context, TAG, t);

                CommonMethods.hideDialog(spotsDialog);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.


        try {
            getMenuInflater().inflate(R.menu.dash_board, menu);
            MenuItem cart = (MenuItem) menu.findItem(R.id.menu_notification);
            cart.setVisible(false);

            MenuItem search = (MenuItem) menu.findItem(R.id.menu_search);


//int orderid=db.getMaxOrderId();


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
        {
            Intent intent=new Intent(context,DashBoardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }
        else if (item.getItemId() == R.id.menu_search) {

            Intent intent = new Intent(context, SearchActivity.class);
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
