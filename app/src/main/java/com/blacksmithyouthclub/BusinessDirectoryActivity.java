package com.blacksmithyouthclub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.blacksmithyouthclub.adapter.BusinessCategoryAdapterRecyclerView;
import com.blacksmithyouthclub.adapter.BusinessSubCategoryAdapterRecyclerView;
import com.blacksmithyouthclub.api.ApiClient;
import com.blacksmithyouthclub.api.ApiInterface;
import com.blacksmithyouthclub.helper.CommonMethods;
import com.blacksmithyouthclub.model.BussinessCategoryData;
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

public class BusinessDirectoryActivity extends AppCompatActivity {


    @BindView(R.id.rvBusinessCategory)
    RecyclerView rvBusinessCategory;

    private Context context = this;
    private SessionManager sessionManager;
    private HashMap<String, String> userDetails = new HashMap<String, String>();
    private SpotsDialog spotsDialog;
    private String TAG = BusinessDirectoryActivity.class.getSimpleName();
    private List<BussinessCategoryData.DATum> list_BusinessCategory = new ArrayList<BussinessCategoryData.DATum>();
    private BusinessCategoryAdapterRecyclerView adapter;
    private MenuItem cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_directory);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        try {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        sessionManager = new SessionManager(context);
        userDetails = sessionManager.getSessionDetails();

        sessionManager.setSearchType(CommonMethods.SEARCH_BUSINESS_CATEGORY);


        spotsDialog = new SpotsDialog(context);
        spotsDialog.setCancelable(false);


        LinearLayoutManager lManager = new LinearLayoutManager(context);
        rvBusinessCategory.setLayoutManager(lManager);









        rvBusinessCategory.addOnItemTouchListener(new CommonMethods.RecyclerTouchListener(this, rvBusinessCategory, new CommonMethods.ClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View view, int position) {

                Log.d(TAG , "Selected Business CategoryID : "+String.valueOf(list_BusinessCategory.get(position).getId()) +" Title : "+String.valueOf(list_BusinessCategory.get(position).getBusinessName()));

                sessionManager.setSelectedBusinessCategoryDetails(String.valueOf(list_BusinessCategory.get(position).getId()), list_BusinessCategory.get(position).getBusinessName());

                Intent intent = new Intent(context, BusinessSubCategoryActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);


            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }
        ));

        getBusinessCategoryDataWithCountFromServer();
    }

    private void getBusinessCategoryDataWithCountFromServer() {
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

                            list_BusinessCategory = response.body().getDATA();


                            adapter = new BusinessCategoryAdapterRecyclerView(context, list_BusinessCategory);
                            rvBusinessCategory.setAdapter(adapter);

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
            public void onFailure(Call<BussinessCategoryData> call, Throwable t) {

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
            cart = (MenuItem) menu.findItem(R.id.menu_notification);
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
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(context, DashBoardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        } else if (item.getItemId() == R.id.menu_search) {


            sessionManager.setSearchType(CommonMethods.SEARCH_BUSINESS_CATEGORY);
            Intent intent = new Intent(context, SearchActivity.class);
            intent.putExtra(CommonMethods.ACTIVITY_NAME , TAG);
          //  intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(context, DashBoardActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

}
